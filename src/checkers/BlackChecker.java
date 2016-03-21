package checkers;

import boardgame.Position;

/**
 * Created by Evan on 3/2/2016.
 */
public class BlackChecker extends Checker {

//    public BlackChecker() {
//        super();
//        stringRepr = "b";
//        color = PieceColor.BLACK;
//    }

    public BlackChecker(Position position) {
        super(position);
        stringRepr = "b";
        color = PieceColor.BLACK;
        addGenerator(new UpStandardMoveGenerator());
    }
}
