package com.werewolf.controllers;

import com.werewolf.models.Game;
import com.werewolf.models.GameConfiguration;
import com.werewolf.models.Player;
import com.werewolf.models.response.GameResponseVO;
import com.werewolf.services.GameService;
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

import java.util.Optional;

@Controller
public class GamesController {
    private final Logger logger = LoggerFactory.getLogger(GamesController.class);

    @Autowired
    private GameService gameService;
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    @Autowired
    private GameMessageBroker gameMessageBroker;

    @MessageMapping("/create")
    public void createGame(@RequestBody GameConfiguration gameConfiguration, SimpMessageHeaderAccessor accessor) {
        String sessionId = accessor.getSessionId();
        String roomNum = gameService.registerGame(gameConfiguration, sessionId);
        logger.info("Create new room {}.", roomNum);

        gameMessageBroker.sendMessage(accessor, new GameResponseVO().setRoomNum(roomNum));
    }

    @MessageMapping("/join")
    public void joinGame(@RequestBody String body, SimpMessageHeaderAccessor accessor) {
        String sessionId = accessor.getSessionId();
        String gameId = new JSONObject(body).get("roomNum").toString();
        Integer seatId = new JSONObject(body).getInt("seatNum");

        String roleName = gameService.fetchRole(sessionId, gameId, seatId);

        logger.info("Seat {} joined game {} successfully, role is {}.", seatId, gameId, roleName);

        gameMessageBroker.sendMessage(accessor, new GameResponseVO().setRole(roleName));
    }

    @MessageMapping(value = "/players")
    public void readyToGame(@RequestBody String body, SimpMessageHeaderAccessor accessor) {
        String sessionId = accessor.getSessionId();
        String gameId = new JSONObject(body).getString("roomNum");
        Boolean isReady = new JSONObject(body).getBoolean("isReady");

        Game game = gameService.getGameById(gameId);
        Optional<Player> player = game.getPlayerById(sessionId);

        player.ifPresent(p -> {
            p.setReady(isReady);
            String next = game.checkState();

            logger.info("Player {} is ready, role is {}, next state is: {}.",
                    p.getSeatId(), p.getRole().getName(), next);

            GameResponseVO response = new GameResponseVO().setMessage(next).setVoice(true);
            String judgeSessionId = game.getJudge();

            if (sessionId.equals(judgeSessionId)) {
                SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
                headerAccessor.setSessionId(judgeSessionId);
                headerAccessor.setLeaveMutable(true);
                gameMessageBroker.sendMessage(headerAccessor, response);
            }

        });
    }

}
