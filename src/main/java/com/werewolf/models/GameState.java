package com.werewolf.models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class GameState {

  public GameState(int roomID, int totalPlayerCount, ArrayList<Player> playerList) {
    this.roomNo = roomID;
    this.totalPlayerCount = totalPlayerCount;
    this.playerList = playerList;
  }

  private int roomNo;
  private int totalPlayerCount;
  private ArrayList<Player> playerList;
  private int wolfKillID;

  public int getRoomNo() {
    return roomNo;
  }

  public int getTotalPlayerCount() {
    return totalPlayerCount;
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

  public int getWolfKillID() {
    return wolfKillID;
  }

  public void setWolfKillID(int killID) {
    wolfKillID = killID;
  }

}
