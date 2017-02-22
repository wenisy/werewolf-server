package com.werewolf.models;

import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class Game {

    private Map<Integer, Player> playerMap;

    public Game(Map<Integer, Player> playerMap) {
        this.playerMap = playerMap;
    }

    public Game(GameConfiguration gameConfiguration) {

    }

    public Optional<Player> getPlayerById(Integer id) {
        if(playerMap.containsKey(id)) {
            return Optional.of(playerMap.get(id));
        }
        return Optional.empty();
    }
}
