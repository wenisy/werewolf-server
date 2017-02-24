package com.werewolf.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Werewolf extends Role{

    private static String name = "werewolf";
    private int type = WEREWOLF;

    public Werewolf() {
        super();
        this.actionMap.put("suicide", (Player target) -> suicide(target));
        this.actionMap.put("kill", (Player target) -> kill(target));
    }

    private Map<String, Object> suicide(Player target){
        Map<String, Object> actionResult = new HashMap<>();
        if(WEREWOLF == target.getRole().getType()) {
            target.setAlive(false);
        }
        actionResult.put("Action", "suicide");
        actionResult.put("Object", target);
        return actionResult;
    }

    private Map<String, Object> kill(Player target){
        Map<String, Object> actionResult = new HashMap<>();
        target.setAlive(false);
        actionResult.put("Action", "kill");
        actionResult.put("Object", target);
        return actionResult;
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
