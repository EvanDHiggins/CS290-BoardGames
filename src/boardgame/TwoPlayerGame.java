package boardgame;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Evan on 2/29/2016.
 */
public abstract class TwoPlayerGame {

    protected Player currentPlayer;

    protected Player otherPlayer;

    protected TwoPlayerGame(Player player1, Player player2) {
        currentPlayer = player1;
        otherPlayer = player2;
    }

    protected void nextPlayer() {
        Player temp = currentPlayer;
        currentPlayer = otherPlayer;
        otherPlayer = temp;
    }

    public abstract void run();
}
