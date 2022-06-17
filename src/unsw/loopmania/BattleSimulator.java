package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
/**
 * BattleSimulator runs all the battles between the character, allies
 * and enemies. It deals with all the special attack mechanics (critical hits)
 * and loot that drops from the enemies.
 */
public class BattleSimulator {

    private int healPoint = 5;
    private int duration = 2;
    private double criticalChance = 0.3;
    private double zombieChance = 0.1;
    private int healRadius = 3;

    public BattleSimulator() {

    }
    /**
     * Runs all the battles between allies, characters and enemies
     * 
     * @param enemies
     * @param allies
     * @param character
     */
    public ArrayList<Enemy> runBattle(List<Enemy> enemies, List<AllySoldier> allies, Character character) {
        ArrayList<Enemy> defeatedEnemies = new ArrayList<Enemy>();
        setMonsterMove(enemies, character);
        enemyEffect(enemies, allies, character);
        enemyAttackCharacter(enemies, character);
        enemyAttackAlly(enemies, allies);
        allyAttackEnemy(enemies, allies);
        characterAttackEnemy(enemies, character);
        tranceAttackEnemy(enemies);

        for (Enemy e : enemies) {
            if (e.getHp() <= 0) {
                character.setGold(e.getGold());
                character.setExp(e.getExp());
                defeatedEnemies.add(e);
            }
        }
        return defeatedEnemies;
    }

    public void setCharacter(Character character) {
        character.setMovingState();
    }
    /**
     * Sets the moving state of each entitiy depending on how far they
     * are from each other
     * @param enemies
     * @param character
     */
    public void setMonsterMove(List<Enemy> enemies, Character character) {
        for (Enemy e : enemies) {
            if (DistanceCalculate(character, e) > e.getSupportRange()) {
                e.setMovingState();
            }
        }
    }
    /**
     * Special mechanics of each enemy
     * 
     * @param enemies
     * @param allies
     * @param character
     */
    public void enemyEffect(List<Enemy> enemies, List<AllySoldier> allies, Character character) {
        for (Enemy e : enemies) {
            if (e instanceof ElanMuske) {
                healEnemies(enemies, e);
            } else if (e instanceof Zombie) {
                biteAlly(allies, e);
            } else if (e instanceof Doggie) {
                stun(character, e);
            }
        }
    }
    /**
     * Logic for enemies attacking the character with
     * the special types of attacks.
     * @param enemies
     * @param character
     */
    public void enemyAttackCharacter(List<Enemy> enemies, Character character) {
        for (Enemy e : enemies) {
            if (e.getState().equals(new String("TRANCE"))) {
                continue;
            }
            boolean critical = false;
            if (DistanceCalculate(character, e) <= e.getAttackRange()) {
                if (e instanceof Vampire) {
                    critical = criticalBite(character, e);
                }
                e.setAttackState();
                character.damage(e.attack(critical));
            } else if (DistanceCalculate(character, e) <= e.getSupportRange()) {
                e.setSupportState();
                character.damage(e.attack(critical));
            }
        }
    }
    /**
     * Logic for enemies attacking allies
     * 
     * @param enemies
     * @param allies
     */
    public void enemyAttackAlly(List<Enemy> enemies, List<AllySoldier> allies) {
        for (Enemy e : enemies) {
            for (AllySoldier a : allies) {
                if (DistanceCalculate(a, e) <= e.getAttackRange()) {
                    e.setAttackState();
                    a.damage(e.attack(false));
                } else if (DistanceCalculate(a, e) <= e.getSupportRange()) {
                    e.setSupportState();
                    a.damage(e.attack(false));
                }
            }
        }
    }
    /**
     * Logic for allies attacking enemies
     * 
     * @param enemies
     * @param allies
     */
    public void allyAttackEnemy(List<Enemy> enemies, List<AllySoldier> allies) {
        for (AllySoldier a : allies) {
            for (Enemy e : enemies) {
                if (DistanceCalculate(a, e) <= a.getAttackRange()) {
                    a.setAttackState();
                    e.takeDamage(a.battle());
                } else if (DistanceCalculate(a, e) <= a.getSupportRange()
                        && !(e.getState().equals(new String("MOVING")))) {
                    a.setSupportState();
                    e.takeDamage(a.battle());
                }
            }
        }
    }
    /**
     * Logic for character attacking enemies
     * 
     * @param enemies
     * @param character
     */
    public void characterAttackEnemy(List<Enemy> enemies, Character character) {
        for (Enemy e : enemies) {
            if (DistanceCalculate(character, e) == 1) {
                character.setAttackState();
                double chance = Math.random();
                if (chance <= criticalChance && character.getWeaponType().equals(new String("Staff"))) {
                    e.setTranceState();
                }
                if (e.getHp() > 0) {
                    e.takeDamage(character.battle(e.getName()));
                }
                if (e.getHp() <= 0) {
                    character.setMovingState();
                }
            }
        }
    }
    /**
     * Causes enemies to be set in the trance state
     * 
     * @param enemies
     */
    public void tranceAttackEnemy(List<Enemy> enemies) {
        for (Enemy t : enemies) {
            if (t.getState().equals(new String("TRANCE"))) {
                for (Enemy e : enemies) {
                    boolean critical = false;
                    if (!(e.getState().equals(new String("TRANCE")))
                            && DistanceCalculate(t, e) <= t.getSupportRange()) {
                        if (e.getHp() > 0) {
                            e.takeDamage(t.attack(critical));
                        }
                    }
                }
                t.updateTranceCounter();
            }
        }
    }

