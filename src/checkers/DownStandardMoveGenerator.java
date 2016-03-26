package checkers;

import boardgame.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by evan on 3/21/16.
 *
 * Defines the standard movement of the DownChecker.
 */
public class DownStandardMoveGenerator implements IMoveGenerator {

    @Override
    public Set<Move> generate(GameBoard board, Piece piece) {
        Set<Position> maybeMoves = new HashSet<>();

        Position move1 = new Position(piece.getPosition().row()+1, piece.getPosition().column()-1);
        Position move2 = new Position(piece.getPosition().row()+1, piece.getPosition().column()+1);

        maybeMoves.add(move1);
        maybeMoves.add(move2);

        return maybeMoves.stream().filter(board::withinBounds)
                .filter(pos -> !board.pieceAt(pos))
                .map(pos -> new Move(piece.getPosition(), pos))
                .collect(Collectors.toSet());
    }
}
