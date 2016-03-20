package checkers;

import boardgame.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by Evan on 3/2/2016.
 */
public class CheckersGame extends TwoPlayerGame {

    final String positionRegex = "[A-Ha-h][1-8]";

    CheckerBoard board;

    public CheckersGame(Player player1, Player player2, int boardSize) {
        super("Checkers", player1, player2);

        board = new CheckerBoard(boardSize);
    }

    @Override
    public void run() {
        board.printBoard();
        while(true) {
            String moveString = Main.input.nextLine();
            if(!isValidMove(moveString)) {
                System.out.println("Invalid move string. Try again.");
                continue;
            }
            Move move = parseMove(moveString);

            Set<Move>moves = board.getPieceAt(move.getFrom())
                                  .map(p -> p.generateMoves(board))
                                  .orElse(new HashSet<>());

            for(Move m : moves) {
                System.out.println("Move: " + m);
            }
            if(moves.contains(move)) {
                board.movePiece(move);
            } else {
                System.out.println("That is not a valid move.");
            }

            board.printBoard();
        }
    }

    private boolean makeMove(Move move) {
//        Optional<Piece> piece = board.getPieceAt(move.getFrom());
//        if(!piece.isPresent()) {
//            System.out.println("There is not a piece there.");
//        }
        board.movePiece(move);

        return true;
    }

    private boolean isValidMove(String moveString) {
        Pattern pattern = Pattern.compile("^" + positionRegex + "-" + positionRegex + "$");
        return pattern.matcher(moveString).matches();
    }

    private Move parseMove(String moveString) {
        if(!isValidMove(moveString))
            throw new IllegalArgumentException("String not a valid move string");

        String[] strings = moveString.split("-");
        Position from = parsePosition(strings[0]);
        Position to = parsePosition(strings[1]);

        return new Move(from, to);
    }

    private boolean isValidPosition(String positionString) {
        Pattern pattern = Pattern.compile("^" + positionRegex + "$");
        return pattern.matcher(positionString).matches();
    }

    private Position parsePosition(String positionString) {
        if(!isValidPosition(positionString))
            throw new IllegalArgumentException("String not a valid position string");

        positionString = positionString.toLowerCase();
        int row = '8' - positionString.charAt(1);
        int column = positionString.charAt(0) - 'a';
        return new Position(row, column);
    }
}

