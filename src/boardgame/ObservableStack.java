package boardgame;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Created by Evan on 4/27/2016.
 */
public class ObservableStack<T> implements MyObservable {

    private Stack<T> stack = new Stack<>();

    private Set<MyObserver> observers = new HashSet<>();

    public int size() {
        return stack.size();
    }

    public void push(T element) {
        stack.push(element);
        notifyObservers();
    }

    public T pop() {
        T item = stack.pop();
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
            o.update(this, stack);
        }
    }

    @Override
    public void removeObserver(MyObserver o) {
        observers.remove(o);
    }
}
