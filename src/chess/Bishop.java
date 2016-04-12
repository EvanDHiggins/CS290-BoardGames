package chess;

import boardgame.Position;

/**
 * Created by evan on 4/8/16.
 */
public class Bishop extends ChessPiece {
    public Bishop(char repr, PieceColor color, Position position) {
        super(repr, color, position);
        addGenerator(new DiagonalMoveGenerator());
    }
}
