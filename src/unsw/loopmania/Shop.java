package unsw.loopmania;

import java.util.HashMap;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/*
* Shop that deals with the buy/sell logic that uses
* a strategy pattern
*/
public class Shop {
    private HashMap<String, Integer> itemsAvailable;         // Use in frontend to display items on sale.
    private List<Entity> unequippedInventoryItems;     // Use in frontend to display unequippedInventory of character.
    private ShopStrategy strategy;
    private BooleanProperty shopActive;
    private int shopCounter;
    private int shopCycle;

    public Shop(ShopStrategy strategy, List<Entity> unequippedInventoryItems) {
        this.strategy = strategy;
        this.unequippedInventoryItems = unequippedInventoryItems;
        itemsAvailable = strategy.initialiseShopItems();
        shopActive = new SimpleBooleanProperty();
        setShopActive(false);
        shopCounter = 2;
        shopCycle = 1;
    }

    public Shop() {
        shopActive = new SimpleBooleanProperty();
        setShopActive(false);
    }

    /**
     * Buys items from shop and puts into LoopManiaWorld unequippedInventory.
     * World also stores the charcter. Gold would be taken from the charater there.
     * Purchasing is dependent of the shop strategy set during initialisation of shop.
     * @param world
     * @param item <-- item here is the item clicked on in the shop frontend.
     */
    public void buyFromShop(LoopManiaWorldController worldController, String item) {
        itemsAvailable = strategy.buyFromShop(worldController, itemsAvailable, item);
    }

    /**
     * Buys items from unequipped inventory of the character. Bought items are removed from 
     * character unequipped inventory.
     * @param world
     * @param item <-- item here is the item clicked on in the unequipped inventory.
     */
    public void buyFromCharacter(LoopManiaWorld world, String item) {
        Character tempChar = world.getCharacter();
        int itemValue = 0;
        Items temp = null;

        unequippedInventoryItems = world.getUnequippedInventory();
        for (Entity e : unequippedInventoryItems) {
            temp = (Items) e;
            if (temp.getType().equalsIgnoreCase(item)) {
                break;
            }
        }
        // Get gold value of item depending on item type.
        if (temp == null) {
            return;
        }
        else if (temp instanceof Weapon) {
            itemValue = ((Weapon) temp).getValueInShop();
        }
        else if (temp instanceof ProtectiveGear) {
            itemValue = ((ProtectiveGear) temp).getValueInShop();
        }
        else if (temp instanceof HealthPotion) {
            itemValue = ((HealthPotion) temp).getValueInShop();
        }
        else if (temp instanceof DoggieCoin) {
            itemValue = ((DoggieCoin) temp).getValueInShop();
        }
        // Adds gold into character gold count.
        tempChar.setGold(itemValue);
        
        world.removeUnequippedInventoryItemByCoordinates(temp.getX(), temp.getY());

    }

    public void shopExit() {
        itemsAvailable = strategy.exitShop(itemsAvailable);
    }

    public void checkShopActivate(int charX, int charY, int cycle) {
        if (charX == 0 && charY == 0 && shopCycle == cycle) {
            shopCycle += shopCounter;
            shopCounter++;
            setShopActive(true);
        } else {
            setShopActive(false);

        }
    }
    public BooleanProperty getShopActive() {
        return shopActive;
    }

    public void setShopActive(boolean res) {
        shopActive.set(res);
    }

    public ShopStrategy getStrategy() {
        return strategy;
    }
    
}
