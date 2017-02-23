package com.werewolf.services;

import com.werewolf.GamePool;
import com.werewolf.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
public class GameService {
    @Autowired
    private GamePool gamePool;

    public String registerGame(GameConfiguration gameConfiguration, String sessionId) {
        if (gameConfiguration == null) {
            gameConfiguration = new GameConfiguration()
                    .setHasSheriff(true)
                    .setHunter(0)
                    .setProphet(1)
                    .setVillager(1)
                    .setWitch(0)
                    .setWolf(1);
        }
        Game game = new Game(gameConfiguration, sessionId);
        gamePool.registerGame(game);
        return game.getGameId();
    }

    public String fetchRole(String sessionId, String gameId, Integer seatId) {
        Game game = gamePool.getGameById(gameId);
        LinkedList<RoleType> playerQueue = game.getPlayerQueue();

        if (playerQueue.isEmpty()) {
            return "角色分配完毕";
        } else if(game.getPlayers().containsKey(sessionId)) {
            return "您已加入该房间，请勿重新加入";
        }
        RoleType roleType = playerQueue.poll();
        addPlayer(game, roleType, sessionId, seatId);
        return roleType.getType();
    }

    private void addPlayer(Game game, RoleType roleType, String sessionId, Integer seatId) {
        Role role = generateRoleByType(roleType);
        Player player = new Player(game, seatId, role);

        game.addPlayer(sessionId, player);
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
}
