package checkers;

import boardgame.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by evan on 3/21/16.
 *
 * Specifies all captures (jumps) moving down the board from row 8 towards row 1.
 */
public class DownCaptureMoveGenerator implements IMoveGenerator {

    @Override
    public Set<Move> generate(GameBoard board, Piece piece) {
        Set<Move> maybeMoves = new HashSet<>();

        Position transform1 = new Position(1, -1);
        Position transform2 = new Position(1, 1);

        Position to1 = piece.getPosition().plus(transform1).plus(transform1);
        Position to2 = piece.getPosition().plus(transform2).plus(transform2);

        Position capture1 = piece.getPosition().plus(transform1);
        Position capture2 = piece.getPosition().plus(transform2);

        Move move1 = new Move(piece.getPosition(), to1);
        Move move2 = new Move(piece.getPosition(), to2);

        move1.setCapture(capture1);
        move2.setCapture(capture2);

        maybeMoves.add(move1);
        maybeMoves.add(move2);

        return maybeMoves.stream().filter(mv -> board.withinBounds(mv.getTo()))
                                  .filter(mv -> !board.pieceAt(mv.getTo()))
                                  .filter(mv -> mv.getCapture().map(board::pieceAt).orElse(false))
                                  .filter(mv -> mv.getCapture()
                                                  .map(p -> !board.tileAt(p).pieceMatchesColor(piece)).orElse(false))
                                  .collect(Collectors.toSet());

    }
}
