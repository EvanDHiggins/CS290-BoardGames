package checkers;

import boardgame.*;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Created by Evan on 3/2/2016.
 */
public class CheckersGame extends TwoPlayerGame {

    CheckerBoard board;

    public CheckersGame(Player player1, Player player2, int boardSize) {
        super(player1, player2);

        board = new CheckerBoard(boardSize);
    }

    @Override
    public void run() {
        while(true) {
            board.printBoard();
            String moveString = Main.input.nextLine();
            Optional<Move> moveAttempt = parseMove(moveString);
            if(!moveAttempt.isPresent()) {
                System.out.println("Invalid move string. Try again.");
            }
            moveAttempt.flatMap(move -> {
                makeMove(move);
                return null;
            });
        }
    }

    private void makeMove(Move move) {
        Optional<Piece> piece = board.getPieceAt(move.getFrom());
        if(!piece.isPresent()) {
            System.out.println("There is not a piece there.");
        }
    }

    private Optional<Move> parseMove(String moveString) {
        Pattern pattern = Pattern.compile("^[A-Ha-h][1-8]-[A-Ha-h][1-8]$");
        if(!pattern.matcher(moveString).matches())
            return Optional.empty();
        String[] strings = moveString.split("-");
        Position from = parsePosition(strings[0]);
        Position to = parsePosition(strings[1]);
        return Optional.of(new Move(from, to));
    }

    private Position parsePosition(String positionString) {
        Pattern pattern = Pattern.compile("^[A-Ha-h][1-8]$");
        if(!pattern.matcher(positionString).matches())
            throw new IllegalArgumentException("String not a valid position string");
        positionString = positionString.toLowerCase();
        int row = positionString.charAt(0) - 'h';
        int column = positionString.charAt(1) - '1';
        return new Position(row, column);
    }
}

