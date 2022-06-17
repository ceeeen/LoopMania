package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
/*
* Rare item that revives character when he dies
*/
public class TheOneRing extends RareItems {

    private String type = "OneRing";

    public TheOneRing(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public void activate(Character character) {
        character.setHp(100);
    }

    public String getType() {
        return type;
    }
}
