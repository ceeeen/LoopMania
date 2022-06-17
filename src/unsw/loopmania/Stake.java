package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents a Stake object in the frontend and backend world that can be added into the unequipped and equipped inventory
 * for the character in order to attack enemies with greater efficiency. Stakes deal greater
 * damage to Vampires.
 */
public class Stake extends Weapon {

    private Attack attack;
    private String type;

    public Stake(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.attack = new StakeWeaponAttack(new BasicAttack());
        this.type = "Stake";

    }

    @Override
    public Attack getAttack() {
        return attack;
    }

    public String getType() {
        return type;
    }

}
