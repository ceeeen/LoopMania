package unsw.loopmania;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/*
* Logical check for goals that deals with cycles
*/
public class CycleGoal extends Goals {

    private BooleanProperty goalStatus;

    public CycleGoal(int quantity) {
        super(quantity);
        goalStatus = new SimpleBooleanProperty();
        goalStatus.set(false);
    }

    /**
     * checks if goal is fulfilled
     * 
     * @param gold
     * @param exp
     * @param cycle
     * @param boss
     */
    @Override
    public void goalCheck(int gold, int exp, int cycle, boolean boss) {
        if (cycle >= quantity) {
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
