package checkers;

import boardgame.*;

import java.security.cert.PKIXRevocationChecker;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * Created by Evan on 3/2/2016.
 */
public class CheckersGame extends TwoPlayerGame {

    static final String EXIT_STRING = "exit";

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
            if(moveString.equalsIgnoreCase(EXIT_STRING))
                break;

            if(!isValidMove(moveString)) {
                System.out.println("Invalid move string. Try again.");
                continue;
            }

            Move move = parseMove(moveString);

            Set<Move>moves = board.getPieceAt(move.getFrom())
                                  .map(p -> p.generateMoves(board))
                                  .orElse(new HashSet<>());

            if(!moves.contains(move)) {
                System.out.println("That is not a valid move.");
                continue;
            }

            if(!board.pieceAtMatches(move.getFrom(), currentPlayer.getPieceColor())) {
                System.out.println("That isn't your piece!");
                continue;
            }

            moves.stream().filter(move::equals)
                          .findFirst()
                          .filter(mv -> board.pieceAtMatches(mv.getFrom(), currentPlayer.getPieceColor()))
                          .map(p -> { makeMove(p); return null; });

            if(playerHasWon(currentPlayer)) {
                //handle win
                break;
            }

            board.printBoard();
            nextPlayer();
        }
    }

    /**
     * If a player has no moves remaining, they lose. If a player
     * has no pieces then they must have no moves remaining thus
     * they lose.
     * @param player
     * @return
     */
    private boolean playerHasWon(Player player) {
        Set<Piece> pieces = board.getAllPieces();
//        if(hasMovesRemaining(player.color)) {
//
//        }
        return false;
    }

    /**
     * Returns true if the other player has remaining moves. Used
     * to determine win conditions.
     */
    private boolean otherPlayerHasMoves(Piece.PieceColor color) {
        return board.getAllPieces().stream()
                        .filter(piece -> !piece.matchesColor(color))
                        .anyMatch(piece -> piece.generateMoves(board).size() > 0);
    }

    /**
     * Executes a move by placing the piece at move.from in
     * move.to and, if the move contains a capture, removes
     * the captured piece.
     */
                                      private void makeMove(Move move) {
        board.movePiece(move.getFrom(), move.getTo());

        move.getCapture().map(capturePos -> {
            System.out.println("Capture");
            board.tileAt(capturePos).clearPiece();
            return null;
        });
    }

    private boolean isValidMove(String moveString) {
        Pattern pattern = Pattern.compile("^" + positionRegex + "-" + positionRegex + "$");
        return pattern.matcher(moveString).matches();
    }

    /**
     * Converts an algebraic notation string into a move. The move
     * does not contain a capture since captures are determined
     * by a piece's move generator. This simply creates from and
     * to positions.
     * @param moveString
     * @return
     */
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

