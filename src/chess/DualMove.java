package chess;

import boardgame.GameBoard;
import boardgame.Move;
import boardgame.Piece;

import java.util.HashSet;
import java.util.Set;

/**
 * Composition of two sequentially executed moves. I did not make an arbitrarily
 * composable class because there are more design decisions involved and YAGNI.
 */
public class DualMove extends Move {

    private Move firstMove;
    private Move secondMove;

    /**
     * The firstMove argument is the identity of the Move. Meaning
     * an object of type Move m equals an object of type DualMove d if
     * d.firstMove.equals(m). This is established through the super
     * constructor. DualMove no longer pays attention to those values
     * afterwards.
     */
    public DualMove(Move firstMove, Move secondMove) {
        super(firstMove.getFrom(), firstMove.getTo());
        this.firstMove = firstMove;
        this.secondMove = secondMove;
    }

    /**
     * It is up to the creator of a DualMove to ensure that firstMove
     * and secondMove won't conflict. Given that they are both valid
     * Moves then the execution isn't expected to fail, but if determined
     * incorrectly by the creator the moves might conflict with each other.
     * @param board the board which is being operated on.
     */
    @Override
    public Set<Piece> execute(GameBoard board) {
        Set<Piece> capturedPieces = new HashSet<>();
        capturedPieces.addAll(firstMove.execute(board));
        capturedPieces.addAll(secondMove.execute(board));
        return capturedPieces;
    }

    @Override
    public void unexecute(GameBoard board) {
        secondMove.unexecute(board);
        firstMove.unexecute(board);
    }

    @Override
    public boolean withinBounds(GameBoard board) {
        return firstMove.withinBounds(board) && secondMove.withinBounds(board);
    }

    @Override
    public String toString() {
        return "Dual Move:" + '\n'
             + "     firstMove: " + firstMove.toString() + '\n'
             + "    secondMove: " + secondMove.toString() + '\n';
    }
}
