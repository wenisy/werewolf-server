package com.werewolf.models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class GameSnapshot {
    private ArrayList<PlayerSnapshot> playerSnapshots;

    public int getNumberOfEmptySeat() {
        return numberOfEmptySeat;
    }

    private int numberOfEmptySeat;

    public GameSnapshot(ArrayList<PlayerSnapshot> playerSnapshots, int numberOfEmptySeat) {
        this.playerSnapshots = playerSnapshots;
        this.numberOfEmptySeat = numberOfEmptySeat;
    }

    public boolean playersAreReady() {
        return playerSnapshots.stream()
                .map(PlayerSnapshot::isReady).reduce(true, (prev, next) -> prev && next);
    }

    public boolean wolfAgreed() {
        return playerSnapshots.stream().filter(playerSnapshot -> playerSnapshot.getRole() instanceof Werewolf)
                .map(PlayerSnapshot::getActionTarget).distinct().limit(2).count() <= 1;
    }

    //check whether players need to apply sheriff
    public boolean needApplySheriff() {
        boolean applySheriff = true;
        for(PlayerSnapshot playerTemp : playerSnapshots) {
            if(playerTemp.isSheriff()) {
                applySheriff= false;
                break;
            }
        }
        return applySheriff;
    }

    //get which player are applying sheriff
    public ArrayList<Integer> getApplySheriffID() {
        ArrayList<Integer> applySheriffPlayerIDs = new ArrayList<Integer>();
        for(PlayerSnapshot playerTemp : playerSnapshots) {
            if(playerTemp.isApplySheriff()) {
                applySheriffPlayerIDs.add(playerTemp.getSeatID());
            }
        }
        return applySheriffPlayerIDs;
    }

    //get sheriff id, if return 0 means no sheriff
    public int getSheriffID() {
        int sheriffID = 0;
        for(PlayerSnapshot playerTemp : playerSnapshots) {
            if(playerTemp.isSheriff()) {
                sheriffID = playerTemp.getSeatID();
                break;
            }
        }
        return sheriffID;
    }

    //get total alive player count
    public int getAlivePlayerCount() {
        int totalAliveCount = 0;
        for(PlayerSnapshot playerTemp : playerSnapshots) {
            if (playerTemp.isPlayerAlive()) {
                totalAliveCount++;
            }
        }
        return totalAliveCount;
    }

    //get seat ID of all dead players
    public ArrayList<Integer> getDeadPlayer() {
        ArrayList<Integer> deadPlayerList = new ArrayList<Integer>();
        for(PlayerSnapshot playerTemp : playerSnapshots) {
            if (!playerTemp.isPlayerAlive()) {
                deadPlayerList.add(playerTemp.getSeatID());
            }
        }
        return deadPlayerList;
    }

    //get information of all alive role, like{"GOD" -> 3, "WEREWOLF" -> 3, "VILLAGER" -> 3}
    public HashMap<String, Integer> getAliveRoleInfo() {
        HashMap<String, Integer> alivePlayerInfo  = new HashMap<String, Integer>();
        alivePlayerInfo.put("GOD",0);
        alivePlayerInfo.put("WEREWOLF",0);
        alivePlayerInfo.put("VILLAGER",0);

        int godAliveCount, woflAliveCount, villagerAliverCount = 0;
        for(PlayerSnapshot playerTemp : playerSnapshots) {
            if(playerTemp.isPlayerAlive()) {
                switch(playerTemp.getRole().getType()) {
                    case (0):
                        godAliveCount = alivePlayerInfo.get("GOD");
                        godAliveCount ++;
                        alivePlayerInfo.put("GOD", godAliveCount);
                        break;
                    case (1):
                        woflAliveCount = alivePlayerInfo.get("WEREWOLF");
                        woflAliveCount ++;
                        alivePlayerInfo.put("WEREWOLF", woflAliveCount);
                        break;
                    case (2):
                        villagerAliverCount = alivePlayerInfo.get("VILLAGER");
                        villagerAliverCount ++;
                        alivePlayerInfo.put("VILLAGER", villagerAliverCount);
                        break;
                }
            }
        }
        return alivePlayerInfo;
    }
}
