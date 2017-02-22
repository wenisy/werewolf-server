package com.werewolf.models;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Created by ctuo on 2/22/17.
 */
public class ProcessStatus {
  public ProcessStatus() {
    PROCESS_DATA_MAP = new HashMap<String, String>();
    PROCESS_DATA_MAP.put("NIGHT_START", "天黑了,请大家闭眼");
    PROCESS_DATA_MAP.put("WOLF_APPEAR", "狼人请睁眼");
    PROCESS_DATA_MAP.put("WOLF_KILL", "狼人请杀人,给我一个座位号");
    PROCESS_DATA_MAP.put("WOLF_UNIFY_OPINION", "狼人请统一意见");
    PROCESS_DATA_MAP.put("WOLF_VANISH", "狼人请闭眼");
    PROCESS_DATA_MAP.put("DAY_START", "天亮了,请大家睁眼");

    PROCESS_RELATION_MAP = new HashMap<String, String>();
    PROCESS_RELATION_MAP.put("NIGHT_START", "WOLF_APPEAR");
    PROCESS_RELATION_MAP.put("WOLF_APPEAR", "WOLF_KILL");
    PROCESS_RELATION_MAP.put("WOLF_KILL", "WOLF_VANISH");
    PROCESS_RELATION_MAP.put("WOLF_VANISH", "DAY_START");

    currentStatus = "NIGHT_START";
  }

  private HashMap<String, String> PROCESS_DATA_MAP;
  private HashMap<String, String> PROCESS_RELATION_MAP;
  private String currentStatus;

  public void setInitStatus() {
    this.currentStatus = "NIGHT_START";
  }

  public void setCurrentStatus(String currentStatus) {
    this.currentStatus = currentStatus;
  }

  public String getCurrentStatus() {
    return this.currentStatus;
  }

  public String goNextStatus(String previousStatus) {
    currentStatus = PROCESS_RELATION_MAP.get(previousStatus);
    return currentStatus;
  }

  public String getMessage() {
    return PROCESS_DATA_MAP.get(currentStatus);
  }

}