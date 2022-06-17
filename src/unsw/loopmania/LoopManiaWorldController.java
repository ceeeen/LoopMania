package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.codefx.libfx.listener.handle.ListenerHandle;
import org.codefx.libfx.listener.handle.ListenerHandles;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.util.Duration;
import java.util.EnumMap;

import java.io.File;
import java.io.IOException;

/**
 * the draggable types. If you add more draggable types, add an enum value here.
 * This is so we can see what type is being dragged.
 */
enum DRAGGABLE_TYPE {
    CARD, SWORD, STAFF, STAKE, ARMOUR, SHIELD, HELMET, ANDURIL, TREESTUMP
}

/**
 * A JavaFX controller for the world.
 * 
 * All event handlers and the timeline in JavaFX run on the JavaFX application
 * thread:
 * https://examples.javacodegeeks.com/desktop-java/javafx/javafx-concurrency-example/
 * Note in
 * https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Application.html
 * under heading "Threading", it specifies animation timelines are run in the
 * application thread. This means that the starter code does not need locks
 * (mutexes) for resources shared between the timeline KeyFrame, and all of the
 * event handlers (including between different event handlers). This will make
 * the game easier for you to implement. However, if you add time-consuming
 * processes to this, the game may lag or become choppy.
 * 
 * If you need to implement time-consuming processes, we recommend: using Task
 * https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Task.html by
 * itself or within a Service
 * https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Service.html
 * 
 * Tasks ensure that any changes to public properties, change notifications for
 * errors or cancellation, event handlers, and states occur on the JavaFX
 * Application thread, so is a better alternative to using a basic Java Thread:
 * https://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm The Service class
 * is used for executing/reusing tasks. You can run tasks without Service,
 * however, if you don't need to reuse it.
 *
 * If you implement time-consuming processes in a Task or thread, you may need
 * to implement locks on resources shared with the application thread (i.e.
 * Timeline KeyFrame and drag Event handlers). You can check whether code is
 * running on the JavaFX application thread by running the helper method
 * printThreadingNotes in this class.
 * 
 * NOTE: http://tutorials.jenkov.com/javafx/concurrency.html and
 * https://www.developer.com/design/multithreading-in-javafx/#:~:text=JavaFX%20has%20a%20unique%20set,in%20the%20JavaFX%20Application%20Thread.
 * 
 * If you need to delay some code but it is not long-running, consider using
 * Platform.runLater
 * https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Platform.html#runLater(java.lang.Runnable)
 * This is run on the JavaFX application thread when it has enough time.
 */
public class LoopManiaWorldController {

    /**
     * squares gridpane includes path images, enemies, character, empty grass,
     * buildings
     */
    @FXML
    private GridPane squares;

    /**
     * cards gridpane includes cards and the ground underneath the cards
     */
    @FXML
    private GridPane cards;

    /**
     * anchorPaneRoot is the "background". It is useful since anchorPaneRoot
     * stretches over the entire game world, so we can detect dragging of
     * cards/items over this and accordingly update DragIcon coordinates
     */
    @FXML
    private AnchorPane anchorPaneRoot;

    /**
     * equippedItems gridpane is for equipped items (e.g. swords, shield, axe)
     */
    @FXML
    private GridPane equippedItems;

    @FXML
    private GridPane unequippedInventory;

    @FXML
    private GridPane statBar;

    @FXML
    private GridPane xpCounter;

    // all image views including tiles, character, enemies, cards... even though
    // cards in separate gridpane...
    private List<ImageView> entityImages;

    /**
     * when we drag a card/item, the picture for whatever we're dragging is set here
     * and we actually drag this node
     */
    private DragIcon draggedEntity;

    private boolean isPaused;
    private LoopManiaWorld world;

    /**
     * runs the periodic game logic - second-by-second moving of character through
     * maze, as well as enemies, and running of battles
     */
    private Timeline timeline;

    private Image vampireCastleCardImage;
    private Image zombiePitCardImage;
    private Image barracksCardImage;
    private Image campfireCardImage;
    private Image towerCardImage;
    private Image trapCardImage;
    private Image villageCardImage;
    private Image basicEnemyImage;
    private Image vampireEnemyImage;
    private Image zombieEnemyImage;
    private Image swordImage;
    private Image VampireCastleImage;
    private Image ZombiePitImage;
    private Image TowerImage;
    private Image VillageImage;
    private Image BarracksImage;
    private Image TrapImage;
    private Image CampfireImage;
    private Image HeroCastleImage;
    private Image AllySoldierImage;
    private Image elanMuskeImage;
    private Image doggieImage;
    private Image covidImage;

    /**
     * the image currently being dragged, if there is one, otherwise null. Holding
     * the ImageView being dragged allows us to spawn it again in the drop location
     * if appropriate. private Image basicBuildingImage;
     * 
     * /** Weapon Image declarations.
     */
    private Image staffImage;
    private Image stakeImage;

    /**
     * Armour Image declarations.
     */
    private Image armourImage;

    /**
     * Shield Image declarations.
     */
    private Image shieldImage;

    /**
     * Helmet Image delarations.
     */
    private Image helmetImage;

    /**
     * Gold Image declaration
     */
    private Image goldImage;

    /**
     * Doggie coin image
     */
    private Image dogCoinImage;

    /**
     * Health potion Image declaration
     */
    private Image healthPotionImage;

    /**
     * Rare item Image declarations.
     */
    private Image theOneRingImage;
    private Image AndurilImage;
    private Image treeStumpImage;

    private Image heartImage;
    /**
     * the building/item currently being dragged, if there is one, otherwise null.
     * Holding the building/item being dragged allows us to spawn it again in the
     * drop location if appropriate.
     */
    private ImageView currentlyDraggedImage;

    /**
     * null if nothing being dragged, or the type of item being dragged
     */
    private DRAGGABLE_TYPE currentlyDraggedType;

    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dropped over its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dragged over the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragOver;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dropped in the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dragged into the boundaries of its appropriate
     * gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragEntered;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered
     * when the draggable type is dragged outside of the boundaries of its
     * appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragExited;

