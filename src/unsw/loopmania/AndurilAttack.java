package unsw.loopmania;

/**
 * Anduril sword will deal extra damage to enemies and triple to bosses
 */
public class AndurilAttack extends AttackDecorator {

    private int additionalDamage = 8;

    public AndurilAttack(Attack attack) {
        super(attack);
    }

    @Override
    public int attackExecute() {
        return additionalDamage + attack.attackExecute();
    }

}
