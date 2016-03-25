package checkers;

import boardgame.Position;

/**
 * Created by Evan on 3/2/2016.
 */
public class BlackChecker extends Checker {

    public BlackChecker(Position position) {
        super(position);
        stringRepr = "b";
        color = PieceColor.BLACK;
        addGenerator(new UpStandardMoveGenerator());
        addGenerator(new UpCaptureMoveGenerator());
    }

    @Override
    public void kingMe() {
        addGenerator(new DownCaptureMoveGenerator());
        addGenerator(new UpCaptureMoveGenerator());
    }
}
