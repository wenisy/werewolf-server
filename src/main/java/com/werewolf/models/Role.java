package com.werewolf.models;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

abstract public class Role {

    protected RoleType roleType;

    protected Map<String, Function< Player, Map<String, Object>>> actionMap = new HashMap<>();

    protected Role(){
        this.actionMap.put("vote", (Player target) -> vote(target));
        this.actionMap.put("campaign", (Player target) -> campaign(target));
    }

    public Map<String, Function< Player, Map<String, Object>>> getActionMap(){
        return actionMap;
    }

    public Category getCategory() {
        return this.roleType.getCategory();
    }

    public String getName() {
        return this.roleType.getType();
    }

    public Faction getFaction() {
        return this.roleType.getFaction();
    }


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

    public Function<Player, ?> getAction(String action) {
        return this.actionMap.get(action);
    }
}
