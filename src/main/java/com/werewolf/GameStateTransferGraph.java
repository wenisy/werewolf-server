package com.werewolf;

import com.werewolf.models.GameSnapshot;
import com.werewolf.models.GameState;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GameStateTransferGraph {

    private Map<GameState, Map<GameSnapshot, GameState>> transferMap;

    public GameStateTransferGraph() {
        this.transferMap = new HashMap<>();
    }
}
