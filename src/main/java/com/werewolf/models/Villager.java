package com.werewolf.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Villager extends Role{

    private static String name = "VILLAGER";
    private int type = VILLAGER;

    @Override
    public Map<Integer, String> execute(Map<String, Object> param) {
        return null;
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
        return skills;
    }
}
