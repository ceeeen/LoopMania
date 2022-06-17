package test;

import org.javatuples.Pair;
import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import unsw.loopmania.GoalParser;
import unsw.loopmania.Goals;

public class GoalTest {

    /**
     * Test complex goal completion
     * 
     */
    @Test
    public void testComplexGoals() {

        String stringjson = "{ 'goal': 'AND', 'subgoals':[ { 'goal': 'cycles', 'quantity': 100 },{ 'goal': 'OR', 'subgoals':[{'goal': 'experience', 'quantity': 123456 },{ 'goal': 'bosses' }]}]}";
        JSONObject json = new JSONObject(stringjson);
        int gold = 0;
        int exp = 123456;
        int cycle = 100;
        boolean boss = false;

        GoalParser gp = new GoalParser();
        Goals setgoal = gp.createGoal(json);
        setgoal.goalCheck(gold, exp, cycle, boss);
        assertTrue(setgoal.getGoalStatus());

    }

    /**
     * Test boss goal completion
     * 
     */
    @Test
    public void testBossGoal() {
        String stringjson = "{ 'goal': 'bosses'}";
        JSONObject json = new JSONObject(stringjson);
        int gold = 0;
        int exp = 123456;
        int cycle = 100;
        boolean boss = true;

        GoalParser gp = new GoalParser();
        Goals setgoal = gp.createGoal(json);
        setgoal.goalCheck(gold, exp, cycle, boss);
        assertTrue(setgoal.getGoalStatus());

    }

    /**
     * Test complex gold completion
     * 
     */
    @Test
    public void testGoldGoal() {
        String stringjson = "{ 'goal': 'gold', 'quantity': 100 }";
        JSONObject json = new JSONObject(stringjson);
        int gold = 100;
        int exp = 123456;
        int cycle = 100;
        boolean boss = true;

        GoalParser gp = new GoalParser();
        Goals setgoal = gp.createGoal(json);
        setgoal.goalCheck(gold, exp, cycle, boss);
        assertTrue(setgoal.getGoalStatus());

    }

    /**
     * Test complex exp completion
     * 
     */
    @Test
    public void testExpGoal() {
        String stringjson = "{ 'goal': 'experience', 'quantity': 100 }";
        JSONObject json = new JSONObject(stringjson);
        int gold = 100;
        int exp = 100;
        int cycle = 100;
        boolean boss = true;

        GoalParser gp = new GoalParser();
        Goals setgoal = gp.createGoal(json);
        setgoal.goalCheck(gold, exp, cycle, boss);
        assertTrue(setgoal.getGoalStatus());

    }

    /**
     * Test complex cycle completion
     * 
     */
    @Test
    public void testCycleGoal() {
        String stringjson = "{ 'goal': 'cycle', 'quantity': 10 }";
        JSONObject json = new JSONObject(stringjson);
        int gold = 100;
        int exp = 100;
        int cycle = 10;
        boolean boss = true;

        GoalParser gp = new GoalParser();
        Goals setgoal = gp.createGoal(json);
        setgoal.goalCheck(gold, exp, cycle, boss);
        assertTrue(setgoal.getGoalStatus());

    }
}
