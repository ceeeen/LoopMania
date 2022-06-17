package unsw.loopmania;


/**
 * Creats an allied soldier that attacks enemies alongside the character. They
 * are spawned from the barracks when the character passes it.
 */
public class AllySoldier extends MovingEntity {

    private boolean bitten;
    private int allyCounter;
    private Attack attack;
    private int hp;
    private int attackRange;
    private int supportRange;

    public AllySoldier(PathPosition position) {
        super(position);
        bitten = false;
        this.hp = 10;
        this.attack = new BasicAttack();
        this.attackRange = 2;
        this.supportRange = 4;
    }

    public int getHp() {
        return this.hp;
    }

    public int getSupportRange() {
        return supportRange;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public boolean getBite() {
        return bitten;
    }

    public void damage(int damage) {
        if (hp > 0) {
            int newHp = hp - damage;
            hp = Math.max(newHp, 0);
        }
    }

    public int battle() {
        return attack.attackExecute();
    }

    /**
     * Ally Soldier moves randomly this basic enemy moves in a random direction...
     * 25% chance up or down, 50% chance not at all...
     **/
    public void move() {
        if (allyCounter > 0) {
            allyCounter--;
            return;
        }
        moveDownPath();
    }

    public void setBite() {
        bitten = true;
    }

    public int getPositionInPath() {
        return getPathPosition().getCurrentPositionInPath();
    }

    public void setAllyCounter(int allyCounter) {
        this.allyCounter = allyCounter;
    }
}
