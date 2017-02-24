package com.werewolf.models;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameStateTest {
    private GameSnapshot currentSnapshot;
    private GameSnapshot incomingSnapshot;
    private GameState gameState;

    @Before
    public void setUp() throws Exception {
        currentSnapshot = mock(GameSnapshot.class);
        incomingSnapshot = mock(GameSnapshot.class);
        gameState = new GameState(currentSnapshot);
    }

    @Test
    public void testInitState() throws Exception {
        gameState.initState();
        assertEquals(GameState.StateDefinition.INIT, gameState.getCurrentState());
    }

    @Test
    public void testInitTransfer() throws Exception {
        gameState.setCurrentState(GameState.StateDefinition.INIT);
        when(incomingSnapshot.playersAreReady()).thenReturn(true);
        when(incomingSnapshot.getNumberOfEmptySeat()).thenReturn(0);

        gameState.transfer(incomingSnapshot);
        assertEquals(GameState.StateDefinition.WAITING_PLAYERS, gameState.getCurrentState());
    }

    @Test
    public void testTransferWaitttingPlayers() throws Exception {
        gameState.setCurrentState(GameState.StateDefinition.WAITING_PLAYERS);
        when(incomingSnapshot.playersAreReady()).thenReturn(true);
        when(incomingSnapshot.getNumberOfEmptySeat()).thenReturn(0);

        gameState.transfer(incomingSnapshot);
        assertEquals(GameState.StateDefinition.NIGHT_START, gameState.getCurrentState());
        assertEquals("天黑了,大家请闭眼", gameState.getCurrentState().getMessage());
    }

    @Test
    public void testTransferNightStart() throws Exception {
        gameState.setCurrentState(GameState.StateDefinition.NIGHT_START);

        gameState.transfer(incomingSnapshot);
        assertEquals(GameState.StateDefinition.WOLF_APPEAR, gameState.getCurrentState());
    }

    @Test
    public void testTransferWolfKill() throws Exception {
        gameState.setCurrentState(GameState.StateDefinition.WOLF_KILL);
        when(incomingSnapshot.wolfAgreed()).thenReturn(true);
        gameState.transfer(incomingSnapshot);
        assertEquals(GameState.StateDefinition.WOLF_VANISH, gameState.getCurrentState());

        gameState.setCurrentState(GameState.StateDefinition.WOLF_KILL);
        when(incomingSnapshot.wolfAgreed()).thenReturn(false);
        gameState.transfer(incomingSnapshot);
        assertEquals(GameState.StateDefinition.WOLF_UNIFY_OPINION, gameState.getCurrentState());
    }

    @Test
    public void testTransferWolfUnifyOpinion() throws Exception {
        gameState.setCurrentState(GameState.StateDefinition.WOLF_UNIFY_OPINION);
        when(incomingSnapshot.wolfAgreed()).thenReturn(true);
        gameState.transfer(incomingSnapshot);
        assertEquals(GameState.StateDefinition.WOLF_VANISH, gameState.getCurrentState());

        gameState.setCurrentState(GameState.StateDefinition.WOLF_UNIFY_OPINION);
        when(incomingSnapshot.wolfAgreed()).thenReturn(false);
        gameState.transfer(incomingSnapshot);
        assertEquals(GameState.StateDefinition.WOLF_UNIFY_OPINION, gameState.getCurrentState());
    }

    @Test
    public void testTransferWolfVanish() throws Exception {
        gameState.setCurrentState(GameState.StateDefinition.WOLF_VANISH);

        gameState.transfer(incomingSnapshot);
        assertEquals(GameState.StateDefinition.PROPHET_APPEAR, gameState.getCurrentState());
    }

    @Test
    public void testTransferProphetAppear() throws Exception {
        gameState.setCurrentState(GameState.StateDefinition.PROPHET_APPEAR);

        gameState.transfer(incomingSnapshot);
        assertEquals(GameState.StateDefinition.PROPHET_VANISH, gameState.getCurrentState());
    }

    @Test
    public void testTransferProphetVanish() throws Exception {
        gameState.setCurrentState(GameState.StateDefinition.PROPHET_VANISH);

        gameState.transfer(incomingSnapshot);
        assertEquals(GameState.StateDefinition.WITCH_APPEAR, gameState.getCurrentState());
    }

    @Test
    public void testTransferWitchAppear() throws Exception {
        gameState.setCurrentState(GameState.StateDefinition.WITCH_APPEAR);

        gameState.transfer(incomingSnapshot);
        assertEquals(GameState.StateDefinition.WITCH_VANISH, gameState.getCurrentState());
    }

    @Test
    public void testTransferWitchVanish() throws Exception {
        gameState.setCurrentState(GameState.StateDefinition.WITCH_VANISH);

        gameState.transfer(incomingSnapshot);
        assertEquals(GameState.StateDefinition.HUNTER_APPEAR, gameState.getCurrentState());
    }

    @Test
    public void testTransferHunterAppear() throws Exception {
        gameState.setCurrentState(GameState.StateDefinition.HUNTER_APPEAR);

        gameState.transfer(incomingSnapshot);
        assertEquals(GameState.StateDefinition.HUNTER_VANISH, gameState.getCurrentState());
    }

    @Test
    public void testTransferHunterVanish() throws Exception {
        gameState.setCurrentState(GameState.StateDefinition.HUNTER_VANISH);
        when(incomingSnapshot.needApplySheriff()).thenReturn(true);
        gameState.transfer(incomingSnapshot);
        assertEquals(GameState.StateDefinition.DAY_START, gameState.getCurrentState());

        gameState.setCurrentState(GameState.StateDefinition.HUNTER_VANISH);
        when(incomingSnapshot.needApplySheriff()).thenReturn(false);
        gameState.transfer(incomingSnapshot);
        assertEquals(GameState.StateDefinition.NIGHT_RESULT, gameState.getCurrentState());
    }

    @Test
    public void testTransferDayStart() throws Exception {
        gameState.setCurrentState(GameState.StateDefinition.DAY_START);

        gameState.transfer(incomingSnapshot);
        assertEquals(GameState.StateDefinition.APPLY_SHERIFF, gameState.getCurrentState());
    }

    @Test
    public void testTransferApplySheriff() throws Exception {
        gameState.setCurrentState(GameState.StateDefinition.APPLY_SHERIFF);

        gameState.transfer(incomingSnapshot);
        assertEquals(GameState.StateDefinition.VOTE_FOR_APPLY_SHERIFF, gameState.getCurrentState());
    }

    @Test
    public void testTransferVoteApplySheriff() throws Exception {
        gameState.setCurrentState(GameState.StateDefinition.VOTE_FOR_APPLY_SHERIFF);
        ArrayList<Integer> fakeCompaignPlayers = new ArrayList<>();
        fakeCompaignPlayers.add(1);
        fakeCompaignPlayers.add(3);
        when(incomingSnapshot.getApplySheriffID()).thenReturn(fakeCompaignPlayers);

        gameState.transfer(incomingSnapshot);
        assertEquals(GameState.StateDefinition.SHERIFF_CANDIDATE_RESULT, gameState.getCurrentState());
        assertEquals("上警的人有1号玩家,3号玩家,", gameState.getCurrentState().getMessage());
    }

    @Test
    public void testTransferSheriffCandidateResult() throws Exception {
        gameState.setCurrentState(GameState.StateDefinition.SHERIFF_CANDIDATE_RESULT);

        gameState.transfer(incomingSnapshot);
        assertEquals(GameState.StateDefinition.SHERIFF_CANDIDATE_SPEECH, gameState.getCurrentState());
    }

    @Test
    public void testTransferSheriffCandidateSpeech() throws Exception {
        gameState.setCurrentState(GameState.StateDefinition.SHERIFF_CANDIDATE_SPEECH);

        gameState.transfer(incomingSnapshot);
        assertEquals(GameState.StateDefinition.VOTE_FOR_SHERIFF, gameState.getCurrentState());
    }

    @Test
    public void testTransferVoteForSheriff() throws Exception {
        gameState.setCurrentState(GameState.StateDefinition.VOTE_FOR_SHERIFF);
        when(incomingSnapshot.getSheriffID()).thenReturn(7);

        gameState.transfer(incomingSnapshot);
        assertEquals(GameState.StateDefinition.SHERIFF_RESULT, gameState.getCurrentState());
        assertEquals("警长是7号玩家", gameState.getCurrentState().getMessage());
    }

    @Test
    public void testTransferSheriffResult() throws Exception {
        gameState.setCurrentState(GameState.StateDefinition.SHERIFF_RESULT);
        ArrayList<Integer> oldDeadPlayerList = new ArrayList<Integer>(){{add(1); add(2);}};
        ArrayList<Integer> latestDeadPlayerList = new ArrayList<Integer>(){{add(1); add(2); add(3); add(4);}};

        when(currentSnapshot.getDeadPlayer()).thenReturn(oldDeadPlayerList);
        when(incomingSnapshot.getDeadPlayer()).thenReturn(latestDeadPlayerList);

        gameState.transfer(incomingSnapshot);
        assertEquals(GameState.StateDefinition.NIGHT_RESULT, gameState.getCurrentState());
        assertEquals("昨天晚上死的人是3号玩家,4号玩家,", gameState.getCurrentState().getMessage());
    }


    @Test
    public void testTransferNightResult() throws Exception {
        gameState.setCurrentState(GameState.StateDefinition.NIGHT_RESULT);

        gameState.transfer(incomingSnapshot);
        assertEquals(GameState.StateDefinition.DAY_SPEECH, gameState.getCurrentState());
    }

    @Test
    public void testTransferDaySpeech() throws Exception {
        gameState.setCurrentState(GameState.StateDefinition.DAY_SPEECH);

        gameState.transfer(incomingSnapshot);
        assertEquals(GameState.StateDefinition.VOTE_FOR_DAY_DEATH, gameState.getCurrentState());
    }


    @Test
    public void testTransferVoteDayDeath() throws Exception {
        gameState.setCurrentState(GameState.StateDefinition.VOTE_FOR_DAY_DEATH);
        ArrayList<Integer> oldDeadPlayerList = new ArrayList<Integer>(){{add(1); add(2);}};
        ArrayList<Integer> latestDeadPlayerList = new ArrayList<Integer>(){{add(1); add(2); add(3); add(4);}};

        when(currentSnapshot.getDeadPlayer()).thenReturn(oldDeadPlayerList);
        when(incomingSnapshot.getDeadPlayer()).thenReturn(latestDeadPlayerList);

        gameState.transfer(incomingSnapshot);
        assertEquals(GameState.StateDefinition.DAY_RESULT, gameState.getCurrentState());
        assertEquals("被投死的是3号玩家,4号玩家,", gameState.getCurrentState().getMessage());
    }

    @Test
    public void testTransferDayResult() throws Exception {
        gameState.setCurrentState(GameState.StateDefinition.DAY_RESULT);

        gameState.transfer(incomingSnapshot);
        assertEquals(GameState.StateDefinition.DEATH_SPEECH, gameState.getCurrentState());
    }

    @Test
    public void testTransferDeathSpeech() throws Exception {
        gameState.setCurrentState(GameState.StateDefinition.DEATH_SPEECH);

        gameState.transfer(incomingSnapshot);
        assertEquals(GameState.StateDefinition.NIGHT_START, gameState.getCurrentState());
    }

    @After
    public void tearDown() throws Exception {
    }
}