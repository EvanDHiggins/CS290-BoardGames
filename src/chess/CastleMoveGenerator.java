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

    @Override
    public Set<Move> generate(GameBoard board, Piece piece) {
        Set<Move> moves = new HashSet<>();
        if(!isCastleValid(board, piece))
            return moves;
        int row = piece.getPosition().getY();
        Move kingMove = new Move(new Position(4, row), new Position(6, row));
        Move rookMove = new Move(new Position(7, row), new Position(5, row));

        moves.add(new DualMove(kingMove, rookMove));
        System.out.println("There are " + moves.size() + " moves in the castling set.");
        moves =  moves.stream()
                .filter(mv -> mv.withinBounds(board))
                .collect(Collectors.toSet());
        System.out.println("There are " + moves.size() + " moves in the castling set after filtering..");
        return moves;
    }

    /**
     * Determines if the getY that piece is in supports castling.
     * Piece can be either a rook or a king.
     */
    protected boolean isCastleValid(GameBoard board, Piece piece) {
        int row = piece.getPosition().getX();
        Optional<Piece> king = board.getPieceAt(new Position(4, row));
        Optional<Piece> rook = board.getPieceAt(new Position(7, row));

        if(king.map(Piece::hasMoved).orElse(false) || rook.map(Piece::hasMoved).orElse(false)) {
            System.out.println("The rook or the king has moved.");
            return false;
        }

        System.out.println("row = " + row);
        System.out.println(board.pieceAt(new Position(5, row)));
        System.out.println(board.getPieceAt(new Position(5, row)));
        if(board.pieceAt(new Position(5, row)) || board.pieceAt(new Position(6,row))) {
            System.out.println("There is a piece in the way.");
            return false;
        }
        System.out.println("Castling is valid.");
        return true;
    }





}
