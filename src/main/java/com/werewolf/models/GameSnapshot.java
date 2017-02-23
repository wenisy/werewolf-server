package com.werewolf.models;

import java.util.ArrayList;
import java.util.HashMap;

public class GameSnapshot {
    private ArrayList<PlayerSnapshot> playerSnapshots;

    public GameSnapshot(ArrayList<PlayerSnapshot> playerSnapshots) {
        this.playerSnapshots = playerSnapshots;
    }

    public boolean playersAreReady() {
        return playerSnapshots.stream()
                .map(PlayerSnapshot::isReady).reduce(true, (prev, next) -> prev && next);
    }

    public boolean wolfAgreed() {
        return playerSnapshots.stream().filter(playerSnapshot -> playerSnapshot.getRole() instanceof Werewolf)
                .map(PlayerSnapshot::getActionTarget).distinct().limit(2).count() <= 1;
    }

    public HashMap<String, Integer> getAlivePlayerInfo() {
        HashMap<String, Integer> alivePlayerInfo  = new HashMap<String, Integer>();
        alivePlayerInfo.put("GOD",0);
        alivePlayerInfo.put("WEREWOLF",0);
        alivePlayerInfo.put("VILLAGER",0);

        int tempCount = 0;
        for(PlayerSnapshot playerTemp : playerSnapshots) {
            if(playerTemp.isPlayerAlive()) {
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
