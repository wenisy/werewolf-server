package com.werewolf.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Prophet extends Role {

    private int type = GOD;
    private Map<Integer, String> executeResult = null;
    private static String name = "PROPHET";

    @Override
    public Map<Integer, String> execute(Map<String, Object> param) {
        Player player = (Player)param.get("Player");

        if(1 == player.getRole().getType()){
            executeResult.put(player.getSeatId(), "isWerewolf");
        }else{
            executeResult.put(player.getSeatId(), "isGoodMan");
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
        skills.add("prophesy");
        return skills;
    }


}
