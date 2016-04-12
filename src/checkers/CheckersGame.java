package checkers;

import boardgame.*;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by Evan on 3/2/2016.
 *
 */
public class CheckersGame extends TwoPlayerGame {


    static final String EXIT_STRING = "exit";


    private CheckeredBoard board;

    public CheckersGame(Player player1, Player player2) {
        super("Checkers", player1, player2);
        int boardSize = 8;
        board = initBoard(boardSize, player1, player2);
    }

    CheckeredBoard initBoard(int size, Player player1, Player player2) {
        CheckeredBoard new_board = new CheckeredBoard(size);
        for(int row = 0; row < size; row++) {
            for(int column = 0; column < size; column++) {
                Position this_pos = new Position(row, column);

                if(row >= size - 3 && new_board.tileAt(this_pos).hasTileColor(CheckeredBoard.whiteTile)) {
                    new_board.tileAt(this_pos).setPiece(new UpChecker(player1.getPieceColor(), new Position(row, column)));
                }

                if(row < 3 && new_board.tileAt(this_pos).hasTileColor(CheckeredBoard.whiteTile)) {
                    new_board.tileAt(this_pos).setPiece(new DownChecker(player2.getPieceColor(), new Position(row, column)));
                }
            }
        }
        return new_board;
    }

    /**
     * It's a property of a checkerboard that the sum of the indices
     * (0-based) of a tile are even if the tile is black and odd if
     * the tile is white.
     */

    @Override
    public void run() {
        while(true) {
            takeTurn(currentPlayer);
            if(currentPlayerHasWon())
                break;
            nextPlayer();
        }

        System.out.println(currentPlayer.getName() + " Has won!");
    }

    private void takeTurn(Player player) {
        Set<Piece> pieces = getValidPieces(player);
        boolean turnOver = false;

        while(!turnOver) {
            board.printBoard();
            Move move = getMoveFromUser(currentPlayer);

            if(!validMove(move, pieces)) {
                System.out.println("Invalid move. Try again.");
                continue;
            }

            //makeMove(move);
            move.execute(board);

            if(isChainJumpAvailable(move)) {
                turnOver = false;
                pieces = new HashSet<>();
                Optional<Piece> oPiece = board.getPieceAt(move.getTo());
                if (oPiece.isPresent()) {
                    Piece piece = oPiece.get();
                    pieces.add(piece);
                }
            } else {
                turnOver = true;
            }

            if(maybeMakePieceKing(move.getTo()))
                turnOver = true;

        }
    }

    /**
     * Whenever a jump is made the current Player can continue
     * executing jumps as long as one exists from the same piece
     * which was originally moved. These are called chain jumps.
     */
    private boolean isChainJumpAvailable(Move move) {
        if(!move.hasCapture())
            return false;

        return board.getPieceAt(move.getTo()).map(p ->
                p.generateCaptures(board).size() != 0
            ).orElse(false);
    }

    /**
     * Determines if the move is valid for any piece in
     * pieces.
     */
    public boolean validMove(Move move, Set<Piece> pieces) {
        for(Piece piece : pieces) {
            for(Move m : piece.generateMoves(board)) {
                if(move.equals(m)) {
                    move.setCapture(m.getCapture());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Asks user for input string until a valid move is received.
     *
     * Note: this only validates a correct move input, not whether
     * the move is legal.
     */
    private Move getMoveFromUser(Player player) {
        String moveString;
        do {
            System.out.println("Enter a move: " + player.getName());
            moveString = Application.input.nextLine();
            if(EXIT_STRING.equalsIgnoreCase(moveString)) {
                System.out.println("Thanks for playing!");
                System.exit(0);
            }
            if (!isValidMoveString(moveString))
                System.out.println("Invalid input. Please enter a move in standard algebraic notation.");
        } while (!isValidMoveString(moveString));

        return parseMove(moveString);
    }

    /**
     * Returns all pieces for the passed player. Any of those pieces
     * can make a capture, only pieces which can make a capture are
     * returned.
     */
    private Set<Piece> getValidPieces(Player player) {
        Set<Piece> pieces = board.getAllPieces().stream()
                                .filter(p -> p.matchesColor(player.getPieceColor()))
                                .collect(Collectors.toSet());

        Set<Piece> withCaptures = pieces.stream()
                                    .filter(p -> p.generateCaptures(board).size() >0)
                                    .collect(Collectors.toSet());
        if(withCaptures.size() > 0)
            return withCaptures;
        return pieces;
    }
    /**
     * Promotes a piece to a king and returns a value indicating
     * whether or not the attempted promotion was successful.
     */
    public boolean maybeMakePieceKing(Position pos) {
        if (pos.row() == 0 || pos.row() == board.getSize() - 1)
            return board.getPieceAt(pos)
                    .filter(piece -> !(((Checker) piece).isKinged()))
                    .map(piece -> {
                        ((Checker) piece).kingMe();
                        return true;
                    }).orElse(false);
        return false;
    }

    /**
     * A player wins when their opponent has no moves remaining.
     * This definition covers both win conditions: When an opponent
     * has no moves remaining, and when they have no pieces remaining.
     * Since having no pieces implies they have no moves.
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
//    private void makeMove(Move move) {
//        board.movePiece(move.getFrom(), move.getTo());
//
//        move.getCapture().map(capturePos -> {
//            board.tileAt(capturePos).clearPiece();
//            return null;
//        });
//    }

//    private boolean isValidMoveString(String moveString) {
//        Pattern pattern = Pattern.compile("^" + positionRegex + "-" + positionRegex + "$");
//        return pattern.matcher(moveString).matches();
//    }
//
//    /**
//     * Converts an algebraic notation string into a move. The move
//     * does not contain a capture since captures are determined
//     * by a piece's move generator. This simply creates from and
//     * to positions.
//     */
//    private Move parseMove(String moveString) {
//        if (!isValidMoveString(moveString))
//            throw new IllegalArgumentException("String not a valid move string");
//
//        String[] strings = moveString.split("-");
//        Position from = parsePosition(strings[0]);
//        Position to = parsePosition(strings[1]);
//
//        return new Move(from, to);
//    }
//
//    private boolean isValidPosition(String positionString) {
//        Pattern pattern = Pattern.compile("^" + positionRegex + "$");
//        return pattern.matcher(positionString).matches();
//    }
//
//    private Position parsePosition(String positionString) {
//        if (!isValidPosition(positionString))
//            throw new IllegalArgumentException("String not a valid position string");
//
//        positionString = positionString.toLowerCase();
//        int row = '8' - positionString.charAt(1);
//        int column = positionString.charAt(0) - 'a';
//        return new Position(row, column);
//    }
}

