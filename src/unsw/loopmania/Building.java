package unsw.loopmania;

import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import org.javatuples.Pair;

// Template Pattern for building interactions
public abstract class Building extends StaticEntity {
    public Building(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public abstract Enemy SpawnEnemy(int cycle, List<Pair<Integer, Integer>> orderedPath);

    public abstract AllySoldier spawnAlly(List<Pair<Integer, Integer>> orderedPath);

    public abstract void buffCharacter(Character character);

    public abstract void unBuffCharacter(Character character);

    public abstract int trapDamage(Enemy e);

    public abstract int towerDamage(Enemy e);

    public abstract void villageHeal(Character c);
}
