package unsw.loopmania;
/**
 * Concrete class for sword's behaviour
 */
public class SwordWeaponAttack extends AttackDecorator {

    private int additionalDamage;

    public SwordWeaponAttack(Attack attack) {
        super(attack);
        this.additionalDamage = 3;
    }

    @Override
    /**
     * Adds additional damage on top of base attack damage.
     */
    public int attackExecute() {
        return additionalDamage + attack.attackExecute();
    }

}
