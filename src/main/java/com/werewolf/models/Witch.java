package com.werewolf.models;

import java.util.Map;

public class Witch extends Role {

    private int type = GOD;
    private boolean poison = true;
    private boolean antidote = true;



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
        return player;
    }

    private void usePoision(Player player){
        player.setAlive(false);
        poison = false;
    }

    private void useAntidote(Player player){
        player.setAlive(true);
        antidote = true;
    }
}
