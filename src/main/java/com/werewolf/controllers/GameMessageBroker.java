package com.werewolf.controllers;

import com.werewolf.models.response.GameResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class GameMessageBroker {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public void sendMessage(SimpMessageHeaderAccessor accessor, GameResponseVO response) {
        String sessionId = accessor.getSessionId();

        executor.submit(() ->
            messagingTemplate.convertAndSendToUser(
                    sessionId,
                    "/queue/players",
                    response,
                    accessor.getMessageHeaders()
            )
        );

    }
}
