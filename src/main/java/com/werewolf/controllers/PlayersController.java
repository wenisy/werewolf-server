package com.werewolf.controllers;

import com.werewolf.models.Game;
import com.werewolf.models.Player;
import java.util.Optional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayersController {

    @Autowired
    private Game game;

    @RequestMapping(value = "/players/{id}", method = RequestMethod.PUT)
    public void readyToGame(@PathVariable Integer id, @RequestBody String body ) {
        Boolean isReady = new JSONObject(body).getBoolean("isReady");
        Optional<Player> player = game.getPlayerById(id);

        if(player.isPresent()) {
            player.get().setReady(isReady);
        }
    }
}
