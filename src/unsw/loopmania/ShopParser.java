package unsw.loopmania;
import java.util.List;
/*
* Creates the correct shop depending on which difficulty
*/
public class ShopParser {
    ShopParser(){};

    public Shop createShop(int difficulty, List<Entity> unequippedInventoryItems) {
        Shop shop = null;
        System.out.println(difficulty);
        
        switch (difficulty) {
            case(0):
            shop = new Shop(new StandardShop(), unequippedInventoryItems);
                break;
            case(1):
            shop = new Shop(new SurvivalShop(), unequippedInventoryItems);
            System.out.println("SURVIVALSHOP");
                break;
            case(2):
            shop = new Shop(new BerserkerShop(), unequippedInventoryItems);
                break;
            case(3):
            shop = new Shop(new StandardShop(), unequippedInventoryItems);
                break;
        }
        return shop;   
    }
}
