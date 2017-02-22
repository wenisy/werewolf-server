package com.werewolf.controllers;

import com.werewolf.GamePool;
import com.werewolf.models.Game;
import com.werewolf.models.Player;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class PlayersController {

    @Autowired
    private GamePool gamePool;
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    ExecutorService executor = Executors.newSingleThreadExecutor();

    @MessageMapping(value = "/players")
    public void readyToGame(@RequestBody String body, SimpMessageHeaderAccessor accessor) {
        String sessionId = accessor.getSessionId();
        String gameId = new JSONObject(body).getString("gameId");
        Boolean isReady = new JSONObject(body).getBoolean("isReady");

        Optional<Player> player = gamePool.getGameById(gameId).getPlayerById(sessionId);

        player.ifPresent(p -> p.setReady(isReady));
    }
}
