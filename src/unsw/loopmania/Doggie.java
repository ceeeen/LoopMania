package unsw.loopmania;

/*
* Doggie boss that drops a special item
*/
public class Doggie extends Enemy {

    private Attack attack;

    public Doggie(PathPosition position) {
        super(position);
        this.name = "Boss";
        this.movementCounter = 0;
        this.movementLimit = 2;
        this.hp = 50;
        this.maxhp = hp;
        this.gold = 0;
        this.exp = 200;
        this.attack = new DoggieAttack(new BasicAttack());
        this.attackRange = 1;
        this.supportRange = 1;
    }

    /**
     * Moves up the path direction
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
