package unsw.loopmania;

import java.util.List;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
/**
* Hero Castle Building that spawns at 0,0
 */
public class HeroCastleBuilding extends Building {
    public HeroCastleBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
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
    public void buffCharacter(Character character) {

    }

    @Override
    public void unBuffCharacter(Character character) {

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
