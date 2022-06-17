package unsw.loopmania;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
/*
* exp observer to display in the frontend
*/
public class XpObserver extends Observer{
    private Label xp = new Label();

    public Label getXp() {
        return this.xp;
    }

    public void update(Subject subject) {
        PlayerStatsSubject temp = (PlayerStatsSubject) subject;
        this.xp.setText(temp.getXp());
        this.xp.setFont(new Font("Arial", 24));
    }
}
