package com.werewolf.models;

import java.util.List;
import java.util.Map;

public class Werewolf extends Role{

    private static String name = "werewolf";
    private int type = WEREWOLF;
    private Map<String, Object> executeResult = null;


    @Override
    public Map<String, Object> executeSpecialAction(Map<String, Object> param) {
        Player player = (Player)param.get("Player");
        String action = (String)param.get("Action");
        if("suicide".equals(action)){
            executeResult.put("ActionResult", "suicide");
            executeResult.put("TargetSeatId", player.getSeatId());
        }else {
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
        skills.add("suicide");
        return skills;
    }
}
