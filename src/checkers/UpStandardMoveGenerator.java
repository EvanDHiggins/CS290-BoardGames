package checkers;

import boardgame.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Evan on 3/2/2016.
 *
 * Defines the standard upward movement of the UpChecker.
 */
public class UpStandardMoveGenerator implements IMoveGenerator {

    @Override
    public Set<Move> generate(GameBoard board, Piece piece) {
        Set<Position> maybeMoves = new HashSet<>();

        Position move1 = new Position(piece.getPosition().getX()-1, piece.getPosition().getY()-1);
        Position move2 = new Position(piece.getPosition().getX()-1, piece.getPosition().getY()+1);

        maybeMoves.add(move1);
        maybeMoves.add(move2);

        return maybeMoves.stream().filter(board::withinBounds)
                                  .filter(pos -> !board.pieceAt(pos))
                                  .map(pos -> new Move(piece.getPosition(), pos))
                                  .collect(Collectors.toSet());
    }
}
