package unsw.loopmania;
/**
 * Concrete behaviour for vampire attack
 */
public class VampireAttack extends AttackDecorator {

    private int additionalDamage = 4;

    public VampireAttack(Attack attack) {
        super(attack);
    }

    @Override
    public int attackExecute() {
        return additionalDamage + attack.attackExecute();
    }

}
