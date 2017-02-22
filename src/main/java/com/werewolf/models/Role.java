package com.werewolf.models;

abstract public class Role {

    public static int GOD = 0;
    public static int WEREWOLF = 1;
    public static int VILLAGER = 2;

    abstract public void execute(Player player);

    abstract public int getType();




}
