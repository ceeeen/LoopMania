package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Abstract class for RareItems
 */
public abstract class RareItems extends Items {

    protected Attack attack;

    RareItems(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public Attack getAttack() {
        return attack;
    }

}
