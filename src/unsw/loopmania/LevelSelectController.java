package unsw.loopmania;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
/*
* Controller to select which level
*/
public class LevelSelectController {

    private MenuSwitcher gameSwitcher;
    private String map;

    @FXML
    private TextField mapName;

    public void setGameSwitcher(MenuSwitcher gameSwitcher) {
        this.gameSwitcher = gameSwitcher;
    }

    @FXML
    private void switchToGame() throws IOException {
        map = mapName.getText() + ".json";
        gameSwitcher.switchMenu();
    }

    public String getMap() {
        return map;
    }

}
