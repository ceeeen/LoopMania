package unsw.loopmania;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
/*
* Composite pattern for complex goals
*/
public abstract class ComplexGoals extends Goals {

    protected ArrayList<Goals> goalList;

    public ComplexGoals(int quantity, ArrayList<Goals> goalList) {
        super(quantity);
        this.goalList = goalList;
    }

    public abstract void goalCheck(int gold, int exp, int cycle, boolean boss);

    public abstract BooleanProperty getGoal();

    public abstract void setGoal(boolean result);

    public abstract boolean getGoalStatus();
}
