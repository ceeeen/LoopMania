package unsw.loopmania;

import javafx.beans.property.BooleanProperty;
/*
* Abstract class for goals
*/
public abstract class Goals {

    protected int quantity;

    public Goals(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public abstract void goalCheck(int gold, int exp, int cycle, boolean boss);

    public abstract BooleanProperty getGoal();

    public abstract void setGoal(boolean result);

    public abstract boolean getGoalStatus();

}
