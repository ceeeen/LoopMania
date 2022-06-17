package unsw.loopmania;
/**
 * Damage multiplier for bosses
 */
public class BossBuffAttack extends AttackDecorator {

    private int multiplier = 3;

    public BossBuffAttack(Attack attack) {
        super(attack);
    }

    @Override
    public int attackExecute() {
        return multiplier * attack.attackExecute();
    }

}
