package checkers;

import boardgame.Position;

/**
 * Created by Evan on 3/2/2016.
 */
public class DownChecker extends Checker {

    public DownChecker(PieceColor color, Position position) {
        super(color, position);
        stringRepr = "r";
        addGenerator(new DownStandardMoveGenerator());
        addGenerator(new DownCaptureMoveGenerator());
    }

    @Override
    public void kingMe() {
        stringRepr = stringRepr.toUpperCase();
        if(!kinged) {
            addGenerator(new UpStandardMoveGenerator());
            addGenerator(new UpCaptureMoveGenerator());
        }
        kinged = true;
    }
}
