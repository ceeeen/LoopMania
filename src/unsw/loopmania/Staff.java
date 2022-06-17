package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents a Staff objec in the frontend and backend world that can be added into the unequipped and equipped inventory
 * for the character in order to attack enemies with greater efficiency. Staffs have the
 * ability to trance an enemy, turning them into a allied soldier but have a less attack
 * than the other weapon types.
 */
public class Staff extends Weapon {

    private Attack attack;
    private String type;

    public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.attack = new BasicAttack();
        this.type = "Staff";

    }

    @Override
    public Attack getAttack() {
        return attack;
    }

    public String getType() {
        return type;
    }

}
