package com.werewolf.models;

import java.util.*;
import java.util.stream.IntStream;

public class Game {

    private Map<String, Player> playerMap;
    private String gameId;
    private LinkedList<RoleType> playerQueue = new LinkedList<>();

    public Game(GameConfiguration gameConfiguration) {
        char[] digitals = "0123456789".toCharArray();
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            builder.append(digitals[random.nextInt(digitals.length)]);
        }
        this.gameId = builder.toString();
        this.playerMap = new HashMap<>();

        initPlayerQueue(gameConfiguration);
    }

    private void initPlayerQueue(GameConfiguration gameConfiguration) {
        IntStream.range(0, gameConfiguration.getWolf()).forEach(i -> playerQueue.add(RoleType.WEREWOLF));
        IntStream.range(0, gameConfiguration.getVillager()).forEach(i -> playerQueue.add(RoleType.VILLAGER));
        IntStream.range(0, gameConfiguration.getHunter()).forEach(i -> playerQueue.add(RoleType.HUNTER));
        IntStream.range(0, gameConfiguration.getProphet()).forEach(i -> playerQueue.add(RoleType.PROPHET));
        IntStream.range(0, gameConfiguration.getWitch()).forEach(i -> playerQueue.add(RoleType.WITCH));

        Collections.shuffle(playerQueue);
    }

    public Optional<Player> getPlayerById(String sessionId) {
        if(playerMap.containsKey(sessionId)) {
            return Optional.of(playerMap.get(sessionId));
        }
        return Optional.empty();
    }

    public String getGameId() {
        return gameId;
    }

    public Queue<RoleType> getPlayerQueue() {
        return playerQueue;
    }

    public void addPlayer(String sessionId, Player player) {
        playerMap.put(sessionId, player);
    }

    public Map<String, Player> getPlayerMap() {
        return playerMap;
    }
}
