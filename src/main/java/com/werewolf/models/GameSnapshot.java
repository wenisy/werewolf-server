package com.werewolf.models;

import java.util.ArrayList;

public class GameSnapshot {

    private String gameId;
    private ArrayList<PlayerSnapshot> playerSnapshots;

    public GameSnapshot(String gameId, ArrayList<PlayerSnapshot> playerSnapshots) {
        this.gameId = gameId;
        this.playerSnapshots = playerSnapshots;
    }

    public String getGameId() {
        return gameId;
    }

}
