package boardgame;

import java.util.regex.Pattern;

/**
 * Created by Evan on 2/29/2016.
 *
 */
public abstract class TwoPlayerGame {

    final String positionRegex = "[A-Ha-h][1-8]";

    String name;

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

    public abstract void run();

    protected boolean isValidMoveString(String moveString) {
        Pattern pattern = Pattern.compile("^" + positionRegex + "-" + positionRegex + "$");
        return pattern.matcher(moveString).matches();
    }

    /**
     * Converts an algebraic notation string into a move. The move
     * does not contain a capture since captures are determined
     * by a piece's move generator. This simply creates from and
     * to positions.
     */
    protected Move parseMove(String moveString) {
        if (!isValidMoveString(moveString))
            throw new IllegalArgumentException("String not a valid move string");

        String[] strings = moveString.split("-");
        Position from = parsePosition(strings[0]);
        Position to = parsePosition(strings[1]);

        return new Move(from, to);
    }

    protected boolean isValidPosition(String positionString) {
        Pattern pattern = Pattern.compile("^" + positionRegex + "$");
        return pattern.matcher(positionString).matches();
    }

    protected Position parsePosition(String positionString) {
        if (!isValidPosition(positionString))
            throw new IllegalArgumentException("String not a valid position string");

        positionString = positionString.toLowerCase();
        int row = '8' - positionString.charAt(1);
        int column = positionString.charAt(0) - 'a';
        return new Position(row, column);
    }
}
