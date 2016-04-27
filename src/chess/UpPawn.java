package chess;

import boardgame.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by evan on 4/9/16.
 */
public class UpPawn extends Pawn {
    public UpPawn(String repr, PieceColor color, Position position) {
        super(repr, color, position);
        addGenerator(new UpPawnMoveGenerator());
    }

    /**
     * Created by Evan on 4/11/2016.
     *
     * Generates moves for pawns starting from the bottom of the board (row 1) and moving
     * towards the top of the board. En Passant captures are not included.
     */
    public static class UpPawnMoveGenerator implements IMoveGenerator {
        @Override
        public Set<Move> generate(GameBoard board, Piece piece) {
            Set<Move> moves = new HashSet<>();

            if(!board.pieceAt(piece.getPosition().plus(new Position(0, 1)))) {
                if(!piece.hasMoved())
                    moves.add(new Move(piece.getPosition(), piece.getPosition().plus(new Position(0, 2))));
                moves.add(new Move(piece.getPosition(), piece.getPosition().plus(new Position(0, 1))));
            }

            Position rightCapture = piece.getPosition().plus(new Position(1, 1));
            board.ifPieceAt(rightCapture, pce -> {
                if(!pce.matchesColor(piece))
                    moves.add(new Move(piece.getPosition(), rightCapture, rightCapture));
            });

            Position leftCapture = piece.getPosition().plus(new Position(-1, 1));
            board.ifPieceAt(leftCapture, pce -> {
                if(!pce.matchesColor(piece))
                    moves.add(new Move(piece.getPosition(), leftCapture, leftCapture));
            });

            return moves;
        }
    }
}
