package checkers;

import boardgame.Position;

/**
 * Created by Evan on 3/2/2016.
 */
public class RedChecker extends Checker {

    public RedChecker(Position position) {
        super(position);
        stringRepr = "r";
        color = PieceColor.RED;
        addGenerator(new DownStandardMoveGenerator());
        addGenerator(new DownCaptureMoveGenerator());
    }

}
