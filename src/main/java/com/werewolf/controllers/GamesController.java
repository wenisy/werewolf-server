package com.werewolf.controllers;

import com.werewolf.models.Game;
import com.werewolf.models.GameConfiguration;
import com.werewolf.models.GameState;
import com.werewolf.models.Player;
import com.werewolf.models.response.GameResponseVO;
import com.werewolf.services.GameService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class GamesController {
    private final Logger logger = LoggerFactory.getLogger(GamesController.class);

    @Autowired
    private GameService gameService;
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    @Autowired
    private GameMessageBroker gameMessageBroker;

    @MessageMapping("/create")
    public void createGame(@RequestBody GameConfiguration gameConfiguration, SimpMessageHeaderAccessor accessor) {
        String sessionId = accessor.getSessionId();
        String roomNum = gameService.registerGame(gameConfiguration, sessionId);
        logger.info("Create new room {}.", roomNum);

        gameMessageBroker.sendMessage(accessor, new GameResponseVO().setRoomNum(roomNum));
    }

    @MessageMapping("/join")
    public void joinGame(@RequestBody String body, SimpMessageHeaderAccessor accessor) {
        String sessionId = accessor.getSessionId();
        String gameId = new JSONObject(body).get("roomNum").toString();
        Integer seatId = new JSONObject(body).getInt("seatNum");

        String roleName = gameService.fetchRole(sessionId, gameId, seatId);

        logger.info("Seat {} joined game {} successfully, role is {}.", seatId, gameId, roleName);

        gameMessageBroker.sendMessage(accessor, new GameResponseVO().setRole(roleName));
    }

    @MessageMapping(value = "/players")
    public void readyToGame(@RequestBody String body, SimpMessageHeaderAccessor accessor) {
        String sessionId = accessor.getSessionId();
        String gameId = new JSONObject(body).getString("roomNum");
        Boolean isReady = new JSONObject(body).getBoolean("isReady");

        Game game = gameService.getGameById(gameId);
        Optional<Player> player = game.getPlayerById(sessionId);

        player.ifPresent(p -> {
            GameState current = game.getCurrentState();
            p.setReady(isReady);
            GameState next = game.checkState();

            logger.info("Player {} is ready, role is {}, next state is: {}.",
                    p.getSeatId(), p.getRole().getName(), next.getCurrentState().getMessage());

            if (current.equals(next)) return;
            sendNextToJudge(game, next);

        });
    }

    private void sendNextToJudge(Game game, GameState next) {
        GameResponseVO response = new GameResponseVO().setMessage(next.getStateMessage()).setVoice(true);
        String judgeSessionId = game.getJudge();

        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(judgeSessionId);
        headerAccessor.setLeaveMutable(true);
        logger.info("Sending message to judge.");
        gameMessageBroker.sendMessage(headerAccessor, response);
    }

    @MessageMapping("/play")
    public void play(@RequestBody String body, SimpMessageHeaderAccessor accessor){
        String sessionId = accessor.getSessionId();
        String gameId = new JSONObject(body).getString("roomNum");
        String action = new JSONObject(body).getString("action");
        int seatId = new JSONObject(body).getInt("target");

        Game game = gameService.getGameById(gameId);
        Optional<Player> player = game.getPlayerById(sessionId);

        Map<String, Object> param = new HashMap<>();
        param.put("Player", game.getPlayerBySeatId(seatId));
        param.put("Action", action.split(":")[1]);

        player.ifPresent(p -> {
            GameState current = game.getCurrentState();
            Map<String, Object> actionResult = p.getRole().executeSpecialAction(param);
            Player target = game.getPlayerBySeatId((int)actionResult.get("TargetSeatId")).get();
            switch((String)actionResult.get("ActionResult")){
                case "kill":
                    target.setAlive(false);
                    break;
                case "saved":
                    target.setAlive(true);
                    break;
                default:
                    break;
            }

            GameState next = game.checkState();

            logger.info("Player {} is killed.",target.getSeatId());

            if (current.equals(next)) return;
            sendNextToJudge(game, next);

        });
    }

}
