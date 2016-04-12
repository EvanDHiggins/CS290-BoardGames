package chess;

import boardgame.Position;

/**
 * Created by evan on 4/8/16.
 */
public class Queen extends ChessPiece {

    public Queen(char repr, PieceColor color, Position position) {
        super(repr, color, position);
        addGenerator(new LinearContinuousMoveGen());
        addGenerator(new DiagonalContinuousMoveGen());
    }
}
