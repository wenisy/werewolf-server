package com.werewolf.controllers;

import com.werewolf.GamePool;
import com.werewolf.models.Game;
import com.werewolf.models.GameConfiguration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GamesController {

    @Autowired
    private GamePool gamePool;
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    ExecutorService executor = Executors.newSingleThreadExecutor();

    @RequestMapping(value = "/games", method = RequestMethod.POST)
    public ResponseEntity<String> createGame(@RequestBody GameConfiguration gameConfiguration) {
        Game game = new Game(gameConfiguration);
        return ResponseEntity.ok().body(game.toString());
    }

    @MessageMapping("/join")
    @SendToUser("/queue/{seatId}")
    public void joinGame(@RequestBody String body, SimpMessageHeaderAccessor accessor) {

        String sessionId = accessor.getSessionId();
        Integer gameId = (Integer) new JSONObject(body).get("gameId");
        Integer seatId = (Integer) new JSONObject(body).get("seatId");

        executor.submit(() -> {
            join(sessionId, gameId, seatId);
        });
    }

    private void join(String sessionId, Integer gameId, Integer seatId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);

        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);

        messagingTemplate.convertAndSendToUser(sessionId,"/queue/" + seatId, "http://tsn.baidu.com/text2audio?tex=" + seatId + "&lan=zh&cuid=1&ctp=1&tok=24.2beb0786a12a2b365a92239414f5b6db.2592000.1488864448.282335-9247277%22",
                headerAccessor.getMessageHeaders());
    }

}
