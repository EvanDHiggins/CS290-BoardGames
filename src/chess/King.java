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
                    .filter(mv -> !inCheckAfterMove(piece, mv, board))
                    .collect(Collectors.toSet());
        }

        /**
         * Returns true if piece could be captured if it were to make move.
         * @param king
         * @param move
         * @return
         */
        private boolean inCheckAfterMove(Piece king, Move move, GameBoard board) {
            move.execute(board);
            Set<Piece> opponentPieces = board.getAllPieces().stream()
                                                                .filter(p -> !p.matchesColor(king))
                                                                .collect(Collectors.toSet());
            for(Piece p : opponentPieces) {
                Set<Move> moves = p.generateMoves(board);
                if(moves.stream().anyMatch(mv -> mv.capturesAt(king.getPosition()))) {
                    move.unexecute(board);
                    return true;
                }
            }
            move.unexecute(board);
            return false;
        }
    }
}
