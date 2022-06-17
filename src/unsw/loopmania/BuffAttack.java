package unsw.loopmania;

/**
 * Concrete class for buffing attack
 */
public class BuffAttack extends AttackDecorator {

    private int multiplier;

    public BuffAttack(Attack attack, int multiplier) {
        super(attack);
        this.multiplier = multiplier;
    }

    @Override
    public int attackExecute() {
        return (multiplier * attack.attackExecute());
    }

}
