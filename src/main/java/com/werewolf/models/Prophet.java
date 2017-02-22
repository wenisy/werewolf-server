package com.werewolf.models;

import java.util.Map;

public class Prophet extends Role {

    private int type = GOD;
    private ExecuteResultModel result = null;

    @Override
    public Object execute(Player player) {

        boolean isGoodMam = true;
        if(1 == player.getRole().getType()){
            isGoodMam = false;
        }

        result.setSitId(player.getSitId());
        result.setResult(isGoodMam);

        return result;
    }

    @Override
    public int getType() {
        return type;
    }


}
