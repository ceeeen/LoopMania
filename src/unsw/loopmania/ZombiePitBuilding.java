package unsw.loopmania;

import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import org.javatuples.Pair;
/**
 * Spawns Zombie into the world every cycle
 */
public class ZombiePitBuilding extends Building {
    // private final String name = "ZombiePitBuilding";
    public ZombiePitBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    // @Override
    // public String getName() {
    // return name;
    // }
	/**
     * Spawns zombie at closest pathTile
     * @param int 
     * @param List<Pair<Integer, Integer>> orderedPath
     * @return int 
     */
    @Override
    public Zombie SpawnEnemy(int cycle, List<Pair<Integer, Integer>> orderedPath) {
        int xPosition = super.getX();
        int yPosition = super.getY();
        int indexPosition = adjacentPathTile(xPosition, yPosition, orderedPath);
        Zombie zombie = new Zombie(new PathPosition(indexPosition, orderedPath));
        return zombie;
    }
	/**
     * Finds an adjacent valid pathtile
     * @param int x
     * @param int y
     * @param List<Pair<Integer, Integer>> orderedPath
     * @return int 
     */
    public int adjacentPathTile(int x, int y, List<Pair<Integer, Integer>> orderedPath) {
        if (checkPathTile(x, y + 1, orderedPath) != -1)
            return checkPathTile(x, y + 1, orderedPath);
        if (checkPathTile(x + 1, y + 1, orderedPath) != -1)
            return checkPathTile(x + 1, y + 1, orderedPath);
        if (checkPathTile(x, y - 1, orderedPath) != -1)
            return checkPathTile(x, y - 1, orderedPath);
        if (checkPathTile(x - 1, y, orderedPath) != -1)
            return checkPathTile(x - 1, y, orderedPath);
        if (checkPathTile(x - 1, y - 1, orderedPath) != -1)
            return checkPathTile(x - 1, y - 1, orderedPath);
        if (checkPathTile(x + 1, y + 1, orderedPath) != -1)
            return checkPathTile(x + 1, y + 1, orderedPath);
        if (checkPathTile(x + 1, y - 1, orderedPath) != -1)
            return checkPathTile(x + 1, y - 1, orderedPath);
        if (checkPathTile(x + 1, y + 1, orderedPath) != -1)
            return checkPathTile(x + 1, y + 1, orderedPath);
        if (checkPathTile(x - 1, y + 1, orderedPath) != -1)
            return checkPathTile(x - 1, y + 1, orderedPath);
        return 0;
    }
	/**
     * Checks if the given co-ordinate is a pathTile
     * @param int x 
     * @param int y
     * @param List<Pair<Integer, Integer>> orderedPath
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
    public AllySoldier spawnAlly(List<Pair<Integer, Integer>> orderedPath) {
        return null;
    }

    @Override
    public void buffCharacter(Character character) {

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

    @Override
    public void unBuffCharacter(Character character) {

    }
}
