package boardgame;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Created by Evan on 4/27/2016.
 *
 */
public class ObservableStack<T> extends Stack<T> implements MyObservable {

    private Set<MyObserver> observers = new HashSet<>();

    @Override
    public T push(T element) {
        T returnValue = super.push(element);
        notifyObservers();
        return returnValue;
    }

    @Override
    public T pop() {
        T item = super.pop();
        notifyObservers();
        return item;
    }

    @Override
    public void addObserver(MyObserver o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers() {
        for(MyObserver o : observers) {
            o.update(this);
        }
    }

    @Override
    public void removeObserver(MyObserver o) {
        observers.remove(o);
    }
}
