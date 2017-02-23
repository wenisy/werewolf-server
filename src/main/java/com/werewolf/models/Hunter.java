package com.werewolf.models;

import java.util.List;
import java.util.Map;

public class Hunter extends Role{

    private int type = GOD;
    private boolean skillStatus = true;
    private static String name = "Hunter";
    private Map<String, Object> executeResult = null;


    public boolean hasSkill() {
        return skillStatus;
    }

    public void setSkillStatus(boolean skillStatus) {
        this.skillStatus = skillStatus;
    }

    @Override
    public Map<String, Object> executeSpecialAction(Map<String, Object> param) {
        Player player = (Player)param.get("Player");

        if(skillStatus){
            executeResult.put("ActionResult", "kill");
            executeResult.put("TargetSeatId", player.getSeatId());
        }
        return executeResult;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getSkills() {
        List<String> skills = super.getSkills();
        skills.add("revenge");
        return skills;
    }
}
