package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.javatuples.Pair;

import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.AllySoldier;
import unsw.loopmania.BarracksBuilding;
import unsw.loopmania.Enemy;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.MovingEntity;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Vampire;
import unsw.loopmania.VampireCastleBuilding;
import unsw.loopmania.Zombie;
import unsw.loopmania.ZombiePitBuilding;
import unsw.loopmania.Character;

public class BuildingSpawnEntityTest {
    private int width = 3;
    private int height = 3;
    List<Pair<Integer, Integer>> orderedPath = Arrays.asList(new Pair<>(0, 0),
                                                             new Pair<>(0, 1),
                                                             new Pair<>(0, 2),
                                                             new Pair<>(1, 2),
                                                             new Pair<>(2, 2),
                                                             new Pair<>(2, 1),
                                                             new Pair<>(2, 0),
                                                             new Pair<>(1, 0),
                                                             new Pair<>(0, 0));                       
    @Test
    public void testZombieSpawn() {  
        ZombiePitBuilding zombiePitBuilding = new ZombiePitBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1));
        Zombie zombie = zombiePitBuilding.SpawnEnemy(0, orderedPath);
        assertTrue(zombie instanceof Enemy);
    }
    @Test
    public void testVampireSpawn() {
        VampireCastleBuilding vampireCastleBuilding = new VampireCastleBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1));
        Vampire vampire = vampireCastleBuilding.SpawnEnemy(5, orderedPath);
        assertTrue(vampire instanceof Enemy);
    }
    @Test
    public void testAllySpawn() {
        BarracksBuilding barracksBuilding = new BarracksBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2));
        AllySoldier allySoldier = barracksBuilding.spawnAlly(orderedPath);
        assertTrue(allySoldier instanceof MovingEntity);
    }
    @Test
    public void testPossiblySpawnEnemies() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        testWorld.setCycle(5);
        Character testChar = new Character(new PathPosition(0, orderedPath));
        testWorld.setCharacter(testChar);

        testWorld.addBuilding(new VampireCastleBuilding(new SimpleIntegerProperty(2), new SimpleIntegerProperty(3)));
        testWorld.addBuilding(new ZombiePitBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1)));
    
        int numEnemy = testWorld.possiblySpawnEnemies().size();
        assertTrue(numEnemy == 2 || numEnemy == 3);
        assertTrue(testWorld.getEnemies().size() == numEnemy);
    }
    @Test
    public void testPossiblySpawnAllies() {
        List<Pair<Integer, Integer>> ordereddPath = new ArrayList<>();
        ordereddPath.add(Pair.with(1, 1));
        ordereddPath.add(Pair.with(0, 0));
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, ordereddPath);
        Character testChar = new Character(new PathPosition(1, ordereddPath));
        testWorld.setCharacter(testChar);
        testWorld.addBuilding(new BarracksBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        testWorld.possiblySpawnAllySoldiers();
        assertEquals(testWorld.getAllies().size(), 1);
        testWorld.possiblySpawnAllySoldiers();
        assertEquals(testWorld.getAllies().size(), 2);
    }
} 
