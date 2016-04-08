package chess;

import boardgame.Player;
import boardgame.TwoPlayerGame;

/**
 * Created by evan on 4/1/16.
 */
public class ChessGame extends TwoPlayerGame {

    ChessBoard board;

    ChessGame(Player playerOne, Player playerTwo) {
        super("Chess", playerOne, playerTwo);
        //board = initBoard()
    }

    @Override
    public void run() {

    }
}
