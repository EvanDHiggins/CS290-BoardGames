package boardgame;

import java.util.Optional;

/**
 * Created by Evan on 2/29/2016.
 */
public abstract class GameBoard {

    protected int boardSize;

    protected Tile[][] board;

    protected GameBoard(int size) {
        this.boardSize = size;
    }

    public boolean positionInBounds(Position position) {
        return position.row() > 0 && position.row() < boardSize &&
               position.column() > 0 && position.column() < boardSize;
    }

    public boolean pieceAt(Position position) {
        if(!positionInBounds(position))
            return false;
        return !board[position.row()][position.column()].isBlank();
    }

    public Optional<Piece> getPieceAt(Position position) {
        if(!positionInBounds(position))
            throw new ArrayIndexOutOfBoundsException("Position not on board.");
        return board[position.row()][position.column()].getPiece();
    }
}
