package chess;

import boardgame.GameBoard;
import boardgame.Move;
import boardgame.Piece;
import boardgame.Position;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Evan on 4/11/2016.
 */
public class DiagonalMoveGenerator extends IterativeMoveGenerator {
    @Override
    public Set<Move> generate(GameBoard board, Piece piece) {
        Set<Move> ret = new HashSet<>();

        ret.addAll(findMovesInDirection(board, piece, new Position(1, 1)));
        ret.addAll(findMovesInDirection(board, piece, new Position(-1, -1)));
        ret.addAll(findMovesInDirection(board, piece, new Position(-1, 1)));
        ret.addAll(findMovesInDirection(board, piece, new Position(1, -1)));

        return ret;
    }
}
