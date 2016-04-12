package chess;

import boardgame.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Evan on 4/11/2016.
 */
public class DownPawn extends Pawn {

    public DownPawn(char repr, PieceColor color, Position position) {
        super(repr, color, position);
        addGenerator(new DownPawnMoveGenerator());
    }

    /**
     * Created by Evan on 4/11/2016.
     */
    public static class DownPawnMoveGenerator implements IMoveGenerator {
        @Override
        public Set<Move> generate(GameBoard board, Piece piece) {
            Set<Move> moves = new HashSet<>();

            if(!board.pieceAt(piece.getPosition().plus(new Position(0, -1)))) {
                if(!piece.hasMoved())
                    moves.add(new Move(piece.getPosition(), piece.getPosition().plus(new Position(0, -2))));
                moves.add(new Move(piece.getPosition(), piece.getPosition().plus(new Position(0, -1))));
            }

            Position rightCapture = piece.getPosition().plus(new Position(1, 1));
            board.ifPieceAt(rightCapture, pce -> {
                if(!pce.matchesColor(piece))
                    moves.add(new Move(piece.getPosition(), rightCapture, rightCapture));
            });

            Position leftCapture = piece.getPosition().plus(new Position(-1, -1));
            board.ifPieceAt(leftCapture, pce -> {
                if(!pce.matchesColor(piece))
                    moves.add(new Move(piece.getPosition(), leftCapture, leftCapture));
            });

            return moves;
        }
    }
}
