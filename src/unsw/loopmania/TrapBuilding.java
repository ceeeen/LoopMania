package unsw.loopmania;

import java.util.List;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Deals Damage to enemies that walk on top of the trap Is destroyed after
 */
public class TrapBuilding extends Building {
    trapContext trapContext;

    public TrapBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.trapContext = new trapContext();
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
    public void buffCharacter(Character character) {

    }

    @Override
    public void unBuffCharacter(Character character) {

    }

    /**
     * Deals damage to enemy if enemy is in the right position
     * 
     * @return damage
     */
    @Override
    public int trapDamage(Enemy e) {
        if (e.getX() == super.getX() && e.getY() == super.getY()) {
            return trapContext.getAttackValue();
        }
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
