package com.werewolf.models;


public class Player {

    private int sitId;
    private boolean ready;
    private boolean alive;
    private Role role;

    public int getSitId() {
        return sitId;
    }

    public void setSitId(int sitId) {
        this.sitId = sitId;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
