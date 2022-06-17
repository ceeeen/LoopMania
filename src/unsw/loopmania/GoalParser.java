package unsw.loopmania;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
/*
* Parses goals to loopworldmania depending on the json file
*/
public class GoalParser {

    public GoalParser() {

    }
    /**
     * creates a goal that has logic AND
     * @param andArray
     * @return goal
     */
    public Goals AndGoal(JSONArray andArray) {

        ArrayList<Goals> goalList = new ArrayList<Goals>();

        for (int i = 0; i < andArray.length(); i++) {
            JSONObject goalCondition = andArray.getJSONObject(i);
            Goals goal = createGoal(goalCondition);
            goalList.add(goal);
        }

        Goals andGoal = new ComplexGoalLogicalAnd(0, goalList);
        return andGoal;
    }
    /**
     * creates a goal that has logic OR
     * @param andArray
     * @return goal
     */
    public Goals OrGoal(JSONArray orArray) {
        ArrayList<Goals> goalList = new ArrayList<Goals>();

        for (int i = 0; i < orArray.length(); i++) {
            JSONObject goalCondition = orArray.getJSONObject(i);
            Goals goal = createGoal(goalCondition);
            goalList.add(goal);
        }

        Goals andGoal = new ComplexGoalLogicalOr(0, goalList);
        return andGoal;
    }
    /**
     * creates a goal
     * @param andArray
     * @return goal
     */
    public Goals createGoal(JSONObject goalCondition) {

        Goals goal = new ExpGoal(0);
        String goalName = (String) goalCondition.get("goal");

        if (goalName.equals(new String("experience"))) {
            goal = new ExpGoal((int) goalCondition.get("quantity"));
        } else if (goalName.equals(new String("gold"))) {
            goal = new GoldGoal((int) goalCondition.get("quantity"));
        } else if (goalName.equals(new String("cycles"))) {
            // System.out.println("after and comes cycles");
            goal = new CycleGoal((int) goalCondition.get("quantity"));
        } else if (goalName.equals(new String("bosses"))) {
            goal = new BossGoal(0);
        } else if (goalName.equals(new String("OR"))) {
            JSONArray arr = goalCondition.getJSONArray("subgoals");
            goal = OrGoal(arr);
        } else if (goalName.equals(new String("AND"))) {
            JSONArray arr = goalCondition.getJSONArray("subgoals");
            goal = AndGoal(arr);
        }
        return goal;
    }
}
