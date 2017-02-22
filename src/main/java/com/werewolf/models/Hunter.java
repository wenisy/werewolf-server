package com.werewolf.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Hunter extends Role{

    private int type = GOD;
    private boolean skillStatus = true;
    private static String name = "HUNTER";
    private Map<Integer, String> executeResult = null;


    public boolean hasSkill() {
        return skillStatus;
    }

    public void setSkillStatus(boolean skillStatus) {
        this.skillStatus = skillStatus;
    }

    @Override
    public Map<Integer, String> execute(Map<String, Object> param) {
        Player player = (Player)param.get("Player");

        if(skillStatus){
            executeResult.put(player.getSeatId(), "killed");
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
        List<String> skills = new ArrayList<>();
        skills.add("revenge");
        return skills;
    }
}
