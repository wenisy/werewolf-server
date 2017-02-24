package com.werewolf.models;

import java.util.*;

public class Witch extends Role {

    private int type = GOD;
    private boolean poison = true;
    private boolean antidote = true;
    private static String name = "witch";

    public Witch() {
        super();
        this.actionMap.put("poison", (Player target)->poison(target));
        this.actionMap.put("antidote", (Player target)->poison(target));
    }

    public int getType() {
        return type;
    }

    public boolean hasPoison() {
        return poison;
    }

    public boolean hasAntidote() {
        return antidote;
    }

    @Override
    public String getName() {
        return name;
    }

    private Map<String, Object> poison(Player player){
        Map<String, Object> actionResult = new HashMap<>();
        if("hunter".equals(player.getRole().getName())){
            Hunter temp = (Hunter)player.getRole();
            temp.setSkillStatus(false);
            player.setRole(temp);
        }
        player.setAlive(false);
        actionResult.put("Action","kill");
        actionResult.put("Object", player);
        poison = false;
        return actionResult;
    }

    private Map<String, Object> antidote(Player player){
        Map<String, Object> actionResult = new HashMap<>();
        player.setAlive(true);
        actionResult.put("Action","save");
        actionResult.put("Object", player);
        antidote = false;
        return actionResult;
    }
}
