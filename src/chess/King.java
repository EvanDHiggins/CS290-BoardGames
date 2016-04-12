package chess;

import boardgame.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by evan on 4/8/16.
 */
public class King extends ChessPiece {
    public King(char repr, PieceColor color, Position position) {
        super(repr, color, position);
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
                    board.ifPieceAt(pos, pce -> {
                        if(!pce.matchesColor(piece))
                            moves.add(new Move(piece.getPosition(), piece.getPosition().plus(pos)));
                    });
                }
            }

            return moves;
        }
    }
}
