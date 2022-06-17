package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/*
* Factory to produce items
*/
public class ItemFactory {

    public Items getItem(String itemType, SimpleIntegerProperty x, SimpleIntegerProperty y) {
        if (itemType == null) {
            return null;
        }
        if (itemType.equalsIgnoreCase("Sword")) {
            return new Sword(x, y);
        } else if (itemType.equalsIgnoreCase("Staff")) {
            return new Staff(x, y);
        } else if (itemType.equalsIgnoreCase("Stake")) {
            return new Stake(x, y);
        } else if (itemType.equalsIgnoreCase("Armour")) {
            return new Armour(x, y);
        } else if (itemType.equalsIgnoreCase("Shield")) {
            return new Shield(x, y);
        } else if (itemType.equalsIgnoreCase("Helmet")) {
            return new Helmet(x, y);
        } else if (itemType.equalsIgnoreCase("Anduril")) {
            return new Anduril(x, y);
        } else if (itemType.equalsIgnoreCase("TreeStump")) {
            return new TreeStump(x, y);
        } else if (itemType.equalsIgnoreCase("DoggieCoin")) {
            return new DoggieCoin(x, y);
        } else if (itemType.equalsIgnoreCase("HealthPotion")) {
            return new HealthPotion(x, y);
        }
        return null;
    }

}
