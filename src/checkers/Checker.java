package checkers;

import boardgame.Piece;
import boardgame.Position;

/**
 * Created by Evan on 3/2/2016.
 */
public abstract class Checker extends Piece {

    protected Checker() {
        super();
    }

    protected Checker(Position position) {
        super(position);
    }

}
