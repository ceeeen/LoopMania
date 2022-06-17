package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * The moving entity
 */
public abstract class MovingEntity extends Entity {

    static final int MOVING = 0;
    static final int ATTACK = 1;
    static final int SUPPORT = 2;
    static final int TRANCE = 3;
    static final int STUN = 4;
    /**
     * object holding position in the path
     */
    private PathPosition position;
    private int state;

    /**
     * Create a moving entity which moves up and down the path in position
     * 
     * @param position represents the current position in the path
     */
    public MovingEntity(PathPosition position) {
        super();
        this.position = position;
        this.state = MOVING;
    }

    /**
     * move clockwise through the path
     */
    public void moveDownPath() {
        position.moveDownPath();
    }

    /**
     * move anticlockwise through the path
     */
    public void moveUpPath() {
        position.moveUpPath();
    }

    public SimpleIntegerProperty x() {
        return position.getX();
    }

    public SimpleIntegerProperty y() {
        return position.getY();
    }

    public int getX() {
        return x().get();
    }

    public int getY() {
        return y().get();
    }

    public void setAttackState() {
        this.state = ATTACK;
    }

    public void setMovingState() {
        this.state = MOVING;
    }

    public void setSupportState() {
        this.state = SUPPORT;
    }

    public void setTranceState() {
        this.state = TRANCE;
    }

    public void setStunState() {
        this.state = STUN;
    }

    /**
     * Checks what state the entitiy is in
     */
    public String getState() {

        if (state == ATTACK) {
            return "ATTACK";
        } else if (state == SUPPORT) {
            return "SUPPORT";
        } else if (state == TRANCE) {
            return "TRANCE";
        } else if (state == STUN) {
            return "STUN";
        } else {
            return "MOVING";
        }
    }

    public PathPosition getPathPosition() {
        return position;
    }
}
