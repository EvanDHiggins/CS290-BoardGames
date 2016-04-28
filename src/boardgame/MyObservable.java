package boardgame;

/**
 * Created by Evan on 4/27/2016.
 *
 * Because the builtin Observable is a class rather than an interface I couldn't let a
 * subclass of something else be observable. So I rolled my own, albeit with slightly
 * different semantics. There's not concept of a "changed" flag. It just calls notify on
 * any change.
 */
public interface MyObservable {
    void addObserver(MyObserver o);
    void notifyObservers();
    void removeObserver(MyObserver o);
}
