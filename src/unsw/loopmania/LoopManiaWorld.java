package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * A backend world.
 *
 * A world can contain many entities, each occupy a square. More than one entity
 * can occupy the same square.
 */
public class LoopManiaWorld {

    public static final int unequippedInventoryWidth = 4;
    public static final int unequippedInventoryHeight = 4;
    private static final int ELAN_EFFECT = 10;

    /**
     * width of the world in GridPane cells
     */
    private int width;

    /**
     * height of the world in GridPane cells
     */
    private int height;

    private int difficulty;

    /**
     * generic entitites - i.e. those which don't have dedicated fields
     */
    private List<Entity> nonSpecifiedEntities;

    private List<String> availableRareItems;

    private Character character;

    private int doggieCoinValue;

    private Goals goal;

    private Shop shop;

    // etc...
    private List<AllySoldier> allies;
    private List<Enemy> enemies;

    private List<Card> cardEntities;

    private List<Entity> unequippedInventoryItems;

    // Equipped items.

    // List of Gold currently in world.
    private List<GoldPile> goldPileList;

    // List of Health potions currently in world.
    private List<HealthPotion> healthPotionList;

    private List<Building> buildingEntities;

    /**
     * list of x,y coordinate pairs in the order by which moving entities traverse
     * them
     */
    private List<Pair<Integer, Integer>> orderedPath;

    private IntegerProperty cycle = new SimpleIntegerProperty(1);
    private BossFactory bossFactory = new BossFactory();
    private BattleSimulator simulator = new BattleSimulator();
    private boolean vistedHeroCastle;
    private boolean allBossesBeaten;
    private boolean beatenDoggie;
    private boolean beatenElan;

