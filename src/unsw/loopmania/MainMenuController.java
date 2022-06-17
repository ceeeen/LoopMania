package unsw.loopmania;

import java.io.IOException;
import javafx.fxml.FXML;

/**
 * controller for the main menu. 
 */
public class MainMenuController {
    /**
     * facilitates switching to main game
     */
    private MenuSwitcher gameSwitcher;
    private MenuSwitcher difficultySwitcher;

    public void setGameSwitcher(MenuSwitcher gameSwitcher) {
        this.gameSwitcher = gameSwitcher;
    }

    public void setDifficultySwitcher(MenuSwitcher difficultySwitcher) {
        this.difficultySwitcher = difficultySwitcher;
    }

    /**
     * facilitates switching to main game upon button click
     * 
     * @throws IOException
     */
    @FXML
    private void switchToGame() throws IOException {
        gameSwitcher.switchMenu();
    }

    @FXML
    private void switchToDifficulty() throws IOException {
        difficultySwitcher.switchMenu();
    }

}
