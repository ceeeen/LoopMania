package unsw.loopmania;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
/*
* Health Point observer to display on frontend
*/
public class HealthObserver extends Observer{

    private Label health = new Label();

    public Label getHealth() {
        return this.health;
    }

    public void update(Subject subject) {
        PlayerStatsSubject temp = (PlayerStatsSubject) subject;
        this.health.setText(temp.getHealth());
        this.health.setFont(new Font("Arial", 24));
    }
}
