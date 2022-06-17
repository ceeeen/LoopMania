package unsw.loopmania;

import java.util.Random;

/**
 * Slug Entity
 */
public class Slug extends Enemy {

    private Attack attack;

    public Slug(PathPosition position) {
        super(position);
        this.name = "Slug";
        this.movementCounter = 0;
        this.movementLimit = 3;
        this.hp = 5;
        this.maxhp = hp;
        this.gold = 5;
        this.exp = 5;
        this.attack = new SlugAttack();
        this.attackRange = 1;
        this.supportRange = 1;
    }

    /**
     * Moves in a random direction
     */
    public void move() {
        String state = getState();
        if (state.equals(new String("MOVING"))) {
            if (movementCounter == movementLimit) {
                int directionChoice = (new Random()).nextInt(2);
                if (directionChoice == 0) {
                    moveUpPath();
                } else if (directionChoice == 1) {
                    moveDownPath();
                }
                movementCounter = 0;
            } else {
                movementCounter++;
            }
        }
    }

    /**
     * @param boolean
     * @return final attack damage
     */
    @Override
    public int attack(boolean critical) {
        return attack.attackExecute();
    }

}
