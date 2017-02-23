package com.werewolf.controllers;

import com.werewolf.GamePool;
import com.werewolf.models.Game;
import com.werewolf.models.Player;
import com.werewolf.models.response.GameResponseVO;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class PlayersController {
    private final Logger logger = LoggerFactory.getLogger(PlayersController.class);

    @Autowired
    private GamePool gamePool;
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @MessageMapping(value = "/players")
    public void readyToGame(@RequestBody String body, SimpMessageHeaderAccessor accessor) {
        String sessionId = accessor.getSessionId();
        String gameId = new JSONObject(body).getString("roomNum");
        Boolean isReady = new JSONObject(body).getBoolean("isReady");

        Game game = gamePool.getGameById(gameId);
        Optional<Player> player = game.getPlayerById(sessionId);

        player.ifPresent(p -> {
            p.setReady(isReady);
            String next = game.checkState();

            logger.info("Player {} is ready, role is {}, next state is: {}.",
                    p.getSeatId(), p.getRole().getName(), next);

            GameResponseVO response = new GameResponseVO().setMessage(next).setVoice(true);
            executor.submit(() -> {
                SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);

                String judgeSessionId = game.getJudge();
                headerAccessor.setSessionId(judgeSessionId);
                headerAccessor.setLeaveMutable(true);

                messagingTemplate.convertAndSendToUser(
                        judgeSessionId, "/queue/players", response, headerAccessor.getMessageHeaders());
            });
        });
    }
}