    /**
     * object handling switching to the main menu
     */
    private MenuSwitcher mainMenuSwitcher;
    private MenuSwitcher endScreenSwitcher;
    private MenuSwitcher shopScreenSwitcher;
    private MenuSwitcher deathScreenSwitcher;

    /**
     * Observables
     */
    private PlayerStatsSubject playerStatsSubject;

    /**
     * Observers
     */
    private HealthObserver healthObserver;
    private GoldObserver goldObserver;
    private XpObserver xpObserver;

    private boolean spawnedOneRing = false;
    private boolean spawnedAndruil = false;
    private boolean spawnedTreeStump = false;

    /**
     * @param world           world object loaded from file
     * @param initialEntities the initial JavaFX nodes (ImageViews) which should be
     *                        loaded into the GUI
     */
    public LoopManiaWorldController(LoopManiaWorld world, List<ImageView> initialEntities) {
        this.world = world;
        entityImages = new ArrayList<>(initialEntities);
        ImageFactory imageFactory = new ImageFactory();
        vampireCastleCardImage = imageFactory.imageGet("VampireCastle");
        zombiePitCardImage = imageFactory.imageGet("ZombiePitCard");
        barracksCardImage = imageFactory.imageGet("BarracksCard");
        campfireCardImage = imageFactory.imageGet("CampfireCard");
        towerCardImage = imageFactory.imageGet("TowerCard");
        trapCardImage = imageFactory.imageGet("TrapCard");
        villageCardImage = imageFactory.imageGet("VillageCard");
        basicEnemyImage = imageFactory.imageGet("BasicEnemy");
        vampireEnemyImage = imageFactory.imageGet("VampireEnemy");
        zombieEnemyImage = imageFactory.imageGet("ZombieEnemy");
        VampireCastleImage = imageFactory.imageGet("VampireCastle");
        ZombiePitImage = imageFactory.imageGet("ZombiePit");
        TowerImage = imageFactory.imageGet("Tower");
        AllySoldierImage = imageFactory.imageGet("AllySoldier");
        VillageImage = imageFactory.imageGet("Village");
        BarracksImage = imageFactory.imageGet("Barracks");
        TrapImage = imageFactory.imageGet("Trap");
        CampfireImage = imageFactory.imageGet("Campfire");
        HeroCastleImage = imageFactory.imageGet("HeroCastle");
        doggieImage = imageFactory.imageGet("Doggie");
        elanMuskeImage = imageFactory.imageGet("ElanMuske");
        covidImage = imageFactory.imageGet("Covid");

        // Weapons
        swordImage = imageFactory.imageGet("Sword");
        staffImage = imageFactory.imageGet("Staff");
        stakeImage = imageFactory.imageGet("Stake");

        // Armour
        armourImage = imageFactory.imageGet("Armour");
        // Shields
        shieldImage = imageFactory.imageGet("Shield");
        // Helmets
        helmetImage = imageFactory.imageGet("Helmet");

        // Static Items
        goldImage = imageFactory.imageGet("Gold");
        healthPotionImage = imageFactory.imageGet("HealthPotion");

        dogCoinImage = imageFactory.imageGet("DoggieCoin");

        // Rare item
        theOneRingImage = imageFactory.imageGet("OneRing");
        AndurilImage = imageFactory.imageGet("Anduril");
        treeStumpImage = imageFactory.imageGet("TreeStump");

        heartImage = imageFactory.imageGet("Heart");

        currentlyDraggedImage = null;
        currentlyDraggedType = null;

        // initialize them all...
        gridPaneSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragOver = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragEntered = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragExited = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
    }

    @FXML
    public void initialize() {

        Image pathTilesImage = new Image((new File("src/images/32x32GrassAndDirtPath.png")).toURI().toString());
        Image inventorySlotImage = new Image((new File("src/images/empty_slot.png")).toURI().toString());
        Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);

        // goal checker
        goalTracker(world.getGoal());
        cycleTracker();
        hpTracker();
        shopTracker(world.getShop());

