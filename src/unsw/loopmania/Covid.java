package unsw.loopmania;

/*
* COVID Boss entity
*/
public class Covid extends Enemy {

    public Covid(PathPosition position) {
        super(position);
        this.name = "Covid";
        this.movementCounter = 0;
        this.movementLimit = 3;
        this.hp = 100;
        this.gold = 0;
        this.exp = 0;
        this.attackRange = 2;
        this.supportRange = 2;
    }

    /**
     * Moves up the path
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
        return 0;
    }

    @Override
    public void setAttackState() {
        super.setMovingState();
    }
}
