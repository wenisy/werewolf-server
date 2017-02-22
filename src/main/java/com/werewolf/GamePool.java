package com.werewolf;

import com.werewolf.models.Game;
import com.werewolf.models.GameConfiguration;
import java.util.HashMap;
import org.springframework.stereotype.Component;

@Component
public class GamePool {

    private HashMap<String, Game> games;

    public GamePool() {
        this.games = new HashMap<>();
        GameConfiguration defaultGameConfiguration = new GameConfiguration();
        defaultGameConfiguration
                .setHasSheriff(true)
                .setHunter(1)
                .setProphet(1)
                .setVillager(3)
                .setWitch(1)
                .setWolf(3);
        games.put("a default game", new Game(defaultGameConfiguration));
    }

    public HashMap<String, Game> getGames() {
        return games;
    }

    public Game getGameById(String id) {
        return games.get(id);
    }
}
