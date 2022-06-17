package unsw.loopmania;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
/*
* Logical checker for goals to see if exp goal is fulfilled
*/
public class ExpGoal extends Goals {

    private BooleanProperty goalStatus;

    public ExpGoal(int quantity) {
        super(quantity);
        goalStatus = new SimpleBooleanProperty();
        goalStatus.set(false);
    }
    /**
     * checks if goal is fulfilled
     * @param gold
     * @param exp
     * @param cycle
     * @param boss
     */
    @Override
    public void goalCheck(int gold, int exp, int cycle, boolean boss) {

        if (exp >= quantity) {
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
