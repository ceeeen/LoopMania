package test;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import static org.junit.Assert.assertTrue;
import unsw.loopmania.*;
import unsw.loopmania.Character;

public class ShopTest {
    private int width = 3;
    private int height = 3;
    List<Pair<Integer, Integer>> orderedPath = Arrays.asList(new Pair<>(0, 0), new Pair<>(0, 1), new Pair<>(0, 2),
            new Pair<>(1, 2), new Pair<>(2, 2), new Pair<>(2, 1), new Pair<>(2, 0), new Pair<>(1, 0), new Pair<>(0, 0));

    @Test
    /**
     * Tests failure when buying an item from character when character has no item.
     */
    public void testBuyItemFromCharacterNoItem() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Character testChar = new Character(new PathPosition(0, orderedPath));
        List<Entity> unequippedInventoryItems = new ArrayList<>();
        Shop testShop = new Shop(new StandardShop(), unequippedInventoryItems);

        testWorld.setUnequippedInventory(unequippedInventoryItems);
        testWorld.setCharacter(testChar);
        
        testShop.buyFromCharacter(testWorld, "Sword");
        unequippedInventoryItems = testWorld.getUnequippedInventory();
        assertEquals(unequippedInventoryItems.size(), 0);
        assertEquals(testChar.getGold(), 0);
    }
    
    @Test
    /**
     * Tests for buying a weapon from character.
     */
    public void testBuyWeaponFromCharacter() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Character testChar = new Character(new PathPosition(0, orderedPath));
        List<Entity> unequippedInventoryItems = new ArrayList<>();
        Shop testShop = new Shop(new StandardShop(), unequippedInventoryItems);

        Sword testSword = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        unequippedInventoryItems.add(testSword);
        
        testWorld.setUnequippedInventory(unequippedInventoryItems);
        testWorld.setCharacter(testChar);
        
        testShop.buyFromCharacter(testWorld, "Sword");
        unequippedInventoryItems = testWorld.getUnequippedInventory();
        assertEquals(unequippedInventoryItems.size(), 0);
        assertEquals(testChar.getGold(), testSword.getValueInShop());
        
    }

    @Test
    /**
     * Tests for buying an armour from character.
     */
    public void testBuyGearFromCharacter() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Character testChar = new Character(new PathPosition(0, orderedPath));
        List<Entity> unequippedInventoryItems = new ArrayList<>();
        Shop testShop = new Shop(new StandardShop(), unequippedInventoryItems);

        Armour testArmour = new Armour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        unequippedInventoryItems.add(testArmour);
        
        testWorld.setUnequippedInventory(unequippedInventoryItems);
        testWorld.setCharacter(testChar);
        
        testShop.buyFromCharacter(testWorld, "Sword");
        unequippedInventoryItems = testWorld.getUnequippedInventory();
        assertEquals(unequippedInventoryItems.size(), 0);
        assertEquals(testChar.getGold(), testArmour.getValueInShop());
    }

    @Test
    /**
     * Tests for buying a DoggieCoin from character.
     */
    public void testBuyDoggieCoinFromCharacter() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Character testChar = new Character(new PathPosition(0, orderedPath));
        List<Entity> unequippedInventoryItems = new ArrayList<>();
        Shop testShop = new Shop(new StandardShop(), unequippedInventoryItems);

        DoggieCoin testCoin = new DoggieCoin(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        unequippedInventoryItems.add(testCoin);
        
        testWorld.setUnequippedInventory(unequippedInventoryItems);
        testWorld.setCharacter(testChar);
        
        testShop.buyFromCharacter(testWorld, "Sword");
        unequippedInventoryItems = testWorld.getUnequippedInventory();
        assertEquals(unequippedInventoryItems.size(), 0);
        assertEquals(testChar.getGold(), testCoin.getValueInShop());
    }

    @Test
    /**
     * Tests if the shop is appears at the correct cycles.
     */
    public void testShopActivate() {
        Character testChar = new Character(new PathPosition(0, orderedPath));
        List<Entity> unequippedInventoryItems = new ArrayList<>();
        Shop testShop = new Shop(new StandardShop(), unequippedInventoryItems);

        testShop.checkShopActivate(testChar.getX(), testChar.getY(), 1);
        assertTrue(testShop.getShopActive().get());

        testShop.checkShopActivate(testChar.getX(), testChar.getY(), 2);
        assertFalse(testShop.getShopActive().get());
    }
}
