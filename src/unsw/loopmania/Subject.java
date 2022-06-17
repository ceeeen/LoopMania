package unsw.loopmania;
/*
* Abstract class for subject that is used in the observer pattern
*/
public abstract class Subject {
    
    public abstract void subscribe(Observer observer);

    public abstract void unsubscribe(Observer observer);

    public abstract void notifyObservers();
}
