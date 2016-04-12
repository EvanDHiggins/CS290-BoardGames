package chess;


import boardgame.CheckeredBoard;

/**
 * Created by evan on 4/9/16.
 *
 * In checkers I implemented my board with (0,0) in the top left corner
 * and (7, 7) in the bottom right. This turned out to be very confusing
 * and unintuitive since the board is labeled the reverse of that. To change
 * it for checkers would require rewriting a tremendous amount of logic, or
 * writing something to crudely work around it (I considered a decorator, but
 * I think that is ultimately overkill). So instead I chose a different
 * method of crudely working around it. This subclasses checkeredboard and just
 * overrides printboard to print the board with (0,0) in the bottom left
 * and (7, 7) in the top right. This is the only shared logic between chess
 * and checkers that is dependent on that specific detail. Otherwise the
 * board can be interacted with just by indices (and Position objects).
 */
public class ChessBoard extends CheckeredBoard {

    public ChessBoard(int size) {
        super(size);
    }

    @Override
    public void printBoard() {
        System.out.print("   ");
        for(int i = getSize() - 1; i >= 0; i--) {
            System.out.print((char) ('H' - i) + " ");
        }
        System.out.println();
        System.out.println();
        for(int row = getSize()-1; row >= 0; row--) {
            System.out.print((row + 1) + " ");
            for(int column = 0; column < getSize(); column++) {
                System.out.print(board[column][row].toString() + " ");
            }
            System.out.println(" " + (row + 1));
        }

        System.out.println();
        System.out.print("   ");

        for(int i = getSize() - 1; i >= 0; i--) {
            System.out.print((char) ('H' - i) + " ");
        }
        System.out.println();
    }
}