    /**
     * create the world (constructor)
     * 
     * @param width       width of world in number of cells
     * @param height      height of world in number of cells
     * @param orderedPath ordered list of x, y coordinate pairs representing
     *                    position of path cells in world
     */
    public LoopManiaWorld(int width, int height, List<Pair<Integer, Integer>> orderedPath) {
        this.width = width;
        this.height = height;
        nonSpecifiedEntities = new ArrayList<>();
        availableRareItems = new ArrayList<>();
        character = null;
        allies = new ArrayList<>();
        enemies = new ArrayList<>();
        goldPileList = new ArrayList<>();
        healthPotionList = new ArrayList<>();
        cardEntities = new ArrayList<>();
        unequippedInventoryItems = new ArrayList<>();
        this.orderedPath = orderedPath;
        buildingEntities = new ArrayList<>();
        cycle.set(1);
        vistedHeroCastle = false;
        doggieCoinValue = 1;
        allBossesBeaten = false;
        beatenDoggie = false;
        beatenElan = false;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * set the character. This is necessary because it is loaded as a special entity
     * out of the file
     * 
     * @param character the character
     */
    public void setCharacter(Character character) {
        this.character = character;
    }

    public void setEnemy(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public List<String> getRareItems() {
        return this.availableRareItems;
    }

    public void setRareItems(List<String> rareItems) {
        this.availableRareItems = rareItems;
    }

    /**
     * returns the character instance for testing purposes.
     * 
     * @return
     */
    public Character getCharacter() {
        return this.character;
    }

    /**
     * Adds a gold pile to goldPileList. Used for testing purposes.
     * 
     * @param pile
     */
    public void addGoldPile(GoldPile pile) {
        this.goldPileList.add(pile);
    }

    /**
     * Adds a health potion to healthPotionList. Used for testing purposes.
     * 
     * @param potion
     */
    public void addHealthPotion(HealthPotion potion) {
        this.healthPotionList.add(potion);
    }

    /**
     * 
     * @return
     */
    public List<Entity> getUnequippedInventory() {
        return this.unequippedInventoryItems;
    }

    public void setUnequippedInventory(List<Entity> unequippedInventoryItems) {
        this.unequippedInventoryItems = unequippedInventoryItems;
    }

    /**
     * Return the character HP as a string to be displayed on the frontend.
     * 
     * @return the character's HP value as a string
     */
    public String getCharacterHpAsString() {
        return Integer.toString(character.getHp());
    }

    /**
     * Return the character's wealth to be displayed on the frontend.
     * 
     * @return the character's wealth in gold as a string.
     */
    public String getCharacterGoldAsString() {
        return Integer.toString(character.getGold());
    }

    /**
     * Return the character's XP to be displayed on the frontend.
     * 
     * @return the character's XP value as a string.
     */
    public String getCharacterXpAsString() {
        return Integer.toString(character.getExp());
    }

    /**
     * add a generic entity (without it's own dedicated method for adding to the
     * world)
     * 
     * @param entity
     */
    public void addEntity(Entity entity) {
        // for adding non-specific entities (ones without another dedicated list)
        nonSpecifiedEntities.add(entity);
    }

    /**
     * spawns enemies if the conditions warrant it, adds to world
     * 
     * @return list of the enemies to be displayed on screen
     */
    public List<Enemy> possiblySpawnEnemies() {
        Pair<Integer, Integer> pos = possiblyGetBasicEnemySpawnPosition();
        List<Enemy> spawningEnemies = new ArrayList<>();
        if (pos != null) {
            int indexInPath = orderedPath.indexOf(pos);
            Enemy enemy = new Slug(new PathPosition(indexInPath, orderedPath));
            enemies.add(enemy);
            spawningEnemies.add(enemy);

        }
        if (character.getX() == 0 && character.getY() == 0) { // CHECK IF PLAYER COMPLETES CYCLE
            List<Enemy> buildingSpawnedEnemies = buildingSpawnEnemies();
            enemies.addAll(buildingSpawnedEnemies);
            spawningEnemies.addAll(buildingSpawnedEnemies);
        }
        return spawningEnemies;
    }

    /**
     * checks if conditions are met to spawn enemies through buildings
     * 
     * @return list of the enemies to be spawned
     */
    public List<Enemy> buildingSpawnEnemies() {
        List<Enemy> enemies = new ArrayList<>();
        for (Building building : buildingEntities) {
            Enemy enemy = building.SpawnEnemy(cycle.get(), orderedPath);
            if (enemy != null)
                enemies.add(enemy);
        }
        return enemies;
    }

    /**
     * spawns allies if the conditions warrant it, adds to world
     * 
     * @return list of the allies to be displayed on screen
     */
    public List<AllySoldier> possiblySpawnAllySoldiers() {
        List<AllySoldier> ally = new ArrayList<>();
        for (Building building : buildingEntities) {
            if (character.getX() == building.getX() && character.getY() == building.getY()) {
                AllySoldier soldier = building.spawnAlly(orderedPath);
                if (soldier != null)
                    ally.add(soldier);
            }
        }
        allies.addAll(ally);
        return ally;
    }

    /**
     * Creates a random amount between 1 to 10 gold piles to be spawned. Adds to a
     * list of goldPiles that need to bespawned.
     * 
     * @return Returns a list of gold piles objects that will be spawned.
     */
    public List<StaticEntity> possiblySpawnGoldPile() {
        List<StaticEntity> spawningGoldPile = new ArrayList<>();

        // Adds up to 10 gold piles to spawningGoldPile list.
        Random random = new Random();
        int numGoldPilesToBeSpawned = random.nextInt(10);

        // At least 1 gold should be spawned.
        if (numGoldPilesToBeSpawned == 0) {
            numGoldPilesToBeSpawned++;
        }
        int listSize = goldPileList.size();
        while (listSize < numGoldPilesToBeSpawned) {
            Random r = new Random();
            int randomInList = r.nextInt(orderedPath.size());
            Pair<Integer, Integer> pos = orderedPath.get(randomInList);
            if (pos != null) {
                GoldPile newGold = new GoldPile(new SimpleIntegerProperty(pos.getValue0()),
                        new SimpleIntegerProperty(pos.getValue1()));
                goldPileList.add(newGold);
                spawningGoldPile.add(newGold);
                listSize++;
            }
        }

        return spawningGoldPile;
    }

    /**
     * Create a random amount between 1 to 3 health potions to be spawned. Adds to a
     * list of health potions to be spawned.
     * 
     * @return a list of health potion objects to be spawned.
     */
    public List<StaticEntity> possiblySpawnHealthPotion() {
        List<StaticEntity> spawningHealthPotion = new ArrayList<>();

        if (cycle.get() % 2 == 0) {
            return null;
        }

        // Adds up to 2 health potions
        Random random = new Random();
        int healthPotionsToBeSpawned = random.nextInt(3);

        int listSize = healthPotionList.size();
        while (listSize < healthPotionsToBeSpawned) {
            Random r = new Random();
            int randomInList = r.nextInt(orderedPath.size());
            Pair<Integer, Integer> pos = orderedPath.get(randomInList);
            if (pos != null) {
                HealthPotion newHealthPotion = new HealthPotion(new SimpleIntegerProperty(pos.getValue0()),
                        new SimpleIntegerProperty(pos.getValue1()));
                healthPotionList.add(newHealthPotion);
                spawningHealthPotion.add(newHealthPotion);
                listSize++;
            }
        }

        return spawningHealthPotion;
    }

    /**
     * kill an enemy
     * 
     * @param enemy enemy to be killed
     */
    private void killEnemy(Enemy enemy) {
        enemy.destroy();
        enemies.remove(enemy);
    }

    /**
     * Destroy building
     * 
     * @param enemy enemy to be killed
     */
    private void destroyBuilding(Building building) {
        building.destroy();
        buildingEntities.remove(building);
    }

    /**
     * run the expected battles in the world, based on current world state
     * 
     * @return list of enemies which have been killed
     */
    public List<Enemy> runBattles() {
        ArrayList<Enemy> defeatedEnemies = new ArrayList<Enemy>();
        buildingBuff();
        for (Enemy e : enemies) {
            buildingTrap(e);
            buildingAttack(e);
        }
        defeatedEnemies.addAll(simulator.runBattle(enemies, allies, character));
        buildingUnbuff();
        for (Enemy e : defeatedEnemies) {
            killEnemy(e);
        }
        return defeatedEnemies;
    }

    /**
     * Calculates distance between the character and an enemy
     * 
     * @param Character
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
     * Pick up any items that the character comes into contact with. Returns a list
     * of items that can be spawned.
     */
    public void pickUpItems() {
        List<GoldPile> pickedUpGoldPiles = new ArrayList<GoldPile>();
        List<HealthPotion> pickedUpPotions = new ArrayList<HealthPotion>();

        for (GoldPile goldPile : goldPileList) {
            if (character.getX() == goldPile.getX() && character.getY() == goldPile.getY()) {
                pickedUpGoldPiles.add(goldPile);
            }
        }

        for (GoldPile goldPile : pickedUpGoldPiles) {
            pickUpGoldPile(goldPile);
        }

        for (HealthPotion healthPotion : healthPotionList) {
            if (character.getX() == healthPotion.getX() && character.getY() == healthPotion.getY()) {
                pickedUpPotions.add(healthPotion);
            }
        }

        for (HealthPotion potion : pickedUpPotions) {
            pickUpHealthPotion(potion);
        }

    }

    /**
     * Picks up goldPile that character comes into contact with, adds to character
     * gold count, goldPile is destroyed and removed.
     * 
     * @param goldPile
     */
    private void pickUpGoldPile(GoldPile goldPile) {
        character.setGold(goldPile.getGoldPileValue());
        goldPile.destroy();
        goldPileList.remove(goldPile);
    }

    /**
     * Picks up health potion that character comes into contact with, refills
     * character health.
     * 
     * @param healthPotion
     */
    private void pickUpHealthPotion(HealthPotion healthPotion) {

        consumeHealthPotion(healthPotion);
        healthPotion.destroy();
        healthPotionList.remove(healthPotion);
    }

    public void consumeHealthPotion(HealthPotion healthPotion) {
        character.setHp(healthPotion.getHpRefilled());
    }

    /**
     * spawn a card in the world and return the card entity
     * 
     * @return a card to be spawned in the controller as a JavaFX node
     */
    public VampireCastleCard loadVampireCard() {
        // if adding more cards than have, remove the first card...
        if (cardEntities.size() >= getWidth()) {
            removeCardGiveGold(0);
        }
        CardFactory cardFactory = new CardFactory();
        Card card = cardFactory.getCard("VampireCastle", new SimpleIntegerProperty(cardEntities.size()),
                new SimpleIntegerProperty(0));
        cardEntities.add(card);
        return (VampireCastleCard) card;
    }

    /**
     * spawn a card in the world and return the card entity
     * 
     * @return a card to be spawned in the controller as a JavaFX node
     */
    public ZombiePitCard loadZombieCard() {
        // if adding more cards than have, remove the first card...
        if (cardEntities.size() >= getWidth()) {
            removeCardGiveGold(0);
        }
        CardFactory cardFactory = new CardFactory();
        Card card = cardFactory.getCard("ZombiePit", new SimpleIntegerProperty(cardEntities.size()),
                new SimpleIntegerProperty(0));
        cardEntities.add(card);
        return (ZombiePitCard) card;
    }

    /**
     * spawn a card in the world and return the card entity
     * 
     * @return a card to be spawned in the controller as a JavaFX node
     */
    public BarracksCard loadBarracksCard() {
        // if adding more cards than have, remove the first card...
        if (cardEntities.size() >= getWidth()) {
            removeCardGiveGold(0);
        }
        CardFactory cardFactory = new CardFactory();
        Card card = cardFactory.getCard("Barracks", new SimpleIntegerProperty(cardEntities.size()),
                new SimpleIntegerProperty(0));
        cardEntities.add(card);
        return (BarracksCard) card;
    }

    /**
     * spawn a card in the world and return the card entity
     * 
     * @return a card to be spawned in the controller as a JavaFX node
     */
    public CampfireCard loadCampfireCard() {
        // if adding more cards than have, remove the first card...
        if (cardEntities.size() >= getWidth()) {
            removeCardGiveGold(0);
        }
        CardFactory cardFactory = new CardFactory();
        Card card = cardFactory.getCard("Campfire", new SimpleIntegerProperty(cardEntities.size()),
                new SimpleIntegerProperty(0));
        cardEntities.add(card);
        return (CampfireCard) card;
    }

    /**
     * spawn a card in the world and return the card entity
     * 
     * @return a card to be spawned in the controller as a JavaFX node
     */
    public TowerCard loadTowerCard() {
        // if adding more cards than have, remove the first card...
        if (cardEntities.size() >= getWidth()) {
            removeCardGiveGold(0);
        }
        CardFactory cardFactory = new CardFactory();
        Card card = cardFactory.getCard("Tower", new SimpleIntegerProperty(cardEntities.size()),
                new SimpleIntegerProperty(0));
        cardEntities.add(card);
        return (TowerCard) card;
    }

    /**
     * spawn a card in the world and return the card entity
     * 
     * @return a card to be spawned in the controller as a JavaFX node
     */
    public TrapCard loadTrapCard() {
        // if adding more cards than have, remove the first card...
        if (cardEntities.size() >= getWidth()) {
            removeCardGiveGold(0);
        }
        CardFactory cardFactory = new CardFactory();
        Card card = cardFactory.getCard("Trap", new SimpleIntegerProperty(cardEntities.size()),
                new SimpleIntegerProperty(0));
        cardEntities.add(card);
        return (TrapCard) card;
    }

    /**
     * spawn a card in the world and return the card entity
     * 
     * @return a card to be spawned in the controller as a JavaFX node
     */
    public VillageCard loadVillageCard() {
        // if adding more cards than have, remove the first card...
        if (cardEntities.size() >= getWidth()) {
            removeCardGiveGold(0);
        }
        CardFactory cardFactory = new CardFactory();
        Card card = cardFactory.getCard("Village", new SimpleIntegerProperty(cardEntities.size()),
                new SimpleIntegerProperty(0));
        cardEntities.add(card);
        return (VillageCard) card;
    }

    /**
     * remove card at a particular index of cards (position in gridpane of unplayed
     * cards)
     * 
     * @param index the index of the card, from 0 to length-1
     */
    private void removeCard(int index) {
        Card c = cardEntities.get(index);
        int x = c.getX();
        c.destroy();
        cardEntities.remove(index);
        shiftCardsDownFromXCoordinate(x);
    }

    private void removeCardGiveGold(int index) {
        Card c = cardEntities.get(index);
        int x = c.getX();
        character.setGold(5);
        c.destroy();
        cardEntities.remove(index);
        shiftCardsDownFromXCoordinate(x);
    }

    /**
     * Spawns a sword in the world and return the sword entity.
     * 
     * @return a sword to be spawned in the controller as a JavaFX node
     */
    public Sword addUnequippedSword() {

        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null) {
            // eject the oldest unequipped item and replace it... oldest item is that at
            // beginning of items
            removeItemByPositionInUnequippedInventoryItemsGiveGold(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }

        // now we insert the new sword, as we know we have at least made a slot
        // available...
        Sword sword = new Sword(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
                new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(sword);
        return sword;
    }

    public void addItemToUnequippedInventory(Items s) {
        unequippedInventoryItems.add(s);
    }

    /**
     * Spawns a staff in the world and return the staff entity.
     *
     * @return a staff to be spawned in the controller as a JavaFx node.
     */
    public Staff addUnequippedStaff() {
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null) {
            // eject the oldest unequipped item and replace it... oldest item is that at
            // beginning of items
            removeItemByPositionInUnequippedInventoryItemsGiveGold(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }

        Staff staff = new Staff(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
                new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(staff);
        return staff;
    }

    /**
     * Spawns a stake in the world and return the stake entity.
     * 
     * @return a stake to be spawned in the controller as a JavaFx node.
     */
    public Stake addUnequippedStake() {
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null) {
            // eject the oldest unequipped item and replace it... oldest item is that at
            // beginning of items
            removeItemByPositionInUnequippedInventoryItemsGiveGold(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }

        Stake stake = new Stake(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
                new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(stake);
        return stake;
    }

    /**
     * Spawns an armour in the world and return the armour entity.
     * 
     * @return an armour to be spawned in the controller as a JavaFx node.
     */
    public Armour addUnequippedArmour() {
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null) {
            // eject the oldest unequipped item and replace it... oldest item is that at
            // beginning of items
            removeItemByPositionInUnequippedInventoryItemsGiveGold(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }

        Armour armour = new Armour(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
                new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(armour);
        return armour;
    }

    /**
     * Spawns a shield in the world and return the shield entity.
     * 
     * @return a shield to be spawned in the controller as a JavaFx node.
     */
    public Shield addUnequippedShield() {
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null) {
            // eject the oldest unequipped item and replace it... oldest item is that at
            // beginning of items
            removeItemByPositionInUnequippedInventoryItemsGiveGold(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }

        Shield shield = new Shield(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
                new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(shield);
        return shield;
    }

    /**
     * Spawns a helmet in the world and return the helmet entity.
     * 
     * @return a helmet to be spawned in the controller as a JavaFx node.
     */
    public Helmet addUnequippedHelmet() {
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null) {
            // eject the oldest unequipped item and replace it... oldest item is that at
            // beginning of items
            removeItemByPositionInUnequippedInventoryItemsGiveGold(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }

        Helmet helmet = new Helmet(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
                new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(helmet);
        return helmet;
    }

    /**
     * Spawns the_one_ring in the world if it is enable by an input game file.
     * Returns the ring entity.
     * 
     * @return the One Ring to be spawned in the controller as a JavaFx node.
     */
    public TheOneRing addOneRing() {
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (!availableRareItems.contains("the_one_ring")) {
            return null;
        }
        if (firstAvailableSlot == null) {
            // eject the oldest unequipped item and replace it... oldest item is that at
            // beginning of items
            removeItemByPositionInUnequippedInventoryItemsGiveGold(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }

        TheOneRing ring = new TheOneRing(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
                new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(ring);
        return ring;
    }

    /**
     * Spawns anduril in the world if it is enable by an input game file. Returns
     * the anduril entity.
     * 
     * @return anduril to be spawned in the controller as a JavaFx node.
     */
    public Anduril addAnduril() {
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (!availableRareItems.contains("anduril_flame_of_the_west")) {
            return null;
        }
        if (firstAvailableSlot == null) {
            // eject the oldest unequipped item and replace it... oldest item is that at
            // beginning of items
            removeItemByPositionInUnequippedInventoryItemsGiveGold(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }

        Anduril anduril = new Anduril(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
                new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(anduril);
        return anduril;
    }

    /**
     * Spawns tree stump in the world if it is enable by an input game file. Returns
     * the anduril entity.
     * 
     * @return tree stump to be spawned in the controller as a JavaFx node.
     */
    public TreeStump addTreeStump() {
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (!availableRareItems.contains("tree_stump")) {
            return null;
        }
        if (firstAvailableSlot == null) {
            // eject the oldest unequipped item and replace it... oldest item is that at
            // beginning of items
            removeItemByPositionInUnequippedInventoryItemsGiveGold(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }

        TreeStump treeStump = new TreeStump(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
                new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(treeStump);
        return treeStump;
    }

    /**
     * Spawns DoggieCoin in the world if it is enable by an input game file. Returns
     * the DoggieCoin entity.
     * 
     * @return DoggieCoin to be spawned in the controller as a JavaFx node.
     */
    public DoggieCoin addDoggieCoin() {
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null) {
            // eject the oldest unequipped item and replace it... oldest item is that at
            // beginning of items
            removeItemByPositionInUnequippedInventoryItemsGiveGold(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }

        DoggieCoin doggieCoin = new DoggieCoin(new SimpleIntegerProperty(firstAvailableSlot.getValue0()),
                new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(doggieCoin);
        return doggieCoin;
    }

    /**
     * Use a rare item when provided name of the rare item.
     * 
     * @param name
     */
    public void useRareItem(String name) {
        switch (name) {
            case "the_one_ring":
                for (int i = 0; i < unequippedInventoryItems.size(); i++) {
                    if (unequippedInventoryItems.get(i) instanceof TheOneRing) {
                        useOneRing(character);
                        removeItemByPositionInUnequippedInventoryItems(i);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * Activates the one ring to revive
     * 
     * @param character
     */
    private void useOneRing(Character character) {
        TheOneRing tempRing = new TheOneRing(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        tempRing.activate(character);
    }

    public boolean checkOneRing() {
        for (Entity e : unequippedInventoryItems) {
            if (e instanceof TheOneRing) {
                return true;
            }
        }
        return false;
    }

    /**
     * Inputs gear and sets it in the appropriate gear slot in Character.
     * 
     * @param gear
     */
    public void addToEquippedProtectiveItems(Items item) {
        ProtectiveGear gear = (ProtectiveGear) item;
        if (gear instanceof Armour) {
            character.setArmour(gear);
        } else if (gear instanceof Shield) {
            character.setShield(gear);
        } else if (gear instanceof Helmet) {
            character.setHelmet(gear);
        } else if (gear instanceof TreeStump) {
            character.setShield(gear);
        }
    }

    /**
     * Inputs a weapon and sets it in the weapons slot in Character.
     * 
     * @param weapon
     */
    public void addToEquippedWeapon(Items item) {
        Weapon weapon = (Weapon) item;
        character.setWeapon(weapon);
    }

    /**
     * Removes the equipped item at the given input coordinates.
     * 
     * @param x
     * @param y
     */
    public void removeEquippedItemByCoordinates(int x, int y) {
        Entity item = getEquippedItemByCoordinates(x, y);
        removeEquippedItem(item);

    }

    private void removeEquippedItem(Entity item) {
        if (item instanceof Weapon) {
            character.setWeapon(null);
        } else if (item instanceof Armour) {
            character.setArmour(null);
        } else if (item instanceof Shield) {
            character.setShield(null);
        } else if (item instanceof Helmet) {
            character.setHelmet(null);
        }
        item.destroy();
    }

    private Entity getEquippedItemByCoordinates(int x, int y) {
        Entity e = character.getWeapon();
        if (e != null && (e.getX() == x) && (e.getY() == y)) {
            return e;
        }
        e = character.getArmour();
        if (e != null && (e.getX() == x) && (e.getY() == y)) {
            return e;
        }
        e = character.getShield();
        if (e != null && (e.getX() == x) && (e.getY() == y)) {
            return e;
        }
        e = character.getHelmet();
        if (e != null && (e.getX() == x) && (e.getY() == y)) {
            return e;
        }
        return null;
    }

    /**
     * remove an item by x,y coordinates
     * 
     * @param x x coordinate from 0 to width-1
     * @param y y coordinate from 0 to height-1
     */
    public void removeUnequippedInventoryItemByCoordinates(int x, int y) {
        Entity item = getUnequippedInventoryItemEntityByCoordinates(x, y);
        removeUnequippedInventoryItem(item);
    }

    /**
     * run moves which occur with every tick without needing to spawn anything
     * immediately
     */
    public void runTickMoves() {

        character.move();
        moveBasicEnemies();
        if (character.getX() == 0 && character.getY() == 0) {
            vistedHeroCastle = true;
        }
        if (character.getX() != 0 && character.getY() != 0 && vistedHeroCastle) {
            int i = cycle.get();
            i++;
            cycle.set(i);
            vistedHeroCastle = false;
        }
    }

    public void updateGoal() {
        goal.goalCheck(character.getGold(), character.getExp(), cycle.get(), allBossesBeaten);
    }

    /**
     * remove an item from the unequipped inventory
     * 
     * @param item item to be removed
     */
    private void removeUnequippedInventoryItem(Entity item) {
        item.destroy();
        unequippedInventoryItems.remove(item);
    }

    /**
     * return an unequipped inventory item by x and y coordinates assumes that no 2
     * unequipped inventory items share x and y coordinates
     * 
     * @param x x index from 0 to width-1
     * @param y y index from 0 to height-1
     * @return unequipped inventory item at the input position
     */
    private Entity getUnequippedInventoryItemEntityByCoordinates(int x, int y) {
        for (Entity e : unequippedInventoryItems) {
            if ((e.getX() == x) && (e.getY() == y)) {
                return e;
            }
        }
        return null;
    }

    /**
     * remove item at a particular index in the unequipped inventory items list
     * (this is ordered based on age in the starter code)
     * 
     * @param index index from 0 to length-1
     */
    private void removeItemByPositionInUnequippedInventoryItems(int index) {
        Entity item = unequippedInventoryItems.get(index);
        item.destroy();
        unequippedInventoryItems.remove(index);
    }

    private void removeItemByPositionInUnequippedInventoryItemsGiveGold(int index) {
        Entity item = unequippedInventoryItems.get(index);
        character.setGold(5);
        item.destroy();
        unequippedInventoryItems.remove(index);
    }

    /**
     * get the first pair of x,y coordinates which don't have any items in it in the
     * unequipped inventory
     * 
     * @return x,y coordinate pair
     */
    private Pair<Integer, Integer> getFirstAvailableSlotForItem() {
        // first available slot for an item...
        // IMPORTANT - have to check by y then x, since trying to find first available
        // slot defined by looking row by row
        for (int y = 0; y < unequippedInventoryHeight; y++) {
            for (int x = 0; x < unequippedInventoryWidth; x++) {
                if (getUnequippedInventoryItemEntityByCoordinates(x, y) == null) {
                    return new Pair<Integer, Integer>(x, y);
                }
            }
        }
        return null;
    }

    /**
     * shift card coordinates down starting from x coordinate
     * 
     * @param x x coordinate which can range from 0 to width-1
     */
    private void shiftCardsDownFromXCoordinate(int x) {
        for (Card c : cardEntities) {
            if (c.getX() >= x) {
                c.x().set(c.getX() - 1);
            }
        }
    }

    /**
     * move all enemies
     */
    private void moveBasicEnemies() {
        for (Enemy e : enemies) {
            e.move();
        }
        for (AllySoldier a : allies) {
            a.move();
        }
    }

    /**
     * get a randomly generated position which could be used to spawn an enemy
     * 
     * @return null if random choice is that wont be spawning an enemy or it isn't
     *         possible, or random coordinate pair if should go ahead
     */
    private Pair<Integer, Integer> possiblyGetBasicEnemySpawnPosition() {

        // has a chance spawning a basic enemy on a tile the character isn't on or
        // immediately before or after (currently space required = 2)...
        Random rand = new Random();
        int choice = rand.nextInt(2);
        if ((choice == 0) && (enemies.size() < 2)) {
            Pair<Integer, Integer> spawnPosition = getSpawnLocation(rand);
            return spawnPosition;
        }
        return null;
    }

    private Pair<Integer, Integer> getSpawnLocation(Random rand) {
        List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
        int indexPosition = orderedPath.indexOf(new Pair<Integer, Integer>(character.getX(), character.getY()));
        // inclusive start and exclusive end of range of positions not allowed
        int startNotAllowed = (indexPosition - 2 + orderedPath.size()) % orderedPath.size();
        int endNotAllowed = (indexPosition + 3) % orderedPath.size();
        // note terminating condition has to be != rather than < since wrap around...
        for (int i = endNotAllowed; i != startNotAllowed; i = (i + 1) % orderedPath.size()) {
            orderedPathSpawnCandidates.add(orderedPath.get(i));
        }

        // choose random choice
        Pair<Integer, Integer> spawnPosition = orderedPathSpawnCandidates
                .get(rand.nextInt(orderedPathSpawnCandidates.size()));

        return spawnPosition;
    }

    /**
     * remove a card by its x, y coordinates
     * 
     * @param cardNodeX     x index from 0 to width-1 of card to be removed
     * @param cardNodeY     y index from 0 to height-1 of card to be removed
     * @param buildingNodeX x index from 0 to width-1 of building to be added
     * @param buildingNodeY y index from 0 to height-1 of building to be added
     */
    public Building convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX,
            int buildingNodeY) {
        // start by getting card
        Card card = null;
        for (Card c : cardEntities) {
            if ((c.getX() == cardNodeX) && (c.getY() == cardNodeY)) {
                card = c;
                break;
            }
        }
        boolean onPathTile = checkPathTile(buildingNodeX, buildingNodeY);
        boolean adjacentPathTile = checkAdjacentPathTile(buildingNodeX, buildingNodeY);
        if (!checkPlacement(buildingNodeX, buildingNodeY))
            return null;
        // now spawn building
        Building newBuilding = null;
        if (card instanceof ZombiePitCard && !onPathTile && adjacentPathTile) {
            newBuilding = new ZombiePitBuilding(new SimpleIntegerProperty(buildingNodeX),
                    new SimpleIntegerProperty(buildingNodeY));
        } else if (card instanceof VampireCastleCard && !onPathTile && adjacentPathTile) {
            newBuilding = new VampireCastleBuilding(new SimpleIntegerProperty(buildingNodeX),
                    new SimpleIntegerProperty(buildingNodeY));
        } else if (card instanceof TowerCard && !onPathTile && adjacentPathTile) {
            newBuilding = new TowerBuilding(new SimpleIntegerProperty(buildingNodeX),
                    new SimpleIntegerProperty(buildingNodeY));
        } else if (card instanceof VillageCard && onPathTile) {
            newBuilding = new VillageBuilding(new SimpleIntegerProperty(buildingNodeX),
                    new SimpleIntegerProperty(buildingNodeY));
        } else if (card instanceof BarracksCard && onPathTile) {
            newBuilding = new BarracksBuilding(new SimpleIntegerProperty(buildingNodeX),
                    new SimpleIntegerProperty(buildingNodeY));
        } else if (card instanceof TrapCard && onPathTile) {
            newBuilding = new TrapBuilding(new SimpleIntegerProperty(buildingNodeX),
                    new SimpleIntegerProperty(buildingNodeY));
        } else if (card instanceof CampfireCard && !onPathTile) {
            newBuilding = new CampfireBuilding(new SimpleIntegerProperty(buildingNodeX),
                    new SimpleIntegerProperty(buildingNodeY));
        }
        // Invalid Coordinate for card to be placed depending on building
        if (newBuilding == null)
            return null;
        // destroy the card
        card.destroy();
        cardEntities.remove(card);
        shiftCardsDownFromXCoordinate(cardNodeX);

        buildingEntities.add(newBuilding);
        return newBuilding;
    }

    /**
     * Checks if the given co-ordinate is a pathTile
     * 
     * @param int                x
     * @param int                y
     * @param List<Pair<Integer, Integer>> orderedPath
     * @return int
     */
    public boolean checkPathTile(int x, int y) {
        for (Pair<Integer, Integer> pair : orderedPath) {
            if (pair.getValue0() == x && pair.getValue1() == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds an adjacent valid pathtile
     * 
     * @param int                x
     * @param int                y
     * @param List<Pair<Integer, Integer>> orderedPath
     * @return int
     */
    public boolean checkAdjacentPathTile(int x, int y) {
        if (checkPathTile(x - 1, y - 1))
            return true;
        if (checkPathTile(x - 1, y))
            return true;
        if (checkPathTile(x - 1, y + 1))
            return true;
        if (checkPathTile(x, y - 1))
            return true;
        if (checkPathTile(x, y + 1))
            return true;
        if (checkPathTile(x + 1, y - 1))
            return true;
        if (checkPathTile(x + 1, y))
            return true;
        if (checkPathTile(x + 1, y + 1))
            return true;
        return false;
    }

    /**
     * Adding a building to the buildingEntities list.
     * 
     * @param building
     */
    public void addBuilding(Building building) {
        this.buildingEntities.add(building);
    }

    public int getGoldCount() {
        return character.getGold();
    }

    public int getExpCount() {
        return character.getExp();
    }

    public int getPlayerAttack() {
        return character.getAttackDamage();
    }

    public void setGoal(Goals goal) {
        this.goal = goal;
    }

    public int getQuantity() {
        return goal.getQuantity();
    }

    public void setCycle(int cycleNum) {
        cycle.set(cycleNum);
    }

    public IntegerProperty getCycle() {
        return cycle;
    }

    public List<AllySoldier> getAllies() {
        return allies;
    }

    /**
     * Checks if buildings are overlapping
     * 
     * @param int x
     * @param int y
     * @return boolean
     */
    public boolean checkPlacement(int buildingNodeX, int buildingNodeY) {
        for (Building building : buildingEntities) {
            if (building.getX() == buildingNodeX && building.getY() == buildingNodeY) {
                return false;
            }
        }
        return true;
    }

    /**
     * Buffs character if in range of a building that gives buffs
     */
    public void buildingBuff() {
        for (Building building : buildingEntities) {
            if (character.getBuffed())
                return;
            building.buffCharacter(character);
        }
    }

    /**
     * unbuff the character
     */
    public void buildingUnbuff() {
        for (Building building : buildingEntities) {
            if (!character.getBuffed())
                return;
            building.unBuffCharacter(character);
        }
    }

    /**
     * Deals damage to an enemy when they step on a trap Building is destroyed after
     */
    public void buildingTrap(Enemy e) {
        for (Building building : buildingEntities) {
            if (building.trapDamage(e) != 0) {
                e.takeDamage(building.trapDamage(e));
                destroyBuilding(building);
            }
        }
    }

    /**
     * Attacks enemies when they are in range of buildings that attack enemies
     */
    public void buildingAttack(Enemy e) {
        for (Building building : buildingEntities) {
            if (building.towerDamage(e) != 0) {
                e.takeDamage(building.towerDamage(e));
            }
        }
    }

    /**
     * Heals character when character passes through it
     */
    public void buildingHeal() {
        for (Building building : buildingEntities) {
            building.villageHeal(character);
        }
    }

    /**
     * Gets the positoin of the boss location
     * 
     * @param int
     */
    public int getBossSpawnPosition() {
        Random rand = new Random();
        Pair<Integer, Integer> pos = getSpawnLocation(rand);
        int bossSpawnIndex = 0;
        if (pos != null) {
            System.out.println("picking my doggie spawn");
            bossSpawnIndex = orderedPath.indexOf(pos);
            return bossSpawnIndex;
        }
        return bossSpawnIndex;
    }

    /**
     * Sets the value of the coin depending on loopmaniaworld conditions
     */
    public void doggieCoinIncrease(boolean crash) {
        for (Entity e : unequippedInventoryItems) {
            if (e instanceof DoggieCoin) {
                if (!crash && checkElan()) {
                    calculateDoggieCoin(cycle.get() + ELAN_EFFECT);
                } else if (!crash) {
                    doggieCoinValue = cycle.get();
                } else if (crash) {
                    doggieCoinValue = 1;
                }
                e.setSellValue(doggieCoinValue);
            }
        }
    }

    public void calculateDoggieCoin(int multiplier) {
        doggieCoinValue = doggieCoinValue * multiplier;
    }

    private boolean checkElan() {
        for (Enemy e : enemies) {
            if (e instanceof ElanMuske) {
                return true;
            }
        }
        return false;
    }

    public int getPlayerHp() {
        return character.getHp();
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public int getPlayerDefence() {
        return character.getDefence();
    }

    public Goals getGoal() {
        return goal;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public Shop getShop() {
        return shop;
    }

    public void shopCheck() {
        shop.checkShopActivate(character.getX(), character.getY(), cycle.getValue());
    }

    /**
     * Initialise the correct shop depending on the difficulty
     */
    public void initialiseShop(int difficulty) {
        ShopParser shopParser = new ShopParser();
        shop = shopParser.createShop(difficulty, unequippedInventoryItems);

    }

    public List<Pair<Integer, Integer>> getOrderedPath() {
        return orderedPath;
    }

    public BossFactory getBossFactory() {
        return bossFactory;
    }

    public IntegerProperty getHpIntegerProperty() {
        return character.getHpProperty();
    }

    public void updateBossCounter() {
        if (beatenElan && beatenDoggie) {
            this.allBossesBeaten = true;
        }
    }

    public void setBeatenElan() {
        this.beatenElan = true;
        updateBossCounter();
    }

    public void setBeatenDoggie() {
        this.beatenDoggie = true;
        updateBossCounter();
    }
}
