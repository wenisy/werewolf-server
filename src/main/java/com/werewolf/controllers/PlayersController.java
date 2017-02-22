package com.werewolf.controllers;

import com.werewolf.GamePool;
import com.werewolf.models.Player;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class PlayersController {

    @Autowired
    private GamePool gamePool;

    @RequestMapping(value = "/players/{id}", method = RequestMethod.PUT)
    public void readyToGame(@PathVariable Integer id, @RequestBody String body ) {
        Boolean isReady = new JSONObject(body).getBoolean("isReady");
        Optional<Player> player = gamePool.getGameById("some fake game id").getPlayerById(id);

        if(player.isPresent()) {
            player.get().setReady(isReady);
        }
    }
}
