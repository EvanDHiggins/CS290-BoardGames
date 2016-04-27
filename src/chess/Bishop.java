package chess;

import boardgame.Position;

/**
 * Created by evan on 4/8/16.
 *
 * Bishops can move diagonally any number of empty spaces until it encounters
 * a piece. If the piece is owned by another player it can be captured.
 */
public class Bishop extends ChessPiece {
    public Bishop(String repr, PieceColor color, Position position) {
        super(repr, color, position);
        addGenerator(new DiagonalContinuousMoveGen());
    }
}
