package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.javatuples.Pair;

import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.AllySoldier;
import unsw.loopmania.BarracksBuilding;
import unsw.loopmania.CampfireBuilding;
import unsw.loopmania.Enemy;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.MovingEntity;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Slug;
import unsw.loopmania.TowerBuilding;
import unsw.loopmania.TrapBuilding;
import unsw.loopmania.Vampire;
import unsw.loopmania.VampireCastleBuilding;
import unsw.loopmania.VillageBuilding;
import unsw.loopmania.Zombie;
import unsw.loopmania.ZombiePitBuilding;
import unsw.loopmania.Character;
public class BuildingInteractionTest {
    
    private int width = 3;
    private int height = 3;
    List<Pair<Integer, Integer>> orderedPath = Arrays.asList(new Pair<>(0, 0), new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(8, 8));    
    @Test
    public void testTowerAttacks() {
        ZombiePitBuilding zombiePitBuilding = new ZombiePitBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        Zombie zombie1 = zombiePitBuilding.SpawnEnemy(0, orderedPath);
        TowerBuilding tower = new TowerBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertEquals(tower.towerDamage(zombie1), 2);
        ZombiePitBuilding zombiePitBuilding2 = new ZombiePitBuilding(new SimpleIntegerProperty(8), new SimpleIntegerProperty(7));
        Zombie zombie2 = zombiePitBuilding2.SpawnEnemy(0, orderedPath);
        assertEquals(tower.towerDamage(zombie2), 0);
    }

    @Test
    public void testVillageHeals() {
        Character testChar = new Character(new PathPosition(1, orderedPath)); // Position 0, 1
        testChar.setHp(90);
        VillageBuilding village = new VillageBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1));
        village.villageHeal(testChar);
        assertTrue(testChar.getHp() == 100);
        village.villageHeal(testChar);
        assertTrue(testChar.getHp() == 100);
    }
    @Test
    public void testTrapDamages() {
        ZombiePitBuilding zombiePitBuilding = new ZombiePitBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        Zombie zombie1 = zombiePitBuilding.SpawnEnemy(0, orderedPath); // 0, 1
        TrapBuilding trapBuilding = new TrapBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1));
        assertEquals(trapBuilding.trapDamage(zombie1), 2);
        ZombiePitBuilding zombiePitBuilding2 = new ZombiePitBuilding(new SimpleIntegerProperty(8), new SimpleIntegerProperty(7));
        Zombie zombie2 = zombiePitBuilding2.SpawnEnemy(0, orderedPath);
        assertEquals(trapBuilding.trapDamage(zombie2), 0);
    }

    @Test
    public void testCampfireBuff() {
        Character testChar = new Character(new PathPosition(1, orderedPath)); // Position 0, 1
        CampfireBuilding campfire = new CampfireBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        campfire.buffCharacter(testChar);
        assertTrue(testChar.getBuffed());
    
        CampfireBuilding campfire2 = new CampfireBuilding(new SimpleIntegerProperty(8), new SimpleIntegerProperty(8));
        Character testChar2 = new Character(new PathPosition(1, orderedPath)); // Position 0, 1
        campfire2.buffCharacter(testChar2);
        assertFalse(testChar2.getBuffed());
    }

    @Test
    public void testBuildingBuff() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Character testChar = new Character(new PathPosition(0, orderedPath));
        testWorld.setCharacter(testChar);
        ZombiePitBuilding zombiePitBuilding = new ZombiePitBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        CampfireBuilding campfire = new CampfireBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        testWorld.addBuilding(zombiePitBuilding);
        testWorld.buildingBuff();
        assertFalse(testChar.getBuffed());
        testWorld.addBuilding(campfire);
        testWorld.buildingBuff();
        assertTrue(testChar.getBuffed());

    }
    @Test
    public void testBuildingUnbuff() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Character testChar = new Character(new PathPosition(0, orderedPath));
        testWorld.setCharacter(testChar);
        CampfireBuilding campfire = new CampfireBuilding(new SimpleIntegerProperty(10), new SimpleIntegerProperty(10));
        testWorld.addBuilding(campfire);
        testChar.setBuffed(true);
        testWorld.buildingUnbuff();
        assertFalse(testChar.getBuffed());
    }
    @Test
    public void buildingTrapTest() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Slug slug = new Slug(new PathPosition(0, orderedPath));
        TrapBuilding trap = new TrapBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        CampfireBuilding campfire = new CampfireBuilding(new SimpleIntegerProperty(10), new SimpleIntegerProperty(10));
        testWorld.addBuilding(trap);
        testWorld.addBuilding(campfire);
        testWorld.buildingTrap(slug);
        assertEquals(slug.getHp(), 3);
    }
    @Test
    public void buildingAttackTest() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Character testChar = new Character(new PathPosition(0, orderedPath));
        testWorld.setCharacter(testChar);
    
        Slug slug = new Slug(new PathPosition(0, orderedPath));
        TowerBuilding tower = new TowerBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1));
        testWorld.addBuilding(tower);
        testWorld.buildingAttack(slug);
        assertTrue(slug.getHp() > 0);
        testWorld.buildingAttack(slug);
        testWorld.buildingAttack(slug);
        assertTrue(slug.getHp() <= 0);
    }

    @Test
    public void buildingHealTest() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Character testChar = new Character(new PathPosition(0, orderedPath));
        testChar.setHp(90);
        testWorld.setCharacter(testChar);
        TrapBuilding trap = new TrapBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(12));
        TowerBuilding tower = new TowerBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(12));
        VillageBuilding village = new VillageBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        testWorld.addBuilding(tower);
        testWorld.addBuilding(trap);
        testWorld.addBuilding(village);
        testWorld.buildingHeal();
        assertEquals(testWorld.getPlayerHp(), 100);

    }
}
