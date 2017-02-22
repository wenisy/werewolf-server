package com.werewolf.models;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {

    private int seatId;
    private boolean ready;
    private boolean alive;
    private boolean campaign;
    private boolean sheriff;
    private Role role;
    private List<String> skills;

    public Player(int seatId, Role role) {
        this.seatId = seatId;
        this.role = role;
        ready = false;
        alive = true;
        campaign = false;
        sheriff = false;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
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

    public boolean isCampaign() {
        return campaign;
    }

    public void campaignForSheriff() {
        this.campaign = true;
    }

    public boolean isSheriff() {
        return sheriff;
    }

    public void setSheriff(boolean sheriff) {
        this.sheriff = sheriff;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<String> getSkills(){
        skills = role.getSkills();
        skills.add("vote");
        skills.add("voteForCampaign");
        if(isCampaign()){
            skills.remove("voteForCampaign");
        }
        return skills;
    }

    public Map<Integer, Integer> vote(int seatId){
        Map<Integer, Integer> voteResult = new HashMap<>();
        voteResult.put(this.getSeatId(), seatId);
        return voteResult;
    }

}
