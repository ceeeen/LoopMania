package unsw.loopmania;
/**
 * Concrete class for critical hits
 */
public class CriticalWeaponAttack extends AttackDecorator {

    private int additionalDamage;

    public CriticalWeaponAttack(Attack attack) {
        super(attack);
        this.additionalDamage = 5;
    }

    @Override
    public int attackExecute() {
        return additionalDamage + attack.attackExecute();
    }

}
