package com.werewolf.models;

public class GameConfiguration {

    private int wolf;
    private int witch;
    private int prophet;
    private int villager;
    private int hunter;
    private boolean hasSheriff;

    public int getWolf() {
        return wolf;
    }

    public void setWolf(int wolf) {
        this.wolf = wolf;
    }

    public int getWitch() {
        return witch;
    }

    public void setWitch(int witch) {
        this.witch = witch;
    }

    public int getProphet() {
        return prophet;
    }

    public void setProphet(int prophet) {
        this.prophet = prophet;
    }

    public int getVillager() {
        return villager;
    }

    public void setVillager(int villager) {
        this.villager = villager;
    }

    public int getHunter() {
        return hunter;
    }

    public void setHunter(int hunter) {
        this.hunter = hunter;
    }

    public boolean isHasSheriff() {
        return hasSheriff;
    }

    public void setHasSheriff(boolean hasSheriff) {
        this.hasSheriff = hasSheriff;
    }
}
