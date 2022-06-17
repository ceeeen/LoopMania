package unsw.loopmania;
/**
 * Concrete class for slug's attacking
 */
public class SlugAttack extends Attack {

    private int damage;

    public SlugAttack() {
        this.damage = 1;
    }

    @Override
    public int attackExecute() {
        return damage;
    }

}
