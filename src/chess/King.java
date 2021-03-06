package chess;

import boardgame.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by evan on 4/8/16.
 */
public class King extends ChessPiece {
    public King(String repr, PieceColor color, Position position) {
        super(repr, color, position);
        addGenerator(new KingMoveGenerator());
        addGenerator(new CastleMoveGenerator());
    }

    @Override
    public boolean isKing() {
        return true;
    }

    private class KingMoveGenerator implements IMoveGenerator {

        /**
         * IMoveGenerators should always return all moves which are
         * valid based exclusively on any involved piece's capture
         * semantics. It DOES NOT check for the legality of these moves.
         * This function will return any move that a king could make, but
         * will not verify that it is legal. So this will return moves which
         * would place the king in check. Validation of these moves is to be done
         * outside of this function.
         */
        @Override
        public Set<Move> generate(GameBoard board, Piece piece) {
            Set<Move> moves = new HashSet<>();

            for(int i = -1; i <= 1; i++) {
                for(int j = -1; j <= 1; j++) {
                    if(i == 0 && j == 0)
                        continue;
                    Position to = piece.getPosition().plus(new Position(i, j));
                    if(board.pieceAt(to)) {
                        board.getPieceAt(to).ifPresent(pce -> {
                            if(!pce.matchesColor(piece)) {
                                moves.add(new Move(piece.getPosition(), to, to));
                            }
                        });
                    } else {
                        moves.add(new Move(piece.getPosition(), to));
                    }
                }
            }

            return moves.stream()
                    .filter(mv -> mv.withinBounds(board))
                    .collect(Collectors.toSet());
        }
    }
}
