package chess;

import boardgame.Position;

/**
 * Created by evan on 4/8/16.
 */
public class Rook extends ChessPiece {
    public Rook(String repr, PieceColor color, Position position) {
        super(repr, color, position);
        addGenerator(new LinearContinuousMoveGen());
    }
}
