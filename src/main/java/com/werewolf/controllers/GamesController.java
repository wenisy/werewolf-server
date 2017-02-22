package com.werewolf.controllers;

import com.werewolf.GamePool;
import com.werewolf.models.Game;
import com.werewolf.models.GameConfiguration;
import com.werewolf.models.PlayerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class GamesController {

    @Autowired
    private GamePool gamePool;

    @RequestMapping(value = "/games", method = RequestMethod.POST)
    @ResponseBody
    public Game createGame(@RequestBody GameConfiguration gameConfiguration) {
        return new Game(gameConfiguration);
    }


    @RequestMapping(value = "/games/{gameId}", method = RequestMethod.PUT)
    public void joinGame(@PathVariable Long gameId, @RequestBody PlayerInfo playerInfo) {

    }
}
