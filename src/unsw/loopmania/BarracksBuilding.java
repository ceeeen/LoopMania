package unsw.loopmania;

import java.util.List;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Creates an allied soldier that attacks enemies alongside the character. They
 * are spawned from the barracks when the character passes it.
 */
public class BarracksBuilding extends Building {
    public BarracksBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public Zombie SpawnEnemy(int cycle, List<Pair<Integer, Integer>> orderedPath) {
        return null;
    }
	/**
     * Spawns an ally on the building
     * @param List<Pair<Integer, Integer>> orderedPath
     * @return AllySoldier 
     * else false
     */
    @Override
    public AllySoldier spawnAlly(List<Pair<Integer, Integer>> orderedPath) {
        int xPosition = super.getX();
        int yPosition = super.getY();
        int indexPosition = checkPathTile(xPosition, yPosition, orderedPath);
        // Spawn on the building
        AllySoldier ally = new AllySoldier(new PathPosition(indexPosition, orderedPath));
        return ally;

    }
	/**
     * Checks a specific co-ordinate to check if it is a pathtile
     * @param List<Pair<Integer, Integer>> orderedPath
     * @param int x
     * @param int y
     * @return int 
     */
    public int checkPathTile(int x, int y, List<Pair<Integer, Integer>> orderedPath) {
        for (int i = 0; i < orderedPath.size(); i++) {
            Pair<Integer, Integer> pair = orderedPath.get(i);
            if (pair.getValue0() == x && pair.getValue1() == y) {
                return i;
            }
        }
        return -1;
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
