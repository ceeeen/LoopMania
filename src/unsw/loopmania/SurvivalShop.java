package unsw.loopmania;

import java.util.HashMap;
import javafx.beans.property.SimpleIntegerProperty;
/*
* Survival shop that limits the amount of health potions that can be
* bought at a time
*/
public class SurvivalShop implements ShopStrategy {

    public HashMap<String, Integer> initialiseShopItems() {
        HashMap<String, Integer> survivalShopItems = new HashMap<String, Integer>();

        // Putting weapons into hashmap. Items are the key with the number available
        // from shop is set as the value.
        // Survival mode can only buy 1 health potion.
        survivalShopItems.put("Sword", Integer.MAX_VALUE);
        survivalShopItems.put("Staff",Integer.MAX_VALUE);
        survivalShopItems.put("Stake",Integer.MAX_VALUE);
        survivalShopItems.put("Armour",Integer.MAX_VALUE);
        survivalShopItems.put("Shield",Integer.MAX_VALUE);
        survivalShopItems.put("Helmet",Integer.MAX_VALUE);
        survivalShopItems.put("HealthPotion", 1);

        return survivalShopItems;
    }

    public HashMap<String, Integer> buyFromShop(LoopManiaWorldController worldController, HashMap<String, Integer> itemsAvailable,
            String item) {
        LoopManiaWorld world = worldController.getWorld();
        Character tempChar = world.getCharacter();
        ItemFactory itemFactory = new ItemFactory();

        Items itemBuy = itemFactory.getItem(item, new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        int itemValue;

        // Early return if there are none of a particular item left in the shop.
        if (itemsAvailable.get(item) <= 0) {
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

        }
        addItemIntoWorld(worldController, itemBuy);

        // Subtracts the quantity remaining of a particular item by 1 in hashmap.
        itemsAvailable.put(item, itemsAvailable.get(item) - 1);
        return itemsAvailable;
    }

    public HashMap<String, Integer> exitShop(HashMap<String, Integer> itemsAvailable) {
        itemsAvailable.put("HealthPotion", 1);
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
