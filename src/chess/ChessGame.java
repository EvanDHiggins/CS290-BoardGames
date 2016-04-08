package chess;

import boardgame.CheckeredBoard;
import boardgame.Player;
import boardgame.TwoPlayerGame;


/**
 * Created by evan on 4/1/16.
 */
public class ChessGame extends TwoPlayerGame {

    private static final int CHESS_BOARD_SIZE = 8;

    CheckeredBoard board;

    ChessGame(Player playerOne, Player playerTwo) {
        super("Chess", playerOne, playerTwo);
        board = initBoard();
    }

    @Override
    public void run() {

    }

    private CheckeredBoard initBoard() {
        CheckeredBoard new_board = new CheckeredBoard(CHESS_BOARD_SIZE);
        for(int column = 0; column < new_board.getSize(); column++) {
            if(column == 0 || column == CHESS_BOARD_SIZE - 1) {
                //create Rook
            } else if (column == 1 || column == CHESS_BOARD_SIZE - 2) {
                //init knight
            } else if (column == 2 || column == CHESS_BOARD_SIZE - 3) {
                //init bishops
            }

        }
        return new_board;
    }
}
