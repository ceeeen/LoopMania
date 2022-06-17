package test;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.List;

import org.javatuples.Pair;

import org.junit.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Card;

public class CardsTest {
    private int width = 1;
    private int height = 3;
    List<Pair<Integer, Integer>> orderedPath = Arrays.asList(new Pair<>(0, 0), new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(8, 8));  

    @Test
    public void loadVampireCardTest() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Card card = testWorld.loadVampireCard();
        assertTrue(card != null);

    }
    @Test
    public void loadZombieCardTest() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Card card = testWorld.loadZombieCard();
        assertTrue(card != null);

    }
    @Test
    public void loadBarracksCardTest() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Card card = testWorld.loadBarracksCard();
        assertTrue(card != null);

    }
    @Test
    public void loadTowerCardTest() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Card card = testWorld.loadTowerCard();
        assertTrue(card != null);

    }
    @Test
    public void loadTrapCardTest() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Card card = testWorld.loadTrapCard();
        assertTrue(card != null);

    }
    @Test
    public void loadVillageCardTest() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Card card = testWorld.loadVillageCard();
        assertTrue(card != null);

    }
    @Test
    public void loadCampfireCardTest() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Card card = testWorld.loadCampfireCard();
        assertTrue(card != null);

    }
}
