package unsw.loopmania;

import java.util.ArrayList;
/*
* Displays the player's current stats to the frontend
*/
public class PlayerStatsSubject extends Subject{
    private ArrayList<Observer> observers = new ArrayList<>();
    private String health;
    private String gold;
    private String xp;

    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    public String getHealth() {
        return this.health;
    }

    public String getGold() {
        return this.gold;
    }

    public String getXp() {
        return this.xp;
    }

    public void setCharacterState(String health, String gold, String xp) {
        this.health = health;
        this.gold = gold;
        this.xp = xp;
        notifyObservers();
    }

    public void notifyObservers() { {}
        for (Observer o : observers) {
            o.update(this);
        }
    }

}
