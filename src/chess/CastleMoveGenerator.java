package chess;

import boardgame.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Evan on 4/14/2016.
 */
public class CastleMoveGenerator implements IMoveGenerator {

    /**
     * Designed to be attached to a King piece. Generates a DualMove object
     * which contains the execution semantics of the castling action.
     */
    @Override
    public Set<Move> generate(GameBoard board, Piece piece) {
        Set<Move> moves = new HashSet<>();
        if(!isCastleValid(board, piece))
            return moves;
        int row = piece.getPosition().getY();
        Move kingMove = new Move(new Position(4, row), new Position(6, row));
        Move rookMove = new Move(new Position(7, row), new Position(5, row));

        moves.add(new DualMove(kingMove, rookMove));
        moves =  moves.stream()
                .filter(mv -> mv.withinBounds(board))
                .collect(Collectors.toSet());
        return moves;
    }

    /**
     * Determines if the row that piece is in supports castling.
     *
     * Castling is valid when
     */
    protected boolean isCastleValid(GameBoard board, Piece piece) {
        int row = piece.getPosition().getY();
        Optional<Piece> king = board.getPieceAt(new Position(4, row));
        Optional<Piece> rook = board.getPieceAt(new Position(7, row));

        if(king.map(Piece::hasMoved).orElse(false) || rook.map(Piece::hasMoved).orElse(false)) {
            return false;
        }

        if(board.pieceAt(new Position(5, row)) || board.pieceAt(new Position(6,row))) {
            return false;
        }
        return true;
    }





}
