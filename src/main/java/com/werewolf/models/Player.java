package com.werewolf.models;


import com.werewolf.controllers.GameMessageBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Player {

    private final Logger logger = LoggerFactory.getLogger(Player.class);

    private String sessionId;
    private int seatId;
    private boolean ready;
    private boolean alive;
    private boolean campaign;
    private boolean sheriff;
    private Role role;
    private boolean actionDone;
    private int actionTarget;

    private Game game;
    @Autowired
    private GameMessageBroker messageBroker;

    public Player(Game game, String sessionId, int seatId, Role role) {
        this.sessionId = sessionId;
        this.seatId = seatId;
        this.role = role;
        ready = false;
        alive = true;
        campaign = false;
        sheriff = false;
        this.game = game;
        this.actionDone = false;
        this.actionTarget = 0;
    }

    public void doAction(String action, Player target) {
        this.actionTarget = target.getSeatId();
        this.role.getAction(action).apply(target);
        logger.info("Player {} did {} to player {}", seatId, action, target.getSeatId());
        this.actionDone = true;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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
        this.sheriff = true;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String[] skills(){
        if(this.isCampaign()){
            this.getRole().getActionMap().remove("vote");
        }
        String[] skills = (String[]) this.getRole().getActionMap().keySet().toArray();
        return skills;
    }

    public boolean isJudge() {
        return this.sessionId.equals(game.getJudge().getSessionId());
    }

    public int getActionTarget() {
        return actionTarget;
    }

    public boolean isActionDone() {
        return actionDone;
    }

    public void resetAction() {
        this.actionTarget = 0;
        this.actionDone = false;
    }
}
