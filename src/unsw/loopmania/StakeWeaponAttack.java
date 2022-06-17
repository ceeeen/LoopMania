package unsw.loopmania;
/**
 * Concrete class for stake weapon's hehaviour
 */
public class StakeWeaponAttack extends AttackDecorator {

    private int additionalDamage;

    public StakeWeaponAttack(Attack attack) {
        super(attack);
        this.additionalDamage = 1;
    }

    /**
     * Adds additional damage on top of the base damage.
     */
    @Override
    public int attackExecute() {
        return additionalDamage + attack.attackExecute();
    }

}
