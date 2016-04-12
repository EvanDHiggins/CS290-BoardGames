package chess;

import boardgame.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by evan on 4/8/16.
 */
public class King extends ChessPiece {
    public King(char repr, PieceColor color, Position position) {
        super(repr, color, position);
        addGenerator(new KingMoveGenerator());
    }

    private class KingMoveGenerator implements IMoveGenerator {

        @Override
        public Set<Move> generate(GameBoard board, Piece piece) {
            Set<Move> moves = new HashSet<>();

            for(int i = -1; i <= 1; i++) {
                for(int j = -1; j <= 1; j++) {
                    if(i != 0 || j != 0)
                        continue;
                    Position pos = new Position(i, j);
                    if(board.pieceAt(pos)) {
                        board.getPieceAt(pos).map(pce -> {
                            if(!pce.matchesColor(piece)) {
                                Position to = piece.getPosition().plus(pos);
                                moves.add(new Move(piece.getPosition(), to, to));
                            }
                            return null;
                        });
                    } else {
                        moves.add(new Move(piece.getPosition(), piece.getPosition().plus(pos)));
                    }
                }
            }

            return moves.stream()
                    .filter(mv -> board.withinBounds(mv.getTo()))
                    .filter(mv -> !inCheckAfter(piece, mv))
                    .collect(Collectors.toSet());
        }

        /**
         * Returns true if piece could be captured if it were to make move.
         * @param piece
         * @param move
         * @return
         */
        private boolean inCheckAfter(Piece piece, Move move) {
            return false;
        }
    }
}
