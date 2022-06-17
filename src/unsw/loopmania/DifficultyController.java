package unsw.loopmania;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Controller for the difficulty menu
 */
public class DifficultyController {
    /**
     * facilitates switching to main game
     */
    private MenuSwitcher levelSelectSwitcher;
    private int difficulty = 0;

    @FXML
    private Button basicButton;
    @FXML
    private Button survivalButton;
    @FXML
    private Button berserkerButton;
    @FXML
    private Button confusingButton;

    public void setGameSwitcher(MenuSwitcher levelSelectSwitcher) {
        this.levelSelectSwitcher = levelSelectSwitcher;
    }

    public int getDifficulty() {
        return difficulty;
    }

    /**
     * facilitates switching to main game upon button click
     * 
     * @throws IOException
     */
    @FXML
    private void switchToBasicGame() throws IOException {
        difficulty = 0;
        levelSelectSwitcher.switchMenu();
    }

    @FXML
    private void switchToSurvivalGame() throws IOException {
        difficulty = 1;
        levelSelectSwitcher.switchMenu();
    }

    @FXML
    private void switchToBerserkerGame() throws IOException {
        difficulty = 2;
        levelSelectSwitcher.switchMenu();
    }

    @FXML
    private void switchToConfusingGame() throws IOException {
        difficulty = 3;
        levelSelectSwitcher.switchMenu();
    }

    public int getSelectedDifficulty() {
        return difficulty;
    }

}
