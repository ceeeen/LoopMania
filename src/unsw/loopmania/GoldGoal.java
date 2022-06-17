package unsw.loopmania;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
/*
* Logical Check for gold goal to see if it is fulfilled
*/
public class GoldGoal extends Goals {

    private BooleanProperty goalStatus;

    public GoldGoal(int quantity) {
        super(quantity);
        goalStatus = new SimpleBooleanProperty();
        goalStatus.set(false);
    }
    /**
     * check if goal is fulfilled
     * @param andArray
     * @return goal
     */
    @Override
    public void goalCheck(int gold, int exp, int cycle, boolean boss) {

        if (gold >= quantity) {
            setGoal(true);
            return;
        }
        setGoal(false);
    }

    @Override
    public BooleanProperty getGoal() {
        return this.goalStatus;
    }

    @Override
    public void setGoal(boolean result) {
        goalStatus.set(result);
    }

    @Override
    public boolean getGoalStatus() {
        return goalStatus.get();
    }

}
