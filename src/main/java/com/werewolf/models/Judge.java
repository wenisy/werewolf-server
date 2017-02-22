package com.werewolf.models;

/**
 * Created by ctuo on 2/22/17.
 */
public class Judge {
  public Judge(GameStatus gameStatus) {

  }
  
  public String nightComing() {
    return "天黑了,请大家闭眼";
  }

  public String dayComing() {
    return "天亮了,请大家睁眼";
  }

  public String callWolftoAppear() {
    return "狼人请睁眼";
  }

  public String callWolftoKill() {
    return "狼人请杀人,给我一个座位号";
  }

  public String unifyOpinion() {
    return "狼人请统一意见";
  }

  public String callWolftoVanish () {
    return "狼人请闭眼";
  }

  public String callWitch() {

  }

  public String callProphet() {

  }

  public String callHunter() {

  }

  public String callSheriff() {

  }

  public Boolean isEnd() {
    return false
  }

}
