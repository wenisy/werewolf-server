package com.werewolf.controllers;

import com.werewolf.GamePool;
import com.werewolf.models.Game;
import com.werewolf.models.GameConfiguration;
import com.werewolf.services.GameService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class GamesController {
    private final Logger logger = LoggerFactory.getLogger(GamesController.class);

    @Autowired
    private GamePool gamePool;
    @Autowired
    private GameService gameService;
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    ExecutorService executor = Executors.newSingleThreadExecutor();

    @MessageMapping("/create")
    @SendToUser("/queue/judge")
    public ResponseEntity<String> createGame(@RequestBody GameConfiguration gameConfiguration, SimpMessageHeaderAccessor accessor) {
        String sessionId = accessor.getSessionId();

        if (gameConfiguration == null) {
            gameConfiguration = new GameConfiguration();
            gameConfiguration
                    .setHasSheriff(true)
                    .setHunter(1)
                    .setProphet(1)
                    .setVillager(3)
                    .setWitch(1)
                    .setWolf(3);
        }
        Game game = new Game(gameConfiguration);
        gamePool.registerGame(game);

        logger.info("Create new room {}.", game.getGameId());

        return ResponseEntity.ok().body(game.getGameId());
    }

    @MessageMapping("/join")
    public void joinGame(@RequestBody String body, SimpMessageHeaderAccessor accessor) {

        String sessionId = accessor.getSessionId();
        String gameId = new JSONObject(body).get("roomNum").toString();
        Integer seatId = Integer.valueOf((String) new JSONObject(body).get("seatNum"));

        executor.submit(() -> {
            join(accessor, sessionId, gameId, seatId);
        });
    }

    private void join(SimpMessageHeaderAccessor accessor, String sessionId, String gameId, Integer seatId) {
        String roleName = gameService.fetchRole(sessionId, gameId, seatId);

        logger.info("Seat {} joined game {} successfully, role is {}.", seatId, gameId, roleName);

        messagingTemplate.convertAndSendToUser(
                sessionId,
                "/queue/players",
                "http://tsn.baidu.com/text2audio?tex=" + roleName + "&lan=zh&cuid=1&ctp=1&tok=24.2beb0786a12a2b365a92239414f5b6db.2592000.1488864448.282335-9247277%22",
                accessor.getMessageHeaders()
        );
    }

}
