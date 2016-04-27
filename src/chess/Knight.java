package chess;

import boardgame.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by evan on 4/8/16.
 */
public class Knight extends ChessPiece {
    public Knight(String repr, PieceColor color, Position position) {
        super(repr, color, position);
        addGenerator(new KnightMoveGenerator());
    }

    private class KnightMoveGenerator implements IMoveGenerator {
        /**
         * The set of moves for a knight is the knight's current position shifted by all permutations
         * of pairs (x, y) of the set {-2, -1, 1, 2} where x != y (8 positions in total). Then any positions
         * which are out of bounds or contain a
         * @param board
         * @param piece
         * @return
         */
        @Override
        public Set<Move> generate(GameBoard board, Piece piece) {
            Set<Move> moves = new HashSet<>();

            for(int x = 1; x < 3; x++) {
                int y = 3 - Math.abs(x);
                moves.add(new Move(piece.getPosition(), piece.getPosition().plus(new Position(x, y))));
                moves.add(new Move(piece.getPosition(), piece.getPosition().plus(new Position(x, -y))));
                moves.add(new Move(piece.getPosition(), piece.getPosition().plus(new Position(-x, y))));
                moves.add(new Move(piece.getPosition(), piece.getPosition().plus(new Position(-x, -y))));
            }

            //Finds all moves whose "to" is a valid capture and sets their capture appropriately.
            moves = moves.stream().map(mv -> {
                board.ifPieceAt(mv.getTo(), pce -> {
                    if(!pce.matchesColor(piece))
                        mv.setCapture(mv.getTo());
                });
                return mv;
            }).collect(Collectors.toSet());

            return moves.stream()
                    .filter(mv -> board.withinBounds(mv.getTo()))
                    .filter(mv -> board.getPieceAt(mv.getTo()).map(p -> !p.matchesColor(piece)).orElse(true))
                    .collect(Collectors.toSet());
        }
    }
}
