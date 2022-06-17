package unsw.loopmania;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/*
* Logical check for goals that have AND
*/
public class ComplexGoalLogicalAnd extends ComplexGoals {

    private BooleanProperty goalStatus;

    public ComplexGoalLogicalAnd(int quantity, ArrayList<Goals> goalList) {
        super(quantity, goalList);
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

        for (Goals g : goalList) {
            g.goalCheck(gold, exp, cycle, boss);
            if (!g.getGoalStatus()) {
                setGoal(false);
                return;
            }
        }
        setGoal(true);
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
