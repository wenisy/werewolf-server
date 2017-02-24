package com.werewolf.services;

import com.werewolf.GamePool;
import com.werewolf.controllers.GameMessageBroker;
import com.werewolf.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
public class GameService {
    @Autowired
    private GamePool gamePool;

    @Autowired
    private GameMessageBroker messageBroker;

    public Game registerGame(GameConfiguration gameConfiguration, String sessionId) {
        if (gameConfiguration == null) {
            gameConfiguration = new GameConfiguration()
                    .setHasSheriff(true)
                    .setHunter(0)
                    .setProphet(1)
                    .setVillager(1)
                    .setWitch(0)
                    .setWolf(1);
        }
        Game game = new Game(gameConfiguration, sessionId, messageBroker);
        gamePool.registerGame(game);
        return game;
    }

    public String fetchRole(String sessionId, String gameId, Integer seatId) {
        Game game = gamePool.getGameById(gameId);
        LinkedList<RoleType> playerQueue = game.getPlayerQueue();

        if (playerQueue.isEmpty()) {
            return "角色分配完毕";
        } else if(game.getPlayers().containsKey(seatId)) {
            return "您已加入该房间，请勿重新加入";
        }
        RoleType roleType = playerQueue.poll();
        addPlayer(game, roleType, seatId, sessionId);
        return roleType.getType();
    }

    private void addPlayer(Game game, RoleType roleType, Integer seatId, String sessionId) {
        if(game.getJudge().getSessionId().equals(sessionId)) {
            game.getJudge().setSeatNum(seatId);
        }

        Role role = generateRoleByType(roleType);
        Player player = new Player(game, sessionId, seatId, role);

        game.addPlayer(seatId, player);
    }

    private Role generateRoleByType(RoleType roleType) {
        Role role;
        switch (roleType) {
            case VILLAGER:
                role = new Villager();
                break;
            case WEREWOLF:
                role = new Werewolf();
                break;
            case PROPHET:
                role = new Prophet();
                break;
            case WITCH:
                role = new Witch();
                break;
            case HUNTER:
                role = new Hunter();
                break;
            default:
                role = new Villager();
                break;

        }
        return role;
    }

    public Game getGameById(String gameId) {
        return gamePool.getGameById(gameId);
    }

//    public String doAction(Game game, Player player,Map<String, Object> param){
//
//        Map<String, String> actionResult = game.getActionResultMap();
//        Map<String, Object> playerActionResult = player.getRole().executeAction(param);
//        Player target = (Player)playerActionResult.get("Object");
//
//        switch ((String)playerActionResult.get("Action")) {
//            case "vote":
//                actionResult.put("Action","vote");
//                actionResult.put("CurrentPlayer", String.valueOf(player.getSeatId()));
//                actionResult.put("TargetPlayer", String.valueOf(target.getSeatId()));
//                game.setActionResultMap(actionResult);
//            case "kill":
//                actionResult.put("Action", "kill");
//                actionResult.put("TargetPlayer", String.valueOf(target.getSeatId()));
//            default:
//                break;
//        }
//        return "Nothing was done.";
//    }
}
