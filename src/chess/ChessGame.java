package chess;

import boardgame.Player;
import boardgame.TwoPlayerGame;

/**
 * Created by evan on 4/1/16.
 */
public class ChessGame extends TwoPlayerGame {

    ChessBoard board;

    ChessGame(Player upPlayer, Player downPlayer) {
        super("Chess", upPlayer, downPlayer);

    }

    @Override
    public void run() {

    }
}
