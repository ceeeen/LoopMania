package unsw.loopmania;

import java.util.Random;

/**
 * Vampire Entity
 */
public class Vampire extends Enemy {

    static final int MAXBITE = 5;
    static final int MINBITE = 1;

    private Attack attack;
    private boolean criticalBite;
    private int criticalBiteStrikes;

    public Vampire(PathPosition position) {
        super(position);
        this.name = "Vampire";
        this.movementCounter = 0;
        this.movementLimit = 2;
        this.hp = 15;
        this.maxhp = hp;
        this.gold = 50;
        this.exp = 20;
        this.attack = new VampireAttack(new BasicAttack());
        this.criticalBite = false;
        this.criticalBiteStrikes = 0;
        this.attackRange = 2;
        this.supportRange = 3;
    }

    /**
     * Moves in a random direction
     */
    public void move() {
        String state = getState();
        if (state.equals(new String("MOVING"))) {
            if (movementCounter == movementLimit) {
                moveUpPath();
                movementCounter = 0;
            } else {
                movementCounter++;
            }
        }
    }

    public int getCounter() {
        return movementCounter;
    }

    /**
     * @param boolean
     * @return final attack damage
     */
    @Override
    public int attack(boolean critical) {
        if (critical) {
            this.criticalBite = true;
            Random rand = new Random();
            int randomNum = rand.nextInt((MAXBITE - MINBITE) + 1) + MINBITE;
            this.criticalBiteStrikes = randomNum;
        }

        if (criticalBite && criticalBiteStrikes > 0) {
            criticalBiteStrikes = criticalBiteStrikes - 1;
            Attack vampireBireAttack = new VampireBiteAttack(attack);
            return vampireBireAttack.attackExecute();
        } else {
            criticalBite = false;
            criticalBiteStrikes = 0;
            return attack.attackExecute();
        }
    }

}
