package unsw.loopmania;
/*
* Attack damage by the boss Elan
*/
public class ElanAttack extends AttackDecorator {

    private int additionalDamage = 18;

    public ElanAttack(Attack attack) {
        super(attack);
    }

    @Override
    public int attackExecute() {
        return additionalDamage + attack.attackExecute();
    }

}
