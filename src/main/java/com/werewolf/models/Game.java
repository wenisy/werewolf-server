package com.werewolf.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class Game {

    private Map<Integer, Player> playerMap;
    private String gameId;

    private int villagerAmount;
    private int hunterAmount;
    private int prophetAmount;
    private int witchAmount;
    private int wolfAmount;

    public Game(Map<Integer, Player> playerMap) {
        this.playerMap = playerMap;
    }

    public Game(GameConfiguration gameConfiguration) {
        char[] digitals = "0123456789".toCharArray();
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            builder.append(digitals[random.nextInt(digitals.length)]);
        }
        this.gameId = builder.toString();
        this.playerMap = new HashMap<>();
    }

    public Optional<Player> getPlayerById(Integer id) {
        if(playerMap.containsKey(id)) {
            return Optional.of(playerMap.get(id));
        }
        return Optional.empty();
    }

    public String getGameId() {
        return gameId;
    }
}
