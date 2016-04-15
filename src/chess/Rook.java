package chess;

import boardgame.Position;

/**
 * Created by evan on 4/8/16.
 */
public class Rook extends ChessPiece {
    public Rook(char repr, PieceColor color, Position position) {
        super(repr, color, position);
        addGenerator(new LinearContinuousMoveGen());
        addGenerator(new RookCastleGenerator());
    }
}
