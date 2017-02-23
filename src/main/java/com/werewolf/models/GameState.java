package com.werewolf.models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.concurrent.TimeUnit;

/**
 * Created by wzhao on 22/02/2017.
 */
public enum GameState {

    NIL(""),
    INIT("游戏创建成功"),
    NIGHT("天黑请闭眼"),
    WOLF_KILL("狼人请杀人"), WOLF_KILL_COMPLETE("狼人请闭眼"), WOLF_KILL_RESET("狼人请统一意见"),
    MORNING("天亮了");

    private final String message;
    private GameSnapshot gameSnapshot;

    private int roomNo;
    private int totalPlayerCount;
    private ArrayList<Player> playerList;

    GameState(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public GameState setGameSnapshot(GameSnapshot snapshot) {
        this.gameSnapshot = snapshot;
        return this;
    }

    public static GameState transfer(GameState current, GameSnapshot incomingSnapshot) {
        GameState next = NIL;
        switch (current) {
            case INIT: {
                if(incomingSnapshot.playersAreReady()) {
                    next = NIGHT;
                }
                break;
            }
            case NIGHT: {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException ignored) {}
                next = WOLF_KILL;
                break;
            }
            case WOLF_KILL: {
                if (incomingSnapshot.wolfAgreed()) {
                    next = WOLF_KILL_COMPLETE;
                } else {
                    next = WOLF_KILL_RESET;
                }
                break;
            }
            case WOLF_KILL_RESET: {
                if (incomingSnapshot.wolfAgreed()) {
                    next = WOLF_KILL_COMPLETE;
                } else {
                    next = WOLF_KILL_RESET;
                }
                break;
            }
            case WOLF_KILL_COMPLETE: {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException ignored) {}
                next = MORNING;
                break;
            }
            default: break;
        }
        return next;
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
