package com.werewolf.models;

import com.werewolf.controllers.GameMessageBroker;
import com.werewolf.models.response.GameResponseVO;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static com.werewolf.models.GameState.StateDefinition.*;

public class Game {

    private Map<String, Player> players;
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

    public Optional<Player> getPlayerById(String sessionId) {
        if(players.containsKey(sessionId)) {
            return Optional.of(players.get(sessionId));
        }
        return Optional.empty();
    }

    public Optional<Player> getPlayerBySeatId(int seatId){
        Optional<Player> player = players.values()
                .parallelStream()
                .filter(temp -> temp.getSeatId() == seatId)
                .findFirst();
        return player;
    }

    public String getGameId() {
        return gameId;
    }

    public LinkedList<RoleType> getPlayerQueue() {
        return playerQueue;
    }

    public void addPlayer(String sessionId, Player player) {
        players.put(sessionId, player);
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public GameState checkState() {
        GameState current = getCurrentState();
        GameSnapshot snapshot = getSnapshot();
        GameState next = judge.next(snapshot);

        if (current.equals(next)) return next;
        sendNextToJudge(this, next);

        if (Arrays.asList(autoTransferStates).contains(next.getCurrentState())) {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException ignored) {}
            next = judge.next(snapshot);
        }
        return next;
    }

    private void sendNextToJudge(Game game, GameState next) {
        GameResponseVO response = new GameResponseVO().setMessage(next.getStateMessage()).setVoice(true);
        String judgeSessionId = game.getJudge();

        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(judgeSessionId);
        headerAccessor.setLeaveMutable(true);
        messageBroker.sendMessage(headerAccessor, response);
    }

    private GameSnapshot getSnapshot() {
        ArrayList<PlayerSnapshot> playerSnapshots = new ArrayList<>();
        players.keySet().stream().map(key -> new PlayerSnapshot(players.get(key))).forEach(playerSnapshots::add);
        return new GameSnapshot(playerSnapshots, playerQueue.size());
    }

    public String getJudge() {
        return judge.getSessionId();
    }

    public GameState getCurrentState() {
        return new GameState(judge.getCurrentState());
    }
}
