package unsw.loopmania;

import java.util.HashMap;
/*
* Interface for the strategy pattern for shop
*/
public interface ShopStrategy {
    public abstract HashMap<String, Integer> initialiseShopItems();

    public abstract HashMap<String, Integer> buyFromShop(LoopManiaWorldController worldController, HashMap<String, Integer> itemsAvailable, String item);

    public abstract HashMap<String, Integer> exitShop(HashMap<String, Integer> itemsAvailable);

}
