package boardgame;

/**
 * Created by Evan on 2/29/2016.
 *
 */
public abstract class TwoPlayerGame {

    private String name;

    protected Player currentPlayer;

    protected Player otherPlayer;

    protected TwoPlayerGame(String gameName, Player player1, Player player2) {
        name = gameName;
        currentPlayer = player1;
        otherPlayer = player2;
    }

    protected void nextPlayer() {
        Player temp = currentPlayer;
        currentPlayer = otherPlayer;
        otherPlayer = temp;
    }

    public String getName() {
        return name;
    }

    public void setName(String new_name) {
        name = new_name;
    }

    public abstract void run();

}
