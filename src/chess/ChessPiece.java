package chess;

import boardgame.Piece;
import boardgame.Position;

/**
 * Created by evan on 4/1/16.
 */
public abstract class ChessPiece extends Piece {

    public ChessPiece(char repr, PieceColor color, Position position) {
        super(color, position);
        this.stringRepr = Character.toString(repr);
    }
}
