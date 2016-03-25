package checkers;

import boardgame.Position;

/**
 * Created by Evan on 3/2/2016.
 */
public class UpChecker extends Checker {

    public UpChecker(PieceColor color, Position position) {
        super(color, position);
        stringRepr = "b";
        addGenerator(new UpStandardMoveGenerator());
        addGenerator(new UpCaptureMoveGenerator());
    }

    @Override
    public void kingMe() {
        stringRepr = stringRepr.toUpperCase();
        if(!kinged) {
            addGenerator(new DownCaptureMoveGenerator());
            addGenerator(new DownStandardMoveGenerator());
        }
        kinged = true;
    }
}
