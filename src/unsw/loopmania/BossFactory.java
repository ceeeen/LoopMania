package unsw.loopmania;

/**
 * Factory pattern for spawning bosses (elanmusk, doggie, covid)
 */
public class BossFactory {

    private int doggieCycle = 3;
    private int elanCycle = 5;
    private int covidCycle = 2;
    private int elanExp = 100;

    public BossFactory() {

    }

    /**
     * Checks if a boss is spawned into the world
     * 
     * @param cycle
     * @param experience
     * @return boolean
     */
    public boolean checkSpawn(int cycle, int experience) {
        if (checkDoggieSpawn(cycle) || checkElanSpawn(cycle, experience) || checkCovidSpawn(cycle)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if doggie is spawned into the world
     * 
     * @param cycle
     * @return boolean
     */
    public boolean checkDoggieSpawn(int cycle) {
        if (cycle % doggieCycle == 0) {
            return true;
        }
        return false;
    }

    /**
     * Checks if elan is spawned into the world
     * 
     * @param cycle
     * @param experience
     * @return boolean
     */
    public boolean checkElanSpawn(int cycle, int experience) {
        if ((cycle % elanCycle == 0) && experience >= elanExp) {
            return true;
        }
        return false;
    }

    /**
     * Checks if covid is spawned into the world
     * 
     * @param cycle
     * @return boolean
     */
    public boolean checkCovidSpawn(int cycle) {
        if (cycle % covidCycle == 0) {
            return true;
        }
        return false;
    }

    /**
     * spawns a boss depending on the conditions of the world
     * 
     * @param cycle
     * @param experience
     * @param position
     * 
     * @return enemy
     */
    public Enemy spawnBoss(int cycle, int experience, PathPosition position) {

        if (checkElanSpawn(cycle, experience)) {
            ElanMuske elanMuske = new ElanMuske(position);
            return elanMuske;
        } else if (checkDoggieSpawn(cycle)) {
            Doggie doggie = new Doggie(position);
            return doggie;
        }
        Covid covid = new Covid(position);
        return covid;
    }
}
