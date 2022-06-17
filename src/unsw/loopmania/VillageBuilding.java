package unsw.loopmania;

import java.util.List;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
/**
 * Village heals character for a set amount when he passes through
 */
public class VillageBuilding extends Building {
    public VillageBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
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
	/**
     * Finds an adjacent valid pathtile
     * @param Character 
     */
    @Override
    public void villageHeal(Character c) {
        if (c.getX() == super.getX() && c.getY() == super.getY()) {
            int hp = c.getHp() + 10;
            if (hp + 10 > 100) {
                hp = 100;
                c.setHp(hp);
            } else {
                c.setHp(hp);
            }
        }
        
    }
}
