package com.werewolf.models;

import java.util.HashMap;
import java.util.Map;


public class Prophet extends Role {

    private int type = GOD;
    private static String name = "prophet";

    public Prophet() {
        super();
        this.actionMap.put("prophesy", (Player target) -> prophesy(target));
    }

    private Map<String, Object> prophesy(Player target){
        Map<String, Object> actionResult = new HashMap<>();
        if(WEREWOLF == target.getRole().getType()){
            actionResult.put("Action", "prophesy");
            actionResult.put("Info", "isWerewolf");
            actionResult.put("Object", target.getSeatId());
        }else{
            actionResult.put("Action", "prophesy");
            actionResult.put("Info", "isGoodMan");
            actionResult.put("Object", target.getSeatId());
        }
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
