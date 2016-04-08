package boardgame;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Evan on 2/29/2016.
 */
public abstract class GameBoard {

    protected int boardSize;

    protected Tile[][] board;

    protected GameBoard(int size) {
        this.boardSize = size;
        board = new Tile[this.getSize()][this.getSize()];
    }

    public int getSize() {
        return boardSize;
    }

    public boolean withinBounds(Position position) {
        return position.row() >= 0 && position.row() < boardSize &&
               position.column() >= 0 && position.column() < boardSize;
    }

    public Tile tileAt(Position position) {
        return board[position.row()][position.column()];
    }

    public void setTile(Position position, Tile tile) {
        board[position.row()][position.column()] = tile;
    }

    public boolean pieceAt(Position position) {
        if(!withinBounds(position))
            return false;
        return !board[position.row()][position.column()].isBlank();
    }

    public Optional<Piece> getPieceAt(Position position) {
        if(!withinBounds(position))
            throw new ArrayIndexOutOfBoundsException("Position not on board.");
        return board[position.row()][position.column()].getPiece();
    }

    public List<Piece> getPiecesInRow(int row) {
        if(!withinBounds(new Position(row, 0)))
            throw new ArrayIndexOutOfBoundsException("Position not on board.");
        return Arrays.asList(board[row]).stream()
                        .map(Tile::getPiece)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());

    }

    public void clearTile(Position position) {
        tileAt(position).clearPiece();
    }

    public Set<Piece> getAllPieces() {
        Set<Piece> pieces = new HashSet<>();
        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++) {
                Optional<Piece> piece = getPieceAt(new Position(i, j));

                piece.map(p -> {
                    pieces.add(p);
                    return null;
                });
            }
        }
        return pieces;
    }

}
