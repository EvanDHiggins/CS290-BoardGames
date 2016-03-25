package checkers;

import boardgame.Piece;
import boardgame.Position;

/**
 * Created by Evan on 3/2/2016.
 */
public abstract class Checker extends Piece {

    protected boolean kinged = false;

    protected Checker(PieceColor color, Position position) {
        super(color, position);
    }

    public boolean isKinged() {
        return kinged;
    }

    public abstract void kingMe();
}
