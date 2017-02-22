package com.werewolf.models;


import java.util.Map;

public class Werewolf extends Role{

    private static String name = "WEREWOLF";
    private int type = WEREWOLF;
    private ExecuteResultModel result;


    @Override
    public Object execute(Map<String, Object> param) {
        Player player = (Player)param.get("Player");
        result.setTargetSitId(player.getSitId());
        result.setExecuteResult(false);
        return result;
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
