package unsw.loopmania;

import java.util.Random;

/**
 * a abstract class for enemies in the world
 */
public abstract class Enemy extends MovingEntity {


    protected String name;
    protected int movementCounter;
    protected int movementLimit;
    protected int hp;
    protected int maxhp;
    protected int gold;
    protected int exp;
    protected int supportRange;
    protected int attackRange;
    protected int tranceCounter;

    public Enemy(PathPosition position) {
        super(position);
        this.tranceCounter = 0;
    }

    /**
     * move the enemy
     */
    public void move() {
        // this basic enemy moves in a random direction... 25% chance up or down, 50%
        // chance not at all...
        int directionChoice = (new Random()).nextInt(2);
        if (directionChoice == 0) {
            moveUpPath();
        } else if (directionChoice == 1) {
            moveDownPath();
        }
    }

    public int getHp() {
        return hp;
    }

    public String getName() {
        return this.name;
    }

    public void takeDamage(int damage) {
        if (hp > 0) {
            int newhp = hp - damage;
            hp = Math.max(newhp, 0);
        }
    }

    public int getGold() {
        return gold;
    }

    public int getExp() {
        return exp;
    }

    public int getMaxHp() {
        return maxhp;
    }

    public int getSupportRange() {
        return supportRange;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setTranceCounter() {
        tranceCounter = 3;
    }

    public void updateTranceCounter() {
        if (tranceCounter == 0) {
            setSupportState();
        } else {
            tranceCounter = tranceCounter - 1;
        }
    }

    public abstract int attack(boolean critical);

}
