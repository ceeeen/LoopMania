package unsw.loopmania;

import java.util.Random;
/**
 * Concrete behaviour for vampire bite attack
 */
public class VampireBiteAttack extends AttackDecorator {

    static final int MAX_ADDITIONAL_DAMAGE = 5;
    static final int MIN_ADDITIONAL_DAMAGE = 1;

    public VampireBiteAttack(Attack attack) {
        super(attack);
    }

    @Override
    public int attackExecute() {
        Random rand = new Random();
        int randomNum = rand.nextInt((MAX_ADDITIONAL_DAMAGE - MIN_ADDITIONAL_DAMAGE) + 1) + MIN_ADDITIONAL_DAMAGE;
        return randomNum + attack.attackExecute();
    }

}
