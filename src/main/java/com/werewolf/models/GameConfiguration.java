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

    public GameConfiguration setWolf(int wolf) {
        this.wolf = wolf;
        return this;
    }

    public int getWitch() {
        return witch;
    }

    public GameConfiguration setWitch(int witch) {
        this.witch = witch;
        return this;
    }

    public int getProphet() {
        return prophet;
    }

    public GameConfiguration setProphet(int prophet) {
        this.prophet = prophet;
        return this;
    }

    public int getVillager() {
        return villager;
    }

    public GameConfiguration setVillager(int villager) {
        this.villager = villager;
        return this;
    }

    public int getHunter() {
        return hunter;
    }

    public GameConfiguration setHunter(int hunter) {
        this.hunter = hunter;
        return this;
    }

    public boolean isHasSheriff() {
        return hasSheriff;
    }

    public GameConfiguration setHasSheriff(boolean hasSheriff) {
        this.hasSheriff = hasSheriff;
        return this;
    }
}
