package hex;

import boardgame.Piece;

/**
 * Created by evan on 3/21/16.
 */
public class HexPiece extends Piece {

    static HexPiece createPiece(PieceColor color) {
        if(color == PieceColor.WHITE) {
            return new WhiteHexPiece();
        } else if(color == PieceColor.BLACK) {
            return new BlackHexPiece();
        }
        throw new IllegalArgumentException("Invalid Hex Piece color");
    }
}
