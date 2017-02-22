package com.werewolf.models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class GameState {

  public GameState(int roomNO, int totalPlayerCount, HashMap<Integer, Player> playerMap) {
    this.roomNo = roomNO;
    this.totalPlayerCount = totalPlayerCount;
    this.playerMap = playerMap;
  }

  private int roomNo;
  private int totalPlayerCount;
  private HashMap<Integer, Player> playerMap;
  private int wolfKillNO;

  public int getRoomNo() {
    return roomNo;
  }

  public int getTotalPlayerCount() {
    return totalPlayerCount;
  }

  public int getWolfKillNO() {
    return wolfKillNO;
  }

  public void setWolfKillNO(int killNO) {
    wolfKillNO = killNO;
  }

  public boolean isPlayerAlive(int setNO) {
    return playerMap.get(setNO).isAlive();
  }



}
