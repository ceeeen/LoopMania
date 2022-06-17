package unsw.loopmania;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
/**
 * Class checker for goals to see if the amount of boss kills is achieved
 */
public class BossGoal extends Goals {

    private BooleanProperty goalStatus;

    public BossGoal(int quantity) {
        super(quantity);
        goalStatus = new SimpleBooleanProperty();
        goalStatus.set(false);
    }

    @Override
    public void goalCheck(int gold, int exp, int cycle, boolean boss) {
        setGoal(boss);
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
