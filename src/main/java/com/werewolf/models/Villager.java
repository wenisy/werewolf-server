package com.werewolf.models;

import java.util.List;
import java.util.Map;

public class Villager extends Role{

    private static String name = "villager";
    private int type = VILLAGER;

    @Override
    public Map<String, Object> executeSpecialAction(Map<String, Object> param) {
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
        List<String> skills = super.getSkills();
        return skills;
    }
}
