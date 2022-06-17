package test;

import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;

import org.javatuples.Pair;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import unsw.loopmania.*;
import unsw.loopmania.Character;

public class BattleTest2 {
    private int width = 3;
    private int height = 3;
    List<Pair<Integer, Integer>> orderedPath = Arrays.asList(new Pair<>(0, 0), new Pair<>(0, 1), new Pair<>(0, 2),
            new Pair<>(1, 2), new Pair<>(2, 2), new Pair<>(2, 1), new Pair<>(2, 0), new Pair<>(1, 0), new Pair<>(0, 0));

    @Test
    public void testZombieBattle() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Character testCharacter = new Character(new PathPosition(0, orderedPath));
        Enemy testZombie = new Zombie(new PathPosition(7, orderedPath));

        List<Enemy> testEnemies = new ArrayList<Enemy>();
        testEnemies.add(testZombie);
        testWorld.setEnemy(testEnemies);
        testWorld.setCharacter(testCharacter);

        List<Enemy> defeatedEnemies = new ArrayList<Enemy>();
        defeatedEnemies = testWorld.runBattles();
        assertEquals(defeatedEnemies.size(), 0);
        assertEquals(testCharacter.getHp(), 98);
        assertEquals(testZombie.getHp(), 8);

    }

    @Test
    public void testSlugBattle() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Character testCharacter = new Character(new PathPosition(0, orderedPath));
        Enemy testSlug = new Slug(new PathPosition(7, orderedPath));

        List<Enemy> testEnemies = new ArrayList<Enemy>();
        testEnemies.add(testSlug);
        testWorld.setEnemy(testEnemies);
        testWorld.setCharacter(testCharacter);

        List<Enemy> defeatedEnemies = new ArrayList<Enemy>();
        defeatedEnemies = testWorld.runBattles();
        assertEquals(defeatedEnemies.size(), 0);
        assertEquals(testCharacter.getHp(), 99);
        assertEquals(testSlug.getHp(), 3);

    }

    @Test
    public void testVampireSupport() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Character testCharacter = new Character(new PathPosition(0, orderedPath));
        Enemy testVampire = new Vampire(new PathPosition(5, orderedPath));
        Enemy testZombie = new Zombie(new PathPosition(7, orderedPath));

        List<Enemy> testEnemies = new ArrayList<Enemy>();
        testCharacter.setAttackState();
        testEnemies.add(testZombie);
        testEnemies.add(testVampire);
        testWorld.setEnemy(testEnemies);
        testWorld.setCharacter(testCharacter);

        List<Enemy> defeatedEnemies = new ArrayList<Enemy>();
        defeatedEnemies = testWorld.runBattles();
        assertEquals(defeatedEnemies.size(), 0);
        assertEquals(testCharacter.getHp(), 92);
        assertEquals(testVampire.getHp(), 15);
    }

    /**
     * Test Zombie attack range, as zombies can attack at a range of 2 tiles
     * (Distance = 2) Zombies have the same attack and support range
     */
    @Test
    public void testZombieAttackRange() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Character testCharacter = new Character(new PathPosition(0, orderedPath));
        Enemy testZombie = new Zombie(new PathPosition(6, orderedPath));

        List<Enemy> testEnemies = new ArrayList<Enemy>();
        testEnemies.add(testZombie);
        testWorld.setEnemy(testEnemies);
        testWorld.setCharacter(testCharacter);

        List<Enemy> defeatedEnemies = new ArrayList<Enemy>();
        defeatedEnemies = testWorld.runBattles();
        assertEquals(defeatedEnemies.size(), 0);
        assertEquals(testCharacter.getHp(), 98);
        assertEquals(testZombie.getHp(), 10);
    }

    /**
     * Tests if shield blocks damage from zombie and vampire Tests if shield and
     * armour blocks damage from zombie's and vampire
     * 
     */
    @Test
    public void testShield() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Character testCharacter = new Character(new PathPosition(0, orderedPath));
        Enemy testVampire = new Vampire(new PathPosition(5, orderedPath));
        Enemy testZombie = new Zombie(new PathPosition(7, orderedPath));

        SimpleIntegerProperty x = new SimpleIntegerProperty();
        x.set(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty();
        y.set(0);
        ProtectiveGear testShield = new Shield(x, y);
        testCharacter.setShield(testShield);

        List<Enemy> testEnemies = new ArrayList<Enemy>();
        testEnemies.add(testZombie);
        testEnemies.add(testVampire);
        testCharacter.setAttackState();
        testWorld.setEnemy(testEnemies);
        testWorld.setCharacter(testCharacter);

        List<Enemy> defeatedEnemies = new ArrayList<Enemy>();
        defeatedEnemies = testWorld.runBattles();
        assertEquals(testCharacter.getHp(), 94);
        assertEquals(defeatedEnemies.size(), 0);
        ProtectiveGear testArmour = new Armour(x, y);
        testCharacter.setArmour(testArmour);

        defeatedEnemies = testWorld.runBattles();
        assertEquals(testCharacter.getHp(), 92);

    }

    /**
     * Test for helmet functionality Test to see if helmet defends against vampire
     * and zombie attack Test to see if helmet reduces player attack
     */
    @Test
    public void testHelmet() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Character testCharacter = new Character(new PathPosition(0, orderedPath));
        Enemy testVampire = new Vampire(new PathPosition(5, orderedPath));
        Enemy testZombie = new Zombie(new PathPosition(7, orderedPath));

        SimpleIntegerProperty x = new SimpleIntegerProperty();
        x.set(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty();
        y.set(0);
        ProtectiveGear testHelment = new Helmet(x, y);
        testCharacter.setHelmet(testHelment);
        testCharacter.setAttackState();

        List<Enemy> testEnemies = new ArrayList<Enemy>();
        testEnemies.add(testZombie);
        testEnemies.add(testVampire);
        testWorld.setEnemy(testEnemies);
        testWorld.setCharacter(testCharacter);

        List<Enemy> defeatedEnemies = new ArrayList<Enemy>();
        defeatedEnemies = testWorld.runBattles();
        assertEquals(defeatedEnemies.size(), 0);
        assertEquals(testCharacter.getHp(), 96);
        assertEquals(testZombie.getHp(), 9);
    }

    /**
     * Test for armour functionality to see if armour halfs the incoming damage from
     * Zombies and Vampires
     */
    @Test
    public void testArmour() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Character testCharacter = new Character(new PathPosition(0, orderedPath));
        Enemy testVampire = new Vampire(new PathPosition(5, orderedPath));
        Enemy testZombie = new Zombie(new PathPosition(7, orderedPath));

        SimpleIntegerProperty x = new SimpleIntegerProperty();
        x.set(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty();
        y.set(0);
        ProtectiveGear testArmour = new Armour(x, y);
        testCharacter.setArmour(testArmour);
        testCharacter.setAttackState();
        List<Enemy> testEnemies = new ArrayList<Enemy>();
        testEnemies.add(testZombie);
        testEnemies.add(testVampire);
        testWorld.setEnemy(testEnemies);
        testWorld.setCharacter(testCharacter);

        List<Enemy> defeatedEnemies = new ArrayList<Enemy>();
        defeatedEnemies = testWorld.runBattles();
        assertEquals(defeatedEnemies.size(), 0);
        assertEquals(testCharacter.getHp(), 96);
        assertEquals(testZombie.getHp(), 8);
    }

    /**
     * Test tranced enemies Test initial attack where Zombie1 is not tranced Test
     * followup attack where Zombie1 is tranced
     */
    @Test
    public void testTrancedEnemies() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Character testCharacter = new Character(new PathPosition(0, orderedPath));
        Enemy testZombie1 = new Zombie(new PathPosition(7, orderedPath));
        Enemy testZombie2 = new Zombie(new PathPosition(6, orderedPath));

        List<Enemy> testEnemies = new ArrayList<Enemy>();
        testEnemies.add(testZombie1);
        testEnemies.add(testZombie2);
        testWorld.setEnemy(testEnemies);
        testWorld.setCharacter(testCharacter);

        List<Enemy> defeatedEnemies = new ArrayList<Enemy>();
        defeatedEnemies = testWorld.runBattles();
        assertEquals(defeatedEnemies.size(), 0);
        assertEquals(testCharacter.getHp(), 96);
        assertEquals(testZombie1.getHp(), 8);
        assertEquals(testZombie2.getHp(), 10);

        testZombie1.setTranceState();

        defeatedEnemies = testWorld.runBattles();
        assertEquals(testCharacter.getHp(), 94);
        assertEquals(testZombie1.getHp(), 6);
        assertEquals(testZombie2.getHp(), 8);
    }

    /**
     * Test to check sword attack is working
     */
    @Test
    public void testSword() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Character testCharacter = new Character(new PathPosition(0, orderedPath));
        Enemy testVampire = new Vampire(new PathPosition(7, orderedPath));

        List<Enemy> testEnemies = new ArrayList<Enemy>();
        testEnemies.add(testVampire);
        testWorld.setEnemy(testEnemies);
        testWorld.setCharacter(testCharacter);

        SimpleIntegerProperty x = new SimpleIntegerProperty();
        x.set(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty();
        y.set(0);
        Weapon Sword = new Sword(x, y);
        testCharacter.setWeapon(Sword);

        List<Enemy> defeatedEnemies = new ArrayList<Enemy>();
        assertEquals(defeatedEnemies.size(), 0);
        defeatedEnemies = testWorld.runBattles();
        assertEquals(testVampire.getHp(), 10);

    }

    /**
     * Testing stake attack on vampire
     */
    @Test
    public void testStakeVampire() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Character testCharacter = new Character(new PathPosition(0, orderedPath));
        Enemy testVampire = new Vampire(new PathPosition(7, orderedPath));

        List<Enemy> testEnemies = new ArrayList<Enemy>();
        testEnemies.add(testVampire);
        testWorld.setEnemy(testEnemies);
        testWorld.setCharacter(testCharacter);

        SimpleIntegerProperty x = new SimpleIntegerProperty();
        x.set(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty();
        y.set(0);
        Weapon Stake = new Stake(x, y);
        testCharacter.setWeapon(Stake);

        List<Enemy> defeatedEnemies = new ArrayList<Enemy>();
        assertEquals(defeatedEnemies.size(), 0);
        defeatedEnemies = testWorld.runBattles();
        assertEquals(testVampire.getHp(), 7);
    }

    /**
     * Testing stake attack on zombie
     */
    @Test
    public void testStakeZombie() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Character testCharacter = new Character(new PathPosition(0, orderedPath));
        Enemy testZombie = new Zombie(new PathPosition(7, orderedPath));

        List<Enemy> testEnemies = new ArrayList<Enemy>();
        testEnemies.add(testZombie);
        testWorld.setEnemy(testEnemies);
        testWorld.setCharacter(testCharacter);

        SimpleIntegerProperty x = new SimpleIntegerProperty();
        x.set(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty();
        y.set(0);
        Weapon Stake = new Stake(x, y);
        testCharacter.setWeapon(Stake);

        List<Enemy> defeatedEnemies = new ArrayList<Enemy>();
        assertEquals(defeatedEnemies.size(), 0);
        defeatedEnemies = testWorld.runBattles();
        assertEquals(testZombie.getHp(), 7);
    }

    /**
     * Testing Elan muske attack damage to character
     */
    @Test
    public void testElanMuskeAttack() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Character testCharacter = new Character(new PathPosition(0, orderedPath));
        Enemy testElan = new ElanMuske(new PathPosition(7, orderedPath));

        List<Enemy> testEnemies = new ArrayList<Enemy>();
        testEnemies.add(testElan);
        testWorld.setEnemy(testEnemies);
        testWorld.setCharacter(testCharacter);

        List<Enemy> defeatedEnemies = new ArrayList<Enemy>();
        assertEquals(defeatedEnemies.size(), 0);
        defeatedEnemies = testWorld.runBattles();
        assertEquals(testCharacter.getHp(), 80);
    }

    /**
     * Testing Elan healing nearby Enemies Zombie Hp should decrease from 10 to 8,
     * before healing back to 10 the next battle and reducing to 8 again as the
     * character attacks
     */
    @Test
    public void testElanHealing() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        Character testCharacter = new Character(new PathPosition(0, orderedPath));
        Enemy testElan = new ElanMuske(new PathPosition(6, orderedPath));
        Enemy testZombie = new Zombie(new PathPosition(7, orderedPath));

        List<Enemy> testEnemies = new ArrayList<Enemy>();
        testEnemies.add(testElan);
        testEnemies.add(testZombie);
        testWorld.setEnemy(testEnemies);
        testWorld.setCharacter(testCharacter);

        List<Enemy> defeatedEnemies = new ArrayList<Enemy>();
        defeatedEnemies = testWorld.runBattles();
        assertEquals(defeatedEnemies.size(), 0);
        assertEquals(testZombie.getHp(), 8);
        defeatedEnemies = testWorld.runBattles();
        assertEquals(testCharacter.getHp(), 96);
        assertEquals(testZombie.getHp(), 8);
    }

    /**
     * Testing Doggie boss battle
     */
    @Test
    public void testDoggieBattle() {
        Character testCharacter = new Character(new PathPosition(0, orderedPath));
        Enemy testDoggie = new Doggie(new PathPosition(7, orderedPath));

        List<Enemy> testEnemies = new ArrayList<Enemy>();
        testEnemies.add(testDoggie);
        BattleSimulator simulator = new BattleSimulator();
        simulator.enemyAttackCharacter(testEnemies, testCharacter);
        assertEquals(testCharacter.getHp(), 85);
    }

    /**
     * Test functionality of the one Ring
     */
    @Test
    public void testTheOneRing() {
        LoopManiaWorld testWorld = new LoopManiaWorld(width, height, orderedPath);
        List<String> rareItems = new ArrayList<>();
        rareItems.add("the_one_ring");
        testWorld.setRareItems(rareItems);
        testWorld.addOneRing();
        Character testCharacter = new Character(new PathPosition(0, orderedPath));
        Enemy testElan = new ElanMuske(new PathPosition(7, orderedPath));
        List<Enemy> testEnemies = new ArrayList<Enemy>();

        testEnemies.add(testElan);
        testWorld.setEnemy(testEnemies);
        testWorld.setCharacter(testCharacter);

        BattleSimulator simulator = new BattleSimulator();
        simulator.enemyAttackCharacter(testEnemies, testCharacter);
        simulator.enemyAttackCharacter(testEnemies, testCharacter);
        simulator.enemyAttackCharacter(testEnemies, testCharacter);
        simulator.enemyAttackCharacter(testEnemies, testCharacter);
        simulator.enemyAttackCharacter(testEnemies, testCharacter);
        assertEquals(testCharacter.getHp(), 0);
        testWorld.useRareItem("the_one_ring");
        assertEquals(testCharacter.getHp(), 100);

    }

    /**
     * Test bonus damage of andruil again a boss monster
     */
    @Test
    public void testAndruil() {
        Character testCharacter = new Character(new PathPosition(0, orderedPath));
        Enemy testElan = new ElanMuske(new PathPosition(7, orderedPath));
        List<Enemy> testEnemies = new ArrayList<Enemy>();
        testEnemies.add(testElan);

        SimpleIntegerProperty x = new SimpleIntegerProperty();
        x.set(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty();
        y.set(0);
        Weapon andruil = new Anduril(x, y);
        testCharacter.setWeapon(andruil);

        BattleSimulator simulator = new BattleSimulator();
        simulator.enemyAttackCharacter(testEnemies, testCharacter);
        simulator.characterAttackEnemy(testEnemies, testCharacter);
        assertEquals(testElan.getHp(), 70);
    }

    /**
     * Test defence of tree stump against boss monster
     */
    @Test
    public void testTreeStup() {
        Character testCharacter = new Character(new PathPosition(0, orderedPath));
        Enemy testElan = new ElanMuske(new PathPosition(7, orderedPath));
        List<Enemy> testEnemies = new ArrayList<Enemy>();
        testEnemies.add(testElan);

        SimpleIntegerProperty x = new SimpleIntegerProperty();
        x.set(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty();
        y.set(0);
        ProtectiveGear treeStump = new TreeStump(x, y);
        testCharacter.setShield(treeStump);

        BattleSimulator simulator = new BattleSimulator();
        simulator.characterAttackEnemy(testEnemies, testCharacter);
        simulator.enemyAttackCharacter(testEnemies, testCharacter);
        assertEquals(testCharacter.getHp(), 90);
    }

    /**
     * Test Ally in combat Test Ally attack Test ally getting attack
     */
    @Test
    public void allyCombatTest() {
        Enemy testElan = new ElanMuske(new PathPosition(7, orderedPath));
        AllySoldier testAlly = new AllySoldier(new PathPosition(0, orderedPath));

        List<Enemy> testEnemies = new ArrayList<Enemy>();
        List<AllySoldier> testAllies = new ArrayList<AllySoldier>();
        testEnemies.add(testElan);
        testAllies.add(testAlly);

        BattleSimulator simulator = new BattleSimulator();
        simulator.allyAttackEnemy(testEnemies, testAllies);
        assertEquals(testElan.getHp(), 98);
        simulator.enemyAttackAlly(testEnemies, testAllies);
        assertEquals(testAlly.getHp(), 0);
    }
}
