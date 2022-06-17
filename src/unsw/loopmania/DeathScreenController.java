package unsw.loopmania;

import java.io.IOException;

import javafx.fxml.FXML;
/*
* Controller for the screen when the character dies
*/
public class DeathScreenController {
    /**
     * facilitates switching to main game
     */
    private MenuSwitcher mainMenuSwitcher;

    public void setGameSwitcher(MenuSwitcher mainMenuSwitcher) {
        this.mainMenuSwitcher = mainMenuSwitcher;
    }

    /**
     * facilitates switching to main game upon button click
     * 
     * @throws IOException
     */
    @FXML
    private void switchToMenu() throws IOException {
        mainMenuSwitcher.switchMenu();
    }
}
