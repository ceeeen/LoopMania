package unsw.loopmania;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * the main application run main method from this class
 */
public class LoopManiaApplication extends Application {

    /**
     * the controller for the game. Stored as a field so can terminate it when click
     * exit button
     */
    private LoopManiaWorldController mainController;
    private ShopScreenController shopScreenController;
    private ShopSellController shopSellController;
    private BackgroundMusic music;

    private String map = "world_with_twists_and_turns.json";
    private int difficulty = 0;
    private Stage primaryStage;
    private Scene scene;
    private Parent gameRoot = new Parent() {

    };
    private Parent mainMenuRoot;
    private Parent endScreenRoot;
    private Parent difficultyRoot;
    private Parent levelSelectRoot;
    private Parent shopScreenRoot;
    private Parent shopSellRoot;
    private Parent deathScreenRoot;

    public void createNewGame() throws IOException {
        LoopManiaWorldControllerLoader loopManiaLoader = new LoopManiaWorldControllerLoader(map, difficulty);
        mainController = loopManiaLoader.loadController();
        shopScreenController.setWorldController(mainController);
        shopSellController.setWorldController(mainController);
        shopScreenController.startTimer();
        shopSellController.startTimer();
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("LoopManiaView.fxml"));
        gameLoader.setController(mainController);
        gameRoot = gameLoader.load();
        mainController.setMainMenuSwitcher(() -> {
            playMenuMusic();
            switchToRoot(scene, mainMenuRoot, primaryStage);
        });
        mainController.setEndScreenSwitcher(() -> {
            switchToRoot(scene, endScreenRoot, primaryStage);
        });
        mainController.setShopScreenSwitcher(() -> {
            switchToRoot(scene, shopScreenRoot, primaryStage);
        });
        mainController.setDeathScreenSwitcher(() -> {
            switchToRoot(scene, deathScreenRoot, primaryStage);
        });

    }

    public void endGame() throws IOException {
        if (mainController != null) {
            mainController = null;
        }
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        music = new BackgroundMusic();
        music.play(music.getmenuMusic());
        // set title on top of window bar
        primaryStage.setTitle("Loop Mania");
        primaryStage.setResizable(false);
        this.primaryStage = primaryStage;
        // load the main menu
        MainMenuController mainMenuController = new MainMenuController();
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("MainMenuView.fxml"));
        menuLoader.setController(mainMenuController);
        mainMenuRoot = menuLoader.load();
        gameRoot = mainMenuRoot;

        // load the difficulty screen
        DifficultyController difficultyController = new DifficultyController();
        FXMLLoader difficultyLoader = new FXMLLoader(getClass().getResource("DifficultyView.fxml"));
        difficultyLoader.setController(difficultyController);
        difficultyRoot = difficultyLoader.load();

        // load the level select screen
        LevelSelectController levelSelectController = new LevelSelectController();
        FXMLLoader levelSelectLoader = new FXMLLoader(getClass().getResource("levelSelectScreen.fxml"));
        levelSelectLoader.setController(levelSelectController);
        levelSelectRoot = levelSelectLoader.load();

        // load the main game

        scene = new Scene(mainMenuRoot);

        // load the death screen
        DeathScreenController deathScreenController = new DeathScreenController();
        FXMLLoader deathScreenLoader = new FXMLLoader(getClass().getResource("DeathScreenView.fxml"));
        deathScreenLoader.setController(deathScreenController);
        deathScreenRoot = deathScreenLoader.load();

        // load the end screen
        EndScreenController endScreenController = new EndScreenController();
        FXMLLoader endScreenLoader = new FXMLLoader(getClass().getResource("EndScreenView.fxml"));
        endScreenLoader.setController(endScreenController);
        endScreenRoot = endScreenLoader.load();
        // shop
        ShopScreenController shopScreenController = new ShopScreenController();
        this.shopScreenController = shopScreenController;
        FXMLLoader shopScreenLoader = new FXMLLoader(getClass().getResource("ShopScreenView3.fxml"));
        shopScreenLoader.setController(shopScreenController);
        shopScreenController.startTimer();
        shopScreenRoot = shopScreenLoader.load();
        // shop sell
        shopSellController = new ShopSellController();
        // this.shopSellController = shopSellController;
        FXMLLoader shopSellLoader = new FXMLLoader(getClass().getResource("ShopScreenView4.fxml"));
        shopSellLoader.setController(shopSellController);
        shopSellController.startTimer();
        shopSellRoot = shopSellLoader.load();

        // create new scene with the main menu (so we start with the main menu)

        // set functions which are activated when button click to switch menu is pressed
        // e.g. from main menu to start the game, or from the game to return to main
        // menu

        // createNewGame();

        mainMenuController.setGameSwitcher(() -> {
            playGameMusic();
            switchToRoot(scene, gameRoot, primaryStage);
            if (mainController != null) {
                mainController.startTimer();
            }
        });
        mainMenuController.setDifficultySwitcher(() -> {
            switchToRoot(scene, difficultyRoot, primaryStage);
        });
        difficultyController.setGameSwitcher(() -> {
            switchToRoot(scene, levelSelectRoot, primaryStage);
        });
        levelSelectController.setGameSwitcher(() -> {
            setDifficulty(difficultyController.getDifficulty());
            setMap(levelSelectController.getMap());
            playGameMusic();
            try {
                createNewGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
        });
        endScreenController.setGameSwitcher(() -> {
            playMenuMusic();
            switchToRoot(scene, mainMenuRoot, primaryStage);
            try {
                endGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        deathScreenController.setGameSwitcher(() -> {
            playMenuMusic();
            switchToRoot(scene, mainMenuRoot, primaryStage);
            try {
                endGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        shopScreenController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
        });
        shopScreenController.setSellSwitcher(() -> {
            switchToRoot(scene, shopSellRoot, primaryStage);
        });
        shopSellController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
        });
        shopSellController.setBuySwitcher(() -> {
            switchToRoot(scene, shopScreenRoot, primaryStage);
        });

        // deploy the main onto the stage
        gameRoot.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        // wrap up activities when exit program
        mainController.terminate();
    }

    /**
     * switch to a different Root
     */
    private void switchToRoot(Scene scene, Parent root, Stage stage) {
        scene.setRoot(root);
        root.requestFocus();
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    private void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    private void setMap(String map) {
        this.map = map;
    }

    private void playMenuMusic() {
        music.play(music.getmenuMusic());
    }

    private void playGameMusic() {
        music.play(music.getgameMusic());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
