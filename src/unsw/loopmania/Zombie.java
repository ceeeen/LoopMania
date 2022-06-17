package unsw.loopmania;

/**
 * Zombie Entity
 */
public class Zombie extends Enemy {

    private Attack attack;

    public Zombie(PathPosition position) {
        super(position);
        this.name = "Zombie";
        this.movementCounter = 0;
        this.movementLimit = 5;
        this.hp = 10;
        this.maxhp = hp;
        this.gold = 20;
        this.exp = 10;
        this.attackRange = 2;
        this.supportRange = 2;
        this.attack = new BasicAttack();
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
        return attack.attackExecute();
    }
}
