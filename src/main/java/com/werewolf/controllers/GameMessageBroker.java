package com.werewolf.controllers;

import com.werewolf.models.response.GameResponseVO;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.stereotype.Component;

@Component
public class GameMessageBroker {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public void sendMessageToJudge(String judgeSessionId, GameResponseVO response) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(judgeSessionId);
        headerAccessor.setLeaveMutable(true);

        executor.submit(() ->
                messagingTemplate.convertAndSendToUser(
                        judgeSessionId,
                        "/queue/players",
                        response,
                        headerAccessor.getMessageHeaders()
                )
        );
    }
}
