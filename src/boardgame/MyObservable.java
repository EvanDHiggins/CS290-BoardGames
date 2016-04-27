package boardgame;

import java.util.Observer;

/**
 * Created by Evan on 4/27/2016.
 */
public interface MyObservable {
    void addObserver(MyObserver o);
    void notifyObservers();
    void removeObserver(MyObserver o);
}
