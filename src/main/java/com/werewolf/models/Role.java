package com.werewolf.models;

import java.util.List;
import java.util.Map;

abstract public class Role {

    public static int GOD = 0;
    public static int WEREWOLF = 1;
    public static int VILLAGER = 2;


    abstract public Map<Integer, String> execute(Map<String, Object> param);

    abstract public int getType();

    abstract public String getName();

    abstract public List<String> getSkills();


}
