package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents a Shield object in the frontend and backend world. The shield can
 * be equipped to reduce critical bites by Vampires.
 */

public class Shield extends ProtectiveGear {

    private String type = "Shield";

    public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.defence = 1;
        this.damageDecreaseModifier = 1;
    }

    public String getType() {
        return type;
    }
}
