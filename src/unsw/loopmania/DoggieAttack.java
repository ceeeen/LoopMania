package unsw.loopmania;

/*
* Attack damage from doggie boss
*/
public class DoggieAttack extends AttackDecorator {

    private int additionalDamage = 13;

    public DoggieAttack(Attack attack) {
        super(attack);
    }

    @Override
    public int attackExecute() {
        return additionalDamage + attack.attackExecute();
    }

}
