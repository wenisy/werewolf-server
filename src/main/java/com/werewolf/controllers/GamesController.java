package com.werewolf.controllers;

import com.werewolf.models.GameConfiguration;
import com.werewolf.models.response.GameResponseVO;
import com.werewolf.services.GameService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GamesController {
    private final Logger logger = LoggerFactory.getLogger(GamesController.class);

    @Autowired
    private GameService gameService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @MessageMapping("/create")
    @SendToUser("/queue/players")
    @ResponseBody
    public GameResponseVO createGame(@RequestBody GameConfiguration gameConfiguration, SimpMessageHeaderAccessor accessor) {
        String sessionId = accessor.getSessionId();
        String roomNum = gameService.registerGame(gameConfiguration, sessionId);
        logger.info("Create new room {}.", roomNum);

        return new GameResponseVO().setRoomNum(roomNum);
    }

    @MessageMapping("/join")
    public void joinGame(@RequestBody String body, SimpMessageHeaderAccessor accessor) {

        String sessionId = accessor.getSessionId();
        String gameId = new JSONObject(body).get("roomNum").toString();
        Integer seatId = Integer.valueOf((String) new JSONObject(body).get("seatNum"));

        executor.submit(() -> join(accessor, sessionId, gameId, seatId));
    }

    private void join(SimpMessageHeaderAccessor accessor, String sessionId, String gameId, Integer seatId) {
        String roleName = gameService.fetchRole(sessionId, gameId, seatId);

        logger.info("Seat {} joined game {} successfully, role is {}.", seatId, gameId, roleName);

        GameResponseVO response = new GameResponseVO().setRole(roleName);
        messagingTemplate.convertAndSendToUser(
                sessionId,
                "/queue/players",
                response,
                accessor.getMessageHeaders()
        );
    }

}
