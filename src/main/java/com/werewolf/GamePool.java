package com.werewolf;

import com.werewolf.models.Game;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class GamePool {

    private HashMap<String, Game> games;

    public GamePool() {
        this.games = new HashMap<>();
    };

    public HashMap<String, Game> getGames() {
        return games;
    }
}
