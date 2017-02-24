package com.werewolf.models;

/**
 * Created by wzhao on 22/02/2017.
 */
public class PlayerSnapshot {
    private boolean ready;
    private Role role;
    private Integer actionTarget;
    private boolean isAlive;
    private boolean isSheriff;
    private int seatID;
    private boolean isApplySheriff;

    public PlayerSnapshot(Player player) {
        this.ready = player.isReady();
        this.isAlive = player.isAlive();
        this.isSheriff = player.isSheriff();
        this.seatID = player.getSeatId();
        this.isApplySheriff = player.isCampaign();
        this.actionTarget = player.getActionTarget();
    }

    public boolean isReady() {
        return ready;
    }

    public Role getRole() {
        return role;
    }

    public Integer getActionTarget() {
        return actionTarget;
    }

    public boolean isPlayerAlive() {
        return isAlive;
    }

    public boolean isSheriff() {
        return isSheriff;
    }

    public int getSeatID() {
        return seatID;
    }

    public boolean isApplySheriff() {
        return isApplySheriff;
    }
}
