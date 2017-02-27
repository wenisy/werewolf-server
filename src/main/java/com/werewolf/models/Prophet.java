package com.werewolf.models;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class Prophet extends Role {


//    private LinkedHashMap<Integer, Faction> prophesyResults;

    public Prophet() {
        super();
        this.actionMap.put("prophesy", (Player target) -> prophesy(target));
//        this.prophesyResults = new LinkedHashMap<>();
    }

    private Map<String, Object> prophesy(Player target){
        Map<String, Object> actionResult = new HashMap<>();
        switch (target.getRole().getFaction()) {
            case BAD_GUYS: {
                actionResult.put("Action", "prophesy");
                actionResult.put("Info", false);
                actionResult.put("Object", target.getSeatId());
                break;
            }
            case GOOD_GUYS: {
                actionResult.put("Action", "prophesy");
                actionResult.put("Info", true);
                actionResult.put("Object", target.getSeatId());
                break;
            }
            default: break;
        }
        return actionResult;
    }

}
