package com.werewolf.models;

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

    public boolean isPoison() {
        return poison;
    }

    public void setPoison(boolean poison) {
        this.poison = poison;
    }

    public boolean isAntidote() {
        return antidote;
    }

    public void setAntidote(boolean antidote) {
        this.antidote = antidote;
    }

    @Override
    public void execute(Player player) {

    }
}
