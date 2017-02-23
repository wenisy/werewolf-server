package com.werewolf.models;

import java.util.List;
import java.util.Map;


public class Prophet extends Role {

    private int type = GOD;
    private Map<String, Object> executeResult = null;
    private static String name = "Prophet";

    @Override
    public Map<String, Object> executeSpecialAction(Map<String, Object> param) {
        Player player = (Player)param.get("Player");

        if(1 == player.getRole().getType()){
            executeResult.put("ActionResult", "isWerewolf");
            executeResult.put("TargetSeatId", player.getSeatId());
        }else{
            executeResult.put("ActionResult", "isGoodMan");
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
        skills.add("prophesy");
        return skills;
    }


}
