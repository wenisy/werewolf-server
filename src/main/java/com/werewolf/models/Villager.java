package com.werewolf.models;

public class Villager extends Role{

    private static String name = "villager";
    private int type = VILLAGER;

    public Villager(){
        super();
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
