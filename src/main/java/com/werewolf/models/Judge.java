package com.werewolf.models;

import java.util.HashMap;

/**
 * Created by ctuo on 2/22/17.
 */
public class Judge {
  public Judge(GameStatus initGameStatus) {
    this.currentGameStatus = initGameStatus;
    processStatus = POCESS_STATUS.NIGHT_START;
  }


  private enum POCESS_STATUS {
    NIGHT_START("天黑了,请大家闭眼"),
    WOLF_APPEAR("狼人请睁眼"),
    WOLF_KILL("狼人请杀人,给我一个座位号"),
    WOLF_UNIFY_OPINION("狼人请统一意见"),
    WOLF_VANISH("狼人请闭眼"),
    DAY_START("天亮了,请大家睁眼");

    private String message;

    POCESS_STATUS (String message) {
      this.message = message;
    }

    public String getMessage() {
      return message;
    }
  }

  private POCESS_STATUS processStatus;

  private GameStatus currentGameStatus;

  public String start() {
    return POCESS_STATUS.NIGHT_START.getMessage();
  }

  public String next(GameStatus currentGameStatus) {
    this.currentGameStatus = currentGameStatus;
    if(processStatus == POCESS_STATUS.NIGHT_START) {
      processStatus = POCESS_STATUS.WOLF_APPEAR;
      return POCESS_STATUS.WOLF_APPEAR.getMessage();
    }
    return "";
  }

  public String callWitch() {
      return "";
  }

  public String callProphet() {
      return "";
  }

  public String callHunter() {
      return "";
  }

  public String callSheriff() {
      return "";
  }

  public Boolean isEnd() {
    return false;
  }

}
