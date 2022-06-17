package unsw.loopmania;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents the Character object in the frontend and backend world. The
 * character can equip weapons and protective equipment, attack enemies and
 * interact with other objects.
 */
public class Character extends MovingEntity {
    // classes
    private int hp;
    private Attack attack;
    private int defence;

    // classes

    private Weapon weapon;
    private ProtectiveGear armour;
    private ProtectiveGear shield;
    private ProtectiveGear helmet;
    private int gold;
    private IntegerProperty goldProperty;
    private IntegerProperty hpProperty;
    private int exp;
    private boolean buffed;
    private boolean runaway;
    private int stunDuration;
    private boolean bossAttack;

    public Character(PathPosition position) {
        super(position);
        this.gold = 0;
        this.exp = 0;
        this.hp = 100;
        this.attack = new BasicAttack();
        this.buffed = false;
        this.runaway = false;
        this.defence = 0;
        this.goldProperty = new SimpleIntegerProperty(0);
        this.hpProperty = new SimpleIntegerProperty(hp);
        this.stunDuration = 0;
        this.bossAttack = false;
    }

    public int getHp() {
        return this.hp;
    }

    public Items getWeapon() {
        return weapon;
    }

    public Items getArmour() {
        return armour;
    }

    public Items getShield() {
        return shield;
    }

    public Items getHelmet() {
        return helmet;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
        setAttack();
    }

    /**
     * Sets attack to a specific value depending on what gear the character has
     * equipped
     */
    public void setAttack() {
        if (weapon != null) {
            this.attack = weapon.getAttack();
        } else {
            this.attack = new BasicAttack();
        }
        if (helmet != null) {
            Attack helmetAttack = new ProtectiveGearAttack(this.attack, helmet.getAttackDecrease());
            this.attack = helmetAttack;
        }
    }

    /**
     * Buffs Character from building
     * 
     * @param multiplier
     */
    public void setBuffAttack(int multiplier) {
        Attack buffedAttack = new BuffAttack(this.attack, multiplier);
        this.attack = buffedAttack;
    }

    public void setArmour(ProtectiveGear armour) {
        this.armour = armour;
    }

    public void setShield(ProtectiveGear shield) {
        this.shield = shield;
        if (shield == null) {
            return;
        }
        this.defence = defence + shield.getDefence();
    }

    public void setHelmet(ProtectiveGear helmet) {
        this.helmet = helmet;
        if (helmet == null) {
            return;
        }
        this.defence = defence + helmet.getDefence();
        setAttack();
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setGold(int droppedGold) {
        gold = gold + droppedGold;
        goldProperty.set(gold);
    }

    public void setExp(int gainedExp) {
        exp = exp + gainedExp;
    }

    public boolean hasShield() {
        if (this.shield != null) {
            return true;
        }
        return false;
    }

    public void setBuffed(boolean buff) {
        this.buffed = buff;
    }

    public int getGold() {
        return gold;
    }

    public int getExp() {
        return exp;
    }

    public boolean getBuffed() {
        return buffed;
    }

    public int getAttackDamage() {
        return attack.attackExecute();
    }

    public int getDefence() {
        return defence;
    }

    public String getWeaponType() {
        if (weapon != null) {
            return weapon.getType();
        }
        return "NoWeapon";
    }

    /**
     * Calculates the damage dealt to the specific enemy
     * 
     * @param String
     * @return damage
     */
    public int battle(String name) {

        if (getState().equals(new String("STUN"))) {
            updateStunDuration();
            return 0;
        }

        if (name.equals(new String("Boss"))) {
            bossAttack = true;
        } else {
            bossAttack = false;
        }

        if (weapon != null) {
            if (weapon instanceof Stake && name.equals(new String("Vampire"))) {
                Attack criticalVampireStrike = new CriticalWeaponAttack(attack);
                return criticalVampireStrike.attackExecute();
            } else if (weapon instanceof Anduril && bossAttack) {
                Attack bossBuffAttack = new BossBuffAttack(attack);
                return bossBuffAttack.attackExecute();
            }
        }

        if (name.equals(new String("Covid"))) {
            runaway();
        }

        return attack.attackExecute();
    }

    public void unBuffAttack(int multiplier) {
        setAttack();
    }

    /**
     * Calculates the damage reduced through character gear
     * 
     * @param damage
     * @return damageReduced
     */
    public int calculateArmourDefence(int damage) {
        if (this.armour != null) {
            double damageReductionDouble = damage / armour.getDamageDecreaseModifier();
            int damageReduction = (int) Math.ceil(damageReductionDouble);
            return damageReduction;
        }
        return 0;
    }

    private int currentDefence() {
        if (bossAttack && shield != null) {
            return defence * (int) shield.getDamageDecreaseModifier();
        }
        return defence;
    }

    /**
     * Calculates the overall damage taken after defense
     * 
     * @param damage
     */
    public void damage(int damage) {
        int armourDefence = calculateArmourDefence(damage);
        int currentDefence = currentDefence();
        int protectiveGearDefence = currentDefence + armourDefence;
        damage = damage - protectiveGearDefence;
        if (damage < 0) {
            damage = 0;
        }
        hp = hp - damage;
        if (hp < 0) {
            hp = 0;
        }
        updateHpProperty(hp);
    }

    /**
     * Moves Clockwise
     */
    public void move() {
        String state = getState();
        if (state.equals(new String("MOVING")) & !runaway) {
            moveDownPath();
        } else if (state.equals(new String("MOVING")) & runaway) {
            moveUpPath();
        }
        if (state.equals(new String("STUN"))) {
            updateStunDuration();
        }
    }

    public void updateHpProperty(int hp) {
        this.hpProperty.set(hp);
    }

    public IntegerProperty getHpProperty() {
        return hpProperty;
    }

    public IntegerProperty getGoldProperty() {
        return goldProperty;
    }

    public void setStunDuration(int duration) {
        this.stunDuration = duration;
    }

    private void updateStunDuration() {
        if (stunDuration == 0) {
            setAttackState();
        } else {
            stunDuration--;
        }
    }

    private void runaway() {
        runaway = !runaway;
        super.setMovingState();
    }

    public boolean getRunAway() {
        return runaway;
    }

}
