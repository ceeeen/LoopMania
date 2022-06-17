package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents a Helmet object in the backend and frontend world. The helmet reduces damage
 * by a scalar value but also reduces player attack by a scalar value.
 */
public class Helmet extends ProtectiveGear {

    private String type = "Helmet";

    public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.attackDecrease = 1;
        this.defence = 2;
        this.damageDecreaseModifier = 1;
    }

    public String getType() {
        return type;
    }
}
