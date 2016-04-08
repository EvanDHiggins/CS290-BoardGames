package checkers;

import boardgame.*;

/**
 * Created by Evan on 3/2/2016.
 *
 * This class needs little explanation. It represents a Checker board.
 */
public class CheckerBoard extends GameBoard {

    public static char blackTile = '#';
    public static char whiteTile = '_';

    public CheckerBoard(int size) {
        super(size);
        board = new Tile[this.getSize()][this.getSize()];
        initEmpty();
    }

    public void printBoard() {
        System.out.print("   ");
        for(int i = 0; i < getSize(); i++) {
            System.out.print((char)('A' + i) + " ");
        }
        System.out.println();
        System.out.println();
        for(int row = 0; row < getSize(); row++) {
            System.out.print((getSize() - row) + "  ");
            for(int column = 0; column < getSize(); column++) {
                System.out.print(board[row][column].toString() + " ");
            }
            System.out.println(" " + (getSize() - row));
        }
        System.out.println();
        System.out.print("   ");
        for(int i = 0; i < getSize(); i++) {
            System.out.print((char)('A' + i) + " ");
        }
        System.out.println();
    }

    /**
     * Removes the piece at from and places it at to. This
     * does not contain capture semantics or anything except
     * for simple piece movement.
     */
    public void movePiece(Position from, Position to) {
        if(!withinBounds(from))
            throw new ArrayIndexOutOfBoundsException("From is outside of the board's bounds.");

        if(!withinBounds(to))
            throw new ArrayIndexOutOfBoundsException("To is outside of the board's bounds.");

        tileAt(from).getPiece().map(piece -> {
            clearTile(from);
            tileAt(to).setPiece(piece);
            return null;
        });
    }

    private void initEmpty() {
        for(int row = 0; row < getSize(); row++) {
            for(int column = 0; column < getSize(); column++) {
                board[row][column] = genTile(row, column);
            }
        }
    }

    private Tile genTile(int row, int column) {
        if((row + column) % 2 == 0) {
            return new Tile(new Position(row, column), blackTile);
        }
        return new Tile(new Position(row, column), whiteTile);
    }


    /**
     * This initializes a board with the starting pieces in their correct places.
     */
//    private void initBoard(Piece.PieceColor color1, Piece.PieceColor color2) {
//        for(int row = 0; row < getSize(); row++) {
//            for(int column = 0; column < getSize(); column++) {
//                board[row][column] = genTile(row, column);
//
//                if(row >= getSize() - 3 && board[row][column].hasTileColor(whiteTile)) {
//                    board[row][column].setPiece(new UpChecker(color1, new Position(row, column)));
//                }
//
//                if(row < 3 && board[row][column].hasTileColor(whiteTile)) {
//                    board[row][column].setPiece(new DownChecker(color2, new Position(row, column)));
//                }
//            }
//        }
//    }

}
