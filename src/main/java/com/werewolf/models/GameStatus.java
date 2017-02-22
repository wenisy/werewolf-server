package com.werewolf.models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ctuo on 2/22/17.
 */
public class GameStatus {

  public GameStatus(int roomNO, int totalPlayerCount, HashMap<Integer, PlayerInfo> playerMap) {
    this.roomNo = roomNO;
    this.totalPlayerCount = totalPlayerCount;
    this.playerMap = playerMap;
  }

  private int roomNo;
  private int totalPlayerCount;
  private HashMap<Integer, PlayerInfo> playerMap;

  public boolean isPlayerAlive(int setNO) {
    return playerMap.get(setNO).isAlive();
  }

}
