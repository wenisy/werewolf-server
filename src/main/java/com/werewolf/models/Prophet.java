package com.werewolf.models;

import java.util.Map;

public class Prophet extends Role {

    private int type = GOD;
    private Map<Integer, Boolean> result;

    @Override
    public void execute(Player player) {

        boolean isGoodMam = true;
        if(1 == player.getRole().getType()){
            isGoodMam = false;
        }
        if(result.isEmpty()){
            result.put(player.getSitId(),isGoodMam);
        }else{
            result.
        }
    }
}
