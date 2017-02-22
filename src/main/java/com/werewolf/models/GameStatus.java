package com.werewolf.models;

import java.util.HashMap;

public class GameStatus {

  public GameStatus(int roomNO, int totalPlayerCount, HashMap<Integer, Player> playerMap) {
    this.roomNo = roomNO;
    this.totalPlayerCount = totalPlayerCount;
    this.playerMap = playerMap;
  }

  private int roomNo;
  private int totalPlayerCount;
  private HashMap<Integer, Player> playerMap;

  public boolean isPlayerAlive(int setNO) {
    return playerMap.get(setNO).isAlive();
  }

}
