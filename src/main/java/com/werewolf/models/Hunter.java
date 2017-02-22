package com.werewolf.models;

import java.util.Map;

public class Hunter extends Role{

    private int type = GOD;
    private boolean skillStatus = true;
    private static String name = "HUNTER";
    private ExecuteResultModel result;


    public boolean hasSkill() {
        return skillStatus;
    }

    public void setSkillStatus(boolean skillStatus) {
        this.skillStatus = skillStatus;
    }

    @Override
    public Object execute(Map<String, Object> param) {
        Player player = (Player)param.get("Player");
        result.setTargetSitId(player.getSitId());
        if(hasSkill()){
            result.setExecuteResult(false);
        }
        return result;
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
