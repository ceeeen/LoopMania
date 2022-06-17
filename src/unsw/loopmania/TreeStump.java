package unsw.loopmania;
import javafx.beans.property.SimpleIntegerProperty;
/*
* Rare item tree stump that reduces damage even further
*/
public class TreeStump extends ProtectiveGear {

    public String type = "TreeStump";

    public TreeStump(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.defence = 5;
        this.damageDecreaseModifier = 2;
    }

    public String getType() {
        return type;
    }
}
