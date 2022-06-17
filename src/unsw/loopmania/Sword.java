package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Creates a Sword object that can be added into the unequipped and equipped inventory
 * for the character in order to attack enemies with greater efficiency.
 */
public class Sword extends Weapon {

    private Attack attack;
    private String type;

    public Sword(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.attack = new SwordWeaponAttack(new BasicAttack());
        this.type = "Sword";
    }

    @Override
    public Attack getAttack() {
        return attack;
    }

    public String getType() {
        return type;
    }

}
