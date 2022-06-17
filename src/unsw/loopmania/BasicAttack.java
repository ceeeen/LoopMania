package unsw.loopmania;

/**
 * Basic attack damage without any modifiers (no weapons, no buffs.. etc)
 */
public class BasicAttack extends Attack {
    private int damage = 2;

    @Override
    public int attackExecute() {
        return damage;
    }
}
