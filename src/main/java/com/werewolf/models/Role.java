package com.werewolf.models;

import java.util.Map;

abstract public class Role {

    public static int GOD = 0;
    public static int WEREWOLF = 1;
    public static int VILLAGER = 2;


    abstract public Object execute(Map<String, Object> param);

    abstract public int getType();

    abstract public String getName();




}
