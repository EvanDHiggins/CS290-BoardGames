package chess;

import boardgame.Piece;

/**
 * Created by evan on 4/1/16.
 */
public abstract class ChessPiece extends Piece {

    private String repr;

    public ChessPiece(char repr) {
        this.repr = Character.toString(repr);
    }
}
