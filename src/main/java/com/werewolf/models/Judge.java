package com.werewolf.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Judge {
    private String sessionId;
    private Integer seatNum;
    private GameState currentState;

    private final Logger logger = LoggerFactory.getLogger(Judge.class);


    public Judge(GameSnapshot initGameSnapshot, String sessionId) {
        this.sessionId = sessionId;
        this.currentState = new GameState(initGameSnapshot);
        currentState.initState();
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void start() {
        currentState.initState();
    }

    public GameState next(GameSnapshot nextSnapshot) {
        logger.info("Judge CurrentState: {}: {}", currentState, currentState.getStateMessage());
        currentState = currentState.transfer(nextSnapshot);
        logger.info("Judge NextState: {}: {}", currentState, currentState.getStateMessage());
        return currentState;
    }

    public Boolean isEnd() {
        return false;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Integer getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(Integer seatNum) {
        this.seatNum = seatNum;
    }
}
