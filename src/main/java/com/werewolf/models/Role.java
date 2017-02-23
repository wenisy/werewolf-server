package com.werewolf.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

abstract public class Role {

    public static int GOD = 0;
    public static int WEREWOLF = 1;
    public static int VILLAGER = 2;

    public List<String> skills = new ArrayList<>();


    abstract public Map<String, Object> executeSpecialAction(Map<String, Object> param);

    abstract public int getType();

    abstract public String getName();

    public List<String> getSkills(){
        skills.add("campaign");
        skills.add("vote");
        skills.add("voteForCampaign");
        return skills;
    }


}
