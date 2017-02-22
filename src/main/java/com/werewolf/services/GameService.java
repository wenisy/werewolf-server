package com.werewolf.services;

import com.werewolf.GamePool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameService {
    @Autowired
    private GamePool gamePool;

    public String fetchRole(String sessionId, Integer gameId, Integer seatId) {
        return "女巫";
    }
}
