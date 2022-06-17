package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents a health potion on path tiles in the frontend and backend world.
 */
public class HealthPotion extends Consumables {
    private int hpRefilled;
    private int valueInShop = 500;
    private String type = "HealthPotion";

    public HealthPotion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.hpRefilled = 100;
    }

    public int getHpRefilled() {
        return this.hpRefilled;
    }

    public int getValueInShop() {
        return valueInShop;
    }

    public String getType() {
        return type;
    }

}
