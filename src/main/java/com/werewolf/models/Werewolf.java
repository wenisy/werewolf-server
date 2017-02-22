package com.werewolf.models;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Werewolf extends Role{

    private static String name = "WEREWOLF";
    private int type = WEREWOLF;
    private Map<Integer, String> executeResult = null;


    @Override
    public Map<Integer, String> execute(Map<String, Object> param) {
        Player player = (Player)param.get("Player");
        String action = (String)param.get("Action");
        if("suicide".equals(action)){
            executeResult.put(player.getSeatId(), "suicide");
        }else {
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
        skills.add("suicide");
        return skills;
    }
}
