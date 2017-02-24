package com.werewolf.models;

import java.util.HashMap;
import java.util.Map;

public class Hunter extends Role{

    private int type = GOD;
    private boolean skillStatus = true;
    private static String name = "hunter";

    public Hunter() {
        super();
        this.actionMap.put("revenge", (Player target) -> revenge(target));
    }

    public boolean hasSkill() {
        return skillStatus;
    }

    public void setSkillStatus(boolean skillStatus) {
        this.skillStatus = skillStatus;
    }

    private Map<String, Object> revenge(Player target) {
        Map<String, Object> actionResult = new HashMap<>();

        if(hasSkill()){
            actionResult.put("kill", target);
        }
        return actionResult;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

}
