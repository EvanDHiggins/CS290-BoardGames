package checkers;

import boardgame.Position;
import javafx.geometry.Pos;

/**
 * Created by Evan on 3/2/2016.
 */
public class BlackChecker extends Checker {

    public BlackChecker() {
        super();
        stringRepr = "b";
    }

    public BlackChecker(Position position) {
        super(position);
        stringRepr = "b";
        addGenerator(new UpStandardMoveGenerator());
    }
}
