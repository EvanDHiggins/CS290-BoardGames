package chess;

import boardgame.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Evan on 4/11/2016.
 *
 * This class implements a single method which is used in iterative move generators
 * such as that required of a bishop, rook, or queen.
 */
public abstract class IterativeMoveGenerator implements IMoveGenerator {

    /**
     * Starting from piece.getPosition()+step and determine if that Position
     * is a valid move for piece with chess capture semantics. If the space is empty
     * the move is valid. If the space has a piece then it is a valid move if the piece
     * contained is an opponent of piece.
     *
     * This process is iteratively repeated adding step to the current position each time.
     *
     * Once a piece is encountered or the currentPosition
     * is out of bounds the function returns the set of moves generated.
     * @param piece Piece we are locating moves for
     * @param step Directional step to iterate with
     * @return
     */
    protected Set<Move> findMovesInDirection(GameBoard board, Piece piece, Position step) {
        Set<Move> moves = new HashSet<>();

        Position currentPosition = piece.getPosition().plus(step);

        while(board.withinBounds(currentPosition)) {
            Position lambdaPos = currentPosition;
            if(board.pieceAt(currentPosition)) {
                board.getPieceAt(currentPosition).map(p -> {
                    if(!p.matchesColor(piece))
                        moves.add(new Move(piece.getPosition(), lambdaPos, lambdaPos));
                    return null;
                });
                break;
            }
            moves.add(new Move(piece.getPosition(), currentPosition));
            currentPosition = currentPosition.plus(step);
        }

        return moves;
    }
}
