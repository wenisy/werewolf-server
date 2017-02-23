package com.werewolf.services;

import com.werewolf.GamePool;
import com.werewolf.models.Game;
import com.werewolf.models.GameConfiguration;
import com.werewolf.models.Hunter;
import com.werewolf.models.Player;
import com.werewolf.models.Prophet;
import com.werewolf.models.Role;
import com.werewolf.models.RoleType;
import com.werewolf.models.Villager;
import com.werewolf.models.Werewolf;
import com.werewolf.models.Witch;
import java.util.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameService {
    @Autowired
    private GamePool gamePool;

    public String registerGame(GameConfiguration gameConfiguration) {
        if (gameConfiguration == null) {
            gameConfiguration = new GameConfiguration()
                    .setHasSheriff(true)
                    .setHunter(1)
                    .setProphet(1)
                    .setVillager(3)
                    .setWitch(1)
                    .setWolf(3);
        }
        Game game = new Game(gameConfiguration);
        gamePool.registerGame(game);
        return game.getGameId();
    }

    public String fetchRole(String sessionId, String gameId, Integer seatId) {
        Game game = gamePool.getGameById(gameId);
        Queue<RoleType> playerQueue = game.getPlayerQueue();

        if (playerQueue.isEmpty()) {
            return "角色分配完毕";
        } else if (game.getPlayerMap().containsKey(sessionId)) {
            return "您已加入该房间，请勿重新加入";
        }
        RoleType roleType = playerQueue.poll();
        addPlayer(game, roleType, sessionId, seatId);
        return roleType.getDisplayName();
    }

    private void addPlayer(Game game, RoleType roleType, String sessionId, Integer seatId) {
        Role role = generateRoleByType(roleType);
        Player player = new Player(seatId, role);

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
}
