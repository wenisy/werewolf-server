package com.werewolf.models;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Created by ctuo on 2/22/17.
 */
public class StateRelation {
  public StateRelation() {
//    STATE_RELATION_MAP = new HashMap<String, String>();
//    STATE_RELATION_MAP.put("NIGHT_START", "WOLF_APPEAR");
//    STATE_RELATION_MAP.put("WOLF_APPEAR", "WOLF_KILL");
//    STATE_RELATION_MAP.put("WOLF_KILL", "WOLF_VANISH");
//    STATE_RELATION_MAP.put("WOLF_VANISH", "WITCH_APPEAR");
//    STATE_RELATION_MAP.put("WITCH_APPEAR", "WITCH_VANISH");
//    STATE_RELATION_MAP.put("WITCH_VANISH", "PROPHET_APPEAR");
//    STATE_RELATION_MAP.put("PROPHET_APPEAR", "PROPHET_VANISH");
//    STATE_RELATION_MAP.put("PROPHET_VANISH", "HUNTER_APPEAR");
//    STATE_RELATION_MAP.put("HUNTER_APPEAR", "HUNTER_VANISH");
//    STATE_RELATION_MAP.put("HUNTER_VANISH", "DAY_START");

    currentState = "NIGHT_START";
  }
  
  private HashMap<String, String> STATE_RELATION_MAP;
  private String currentState;

  public void setInitState() {
    this.currentState = "NIGHT_START";
  }

  public void setCurrentState(String currentState) {
    this.currentState = currentState;
  }

  public String getCurrentState() {
    return this.currentState;
  }

  public String goNextState(String previousState) {
    currentState = STATE_RELATION_MAP.get(previousState);
    return currentState;
  }

}