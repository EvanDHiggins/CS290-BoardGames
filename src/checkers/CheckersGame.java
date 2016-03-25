package checkers;

import boardgame.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by Evan on 3/2/2016.
 */
public class CheckersGame extends TwoPlayerGame {

    static final String EXIT_STRING = "exit";

    final String positionRegex = "[A-Ha-h][1-8]";

    CheckerBoard board;

    public CheckersGame(Player player1, Player player2, int boardSize) {
        super("Checkers", player1, player2);

        board = new CheckerBoard(boardSize, player1, player2);
    }

    @Override
    public void run() {
        while (true) {
            String moveString;
            do {
                board.printBoard();
                System.out.println("Make a move " + currentPlayer.getName());
                moveString = Main.input.nextLine();
                if (moveString.equalsIgnoreCase(EXIT_STRING)) {
                    System.out.println("Thanks for playing!");
                    return;
                }
            } while(!moveUntilComplete(moveString));

            if (currentPlayerHasWon()) {
                System.out.println(currentPlayer.getName() + " has won!");
                return;
            }

            nextPlayer();
        }
    }

    /**
     * Returns whether the player is done moving for
     * the turn. A player is "done" moving when they
     * have no remaining jump moves. A player is "not
     * done" moving when an invalid move is entered or
     * there are jumps remaining.
     * @param moveString
     * @return
     */
    private boolean moveUntilComplete(String moveString) {
        boolean performedJump = false;
        Boolean kinged = false;

        if (!isValidMoveString(moveString)) {
            System.out.println("Invalid move string. Try again.");
            return false;
        }

        Move move = parseMove(moveString);

        Set<Move> moves = board.getPieceAt(move.getFrom())
                .map(p -> p.generateMoves(board))
                .orElse(new HashSet<>());

        if(hasJumpsRemaining(currentPlayer)) {
            moves = moves.stream().filter(Move::hasCapture).collect(Collectors.toSet());
            performedJump = true;
        }

        if (!moves.contains(move)) {
            System.out.println("That is not a valid move.");
            return false;
        }

        if (!board.pieceAtMatches(move.getFrom(), currentPlayer.getPieceColor())) {
            System.out.println("That isn't your piece!");
            return false;
        }

        kinged = moves.stream().filter(move::equals)
                .findFirst()
                .filter(mv -> board.pieceAtMatches(mv.getFrom(), currentPlayer.getPieceColor()))
                .map(mv -> {
                    makeMove(mv);
                    return maybeKingPiece(mv.getTo());
                }).orElse(false);
        //Being kinged always ends the turn
        if(performedJump && !kinged)
            return !hasJumpsRemaining(currentPlayer);
        return true;
    }

    /**
     * Promotes a piece to a king and returns a value indicating
     * whether or not the attempted promotion was successful.
     * @param pos
     * @return
     */
    public boolean maybeKingPiece(Position pos) {
        if (pos.row() == 0 || pos.row() == board.getSize() - 1)
            return board.getPieceAt(pos)
                    .filter(piece -> !(((Checker) piece).isKinged()))
                    .map(piece -> {
                        ((Checker) piece).kingMe();
                        return true;
                    }).orElse(false);
        return false;
    }

    public boolean hasJumpsRemaining(Player player) {
        return board.getAllPieces().stream()
                .filter(piece -> piece.matchesColor(player.getPieceColor()))
                .map(piece -> piece.generateMoves(board))
                .anyMatch(moveSet -> moveSet.stream().anyMatch(Move::hasCapture));
    }

    /**
     * A player wins when their opponent has no moves remaining.
     * This definition covers both win conditions: When an opponent
     * has no moves remaining, and when they have no pieces remaining.
     * Since having no pieces implies they have no moves.
     *
     * @return
     */
    private boolean currentPlayerHasWon() {
        return !playerHasMoves(otherPlayer.getPieceColor());
    }

    /**
     * Returns true if the other player has remaining moves. Used
     * to determine win conditions.
     */
    private boolean playerHasMoves(Piece.PieceColor color) {
        return board.getAllPieces().stream()
                .filter(piece -> piece.matchesColor(color))
                .anyMatch(piece -> piece.generateMoves(board).size() > 0);
    }

    /**
     * Executes a move by placing the piece at move.from in
     * move.to and, if the move contains a capture, removes
     * the captured piece.
     */
    private void makeMove(Move move) {
        board.movePiece(move.getFrom(), move.getTo());

        if(move.getTo().row() == 0 || move.getTo().row() == board.getSize() - 1)
            board.getPieceAt(move.getTo()).map(piece -> {((Checker) piece).kingMe(); return null;});

        move.getCapture().map(capturePos -> {
            board.tileAt(capturePos).clearPiece();
            return null;
        });
    }

    private boolean isValidMoveString(String moveString) {
        Pattern pattern = Pattern.compile("^" + positionRegex + "-" + positionRegex + "$");
        return pattern.matcher(moveString).matches();
    }

    /**
     * Converts an algebraic notation string into a move. The move
     * does not contain a capture since captures are determined
     * by a piece's move generator. This simply creates from and
     * to positions.
     *
     * @param moveString
     * @return
     */
    private Move parseMove(String moveString) {
        if (!isValidMoveString(moveString))
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
        if (!isValidPosition(positionString))
            throw new IllegalArgumentException("String not a valid position string");

        positionString = positionString.toLowerCase();
        int row = '8' - positionString.charAt(1);
        int column = positionString.charAt(0) - 'a';
        return new Position(row, column);
    }
}

