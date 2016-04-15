package chess;

import boardgame.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Evan on 4/14/2016.
 */
public abstract class CastleMoveGenerator implements IMoveGenerator {

    /**
     * Determines if the getY that piece is in supports castling.
     * Piece can be either a rook or a king.
     */
    protected boolean isCastleValid(GameBoard board, Piece piece) {
        int row = piece.getPosition().getY();
        Optional<Piece> king = board.getPieceAt(new Position(4, row));
        Optional<Piece> rook = board.getPieceAt(new Position(7, row));

        if(king.map(Piece::hasMoved).orElse(false) || rook.map(Piece::hasMoved).orElse(false))
            return false;

        if(board.pieceAt(new Position(5, row)) || board.pieceAt(new Position(6,row)))
            return false;

        return true;
    }





}
