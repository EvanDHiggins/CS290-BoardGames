package chess;

import boardgame.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by evan on 4/9/16.
 *
 * Generates moves corresponding to vertical and horizontal movement
 * until a piece or the end of the board is encountered (such as rooks in chess).
 * If the encountered piece is an opposing piece it is marked as a capture.
 */
public class LinearContinuousMoveGen extends ContinuousMoveGenerator {
    @Override
    public Set<Move> generate(GameBoard board, Piece piece) {
        Set<Move> ret = new HashSet<>();

        ret.addAll(findMovesInDirection(board, piece, new Position(0, 1)));
        ret.addAll(findMovesInDirection(board, piece, new Position(0, -1)));
        ret.addAll(findMovesInDirection(board, piece, new Position(-1, 0)));
        ret.addAll(findMovesInDirection(board, piece, new Position(1, 0)));

        return ret;
    }

}
