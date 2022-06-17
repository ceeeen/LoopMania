package test;

import org.javatuples.Pair;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import unsw.loopmania.*;
import unsw.loopmania.Character;

public class MovementTest {
    private int width = 3;
    private int height = 3;
    List<Pair<Integer, Integer>> orderedPath = Arrays.asList(new Pair<>(0, 0), new Pair<>(0, 1), new Pair<>(0, 2),
            new Pair<>(1, 2), new Pair<>(2, 2), new Pair<>(2, 1), new Pair<>(2, 0), new Pair<>(1, 0), new Pair<>(0, 0));

    @Test
    /**
     * Tests if player moves in the clockwise direction Tests that player does not
     * move if state is attackState
     */
    public void testPlayerMovement() {
        Character testCharacter = new Character(new PathPosition(0, orderedPath));
        testCharacter.move();
        assertEquals(testCharacter.getX(), 0);
        assertEquals(testCharacter.getY(), 1);
        testCharacter.move();
        assertEquals(testCharacter.getX(), 0);
        assertEquals(testCharacter.getY(), 2);
        testCharacter.setAttackState();
        testCharacter.move();
        assertEquals(testCharacter.getX(), 0);
        assertEquals(testCharacter.getY(), 2);

    }
}
