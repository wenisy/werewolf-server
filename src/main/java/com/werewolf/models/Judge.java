package com.werewolf.models;

import java.util.HashMap;

public class Judge {
    private GameState currentState;

    public Judge(GameSnapshot initGameSnapshot) {
        this.currentState = new GameState(initGameSnapshot);
        currentState.initState();
    }

    public void start() {
        currentState.initState();
    }

    public String next(GameSnapshot nextSnapshot) {
        currentState.setGameSnapshot(nextSnapshot);
        currentState.transfer(nextSnapshot);
        return currentState.getStateMessage(currentState.getCurrentState());
    }

    public Boolean isEnd() {
        return false;
    }
}
