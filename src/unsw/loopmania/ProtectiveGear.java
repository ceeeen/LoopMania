package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Abstract class for  ProtectiveGears.
 */
public abstract class ProtectiveGear extends Items {

    protected int attackDecrease;
    protected int defence;
    protected double damageDecreaseModifier;
    private int valueInShop = 200;

    ProtectiveGear(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public int getAttackDecrease() {
        return attackDecrease;
    }

    public int getDefence() {
        return defence;
    }

    public double getDamageDecreaseModifier() {
        return damageDecreaseModifier;
    }

    public int getValueInShop() {
        return valueInShop;
    }
}
