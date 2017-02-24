package com.werewolf.models;

import java.util.*;

public class Witch extends Role {

    private boolean poison = true;
    private boolean antidote = true;

    public Witch() {
        super();
        this.actionMap.put("poison", (Player target)->poison(target));
        this.actionMap.put("antidote", (Player target)->poison(target));
    }


    public boolean hasPoison() {
        return poison;
    }

    public boolean hasAntidote() {
        return antidote;
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
