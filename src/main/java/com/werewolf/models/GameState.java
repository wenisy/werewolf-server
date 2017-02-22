package com.werewolf.models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public enum GameState {

    INIT("游戏创建成功"),
    NIGHT("天黑请闭眼");

    private final String message;
    private GameSnapshot gameSnapshot;

    private int roomNo;
    private int totalPlayerCount;
    private ArrayList<Player> playerList;

    public static GameState init(GameSnapshot initGameSnapshot) {
        return GameState.INIT;
    }

    GameState(String message) {
        this.message = message;
    }

    public void setGameSnapshot(GameSnapshot snapshot) {
        this.gameSnapshot = snapshot;
    }

    public HashMap<String, Integer> getAlivePlayerInfo() {
        HashMap<String, Integer> alivePlayerInfo  = new HashMap<String, Integer>();
        alivePlayerInfo.put("GOD",0);
        alivePlayerInfo.put("WEREWOLF",0);
        alivePlayerInfo.put("VILLAGER",0);

        int tempCount = 0;
        for(Player playerTemp : playerList) {
            if(playerTemp.isAlive()) {
                switch(playerTemp.getRole().getType()) {
                    case (0):
                        tempCount = alivePlayerInfo.get("GOD");
                        tempCount ++;
                        alivePlayerInfo.put("GOD", tempCount);
                        break;
                    case (1):
                        tempCount = alivePlayerInfo.get("WEREWOLF");
                        tempCount ++;
                        alivePlayerInfo.put("WEREWOLF", tempCount);
                        break;
                    case (2):
                        tempCount = alivePlayerInfo.get("VILLAGER");
                        tempCount ++;
                        alivePlayerInfo.put("VILLAGER", tempCount);
                        break;
                }
            }
        }
        return alivePlayerInfo;
    }

}