    /**
     * Logic for enemies healing by elan
     * 
     * @param enemies
     * @param elan
     */

    public void healEnemies(List<Enemy> enemies, Enemy elan) {
        for (Enemy e : enemies) {
            if (DistanceCalculate(e, elan) <= healRadius) {
                if (!(e instanceof ElanMuske) && e.getHp() > 0) {
                    int newHp = e.getHp() + healPoint;
                    e.setHp(Math.min(newHp, e.getMaxHp()));
                }
            }
        }
    }
    /**
     * Specific attack on allies
     * 
     * @param enemy
     * @param allies
     */
    public void biteAlly(List<AllySoldier> allies, Enemy e) {

        for (AllySoldier a : allies) {
            if (DistanceCalculate(a, e) <= e.getAttackRange()) {
                double chance = Math.random();
                if (chance <= zombieChance) {
                    a.setBite();
                }
            }
        }
    }

    /**
     * Chance to stun character
     * 
     * @param character
     * @param enemy
     */

    public void stun(Character character, Enemy e) {
        double chance = Math.random();
        if (DistanceCalculate(character, e) <= e.getSupportRange()) {
            if (chance <= criticalChance) {
                character.setStunState();
                character.setStunDuration(duration);
            }
        }
    }
    /**
     * Critical Bite against character
     * 
     * @param enemy
     * @param character
     * @return boolean
     */
    public boolean criticalBite(Character character, Enemy e) {
        if (DistanceCalculate(character, e) <= e.getAttackRange()) {
            double chance = Math.random();
            if (chance <= criticalChance) {
                if (character.hasShield()) {
                    double blockChance = Math.random();
                    if (blockChance <= (criticalChance * 2)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    };

    /**
     * Calculates distance between enemy and character
     * 
     * @param character
     * @param Enemy
     * @return distance
     */
    public double DistanceCalculate(Character x, Enemy y) {
        double distanceSquare = Math.pow((x.getX() - y.getX()), 2) + Math.pow((x.getY() - y.getY()), 2);
        return Math.sqrt(distanceSquare);
    }

    /**
     * Calculates distance between 2 enemies
     * 
     * @param Enemy
     * @param Enemy
     * @return distance
     */
    public double DistanceCalculate(Enemy x, Enemy y) {
        double distanceSquare = Math.pow((x.getX() - y.getX()), 2) + Math.pow((x.getY() - y.getY()), 2);
        return Math.sqrt(distanceSquare);
    }
    /**
     * Calculates distance between allysoldier and enemy
     * 
     * @param AllySoldier
     * @param Enemy
     * @return distance
     */
    public double DistanceCalculate(AllySoldier x, Enemy y) {
        double distanceSquare = Math.pow((x.getX() - y.getX()), 2) + Math.pow((x.getY() - y.getY()), 2);
        return Math.sqrt(distanceSquare);
    }

}
