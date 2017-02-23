package com.werewolf.models;

import java.util.ArrayList;

public class GameSnapshot {

    private ArrayList<PlayerSnapshot> playerSnapshots;

    public GameSnapshot(ArrayList<PlayerSnapshot> playerSnapshots) {
        this.playerSnapshots = playerSnapshots;
    }

    public boolean playersAreReady() {
        return playerSnapshots.stream()
                .map(PlayerSnapshot::isReady).reduce(true, (prev, next) -> prev && next);
    }

    public boolean wolfAgreed() {
        return playerSnapshots.stream().filter(playerSnapshot -> playerSnapshot.getRole() instanceof Werewolf)
                .map(PlayerSnapshot::getActionTarget).distinct().limit(2).count() <= 1;
    }
}
