package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Abstract class for Items
 */
public abstract class Items extends StaticEntity {

    public Items(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public abstract String getType();

}
