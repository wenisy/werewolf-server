package com.werewolf.models;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class Prophet extends Role {


    private LinkedHashMap<Integer, Faction> prophesyResults;

    public Prophet() {
        super();
        this.actionMap.put("prophesy", (Player target) -> prophesy(target));
        this.prophesyResults = new LinkedHashMap<>();
    }

    private Map<String, Object> prophesy(Player target){
        Map<String, Object> actionResult = new HashMap<>();
//        prophesyResults.put(target.getSeatId(), target.);
        switch (target.getRole().getFaction()) {
            case BAD_GUYS: {
                break;
            }
            case GOOD_GUYS: {
                break;
            }
            default: break;
        }
        //        if(WEREWOLF == target.getRole().getType()){
//            actionResult.put("Action", "prophesy");
//            actionResult.put("Info", "isWerewolf");
//            actionResult.put("Object", target.getSeatId());
//
//        }else{
//            actionResult.put("Action", "prophesy");
//            actionResult.put("Info", "isGoodMan");
//            actionResult.put("Object", target.getSeatId());
//        }
        return actionResult;
    }

}
