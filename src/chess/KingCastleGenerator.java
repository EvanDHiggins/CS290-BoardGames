package chess;

import boardgame.GameBoard;
import boardgame.Move;
import boardgame.Piece;
import boardgame.Position;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Evan on 4/14/2016.
 */
public class KingCastleGenerator extends CastleMoveGenerator {

    @Override
    public Set<Move> generate(GameBoard board, Piece piece) {
        Set<Move> moves = new HashSet<>();
        if(!isCastleValid(board, piece))
            return moves;
        int row = piece.getPosition().getY();
        Move kingMove = new Move(new Position(4, row), new Position(6, row));
        Move rookMove = new Move(new Position(7, row), new Position(5, row));

        moves.add(new DualMove(kingMove, rookMove));
        return moves.stream()
                .filter(mv -> mv.withinBounds(board))
                .collect(Collectors.toSet());
    }
}
