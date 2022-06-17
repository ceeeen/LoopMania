package unsw.loopmania;

import java.util.HashMap;
import javafx.beans.property.SimpleIntegerProperty;
/**
 * Shop strategy that is created when berserker difficulty is selected. It limits
 * the amount of armor that can be purchased.
 */
public class BerserkerShop implements ShopStrategy {

    public HashMap<String, Integer> initialiseShopItems() {
        HashMap<String, Integer> berserkerShopItems = new HashMap<String, Integer>();

        // Putting weapons into hashmap. Items are the key with the number available
        // from shop is set as the value.
        // Berserker mode only allows 1 piece of protective equipment to be bought.
        berserkerShopItems.put("Sword", Integer.MAX_VALUE);
        berserkerShopItems.put("Staff", Integer.MAX_VALUE);
        berserkerShopItems.put("Stake", Integer.MAX_VALUE);
        berserkerShopItems.put("Armour", 1);
        berserkerShopItems.put("Shield", 1);
        berserkerShopItems.put("Helmet", 1);
        berserkerShopItems.put("HealthPotion",Integer.MAX_VALUE);

        return berserkerShopItems;
    }

    /**
     * Buys items from shop and puts into LoopManiaWorld world unequippedInventory.
     * World also stores the charcter. Gold would be taken from the charater there.
     * 
     * @param world
     * @param itemsAvailable
     * @param item
     */
    public HashMap<String, Integer> buyFromShop(LoopManiaWorldController worldController, HashMap<String, Integer> itemsAvailable,
            String item) {
        LoopManiaWorld world = worldController.getWorld();
        Character tempChar = world.getCharacter();
        ItemFactory itemFactory = new ItemFactory();

        Items itemBuy = itemFactory.getItem(item, new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        int itemValue;

        // Early return if there are none of a particular item left in the shop.
        if (itemsAvailable.get(item) == 0) {
            return itemsAvailable;
        }

        // Typecasting to get correct cost values of weapons, gear etc.
        if (itemBuy instanceof Weapon) {
            itemValue = ((Weapon) itemBuy).getValueInShop();
            if (tempChar.getGold() < itemValue) {
                return itemsAvailable;
            }
            tempChar.setGold(-itemValue);
        } else if (itemBuy instanceof ProtectiveGear) {
            itemValue = ((ProtectiveGear) itemBuy).getValueInShop();
            if (tempChar.getGold() < itemValue) {
                return itemsAvailable;
            }
            tempChar.setGold(-itemValue);
        } else if (itemBuy instanceof HealthPotion) {
            itemValue = ((HealthPotion) itemBuy).getValueInShop();
            if (tempChar.getGold() < itemValue) {
                return itemsAvailable;
            }
            tempChar.setGold(-itemValue);
            System.out.println(world.getCharacter().getGold() + "NEW GOLD VALUE");

        }
        addItemIntoWorld(worldController, itemBuy);

        // Subtracts the quantity remaining of a particular item by 1 in hashmap.
        if (itemBuy instanceof ProtectiveGear) {
            itemsAvailable.put("Armour", 0);
            itemsAvailable.put("Shield", 0);
            itemsAvailable.put("Helmet", 0);
        }
        else {
            itemsAvailable.put(item, itemsAvailable.get(item) - 1);
        }
        return itemsAvailable;
    }
    /**
     * Reset shop when we leave
     * @param itemsAvailable
     * @return itemsAvailale
     */
    public HashMap<String, Integer> exitShop (HashMap<String, Integer> itemsAvailable) {
        itemsAvailable.put("Armour", 1);
        itemsAvailable.put("Shield", 1);
        itemsAvailable.put("Helmet", 1);
        return itemsAvailable;
    }

    /**
     * Adds item bought into world.
     */
    private void addItemIntoWorld(LoopManiaWorldController worldController, Items item) {
        if (item instanceof Sword) {
            worldController.loadSword();
        } else if (item instanceof Staff) {
            worldController.loadStaff();
        } else if (item instanceof Stake) {
            worldController.loadStake();
        } else if (item instanceof Armour) {
            worldController.loadArmour();
        } else if (item instanceof Shield) {
            worldController.loadShield();
        } else if (item instanceof Helmet) {
            worldController.loadHelmet();
        } else if (item instanceof HealthPotion) {
            worldController.getWorld().consumeHealthPotion(((HealthPotion) item));
        }

    }

}
