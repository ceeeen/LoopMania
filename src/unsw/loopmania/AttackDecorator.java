package unsw.loopmania;
/**
 * Decorator for attack
 */
public abstract class AttackDecorator extends Attack {

    protected Attack attack;

    public AttackDecorator(Attack attack) {
        this.attack = attack;
    }

    public abstract int attackExecute();

}
