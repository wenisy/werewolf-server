package com.werewolf.controllers;

import com.werewolf.models.Game;
import com.werewolf.models.GameConfiguration;
import com.werewolf.models.Player;
import com.werewolf.models.response.GameResponseVO;
import com.werewolf.services.GameService;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class GamesController {
    private final Logger logger = LoggerFactory.getLogger(GamesController.class);

    @Autowired
    private GameService gameService;
    @Autowired
    private GameMessageBroker gameMessageBroker;

    @MessageMapping("/create")
    public void createGame(@RequestBody GameConfiguration gameConfiguration, SimpMessageHeaderAccessor accessor) {
        String sessionId = accessor.getSessionId();
        Game game = gameService.registerGame(gameConfiguration, sessionId);
        logger.info("Create new room {}.", game.getGameId());

        gameMessageBroker.sendMessageToJudge(sessionId, GameResponseVO.getVO(game).setVoice(true));
    }

    @MessageMapping("/join")
    public void joinGame(@RequestBody String body, SimpMessageHeaderAccessor accessor) {
        String sessionId = accessor.getSessionId();
        String gameId = new JSONObject(body).get("roomNum").toString();
        Integer seatId = new JSONObject(body).getInt("seatNum");

        Game game = gameService.getGameById(gameId);
        String roleName = gameService.fetchRole(sessionId, gameId, seatId);

        logger.info("Seat {} joined game {} successfully, role is {}.", seatId, gameId, roleName);

        gameMessageBroker.sendMessageToJudge(sessionId, GameResponseVO.getVO(seatId, game, roleName));
    }

    @MessageMapping(value = "/players")
    public void readyToGame(@RequestBody String body, SimpMessageHeaderAccessor accessor) {
        Integer seatNum = Integer.valueOf(new JSONObject(body).getString("seatNum"));
        String gameId = new JSONObject(body).getString("roomNum");
        Boolean isReady = new JSONObject(body).getBoolean("isReady");

        Game game = gameService.getGameById(gameId);
        Optional<Player> player = game.getPlayerById(seatNum);

        player.ifPresent(p -> {
            p.setReady(isReady);
            game.checkState();

            logger.info("Player {} is ready, role is {}.",
                    p.getSeatId(), p.getRole().getName());
        });
    }

    @MessageMapping("/play")
    public void play(@RequestBody String body){
        Integer seatNum = Integer.valueOf(new JSONObject(body).getString("seatNum"));
        String gameId = new JSONObject(body).getString("roomNum");
        String action = new JSONObject(body).getString("action");
        int target = new JSONObject(body).getInt("target");

        Game game = gameService.getGameById(gameId);
        Optional<Player> player = game.getPlayerById(seatNum);

        Map<String, Object> actionMap = new HashMap<>();
        actionMap.put("player", game.getPlayerById(target).get());
        actionMap.put("action", action);

        player.ifPresent(p -> {
            String info = gameService.doAction(game, p, actionMap);

            game.checkState();

            logger.info(info);
        });
    }

}
