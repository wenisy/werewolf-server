package com.werewolf.models;

import com.werewolf.controllers.GameMessageBroker;
import static com.werewolf.models.GameState.StateDefinition.APPLY_SHERIFF;
import static com.werewolf.models.GameState.StateDefinition.DAY_RESULT;
import static com.werewolf.models.GameState.StateDefinition.DAY_START;
import static com.werewolf.models.GameState.StateDefinition.HUNTER_VANISH;
import static com.werewolf.models.GameState.StateDefinition.INIT;
import static com.werewolf.models.GameState.StateDefinition.NIGHT_RESULT;
import static com.werewolf.models.GameState.StateDefinition.NIGHT_START;
import static com.werewolf.models.GameState.StateDefinition.PROPHET_VANISH;
import static com.werewolf.models.GameState.StateDefinition.SHERIFF_CANDIDATE_RESULT;
import static com.werewolf.models.GameState.StateDefinition.SHERIFF_RESULT;
import static com.werewolf.models.GameState.StateDefinition.WITCH_VANISH;
import static com.werewolf.models.GameState.StateDefinition.WOLF_APPEAR;
import static com.werewolf.models.GameState.StateDefinition.WOLF_VANISH;
import com.werewolf.models.response.GameResponseVO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Game {

    private final Logger logger = LoggerFactory.getLogger(Game.class);

    private Map<Integer, Player> players;
    private String gameId;
    private LinkedList<RoleType> playerQueue = new LinkedList<>();
    private Judge judge;
    private GameState.StateDefinition[] autoTransferStates  = {INIT, NIGHT_START, WOLF_APPEAR, WOLF_VANISH, WITCH_VANISH,
            PROPHET_VANISH, HUNTER_VANISH, DAY_START, APPLY_SHERIFF, SHERIFF_CANDIDATE_RESULT, SHERIFF_RESULT,
            NIGHT_RESULT, DAY_RESULT
    };

    private GameMessageBroker messageBroker;

    public Game(GameConfiguration gameConfiguration, String sessionId, GameMessageBroker gameMessageBroker) {
        this.messageBroker = gameMessageBroker;
        char[] digitals = "0123456789".toCharArray();
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            builder.append(digitals[random.nextInt(digitals.length)]);
        }
        this.gameId = builder.toString();
        this.players = new HashMap<>();

        initPlayerQueue(gameConfiguration);

        judge = new Judge(getSnapshot(), sessionId);
    }

    private void initPlayerQueue(GameConfiguration gameConfiguration) {
        IntStream.range(0, gameConfiguration.getWolf()).forEach(i -> playerQueue.add(RoleType.WEREWOLF));
        IntStream.range(0, gameConfiguration.getVillager()).forEach(i -> playerQueue.add(RoleType.VILLAGER));
        IntStream.range(0, gameConfiguration.getHunter()).forEach(i -> playerQueue.add(RoleType.HUNTER));
        IntStream.range(0, gameConfiguration.getProphet()).forEach(i -> playerQueue.add(RoleType.PROPHET));
        IntStream.range(0, gameConfiguration.getWitch()).forEach(i -> playerQueue.add(RoleType.WITCH));

        Collections.shuffle(playerQueue);
    }

    public Optional<Player> getPlayerById(Integer seatNum) {
        if(players.containsKey(seatNum)) {
            return Optional.of(players.get(seatNum));
        }
        return Optional.empty();
    }

    public String getGameId() {
        return gameId;
    }

    public LinkedList<RoleType> getPlayerQueue() {
        return playerQueue;
    }

    public void addPlayer(Integer seatNum, Player player) {
        players.put(seatNum, player);
    }

    public Map<Integer, Player> getPlayers() {
        return players;
    }

    public GameState checkState() {
        GameState current = getCurrentState();
        GameSnapshot snapshot = getSnapshot();
        GameState next = judge.next(snapshot);

        if (current.equals(next)) return next;
        processNextState(this, next);

        if (Arrays.asList(autoTransferStates).contains(next.getCurrentState())) {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException ignored) {}
            return this.checkState();
        }
        return next;
    }

    private void processNextState(Game game, GameState next) {
        logger.info("Next state: {}", next.getStateMessage());

        if (next.getCurrentState().equals(GameState.StateDefinition.WOLF_KILL)) {

        }

        GameResponseVO response = new GameResponseVO().setMessage(next.getStateMessage()).setVoice(true);
        messageBroker.sendMessageToJudge(game.getJudge().getSessionId(), response);
    }

    private GameSnapshot getSnapshot() {
        ArrayList<PlayerSnapshot> playerSnapshots = new ArrayList<>();
        players.keySet().stream().map(key -> new PlayerSnapshot(players.get(key))).forEach(playerSnapshots::add);
        return new GameSnapshot(playerSnapshots, playerQueue.size());
    }

    public Judge getJudge() {
        return judge;
    }

    public GameState getCurrentState() {
        return new GameState(judge.getCurrentState());
    }
}
