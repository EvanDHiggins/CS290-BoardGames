package boardgame;

import java.util.Set;

/**
 * Created by Evan on 3/2/2016.
 *
 * A Piece typically has several IMoveGenerators which define its total move set.
 * For instance, a standard upward moving checker will have two move generators, one
 * for standard moves and one for upward jumps. A King will then have 4 IMoveGenerators:
 * Two defining standard Up/Down and two defining jump up/down moves.
 */
public interface IMoveGenerator {

    /**
     * Returns a set of available moves given a board and a piece on that
     * board.
     */
    Set<Move> generate(GameBoard board, Piece piece);
}
