package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
/**
 * The rare weapon Anduril deals extra damage against enemies and triple enemies against bosses
 */
public class Anduril extends Weapon {

    private String type = "Anduril";
    private Attack attack;

    public Anduril(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.attack = new AndurilAttack(new BasicAttack());
    }

    public String getType() {
        return type;
    }

    @Override
    public Attack getAttack() {
        return attack;
    }
}
