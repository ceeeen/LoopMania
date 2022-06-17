package unsw.loopmania;

import java.util.List;

import javax.swing.plaf.multi.MultiInternalFrameUI;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;

public class CampfireBuilding extends Building {
    public static final int MULTIPLIER = 2;
    public static final double RADIUS = 3;

    public CampfireBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
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
	/**
     * Buffs character attack by 2 and cannot stack
     * @param Character 
     * @return void
     * else false
     */
    @Override
    public void buffCharacter(Character character) {
        if (DistanceCalculate(character) <= RADIUS) {
            character.setBuffAttack(MULTIPLIER);
            character.setBuffed(true);
        }

    }
	/**
     * Calculates distance from campfire to character
     * @return distance value 
     */
    public double DistanceCalculate(Character x) {
        double distanceSquare = Math.pow((x.getX() - super.getX()), 2) + Math.pow((x.getY() - super.getY()), 2);
        return Math.sqrt(distanceSquare);
    }
	/**
     * Unbuff character
     */
    @Override
    public void unBuffCharacter(Character character) {
        character.unBuffAttack(MULTIPLIER);
        character.setBuffed(false);

    }

    @Override
    public int trapDamage(Enemy e) {
        return 0;
    }

    @Override
    public int towerDamage(Enemy e) {
        return 0;
    }

    @Override
    public void villageHeal(Character c) {
    }
}
