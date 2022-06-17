package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents an Armour object in the frontend and backend world that can be added to the character;s
 * equipped or unequipped inventory. Armour halves incoming attack damage to the character.
 */
public class Armour extends ProtectiveGear {

    private String type = "Armour";

    public Armour(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.damageDecreaseModifier = 2;
        this.attackDecrease = 0;
    }

    public String getType() {
        return type;
    }

}
