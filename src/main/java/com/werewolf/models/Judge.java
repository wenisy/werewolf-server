package com.werewolf.models;

public class Judge {
    private String sessionId;
    private GameState currentState;

    public Judge(GameSnapshot initGameSnapshot, String sessionId) {
        this.sessionId = sessionId;
        this.currentState = new GameState(initGameSnapshot);
        currentState.initState();
    }

    public void start() {
        currentState.initState();
    }

    public String next(GameSnapshot nextSnapshot) {
        currentState.transfer(nextSnapshot);
        currentState.setCurrentSnapshot(nextSnapshot);
        return currentState.getStateMessage(currentState.getCurrentState());
    }

    public Boolean isEnd() {
        return false;
    }

    public String getSessionId() {
        return sessionId;
    }
}
