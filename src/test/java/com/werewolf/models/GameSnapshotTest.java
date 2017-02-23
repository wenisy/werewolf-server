package com.werewolf.models;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.ArrayList;

public class GameSnapshotTest {
    private GameSnapshot gameSnapshot;
    private int numberOfEmptySeat;
    private PlayerSnapshot playerSnapshot1;
    private PlayerSnapshot playerSnapshot2;

    @Before
    public void setUp() throws Exception {
        playerSnapshot1 = mock(PlayerSnapshot.class);
        playerSnapshot2 = mock(PlayerSnapshot.class);
        when(playerSnapshot1.getSeatID()).thenReturn(1);
        when(playerSnapshot2.getSeatID()).thenReturn(2);

        numberOfEmptySeat = 0;
        ArrayList<PlayerSnapshot> playerSnapshots = new ArrayList<PlayerSnapshot>();
        playerSnapshots.add(playerSnapshot1);
        playerSnapshots.add(playerSnapshot2);
        gameSnapshot = new GameSnapshot(playerSnapshots,numberOfEmptySeat);
    }

    @Test
    public void needApplySheriff() throws Exception {
        when(playerSnapshot1.isSheriff()).thenReturn(true);
        when(playerSnapshot2.isSheriff()).thenReturn(false);
        assertEquals(false, gameSnapshot.needApplySheriff());
        //assertEquals(judgeTriangle.getIsoscelesTriangle(),judgeTriangle.judgeType(2, 2, 3));
    }

    @Test
    public void getApplySheriffIDTest() throws Exception {
        when(playerSnapshot1.isApplySheriff()).thenReturn(true);
        when(playerSnapshot2.isApplySheriff()).thenReturn(true);
        ArrayList<Integer> expectedResults = new ArrayList<Integer>();
        expectedResults.add(1);
        expectedResults.add(2);

        assertEquals(expectedResults, gameSnapshot.getApplySheriffID());
        //assertEquals(judgeTriangle.getIsoscelesTriangle(),judgeTriangle.judgeType(2, 2, 3));
    }

    @After
    public void tearDown() throws Exception {
    }
}