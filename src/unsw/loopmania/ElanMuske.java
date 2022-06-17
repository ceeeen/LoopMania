package unsw.loopmania;
/*
* Entitiy of the boss ElanMuske 
*/
public class ElanMuske extends Enemy {
    static final int MAXBITE = 5;
    static final int MINBITE = 1;

    private Attack attack;

    public ElanMuske(PathPosition position) {
        super(position);
        this.name = "Boss";
        this.movementCounter = 0;
        this.movementLimit = 2;
        this.hp = 100;
        this.gold = 500;
        this.exp = 100;
        this.attack = new ElanAttack(new BasicAttack());
        this.attackRange = 1;
        this.supportRange = 1;
        this.maxhp = hp;
    }

    /**
     * Moves in a random direction
     */
    public void move() {
        String state = getState();
        if (state.equals(new String("MOVING"))) {
            for (int i = 0; i <= movementLimit; i++) {
                moveUpPath();
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
