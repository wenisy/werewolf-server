package com.werewolf.models;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

abstract public class Role {

    public static int GOD = 0;
    public static int WEREWOLF = 1;
    public static int VILLAGER = 2;

    protected Map<String, Function< Player, Map<String, Object>>> actionMap = new HashMap<>();
    protected Boolean skillComplete = false;

    protected Role(){
        this.actionMap.put("vote", (Player target) -> vote(target));
        this.actionMap.put("campaign", (Player target) -> campaign(target));
    }

    public Map<String, Function< Player, Map<String, Object>>> getActionMap(){
        return actionMap;
    }

//    public Map<String, Object> executeAction(Map<String, Object> param){
//        String actionName = (String)param.get("action");
//        Integer target = (Integer)param.get("target");
//
//        Function action = actionMap.get(actionName);
//
//        return (Map<String, Object>) action.apply(target);
//    }

    abstract public int getType();

    abstract public String getName();


    protected Map<String, Object> vote(Player target){
        Map<String, Object> actionResult = new HashMap<>();
        actionResult.put("Action", "vote");
        actionResult.put("Object", target.getSeatId());
        return actionResult;
    }

    protected Map<String, Object> campaign(Player target){
        Map<String, Object> actionResult = new HashMap<>();
        target.campaignForSheriff();
        actionResult.put("Action", "campaign");
        actionResult.put("Object", target);
        return actionResult;
    }

    protected void setSkillComplete(){

    }

    public Function<Player, ?> getAction(String action) {
        return this.actionMap.get(action);
    }
}
