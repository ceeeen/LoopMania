package unsw.loopmania;

import java.util.List;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
/**
 * Tower attacks all enemies in range
 */
public class TowerBuilding extends Building {
    public final double RADIUS = 3;
    public final int TOWERDAMAGE = 2;

    public TowerBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public Zombie SpawnEnemy(int cycle, List<Pair<Integer, Integer>> orderedPath) {
        return null;
    }

    @Override
    public AllySoldier spawnAlly(List<Pair<Integer, Integer>> orderedPath) {
        return null;
    }

    @Override
    public int trapDamage(Enemy e) {
        return 0;
    }
	/**
     * Checks if enemy is in range, then attack
     * @return damage 
     */
    @Override
    public int towerDamage(Enemy e) {
        if (DistanceCalculate(e) <= RADIUS) {
            return TOWERDAMAGE;
        }
        return 0;
    }
	/**
     * Calculates distance from tower to enemy
     * @return distance value 
     */
    public double DistanceCalculate(Enemy x) {
        double distanceSquare = Math.pow((x.getX() - super.getX()), 2) + Math.pow((x.getY() - super.getY()), 2);
        return Math.sqrt(distanceSquare);
    }

    @Override
    public void villageHeal(Character c) {

    }

    @Override
    public void buffCharacter(Character character) {

    }

    @Override
    public void unBuffCharacter(Character character) {

    }
}
