package unsw.loopmania;

import java.io.IOException;

import javafx.fxml.FXML;
/*
* Controller for the endscreen
*/
public class EndScreenController {
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
