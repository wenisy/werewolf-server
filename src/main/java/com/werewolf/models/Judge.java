package com.werewolf.models;

import java.util.HashMap;

/**
 * Created by ctuo on 2/22/17.
 */
public class Judge {
  public Judge(GameSnapshot initGameSnapshot) {
    this.current = GameState.INIT;
    stateRelation = new StateRelation();

    STATE_DATA_MAP = new HashMap<String, String>();
    STATE_DATA_MAP.put("NIGHT_START", "天黑了,请大家闭眼");
    STATE_DATA_MAP.put("WOLF_APPEAR", "狼人请睁眼");
    STATE_DATA_MAP.put("WOLF_KILL", "狼人请杀人,给我一个座位号");
    STATE_DATA_MAP.put("WOLF_UNIFY_OPINION", "狼人请统一意见");
    STATE_DATA_MAP.put("WOLF_VANISH", "狼人请闭眼");
    STATE_DATA_MAP.put("WITCH_APPEAR", "女巫请睁眼");
    STATE_DATA_MAP.put("WITCH_VANISH", "女巫请闭眼");
    STATE_DATA_MAP.put("PROPHET_APPEAR", "预言家请睁眼");
    STATE_DATA_MAP.put("PROPHET_VANISH", "预言家请闭眼");
    STATE_DATA_MAP.put("HUNTER_APPEAR", "猎人请睁眼");
    STATE_DATA_MAP.put("HUNTER_VANISH", "猎人请闭眼");
    STATE_DATA_MAP.put("DAY_START", "天亮了,请大家睁眼");
    STATE_DATA_MAP.put("APPLY_SHERIFF", "请大家考虑,是否要上警");
    STATE_DATA_MAP.put("VOTE_FOR_APPLY_SHERIFF", "要上警的请投票,5,4,3,2,1");
    STATE_DATA_MAP.put("SHERIFF_CANDIDATE_RESULT", "上警的人有");
    STATE_DATA_MAP.put("SHERIFF_CANDIDATE_SPEECH", "请上警的人从小号开始发言");
    STATE_DATA_MAP.put("VOTE_FOR_SHERIFF", "请大家选举警长,5,4,3,2,1");
    STATE_DATA_MAP.put("SHERIFF_RESULT", "警长是");
    STATE_DATA_MAP.put("NIGHT_RESULT", "昨天晚上死的人是");
    STATE_DATA_MAP.put("DAY_SPEECH", "请警长组织发言");
    STATE_DATA_MAP.put("DAY_RESULT", "被投死的是");
    STATE_DATA_MAP.put("DEATH_SPEECH", "请留遗言");

  }

  private final HashMap<String, String> STATE_DATA_MAP;
  private GameState current;
  private StateRelation stateRelation;

  public String start() {
    stateRelation.setInitState();
    return STATE_DATA_MAP.get(stateRelation.getCurrentState());
  }

  public void updateGameState(GameState gameStatue) {
    current = gameStatue;
  }
    public String next(GameSnapshot nextSnapshot) {
        GameState nextState = GameState.transfer(current, nextSnapshot);
        nextState.setGameSnapshot(nextSnapshot);
        current = nextState;
        return getCurrent().getMessage();
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

    public GameState getCurrent() {
        return current;
    }
}
