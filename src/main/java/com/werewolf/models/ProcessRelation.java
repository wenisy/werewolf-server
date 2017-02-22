package com.werewolf.models;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Created by ctuo on 2/22/17.
 */
public class ProcessRelation {
  public ProcessRelation() {
    PROCESS_RELATION_MAP = new HashMap<String, String>();
    PROCESS_RELATION_MAP.put("NIGHT_START", "WOLF_APPEAR");
    PROCESS_RELATION_MAP.put("WOLF_APPEAR", "WOLF_KILL");
    PROCESS_RELATION_MAP.put("WOLF_KILL", "WOLF_VANISH");
    PROCESS_RELATION_MAP.put("WOLF_VANISH", "DAY_START");

    currentState = "NIGHT_START";
  }

  private HashMap<String, String> PROCESS_RELATION_MAP;
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
    currentState = PROCESS_RELATION_MAP.get(previousState);
    return currentState;
  }

}