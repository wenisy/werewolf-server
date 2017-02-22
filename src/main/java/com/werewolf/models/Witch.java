package com.werewolf.models;

import java.util.Map;

public class Witch extends Role {

    private int type = GOD;
    private boolean poison = true;
    private boolean antidote = true;
    private static String name = "WITCH";
    private ExecuteResultModel result;



    public int getType() {
        return type;
    }

    public boolean hasPoison() {
        return poison;
    }

    public boolean hasAntidote() {
        return antidote;
    }

    public void setAntidote(boolean antidote) {
        this.antidote = antidote;
    }


    @Override
    public String getName() {
        return name;
    }


    @Override
    public Object execute(Map<String, Object> param) {

        Player player = (Player) param.get("Player");
        int action = (Integer) param.get("Action");

        if(poison&&antidote) {
            switch (action) {
                case 1:
                    usePoision(player);
                    break;
                case 2:
                    useAntidote(player);
                    break;
                case 3:
                    break;
                default:
                    break;
            }
        }else if(poison&&!antidote){
            switch (action){
                case 1:
                    usePoision(player);
                    break;
                case 3:
                    break;
                default:
                    break;
            }
        }else{
            switch (action){
            case 2:
                useAntidote(player);
                break;
            case 3:
                break;
            default:
                break;
            }
        }
        return result;
    }

    private void usePoision(Player player){
        result.setTargetSitId(player.getSitId());
        result.setExecuteResult(false);
        if("HUNTER".equals(player.getRole().getName())){
            Hunter temp = (Hunter)player.getRole();
            temp.setSkillStatus(false);
            player.setRole(temp);
        }
        poison = false;
    }

    private void useAntidote(Player player){
        result.setTargetSitId(player.getSitId());
        result.setExecuteResult(true);
        antidote = false;
    }
}
