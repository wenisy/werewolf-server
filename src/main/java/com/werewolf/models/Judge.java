package com.werewolf.models;
import java.util.HashMap;
/**
 * Created by ctuo on 2/22/17.
 */
public class Judge {
  public Judge(GameState initGameState) {
    this.currentGameState = initGameState;
    processRelation = new ProcessRelation();

    PROCESS_DATA_MAP = new HashMap<String, String>();
    PROCESS_DATA_MAP.put("NIGHT_START", "天黑了,请大家闭眼");
    PROCESS_DATA_MAP.put("WOLF_APPEAR", "狼人请睁眼");
    PROCESS_DATA_MAP.put("WOLF_KILL", "狼人请杀人,给我一个座位号");
    PROCESS_DATA_MAP.put("WOLF_UNIFY_OPINION", "狼人请统一意见");
    PROCESS_DATA_MAP.put("WOLF_VANISH", "狼人请闭眼");
    PROCESS_DATA_MAP.put("DAY_START", "天亮了,请大家睁眼");
  }

  private HashMap<String, String> PROCESS_DATA_MAP;
  private GameState currentGameState;
  private ProcessRelation processRelation;

  public String start() {
    processRelation.setInitState();
    return PROCESS_DATA_MAP.get(processRelation.getCurrentState());
  }

  public String next(GameState currentGameState) {
    this.currentGameState = currentGameState;
    processRelation.goNextState(processRelation.getCurrentState());
    return PROCESS_DATA_MAP.get(processRelation.getCurrentState());
  }

  public void updateGameState(GameState gameStatue) {
    currentGameState = gameStatue;
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
