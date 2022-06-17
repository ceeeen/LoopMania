package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Abstract class for Consumables.
 */
public abstract class Consumables extends Items{
    
    Consumables(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
}
