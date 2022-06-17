package unsw.loopmania;

/**
 * ProtectiveGear class for use in strategy pattern.
 */
public class ProtectiveGearAttack extends AttackDecorator {

    private int attackDecrease;

    public ProtectiveGearAttack(Attack attack, int attackDecrease) {
        super(attack);
        this.attackDecrease = attackDecrease;
    }

    @Override
    /**
     * Reduces the attack of the character if they have a particular protective gear equipped.
     */
    public int attackExecute() {

        int finalAttack = attack.attackExecute() - attackDecrease;
        if (finalAttack < 0) {
            finalAttack = 0;
        }
        return finalAttack;
    }

}
