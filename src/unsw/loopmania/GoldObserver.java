package unsw.loopmania;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
/*
* Observer class for gold to display in frontend
*/
public class GoldObserver extends Observer{
    private Label gold = new Label();

    public Label getGold() {
        return this.gold;
    }

    public void update(Subject subject) {
        PlayerStatsSubject temp = (PlayerStatsSubject) subject;
        this.gold.setText(temp.getGold());
        this.gold.setFont(new Font("Arial", 24));
    }
}
