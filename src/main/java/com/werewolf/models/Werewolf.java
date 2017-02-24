package com.werewolf.models;

import java.util.HashMap;
import java.util.Map;

import static com.werewolf.models.Category.WOLVES;


public class Werewolf extends Role{

    public Werewolf() {
        super();
        this.actionMap.put("suicide", (Player target) -> suicide(target));
        this.actionMap.put("kill", (Player target) -> kill(target));
    }

    private Map<String, Object> suicide(Player target){
        Map<String, Object> actionResult = new HashMap<>();
        if(WOLVES == target.getRole().getCategory()) {
            target.setAlive(false);
        }
//        actionResult.put("Action", "suicide");
//        actionResult.put("Object", target);
        return actionResult;
    }

    private Map<String, Object> kill(Player target){
        Map<String, Object> actionResult = new HashMap<>();
        target.setAlive(false);
        actionResult.put("Action", "kill");
        actionResult.put("Object", target);
        return actionResult;
    }

}
