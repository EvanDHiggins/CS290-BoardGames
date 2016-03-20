package checkers;

import boardgame.*;

import java.util.Optional;

/**
 * Created by Evan on 3/2/2016.
 */
public class CheckerBoard extends GameBoard {

    private static char blackTile = '#';
    private static char whiteTile = '_';

    public CheckerBoard(int size) {
        super(size);
        board = new Tile[this.boardSize][this.boardSize];
        initBoard();
    }

    public void printBoard() {
        System.out.print("   ");
        for(int i = 0; i < boardSize; i++) {
            System.out.print((char)('a' + i) + " ");
        }
        System.out.println();
        System.out.println();
        for(int row = 0; row < boardSize; row++) {
            System.out.print((boardSize - row) + "  ");
            for(int column = 0; column < boardSize; column++) {
                System.out.print(board[row][column].toString() + " ");
            }
            System.out.println("  " + (boardSize - row));
        }
        System.out.println();
        System.out.print("   ");
        for(int i = 0; i < boardSize; i++) {
            System.out.print((char)('a' + i) + " ");
        }
    }

    public int getSize() {
        return boardSize;
    }

    public void movePiece(Move move) {

        move.getCapture().map(capture -> {
            tileAt(capture).clearPiece();
            return null;
        });

        Optional<Piece> piece = tileAt(move.getFrom()).getPiece();

        System.out.println(move);
        System.out.println("outside map");
        piece.map(p -> {
            System.out.println("blah");
            clearTile(move.getFrom());
            tileAt(move.getTo()).setPiece(p);
            return null;
        });
    }

    /**
     * This initializes a board with the starting pieces in their correct places.
     */
    private void initBoard() {
        for(int row = 0; row < boardSize; row++) {
            for(int column = 0; column < boardSize; column++) {
                board[row][column] = genTile(row, column);

                if(row < 3 && board[row][column].hasTileColor(whiteTile)) {
                    board[row][column].setPiece(new RedChecker(new Position(row, column)));
                }

                if(row >= boardSize - 3 && board[row][column].hasTileColor(whiteTile)) {
                    board[row][column].setPiece(new BlackChecker(new Position(row, column)));
                }
            }
        }
    }

    /**
     * It's a property of a checkerboard that the sum of the indices
     * (0-based) of a tile are even if the tile is black and odd if
     * the tile is white.
     */
    private Tile genTile(int row, int column) {
        if((row + column) % 2 == 0) {
            return new Tile(new Position(row, column), blackTile);
        }
        return new Tile(new Position(row, column), whiteTile);
    }
}
