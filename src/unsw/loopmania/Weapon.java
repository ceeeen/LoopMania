package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Abstract class for Weapon
 */
public abstract class Weapon extends Items {
    private int valueInShop = 100;

    Weapon(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public abstract Attack getAttack();

    public abstract String getType();

    public int getValueInShop() {
        return valueInShop;
    }
}
