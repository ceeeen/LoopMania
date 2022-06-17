package unsw.loopmania;

/**
 * Concrete behaviour for trap
 */
public class trapContext implements trapBehaviour{
    private final int TRAPDAMMAGE = 2;

    trapContext() {}

    @Override
    public int getAttackValue() {
        return TRAPDAMMAGE;
    }
}