        // Add the ground first so it is below all other entities (inculding all the
        // twists and turns)
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                ImageView groundView = new ImageView(pathTilesImage);
                groundView.setViewport(imagePart);
                squares.add(groundView, x, y);
            }
        }

        // load entities loaded from the file in the loader into the squares gridpane
        for (ImageView entity : entityImages) {
            squares.getChildren().add(entity);
        }

        // add the ground underneath the cards
        for (int x = 0; x < world.getWidth(); x++) {
            ImageView groundView = new ImageView(pathTilesImage);
            groundView.setViewport(imagePart);
            cards.add(groundView, x, 0);
        }

        // add the empty slot images for the unequipped inventory
        for (int x = 0; x < LoopManiaWorld.unequippedInventoryWidth; x++) {
            for (int y = 0; y < LoopManiaWorld.unequippedInventoryHeight; y++) {
                ImageView emptySlotView = new ImageView(inventorySlotImage);
                unequippedInventory.add(emptySlotView, x, y);
            }
        }

        // create the draggable icon
        draggedEntity = new DragIcon();
        draggedEntity.setVisible(false);
        draggedEntity.setOpacity(0.7);
        anchorPaneRoot.getChildren().add(draggedEntity);

        // Spawns items such as gold and health potions into the world.
        loadStaticItems();

        // Loads initial observables and observers.
        playerStatsSubject = new PlayerStatsSubject();
        healthObserver = new HealthObserver();
        goldObserver = new GoldObserver();
        xpObserver = new XpObserver();
        playerStatsSubject.subscribe(healthObserver);
        playerStatsSubject.subscribe(goldObserver);
        playerStatsSubject.subscribe(xpObserver);
    }

    /**
     * create and run the timer
     */
    public void startTimer() {

        System.out.println("starting timer");
        isPaused = false;

        // trigger adding code to process main game logic to queue. JavaFX will target
        // framerate of 0.3 seconds
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.3), event -> {
            statDisplay();
            world.updateGoal();

            if (world.getCharacter().getX() == 0 && world.getCharacter().getY() == 0) {
                loadStaticItems();
            }

            world.runTickMoves();
            world.shopCheck();
            world.buildingHeal();
            List<Enemy> defeatedEnemies = world.runBattles();
            for (Enemy e : defeatedEnemies) {
                if (e instanceof ElanMuske) {
                    world.doggieCoinIncrease(true);
                    world.setBeatenElan();
                } else if (e instanceof Doggie) {
                    world.setBeatenDoggie();
                }
                reactToEnemyDefeat(e);
            }
            List<Enemy> newEnemies = world.possiblySpawnEnemies();
            for (Enemy newEnemy : newEnemies) {
                onLoad(newEnemy);
            }
            List<AllySoldier> newAllySoldiers = world.possiblySpawnAllySoldiers();
            for (AllySoldier allySoldier : newAllySoldiers) {
                onLoad(allySoldier);
            }

            // Picks up any item e.g gold pile, health potion if the character and item are
            // on the same path tile.
            world.pickUpItems();
            world.doggieCoinIncrease(false);
            printThreadingNotes("HANDLED TIMER");
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * pause the execution of the game loop the human player can still drag and drop
     * items during the game pause
     */
    public void pause() {
        isPaused = true;
        System.out.println("pausing");
        timeline.stop();
    }

    public void terminate() {
        pause();
    }

    /**
     * pair the entity an view so that the view copies the movements of the entity.
     * add view to list of entity images
     * 
     * @param entity backend entity to be paired with view
     * @param view   frontend imageview to be paired with backend entity
     */
    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entityImages.add(view);
    }

    /**
     * Loads static items from the world and places them onto the squares.
     */
    public void loadStaticItems() {
        // Loading gold piles.
        List<StaticEntity> newGoldPiles = world.possiblySpawnGoldPile();
        if (newGoldPiles == null) {
            return;
        }
        for (StaticEntity newGoldPile : newGoldPiles) {
            onLoad(newGoldPile);
        }

        // Loading health potions.
        List<StaticEntity> newHealthPotions = world.possiblySpawnHealthPotion();
        if (newHealthPotions == null) {
            return;
        }
        for (StaticEntity newHealthPotion : newHealthPotions) {
            onLoad(newHealthPotion);
        }

    }

    /**
     * load a vampire card from the world, and pair it with an image in the GUI
     */
    private void loadVampireCard() {
        VampireCastleCard vampireCastleCard = world.loadVampireCard();
        onLoad(vampireCastleCard);
    }

    /**
     * load a zombie card from the world, and pair it with an image in the GUI
     */
    private void loadZombieCard() {
        ZombiePitCard zombiePitCard = world.loadZombieCard();
        onLoad(zombiePitCard);
    }

    /**
     * load a barracks card from the world, and pair it with an image in the GUI
     */
    private void loadBarracksCard() {
        BarracksCard barracksCard = world.loadBarracksCard();
        onLoad(barracksCard);
    }

    /**
     * load a campfire card from the world, and pair it with an image in the GUI
     */
    private void loadCampfireCard() {
        CampfireCard campfireCard = world.loadCampfireCard();
        onLoad(campfireCard);
    }

    /**
     * load a tower card from the world, and pair it with an image in the GUI
     */
    private void loadTowerCard() {
        TowerCard towerCard = world.loadTowerCard();
        onLoad(towerCard);
    }

    /**
     * load a trap card from the world, and pair it with an image in the GUI
     */
    private void loadTrapCard() {
        TrapCard trapCard = world.loadTrapCard();
        onLoad(trapCard);
    }

    /**
     * load a village card from the world, and pair it with an image in the GUI
     */
    private void loadVillageCard() {
        VillageCard villageCard = world.loadVillageCard();
        onLoad(villageCard);
    }

    /**
     * load a sword from the world, and pair it with an image in the GUI
     */
    public void loadSword() {
        // start by getting first available coordinates
        Sword sword = world.addUnequippedSword();
        onLoad(sword);
    }

    /**
     * load a staff from the world and pair it with an image in the GUI.
     */
    public void loadStaff() {
        Staff staff = world.addUnequippedStaff();
        onLoad(staff);
    }

    public void loadStake() {
        Stake stake = world.addUnequippedStake();
        onLoad(stake);
    }

    /**
     * <<<<<<< HEAD run GUI events after an enemy is defeated, such as spawning
     * items/experience/gold
     * 
     * ======= load a staff from the world and pair it with an image in the GUI.
     */
    public void loadArmour() {
        Armour armour = world.addUnequippedArmour();
        onLoad(armour);
    }

    public void loadShield() {
        Shield shield = world.addUnequippedShield();
        onLoad(shield);
    }

    public void loadHelmet() {
        Helmet helmet = world.addUnequippedHelmet();
        onLoad(helmet);
    }

    private void loadOneRing() {
        TheOneRing ring = world.addOneRing();
        if (ring == null) {
            return;
        }
        onLoad(ring);
    }

    private void loadAnduril() {
        Anduril anduril = world.addAnduril();
        if (anduril == null) {
            return;
        }
        onLoad(anduril);
    }

    private void loadTreeStump() {
        TreeStump treeStump = world.addTreeStump();
        if (treeStump == null) {
            return;
        }
        onLoad(treeStump);
    }

    private void loadDoggieCoin() {
        DoggieCoin doggieCoin = world.addDoggieCoin();
        onLoad(doggieCoin);
    }

    /**
     * run GUI events after an enemy is defeated, such as spawning
     * items/experience/gold >>>>>>> origin/Anthony_items
     * 
     * @param enemy defeated enemy for which we should react to the death of
     */
    private void reactToEnemyDefeat(Enemy enemy) {
        // react to character defeating an enemy
        // in starter code, spawning extra card/weapon...

        if (enemy instanceof Doggie) {
            loadDoggieCoin();
        }
        Random r = new Random();
        int num = r.nextInt(14);
        switch (num) {
            case 0:
                loadSword();
                break;

            case 1:
                loadVampireCard();
                break;

            case 2:
                loadZombieCard();
                break;

            case 3:
                loadBarracksCard();
                break;

            case 4:
                loadCampfireCard();
                break;

            case 5:
                loadTowerCard();
                break;

            case 6:
                loadTrapCard();
                break;

            case 7:
                loadVillageCard();
                break;

            case 8:
                loadStaff();
                break;

            case 9:
                loadStake();
                break;

            case 10:
                loadArmour();
                break;

            case 11:
                loadShield();
                break;

            case 12:
                loadHelmet();
                break;

        }

        num = r.nextInt(100);
        if (num < 2 && !spawnedOneRing) {
            loadOneRing();
            spawnedOneRing = true;
        } else if (num < 4 && !spawnedAndruil) {
            loadAnduril();
            spawnedAndruil = true;
        } else if (num < 6 && !spawnedTreeStump) {
            loadTreeStump();
            spawnedTreeStump = true;
        }
    }

    /**
     * load a vampire castle card into the GUI. Particularly, we must connect to the
     * drag detection event handler, and load the image into the cards GridPane.
     * 
     * @param vampireCastleCard
     */
    private void onLoad(VampireCastleCard vampireCastleCard) {
        ImageView view = new ImageView(vampireCastleCardImage);

        // FROM
        // https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
        // note target setOnDragOver and setOnDragEntered defined in initialize method
        addDragEventHandlers(view, DRAGGABLE_TYPE.CARD, cards, squares);

        addEntity(vampireCastleCard, view);
        cards.getChildren().add(view);
    }

    /**
     * load a zombie pit card into the GUI.
     * 
     * @param zombiePitCard
     */
    private void onLoad(ZombiePitCard zombiePitCard) {
        ImageView view = new ImageView(zombiePitCardImage);

        addDragEventHandlers(view, DRAGGABLE_TYPE.CARD, cards, squares);

        addEntity(zombiePitCard, view);
        cards.getChildren().add(view);
    }

    /**
     * load a barracks card into the GUI.
     * 
     * @param barracksCard
     */
    private void onLoad(BarracksCard barracksCard) {
        ImageView view = new ImageView(barracksCardImage);

        addDragEventHandlers(view, DRAGGABLE_TYPE.CARD, cards, squares);

        addEntity(barracksCard, view);
        cards.getChildren().add(view);
    }

    /**
     * load a campfire card into the GUI.
     * 
     * @param campfireCard
     */
    private void onLoad(CampfireCard campfireCard) {
        ImageView view = new ImageView(campfireCardImage);

        addDragEventHandlers(view, DRAGGABLE_TYPE.CARD, cards, squares);

        addEntity(campfireCard, view);
        cards.getChildren().add(view);
    }

    /**
     * load a tower card into the GUI.
     * 
     * @param towerCard
     */
    private void onLoad(TowerCard towerCard) {
        ImageView view = new ImageView(towerCardImage);

        addDragEventHandlers(view, DRAGGABLE_TYPE.CARD, cards, squares);

        addEntity(towerCard, view);
        cards.getChildren().add(view);
    }

    /**
     * load a trap card into the GUI.
     * 
     * @param towerCard
     */
    private void onLoad(TrapCard trapCard) {
        ImageView view = new ImageView(trapCardImage);

        addDragEventHandlers(view, DRAGGABLE_TYPE.CARD, cards, squares);

        addEntity(trapCard, view);
        cards.getChildren().add(view);
    }

    /**
     * load a village card into the GUI.
     * 
     * @param villageCard
     */
    private void onLoad(VillageCard villageCard) {
        ImageView view = new ImageView(villageCardImage);

        addDragEventHandlers(view, DRAGGABLE_TYPE.CARD, cards, squares);

        addEntity(villageCard, view);
        cards.getChildren().add(view);
    }

    /**
     * load a sword into the GUI. Particularly, we must connect to the drag
     * detection event handler, and load the image into the unequippedInventory
     * GridPane.
     * 
     * @param sword
     */
    private void onLoad(Sword sword) {
        ImageView view = new ImageView(swordImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.SWORD, unequippedInventory, equippedItems);
        addEntity(sword, view);
        unequippedInventory.getChildren().add(view);
    }

    /**
     * Load staff into the GUI.
     * 
     * @param staff
     */
    private void onLoad(Staff staff) {
        ImageView view = new ImageView(staffImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.STAFF, unequippedInventory, equippedItems);
        addEntity(staff, view);
        unequippedInventory.getChildren().add(view);
    }

    /**
     * Load stake into the GUI.
     * 
     * @param stake
     */
    private void onLoad(Stake stake) {
        ImageView view = new ImageView(stakeImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.STAKE, unequippedInventory, equippedItems);
        addEntity(stake, view);
        unequippedInventory.getChildren().add(view);
    }

    /**
     * Load armour into the GUI.
     * 
     * @param armour
     */
    private void onLoad(Armour armour) {
        ImageView view = new ImageView(armourImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.ARMOUR, unequippedInventory, equippedItems);
        addEntity(armour, view);
        unequippedInventory.getChildren().add(view);
    }

    /**
     * Load shield into the GUI.
     * 
     * @param shield
     */
    private void onLoad(Shield shield) {
        ImageView view = new ImageView(shieldImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.SHIELD, unequippedInventory, equippedItems);
        addEntity(shield, view);
        unequippedInventory.getChildren().add(view);
    }

    /**
     * Loads helmet into the GUI.
     * 
     * @param helmet
     */
    private void onLoad(Helmet helmet) {
        ImageView view = new ImageView(helmetImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.HELMET, unequippedInventory, equippedItems);
        addEntity(helmet, view);
        unequippedInventory.getChildren().add(view);
    }

    private void onLoad(TheOneRing ring) {
        ImageView view = new ImageView(theOneRingImage);
        addEntity(ring, view);
        unequippedInventory.getChildren().add(view);
    }

    private void onLoad(Anduril anduril) {
        ImageView view = new ImageView(AndurilImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.ANDURIL, unequippedInventory, equippedItems);
        addEntity(anduril, view);
        unequippedInventory.getChildren().add(view);
    }

    private void onLoad(TreeStump treeStump) {
        ImageView view = new ImageView(treeStumpImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.TREESTUMP, unequippedInventory, equippedItems);
        addEntity(treeStump, view);
        unequippedInventory.getChildren().add(view);
    }

    private void onLoad(DoggieCoin doggieCoin) {
        ImageView view = new ImageView(dogCoinImage);
        addEntity(doggieCoin, view);
        unequippedInventory.getChildren().add(view);
    }

    /**
     * Loads a static item into the GUI. Weapons, armour and One Ring are loaded
     * into unequipped inventory. Gold piles, health potions are loaded into world
     * squares.
     * 
     * @param newItem
     */
    private void onLoad(StaticEntity newItem) {
        ImageView view = null;

        if (newItem instanceof GoldPile) {
            view = new ImageView(goldImage);
        } else if (newItem instanceof HealthPotion) {
            view = new ImageView(healthPotionImage);
        }

        addEntity(newItem, view);
        squares.getChildren().add(view);
    }

    /**
     * load an enemy into the GUI
     * 
     * @param enemy
     */
    private void onLoad(Enemy enemy) {
        if (enemy instanceof Slug) {
            ImageView view = new ImageView(basicEnemyImage);
            addEntity(enemy, view);
            squares.getChildren().add(view);
        } else if (enemy instanceof Vampire) {
            ImageView view = new ImageView(vampireEnemyImage);
            addEntity(enemy, view);
            squares.getChildren().add(view);
        } else if (enemy instanceof Zombie) {
            ImageView view = new ImageView(zombieEnemyImage);
            addEntity(enemy, view);
            squares.getChildren().add(view);
        } else if (enemy instanceof Doggie) {
            ImageView view = new ImageView(doggieImage);
            addEntity(enemy, view);
            squares.getChildren().add(view);
        } else if (enemy instanceof ElanMuske) {
            ImageView view = new ImageView(elanMuskeImage);
            addEntity(enemy, view);
            squares.getChildren().add(view);
        } else if (enemy instanceof Covid) {
            ImageView view = new ImageView(covidImage);
            addEntity(enemy, view);
            squares.getChildren().add(view);
        }
    }

    private void onLoad(AllySoldier allySoldier) {
        ImageView view = new ImageView(AllySoldierImage);
        addEntity(allySoldier, view);
        squares.getChildren().add(view);
    }

    /**
     * load a building into the GUI
     * 
     * @param building
     */
    private void onLoad(Building building) {
        ImageView view = null;
        if (building instanceof VampireCastleBuilding) {
            view = new ImageView(VampireCastleImage);
        } else if (building instanceof ZombiePitBuilding) {
            view = new ImageView(ZombiePitImage);
        } else if (building instanceof TowerBuilding) {
            view = new ImageView(TowerImage);
        } else if (building instanceof VillageBuilding) {
            view = new ImageView(VillageImage);
        } else if (building instanceof BarracksBuilding) {
            view = new ImageView(BarracksImage);
        } else if (building instanceof TrapBuilding) {
            view = new ImageView(TrapImage);
        } else if (building instanceof CampfireBuilding) {
            view = new ImageView(CampfireImage);
        }
        addEntity(building, view);
        squares.getChildren().add(view);
    }

    /**
     * add drag event handlers for dropping into gridpanes, dragging over the
     * background, dropping over the background. These are not attached to invidual
     * items such as swords/cards.
     * 
     * @param draggableType  the type being dragged - card or item
     * @param sourceGridPane the gridpane being dragged from
     * @param targetGridPane the gridpane the human player should be dragging to
     *                       (but we of course cannot guarantee they will do so)
     */
    private void buildNonEntityDragHandlers(DRAGGABLE_TYPE draggableType, GridPane sourceGridPane,
            GridPane targetGridPane) {

        gridPaneSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /*
                 * you might want to design the application so dropping at an invalid location
                 * drops at the most recent valid location hovered over, or simply allow the
                 * card/item to return to its slot (the latter is easier, as you won't have to
                 * store the last valid drop location!)
                 */
                if (currentlyDraggedType == draggableType) {
                    // problem = event is drop completed is false when should be true...
                    // https://bugs.openjdk.java.net/browse/JDK-8117019
                    // putting drop completed at start not making complete on VLAB...

                    // Data dropped
                    // If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    if (node != targetGridPane && db.hasImage()) {

                        Integer cIndex = GridPane.getColumnIndex(node);
                        Integer rIndex = GridPane.getRowIndex(node);
                        int x = cIndex == null ? 0 : cIndex;
                        int y = rIndex == null ? 0 : rIndex;
                        // Places at 0,0 - will need to take coordinates once that is implemented
                        ImageView image = new ImageView(db.getImage());
                        ItemFactory itemFactory = new ItemFactory();

                        int nodeX = GridPane.getColumnIndex(currentlyDraggedImage);
                        int nodeY = GridPane.getRowIndex(currentlyDraggedImage);
                        switch (draggableType) {
                            case CARD:
                                removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                Building newBuilding = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                if (newBuilding == null) {
                                    currentlyDraggedImage.setVisible(true);

                                    break;
                                }
                                onLoad(newBuilding);
                                break;
                            case SWORD:
                                if (node.getId().equals("swordCell")) {
                                    removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                    removeItemByCoordinates(nodeX, nodeY);

                                    Items item = itemFactory.getItem("Sword", new SimpleIntegerProperty(x),
                                            new SimpleIntegerProperty(y));
                                    addToEquippedWeapon(item);
                                    addDragEventHandlers(image, draggableType, equippedItems, unequippedInventory);
                                    addEntity(item, image);
                                    equippedItems.getChildren().add(image);
                                    break;
                                }
                                // Code to unequip from equipped inventory. Not functional.
                                /*
                                 * else if (targetGridPane == unequippedInventory) {
                                 * removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                 * removeEquippedItemByCoordinates(nodeX, nodeY);
                                 * 
                                 * Sword newSword = new Sword(new SimpleIntegerProperty(x), new
                                 * SimpleIntegerProperty(y)); world.addItemToUnequippedInventory(newSword);
                                 * ImageView view = new ImageView(swordImage); addDragEventHandlers(view,
                                 * draggableType, unequippedInventory, equippedItems); addEntity(newSword,
                                 * view); unequippedInventory.getChildren().add(view); break; }
                                 */

                                return;
                            case STAFF:
                                if (node.getId().equals("swordCell")) {
                                    removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                    removeItemByCoordinates(nodeX, nodeY);

                                    Items item = itemFactory.getItem("Staff", new SimpleIntegerProperty(x),
                                            new SimpleIntegerProperty(y));
                                    addToEquippedWeapon(item);
                                    addDragEventHandlers(image, draggableType, equippedItems, unequippedInventory);
                                    addEntity(item, image);
                                    equippedItems.getChildren().add(image);
                                    break;
                                }
                                return;
                            case STAKE:
                                if (node.getId().equals("swordCell")) {
                                    removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                    removeItemByCoordinates(nodeX, nodeY);

                                    Items item = itemFactory.getItem("Stake", new SimpleIntegerProperty(x),
                                            new SimpleIntegerProperty(y));
                                    addToEquippedWeapon(item);
                                    addDragEventHandlers(image, draggableType, equippedItems, unequippedInventory);
                                    addEntity(item, image);
                                    equippedItems.getChildren().add(image);
                                    break;
                                }
                                return;
                            case ARMOUR:
                                if (node.getId().equals("blankCell")) {
                                    removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                    removeItemByCoordinates(nodeX, nodeY);

                                    Items item = itemFactory.getItem("Armour", new SimpleIntegerProperty(x),
                                            new SimpleIntegerProperty(y));
                                    addEquippedProtectiveItem(item);
                                    addDragEventHandlers(image, draggableType, equippedItems, unequippedInventory);
                                    addEntity(item, image);
                                    equippedItems.getChildren().add(image);
                                    break;
                                }
                                return;
                            case SHIELD:
                                if (node.getId().equals("shieldCell")) {
                                    removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                    removeItemByCoordinates(nodeX, nodeY);

                                    Items item = itemFactory.getItem("Shield", new SimpleIntegerProperty(x),
                                            new SimpleIntegerProperty(y));
                                    addEquippedProtectiveItem(item);
                                    addDragEventHandlers(image, draggableType, equippedItems, unequippedInventory);
                                    addEntity(item, image);
                                    equippedItems.getChildren().add(image);
                                    break;
                                }
                                return;
                            case HELMET:
                                if (node.getId().equals("helmetCell")) {
                                    removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                    removeItemByCoordinates(nodeX, nodeY);

                                    Items item = itemFactory.getItem("Helmet", new SimpleIntegerProperty(x),
                                            new SimpleIntegerProperty(y));
                                    addEquippedProtectiveItem(item);
                                    addDragEventHandlers(image, draggableType, equippedItems, unequippedInventory);
                                    addEntity(item, image);
                                    equippedItems.getChildren().add(image);
                                    break;
                                }
                                return;
                            case ANDURIL:
                                if (node.getId().equals("swordCell")) {
                                    removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                    removeItemByCoordinates(nodeX, nodeY);

                                    Items item = itemFactory.getItem("Anduril", new SimpleIntegerProperty(x),
                                            new SimpleIntegerProperty(y));
                                    addToEquippedWeapon(item);
                                    addDragEventHandlers(image, draggableType, equippedItems, unequippedInventory);
                                    addEntity(item, image);
                                    equippedItems.getChildren().add(image);
                                    break;
                                }
                                return;
                            case TREESTUMP:
                                if (node.getId().equals("shieldCell")) {
                                    removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                    removeItemByCoordinates(nodeX, nodeY);

                                    Items item = itemFactory.getItem("TreeStump", new SimpleIntegerProperty(x),
                                            new SimpleIntegerProperty(y));
                                    addEquippedProtectiveItem(item);
                                    addDragEventHandlers(image, draggableType, equippedItems, unequippedInventory);
                                    addEntity(item, image);
                                    equippedItems.getChildren().add(image);
                                    break;
                                }
                                return;
                            default:
                                break;
                        }

                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                        printThreadingNotes("DRAG DROPPED ON GRIDPANE HANDLED");
                    }

                    // printThreadingNotes("GOT HERE");
                }
                event.setDropCompleted(true);
                // consuming prevents the propagation of the event to the anchorPaneRoot (as a
                // sub-node of anchorPaneRoot, GridPane is prioritized)
                // https://openjfx.io/javadoc/11/javafx.base/javafx/event/Event.html#consume()
                // to understand this in full detail, ask your tutor or read
                // https://docs.oracle.com/javase/8/javafx/events-tutorial/processing.htm
                event.consume();
                printThreadingNotes("GOT HERE");
            }
        });

        // this doesn't fire when we drag over GridPane because in the event handler for
        // dragging over GridPanes, we consume the event
        anchorPaneRootSetOnDragOver.put(draggableType, new EventHandler<DragEvent>() {
            // https://github.com/joelgraff/java_fx_node_link_demo/blob/master/Draggable_Node/DraggableNodeDemo/src/application/RootLayout.java#L110
            @Override
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType) {
                    if (event.getGestureSource() != anchorPaneRoot && event.getDragboard().hasImage()) {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                }
                if (currentlyDraggedType != null) {
                    draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                }
                event.consume();
            }
        });

        // this doesn't fire when we drop over GridPane because in the event handler for
        // dropping over GridPanes, we consume the event
        anchorPaneRootSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType) {
                    // Data dropped
                    // If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    if (node != anchorPaneRoot && db.hasImage()) {
                        // Places at 0,0 - will need to take coordinates once that is implemented
                        currentlyDraggedImage.setVisible(true);
                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        removeDraggableDragEventHandlers(draggableType, targetGridPane);

                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                    }
                    // printThreadingNotes("GOT HERE");
                }
                // let the source know whether the image was successfully transferred and used
                event.setDropCompleted(true);
                event.consume();
            }
        });

    }

    /**
     * remove the card from the world, and spawn and return a building instead where
     * the card was dropped
     * 
     * @param cardNodeX     the x coordinate of the card which was dragged, from 0
     *                      to width-1
     * @param cardNodeY     the y coordinate of the card which was dragged (in
     *                      starter code this is 0 as only 1 row of cards)
     * @param buildingNodeX the x coordinate of the drop location for the card,
     *                      where the building will spawn, from 0 to width-1
     * @param buildingNodeY the y coordinate of the drop location for the card,
     *                      where the building will spawn, from 0 to height-1
     * @return building entity returned from the world
     */
    private Building convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX,
            int buildingNodeY) {
        return world.convertCardToBuildingByCoordinates(cardNodeX, cardNodeY, buildingNodeX, buildingNodeY);
    }

    /**
     * remove an item from the unequipped inventory by its x and y coordinates in
     * the unequipped inventory gridpane
     * 
     * @param nodeX x coordinate from 0 to unequippedInventoryWidth-1
     * @param nodeY y coordinate from 0 to unequippedInventoryHeight-1
     */
    private void removeItemByCoordinates(int nodeX, int nodeY) {
        world.removeUnequippedInventoryItemByCoordinates(nodeX, nodeY);
    }

    /**
     * Adds an item to equipped items list.
     * 
     * @param entity
     */
    private void addEquippedProtectiveItem(Items gear) {
        world.addToEquippedProtectiveItems(gear);
    }

    private void addToEquippedWeapon(Items weapon) {
        world.addToEquippedWeapon(weapon);
    }

    private void removeEquippedItemByCoordinates(int x, int y) {
        world.removeEquippedItemByCoordinates(x, y);
    }

    /**
     * add drag event handlers to an ImageView
     * 
     * @param view           the view to attach drag event handlers to
     * @param draggableType  the type of item being dragged - card or item
     * @param sourceGridPane the relevant gridpane from which the entity would be
     *                       dragged
     * @param targetGridPane the relevant gridpane to which the entity would be
     *                       dragged to
     */
    private void addDragEventHandlers(ImageView view, DRAGGABLE_TYPE draggableType, GridPane sourceGridPane,
            GridPane targetGridPane) {
        view.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                currentlyDraggedImage = view; // set image currently being dragged, so squares setOnDragEntered can
                                              // detect it...
                currentlyDraggedType = draggableType;
                // Drag was detected, start drap-and-drop gesture
                // Allow any transfer node
                Dragboard db = view.startDragAndDrop(TransferMode.MOVE);

                // Put ImageView on dragboard
                ClipboardContent cbContent = new ClipboardContent();
                cbContent.putImage(view.getImage());
                db.setContent(cbContent);
                view.setVisible(false);

                buildNonEntityDragHandlers(draggableType, sourceGridPane, targetGridPane);

                draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                switch (draggableType) {
                    case CARD:
                        draggedEntity.setImage(vampireCastleCardImage);
                        draggedEntity.setImage(zombiePitCardImage);
                        draggedEntity.setImage(barracksCardImage);
                        draggedEntity.setImage(campfireCardImage);
                        draggedEntity.setImage(towerCardImage);
                        draggedEntity.setImage(trapCardImage);
                        break;
                    case SWORD:
                        draggedEntity.setImage(view.getImage());
                        break;
                    default:
                        draggedEntity.setImage(view.getImage());
                        break;
                }

                draggedEntity.setVisible(true);
                draggedEntity.setMouseTransparent(true);
                draggedEntity.toFront();

                // IMPORTANT!!!
                // to be able to remove event handlers, need to use addEventHandler
                // https://stackoverflow.com/a/67283792
                targetGridPane.addEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_DROPPED,
                        anchorPaneRootSetOnDragDropped.get(draggableType));

                for (Node n : targetGridPane.getChildren()) {
                    // events for entering and exiting are attached to squares children because that
                    // impacts opacity change
                    // these do not affect visibility of original image...
                    // https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
                    gridPaneNodeSetOnDragEntered.put(draggableType, new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType) {
                                // The drag-and-drop gesture entered the target
                                // show the user that it is an actual gesture target
                                if (event.getGestureSource() != n && event.getDragboard().hasImage()) {
                                    n.setOpacity(0.7);
                                }
                            }
                            event.consume();
                        }
                    });
                    gridPaneNodeSetOnDragExited.put(draggableType, new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType) {
                                n.setOpacity(1);
                            }

                            event.consume();
                        }
                    });
                    n.addEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
                    n.addEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
                }
                event.consume();
            }

        });
    }

    /**
     * remove drag event handlers so that we don't process redundant events this is
     * particularly important for slower machines such as over VLAB.
     * 
     * @param draggableType  either cards, or items in unequipped inventory
     * @param targetGridPane the gridpane to remove the drag event handlers from
     */
    private void removeDraggableDragEventHandlers(DRAGGABLE_TYPE draggableType, GridPane targetGridPane) {
        // remove event handlers from nodes in children squares, from anchorPaneRoot,
        // and squares
        targetGridPane.removeEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));

        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_DROPPED, anchorPaneRootSetOnDragDropped.get(draggableType));

        for (Node n : targetGridPane.getChildren()) {
            n.removeEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
            n.removeEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
        }
    }

    /**
     * handle the pressing of keyboard keys. Specifically, we should pause when
     * pressing SPACE
     * 
     * @param event some keyboard key press
     */
    @FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case SPACE:
                if (isPaused) {
                    startTimer();
                } else {
                    pause();
                }
                break;
            default:
                break;
        }
    }

    public void setMainMenuSwitcher(MenuSwitcher mainMenuSwitcher) {
        this.mainMenuSwitcher = mainMenuSwitcher;
    }

    /**
     * this method is triggered when click button to go to main menu in FXML
     * 
     * @throws IOException
     */
    @FXML
    private void switchToMainMenu() throws IOException {
        pause();
        mainMenuSwitcher.switchMenu();
    }

    public void setEndScreenSwitcher(MenuSwitcher endScreenSwitcher) {
        this.endScreenSwitcher = endScreenSwitcher;
    }

    public void setDeathScreenSwitcher(MenuSwitcher deathScreenSwitcher) {
        this.deathScreenSwitcher = deathScreenSwitcher;
    }

    private void switchToEndScreen() {
        pause();
        endScreenSwitcher.switchMenu();
    }

    private void switchToDeathScreen() {
        pause();
        deathScreenSwitcher.switchMenu();
    }

    public void setShopScreenSwitcher(MenuSwitcher shopScreenSwitcher) {
        this.shopScreenSwitcher = shopScreenSwitcher;
    }

    @FXML
    private void switchToShop() throws IOException {
        pause();
        System.out.println("SWITCH TO SHOP");
        shopScreenSwitcher.switchMenu();
    }

    private void statDisplay() {
        statBar.getChildren().clear();

        ImageView heart = new ImageView(heartImage);
        ImageView gold = new ImageView(goldImage);

        /*
         * Label health = new Label(); health.setText(world.getCharacterHpAsString());
         * health.setFont(new Font("Arial", 24));
         * 
         * Label goldCount = new Label();
         * goldCount.setText(world.getCharacterGoldAsString()); goldCount.setFont(new
         * Font("Arial", 24));
         */

        Label xpDisplay = new Label();
        xpDisplay.setText("XP: ");
        xpDisplay.setFont(new Font("Arial", 24));
        xpDisplay.setStyle("-fx-color: green");
        /*
         * Label xpCount = new Label(); xpCount.setText(world.getCharacterXpAsString());
         * xpCount.setFont(new Font("Arial", 24));
         * 
         */
        playerStatsSubject.setCharacterState(world.getCharacterHpAsString(), world.getCharacterGoldAsString(),
                world.getCharacterXpAsString());

        statBar.add(heart, 0, 0);
        statBar.add(healthObserver.getHealth(), 2, 0);

        statBar.add(gold, 0, 2);
        statBar.add(goldObserver.getGold(), 2, 2);

        statBar.add(xpDisplay, 0, 4);
        statBar.add(xpObserver.getXp(), 2, 4);

    }

    /**
     * Set a node in a GridPane to have its position track the position of an entity
     * in the world.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the model
     * will automatically be reflected in the view.
     * 
     * note that this is put in the controller rather than the loader because we
     * need to track positions of spawned entities such as enemy or items which
     * might need to be removed should be tracked here
     * 
     * NOTE teardown functions setup here also remove nodes from their GridPane. So
     * it is vital this is handled in this Controller class
     * 
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());

        ChangeListener<Number> xListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        };
        ChangeListener<Number> yListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        };

        // if need to remove items from the equipped inventory, add code to remove from
        // equipped inventory gridpane in the .onDetach part
        ListenerHandle handleX = ListenerHandles.createFor(entity.x(), node)
                .onAttach((o, l) -> o.addListener(xListener)).onDetach((o, l) -> {
                    o.removeListener(xListener);
                    entityImages.remove(node);
                    squares.getChildren().remove(node);
                    cards.getChildren().remove(node);
                    equippedItems.getChildren().remove(node);
                    unequippedInventory.getChildren().remove(node);
                }).buildAttached();
        ListenerHandle handleY = ListenerHandles.createFor(entity.y(), node)
                .onAttach((o, l) -> o.addListener(yListener)).onDetach((o, l) -> {
                    o.removeListener(yListener);
                    entityImages.remove(node);
                    squares.getChildren().remove(node);
                    cards.getChildren().remove(node);
                    equippedItems.getChildren().remove(node);
                    unequippedInventory.getChildren().remove(node);
                }).buildAttached();
        handleX.attach();
        handleY.attach();

        // this means that if we change boolean property in an entity tracked from here,
        // position will stop being tracked
        // this wont work on character/path entities loaded from loader classes
        entity.shouldExist().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obervable, Boolean oldValue, Boolean newValue) {
                handleX.detach();
                handleY.detach();
            }
        });
    }

    private void goalTracker(Goals goals) {
        goals.getGoal().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    switchToEndScreen();
                }
            }
        });
    }

    /**
     * Listener for cycle to see if it changes
     */
    private void cycleTracker() {
        world.getCycle().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observale, Number oldValue, Number newValue) {
                int cycle = (int) newValue;
                if (world.getBossFactory().checkSpawn(cycle, world.getExpCount())) {
                    System.out.println("WOW LOOK A BOSS SPAWNED OMG");
                    PathPosition position = new PathPosition(world.getBossSpawnPosition(), world.getOrderedPath());
                    Enemy boss = world.getBossFactory().spawnBoss(cycle, world.getExpCount(), position);
                    world.getEnemies().add(boss);
                    onLoad(boss);
                }
            }
        });
    }

    /**
     * Listener for hp to see if it changes
     */
    private void hpTracker() {
        world.getHpIntegerProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observale, Number oldValue, Number newValue) {
                int hp = (int) newValue;
                if (hp <= 0 && !world.getRareItems().contains("the_one_ring")) {
                    switchToDeathScreen();
                } else if (hp <= 0 && world.checkOneRing()) {
                    world.useRareItem("the_one_ring");
                } else if (hp <= 0) {
                    switchToDeathScreen();
                }
            }
        });
    }

    /**
     * Listener for shop to see if it activates
     */
    private void shopTracker(Shop shop) {

        shop.getShopActive().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    try {
                        System.out.println(newValue);
                        switchToShop();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public LoopManiaWorld getWorld() {
        return this.world;
    }

    /**
     * we added this method to help with debugging so you could check your code is
     * running on the application thread. By running everything on the application
     * thread, you will not need to worry about implementing locks, which is outside
     * the scope of the course. Always writing code running on the application
     * thread will make the project easier, as long as you are not running
     * time-consuming tasks. We recommend only running code on the application
     * thread, by using Timelines when you want to run multiple processes at once.
     * EventHandlers will run on the application thread.
     */
    private void printThreadingNotes(String currentMethodLabel) {
        System.out.println("\n###########################################");
        System.out.println("current method = " + currentMethodLabel);
        System.out.println("In application thread? = " + Platform.isFxApplicationThread());
        System.out.println("Current system time = " + java.time.LocalDateTime.now().toString().replace('T', ' '));
        System.out.println("Current player gold = " + world.getGoldCount());
        System.out.println("Current Player exp = " + world.getExpCount());
        System.out.println("Current player damage = " + world.getPlayerAttack());
        System.out.println("Current Cycle = " + world.getCycle());
        System.out.println("Goal quantity: " + world.getQuantity());
        System.out.println("Current player hp = " + world.getPlayerHp());
        System.out.println("Current player defence = " + world.getPlayerDefence());
        System.out.println("Currently difficulty is = " + world.getDifficulty());
    }
}
