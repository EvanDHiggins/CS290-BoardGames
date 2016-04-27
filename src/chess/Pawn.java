package chess;

import boardgame.Position;

/**
 * Created by evan on 4/8/16.
 */
public abstract class Pawn extends ChessPiece {

    public Pawn(String repr, PieceColor color, Position position) {
        super(repr, color, position);
    }
}
