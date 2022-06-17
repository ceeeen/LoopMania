package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
/*
* Factory pattern for
*/
public class CardFactory {
    
    public Card getCard(String cardType, SimpleIntegerProperty x, SimpleIntegerProperty y) {
        if (cardType == null) {
            return null;
        }
        if (cardType.equalsIgnoreCase("Campfire")) {
            return new CampfireCard(x, y);
        }
        else if (cardType.equalsIgnoreCase("Barracks")) {
            return new BarracksCard(x, y);
        }
        else if (cardType.equalsIgnoreCase("Tower")) {
            return new TowerCard(x, y);
        }
        else if (cardType.equalsIgnoreCase("Trap")) {
            return new TrapCard(x, y);
        }
        else if (cardType.equalsIgnoreCase("VampireCastle")) {
            return new VampireCastleCard(x, y);
        }
        else if (cardType.equalsIgnoreCase("Village")) {
            return new VillageCard(x, y);
        }
        else if (cardType.equalsIgnoreCase("ZombiePit")) {
            return new ZombiePitCard(x, y);
        }
        return null;
    }
}
