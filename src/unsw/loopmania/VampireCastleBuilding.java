package unsw.loopmania;

import java.util.List;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Building that spawns vampires
 */
public class VampireCastleBuilding extends Building {

    public VampireCastleBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
	/**
     * Spawns vampire depending on cycle on a valid pathtile
     * @param int cycle
     * @param List<Pair<Integer, Integer>> orderedPath
     * @return vampire
     */
    @Override
    public Vampire SpawnEnemy(int cycle, List<Pair<Integer, Integer>> orderedPath) {
        // Can only spawn every 5 cycles
        if (cycle % 5 != 0)
            return null;
        int xPosition = super.getX();
        int yPosition = super.getY();
        int indexPosition = adjacentPathTile(xPosition, yPosition, orderedPath);
        Vampire vampire = new Vampire(new PathPosition(indexPosition, orderedPath));
        return vampire;
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
