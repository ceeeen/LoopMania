package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
/*
* Special item that drops when doggie boss is defeated
*/
public class DoggieCoin extends Items {

    private String type = "DoggieCoin";
    private int valueInShop = 1;

    public DoggieCoin(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public String getType() {
        return type;
    }

    public int getValueInShop() {
        return valueInShop;
    }

    public void setValueInShop(int valueInShop) {
        this.valueInShop = valueInShop;
    }

    @Override
    public void setSellValue(int value) {
        valueInShop = value;
    }
}
