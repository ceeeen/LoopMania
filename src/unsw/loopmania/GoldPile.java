package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents a pile of gold on path tiles in frontend and backend world.
 */
public class GoldPile extends StaticEntity{
    private int goldPileValue = 10;

    public GoldPile(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public int getGoldPileValue() {
        return this.goldPileValue;
    }
}
