package com.werewolf.models;

import java.util.Map;

public class Villager extends Role{

    private static String name = "VILLAGER";
    private int type = VILLAGER;

    @Override
    public Object execute(Map<String, Object> param) {
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
}
