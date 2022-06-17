package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.List;

import org.javatuples.Pair;

import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Slug;
import unsw.loopmania.ZombiePitBuilding;
import unsw.loopmania.Character;

public class loopManiaHelperFunctionTest {
    private int width = 3;
    private int height = 3;
    List<Pair<Integer, Integer>> orderedPath = Arrays.asList(new Pair<>(0, 0), new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(8, 8)); 

    @Test
    public void checkAdjacentPathTileTest() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        assertTrue(testWorld.checkAdjacentPathTile(7,7));
        assertTrue(testWorld.checkAdjacentPathTile(8,7));
        assertTrue(testWorld.checkAdjacentPathTile(9,7));
        assertTrue(testWorld.checkAdjacentPathTile(7,8));
        assertTrue(testWorld.checkAdjacentPathTile(9,8));
        assertTrue(testWorld.checkAdjacentPathTile(7,9));
        assertTrue(testWorld.checkAdjacentPathTile(8,9));
        assertTrue(testWorld.checkAdjacentPathTile(9,9));
        assertFalse(testWorld.checkAdjacentPathTile(5,5));
    }
    @Test 
    public void checkPlacementTest() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        ZombiePitBuilding zombiePitBuilding = new ZombiePitBuilding((new SimpleIntegerProperty(1)), new SimpleIntegerProperty(1));
        testWorld.addBuilding(zombiePitBuilding);
        assertFalse(testWorld.checkPlacement(1, 1));
        assertTrue(testWorld.checkPlacement(1, 2));
    }
    @Test
    public void checkDistanceCalcuate() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Character testChar = new Character(new PathPosition(1, orderedPath));
        Slug slug = new Slug(new PathPosition(0, orderedPath));
        assertEquals(testWorld.DistanceCalculate(testChar, slug), 1.0, 0.001);
    }
}
