package com.werewolf.models;

import java.util.*;

public class Witch extends Role {

    private int type = GOD;
    private boolean poison = true;
    private boolean antidote = true;
    private static String name = "WITCH";
    private Map<Integer, String> executeResult = null;



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

    @Override
    public List<String> getSkills() {
        List<String> skills = new ArrayList<String>();
        if(poison){
            skills.add("poison");
        }else{
            skills.remove("poison");
        }
        if(antidote){
            skills.add("antidote");
        }else{
            skills.remove("antidote");
        }
        return skills;
    }


    @Override
    public Map<Integer, String> execute(Map<String, Object> param) {

        Player player = (Player) param.get("Player");
        String action = (String) param.get("Action");

        if(null == param){
            return executeResult;
        }
        if(getSkills().contains(action)){
            switch(action){
                case "poison":
                    executeResult.put(player.getSeatId(), "killed");
                    poison = false;
                break;
                case "antidote":
                    executeResult.put(player.getSeatId(), "saved");
                    antidote = false;
                break;
            }
        }
        return executeResult;
    }

//    private void usePoison(Player player){
//        executeResult.setTargetSitId(player.getSeatId());
//        Map<String, Boolean> actionAndResult = new HashMap<>();
//        actionAndResult.put("", false);
//        executeResult.setExecuteResult(actionAndResult);
//        if("HUNTER".equals(player.getRole().getName())){
//            Hunter temp = (Hunter)player.getRole();
//            temp.setSkillStatus(false);
//            player.setRole(temp);
//        }
//        poison = false;
//    }
//
//    private void useAntidote(Player player){
//        executeResult.setTargetSitId(player.getSeatId());
//        Map<String, Boolean> actionAndResult = new HashMap<>();
//        actionAndResult.put("UseAn")
//        executeResult.setExecuteResult(true);
//        antidote = false;
//    }
}
