package com.werewolf.models;

/**
 * Created by ctuo on 2/22/17.
 */
public class Judge {
  public Judge(GameStatus initGameStatus) {
    this.currentGameStatus = initGameStatus;
    processStatus = new ProcessStatus();
  }

  private GameStatus currentGameStatus;
  private ProcessStatus processStatus;

  public String start() {
    processStatus.setInitStatus();
    return processStatus.getMessage();
  }

  public String next(GameStatus currentGameStatus) {
    this.currentGameStatus = currentGameStatus;
    processStatus.goNextStatus(processStatus.getCurrentStatus());
    return processStatus.getMessage();
  }

  public void updateGameStatus(GameStatus gameStatue) {
    currentGameStatus = gameStatue;
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
