package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.Assert.assertTrue;

import org.javatuples.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.*;
import unsw.loopmania.Character;
import unsw.loopmania.GoldPile;
import unsw.loopmania.HealthPotion;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.StaticEntity;

import unsw.loopmania.Sword;

import unsw.loopmania.Staff;
import unsw.loopmania.Stake;
import unsw.loopmania.Armour;
import unsw.loopmania.Shield;
import unsw.loopmania.Helmet;

public class ItemsTest {
    private int width = 3;
    private int height = 3;
    List<Pair<Integer, Integer>> orderedPath = Arrays.asList(new Pair<>(0, 0), new Pair<>(0, 1), new Pair<>(0, 2),
            new Pair<>(1, 2), new Pair<>(2, 2), new Pair<>(2, 1), new Pair<>(2, 0), new Pair<>(1, 0), new Pair<>(0, 0));

    @Test
    /**
     * Tests if gold piles spawn on only path tiles and if the correct amount of
     * gold is spawned.
     */
    public void testPossiblySpawnGoldPile() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        List<StaticEntity> spawningGoldPile = testWorld.possiblySpawnGoldPile();
        for (StaticEntity gold : spawningGoldPile) {
            Pair<Integer, Integer> test = new Pair<Integer, Integer>(gold.getX(), gold.getY());
            assertTrue(orderedPath.contains(test));
        }
        assertTrue(spawningGoldPile.size() > 0 && spawningGoldPile.size() < 10);

    }

    @Test
    /**
     * Tests if health potions spawn on only path tiles and if the correct amount of
     * health potion is spawned.
     */
    public void testPossiblySpawnHealthPotion() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        testWorld.setCycle((testWorld.getCycle()).get() + 1);
        List<StaticEntity> spawningHealthPotion = testWorld.possiblySpawnHealthPotion();

        // Health potions spawn on odd numbered cycles.
        assertNull(spawningHealthPotion);


        testWorld.setCycle((testWorld.getCycle()).get() + 1);
        spawningHealthPotion = testWorld.possiblySpawnHealthPotion();

        for (StaticEntity potion : spawningHealthPotion) {
            Pair<Integer, Integer> test = new Pair<Integer, Integer>(potion.getX(), potion.getY());
            assertTrue(orderedPath.contains(test));
        }

        assertTrue(spawningHealthPotion.size() > 0 && spawningHealthPotion.size() < 3);

    }

    @Test
    /**
     * Tests for character picking up gold and updating the gold value in character
     * class.
     */
    public void testPickUpGold() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);

        Character testChar = new Character(new PathPosition(0, orderedPath));
        testWorld.setCharacter(testChar);

        GoldPile testGold = new GoldPile(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        testWorld.addGoldPile(testGold);

        testWorld.pickUpItems();
        assertEquals(testWorld.getCharacter().getGold(), 10);

    }

    @Test
    public void testPickUpHealthPotion() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);

        Character testChar = new Character(new PathPosition(0, orderedPath));
        testChar.setHp(50);
        testWorld.setCharacter(testChar);

        HealthPotion testPotion = new HealthPotion(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        testWorld.addHealthPotion(testPotion);

        testWorld.pickUpItems();
        assertEquals(testWorld.getCharacter().getHp(), testPotion.getHpRefilled());
    }

    @Test
    /**
     * Tests if an unequipeed item is added to unequipped inventory.
     */
    public void testAddUnequippedSword() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Sword testSword = testWorld.addUnequippedSword();

        List<Entity> unequippedItems = testWorld.getUnequippedInventory();
        assertEquals(unequippedItems.get(0), testSword);
        assertEquals(unequippedItems.size(), 1);

    }

    @Test
    /**
     * Tests the Item Factory
     */
    public void testItemFactory() {
        ItemFactory itemFactory = new ItemFactory();
        Items item = itemFactory.getItem("Sword", new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        assertTrue(item instanceof Sword);
    }

    @Test
    public void testAddUnequippedStake() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Stake testStake = testWorld.addUnequippedStake();

        List<Entity> unequippedItems = testWorld.getUnequippedInventory();
        assertEquals(unequippedItems.get(0), testStake);
        assertEquals(unequippedItems.size(), 1);
    }

    @Test
    public void testAddUnequippedStaff() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Staff testStaff = testWorld.addUnequippedStaff();

        List<Entity> unequippedItems = testWorld.getUnequippedInventory();
        assertEquals(unequippedItems.get(0), testStaff);
        assertEquals(unequippedItems.size(), 1);
    }

    @Test
    public void testAddUnequippedArmour() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Armour testArmour = testWorld.addUnequippedArmour();

        List<Entity> unequippedItems = testWorld.getUnequippedInventory();
        assertEquals(unequippedItems.get(0), testArmour);
        assertEquals(unequippedItems.size(), 1);
    }

    @Test
    public void testAddUnequippedShield() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Shield testShield = testWorld.addUnequippedShield();

        List<Entity> unequippedItems = testWorld.getUnequippedInventory();
        assertEquals(unequippedItems.get(0), testShield);
        assertEquals(unequippedItems.size(), 1);
    }

    @Test
    public void testAddUnequippedHelmet() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Helmet testHelmet = testWorld.addUnequippedHelmet();

        List<Entity> unequippedItems = testWorld.getUnequippedInventory();
        assertEquals(unequippedItems.get(0), testHelmet);
        assertEquals(unequippedItems.size(), 1);
    }

    @Test
    public void testAddOneRing() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);

        List<String> rareItems = new ArrayList<>();
        rareItems.add("the_one_ring");

        testWorld.setRareItems(rareItems);
        TheOneRing testRing = testWorld.addOneRing();

        List<Entity> unequippedItems = testWorld.getUnequippedInventory();
        assertEquals(unequippedItems.get(0), testRing);
        assertEquals(unequippedItems.size(), 1);
    }

    @Test
    public void testAddRareItemDisabled() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);

        testWorld.addOneRing();

        List<Entity> unequippedItems = testWorld.getUnequippedInventory();
        assertEquals(unequippedItems.size(), 0);
    }

    @Test
    public void testAddAnduril() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);

        List<String> rareItems = new ArrayList<>();
        rareItems.add("anduril_flame_of_the_west");

        testWorld.setRareItems(rareItems);
        Anduril testAnduril = testWorld.addAnduril();

        List<Entity> unequippedItems = testWorld.getUnequippedInventory();
        assertEquals(unequippedItems.get(0), testAnduril);
        assertEquals(unequippedItems.size(), 1);
    }

    @Test
    public void testAddTreeStump() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);

        List<String> rareItems = new ArrayList<>();
        rareItems.add("tree_stump");

        testWorld.setRareItems(rareItems);
        TreeStump testStump = testWorld.addTreeStump();

        List<Entity> unequippedItems = testWorld.getUnequippedInventory();
        assertEquals(unequippedItems.get(0), testStump);
        assertEquals(unequippedItems.size(), 1);
    }

    @Test
    /**
     * Tests if the character uses the one ring if available in unequipped
     * inventory.
     */
    public void testUseOneRing() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Character testChar = new Character(new PathPosition(0, orderedPath));
        testChar.setHp(0);

        testWorld.setCharacter(testChar);

        List<String> rareItems = new ArrayList<>();
        rareItems.add("the_one_ring");
        testWorld.setRareItems(rareItems);

        testWorld.addOneRing();

        testWorld.useRareItem("the_one_ring");

        assertEquals(testWorld.getCharacter().getHp(), 100);

    }

    @Test
    public void testDestroyingItemsFromFullInventory() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Character testChar = new Character(new PathPosition(0, orderedPath));

        testWorld.setCharacter(testChar);

        for (int i = 0; i < 17; i++) {
            testWorld.addUnequippedSword();
        }

        assertEquals(testWorld.getCharacter().getGold(), 5);
    }

    @Test
    /**
     * Tests if weapons become stored into the Character class.
     */
    public void testAddToEquippedWeaposn() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Character testChar = new Character(new PathPosition(0, orderedPath));

        testWorld.setCharacter(testChar);

        Sword testSword = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        testWorld.addToEquippedWeapon(testSword);

        assertEquals(testWorld.getCharacter().getWeapon(), testSword);

    }

    @Test
    public void testAddToEquippedItems() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Character testChar = new Character(new PathPosition(0, orderedPath));

        testWorld.setCharacter(testChar);

        Armour testArmour = new Armour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        testWorld.addToEquippedProtectiveItems(testArmour);

        Shield testShield = new Shield(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        testWorld.addToEquippedProtectiveItems(testShield);

        Helmet testHelmet = new Helmet(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        testWorld.addToEquippedProtectiveItems(testHelmet);

        assertEquals(testWorld.getCharacter().getArmour(), testArmour);
        assertEquals(testWorld.getCharacter().getShield(), testShield);
        assertEquals(testWorld.getCharacter().getHelmet(), testHelmet);

    }

    @Test
    public void testRemoveEquippedItemByCoordinates() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Character testChar = new Character(new PathPosition(0, orderedPath));

        testWorld.setCharacter(testChar);

        Sword testSword = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        testWorld.addToEquippedWeapon(testSword);

        Armour testArmour = new Armour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1));
        testWorld.addToEquippedProtectiveItems(testArmour);

        Shield testShield = new Shield(new SimpleIntegerProperty(0), new SimpleIntegerProperty(2));
        testWorld.addToEquippedProtectiveItems(testShield);

        Helmet testHelmet = new Helmet(new SimpleIntegerProperty(0), new SimpleIntegerProperty(3));
        testWorld.addToEquippedProtectiveItems(testHelmet);

        testWorld.removeEquippedItemByCoordinates(0, 0);
        assertEquals(testWorld.getCharacter().getWeapon(), null);

        testWorld.removeEquippedItemByCoordinates(0, 1);
        assertEquals(testWorld.getCharacter().getArmour(), null);

        testWorld.removeEquippedItemByCoordinates(0, 2);
        assertEquals(testWorld.getCharacter().getShield(), null);

        testWorld.removeEquippedItemByCoordinates(0, 3);
        assertEquals(testWorld.getCharacter().getHelmet(), null);

    }

    @Test
    public void testRemovedUnequippedInventoryItemByCoordinates() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        testWorld.addUnequippedSword();

        testWorld.removeUnequippedInventoryItemByCoordinates(0, 0);

        List<Entity> unequippedItems = testWorld.getUnequippedInventory();
        assertEquals(unequippedItems.size(), 0);
    }

}
