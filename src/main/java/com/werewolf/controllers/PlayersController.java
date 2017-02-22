package com.werewolf.controllers;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayersController {

    @RequestMapping(value = "/players/{id}", method = RequestMethod.PUT)
    public String readyToGame(@PathVariable Integer id, @RequestBody String body ) {
        Boolean isReady = (Boolean) new JSONObject(body).get("isReady");
        return isReady.toString();
    }
}
