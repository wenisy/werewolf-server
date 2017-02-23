package com.werewolf.models;

/**
 * Created by wzhao on 22/02/2017.
 */
public class PlayerSnapshot {
    private boolean ready;
    private Role role;
    private Integer actionTarget;
    private boolean isAlive;

    public PlayerSnapshot(Player player) {
        this.ready = player.isReady();
        this.isAlive = player.isAlive();
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
}
