package chess;

import boardgame.*;

import java.util.Stack;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import static java.lang.Character.toUpperCase;


/**
 * Created by evan on 4/1/16.
 */
public class ChessGame extends TwoPlayerGame {

    private final String positionRegex = "[A-Ha-h][1-8]";

    private static final char PAWN = 'p';
    private static final char ROOK = 'r';
    private static final char KNIGHT = 'n';
    private static final char BISHOP = 'b';
    private static final char KING = 'k';
    private static final char QUEEN = 'q';

    private static final int CHESS_BOARD_SIZE = 8;

    private ChessBoard board;

    private final String UNDO_MOVE_STR = "UNDO";

    //Holds previous moves. Can be unexecuted.
    private Stack<Move> oldMoveStack = new Stack<>();

    public ChessGame(Player playerOne, Player playerTwo) {
        super("Chess", playerOne, playerTwo);
        initBoard(playerOne, playerTwo);
    }

    @Override
    public void run() {
        board.printBoard();
        while(true) {
            String userInput = Application.input.nextLine();
            if(UNDO_MOVE_STR.equalsIgnoreCase(userInput) && oldMoveStack.size() > 0) {
                oldMoveStack.pop().unexecute(board);
                nextPlayer();
                continue;
            }

            if(!isValidMoveString(userInput)) {
                System.out.println("Invalid Input. Try again.");
                continue;
            }

            System.out.println(parseMove(userInput));

            //board.printBoard();
        }

    }

    private void initBoard(Player playerOne, Player playerTwo) {
        board = new ChessBoard(CHESS_BOARD_SIZE);
        initPlayerOneTeam(playerOne);
        initPlayerTwoTeam(playerTwo);
    }

    /**
     * Places player one's pieces in their starting location. Player 1 starts at the bottom of the board.
     *
     */
    private void initPlayerOneTeam(Player player) {
        for(int column = 0; column < board.getSize(); column++) {
            Position pos = new Position(column, 1);
            board.setPieceAt(pos, new UpPawn(PAWN, player.getPieceColor(), pos));
        }

        initTeamAtRow(0, player.getPieceColor(), c -> c);
    }

    /**
     * Places player two's pieces in their starting location. Player 2 starts at the top of the board.
     */
    private void initPlayerTwoTeam(Player player) {
        for(int column = 0; column < board.getSize(); column++) {
            Position pos = new Position(column, board.getSize() - 2);
            board.setPieceAt(pos, new DownPawn(toUpperCase(PAWN), player.getPieceColor(), pos));
        }

        initTeamAtRow(board.getSize() - 1, player.getPieceColor(), Character::toUpperCase);
    }

    /**
     * Initializes a team at a given row for an 8x8 chess board.
     * @param row Row that the team is placed at.
     * @param color Color of the team
     * @param trans Transformation to apply to team representation. Allows one team to have capitalized pieces.
     */
    private void initTeamAtRow(int row, Piece.PieceColor color, UnaryOperator<Character> trans) {

        board.setPieceAt(new Position(0, row), new Rook(trans.apply(ROOK), color, new Position(0, row)));
        board.setPieceAt(new Position(7, row), new Rook(trans.apply(ROOK), color, new Position(7, row)));

        board.setPieceAt(new Position(1, row), new Knight(trans.apply(KNIGHT), color, new Position(1, row)));
        board.setPieceAt(new Position(6, row), new Knight(trans.apply(KNIGHT), color, new Position(6, row)));

        board.setPieceAt(new Position(2, row), new Bishop(trans.apply(BISHOP), color, new Position(2, row)));
        board.setPieceAt(new Position(5, row), new Bishop(trans.apply(BISHOP), color, new Position(5, row)));

        board.setPieceAt(new Position(3, row), new Queen(trans.apply(QUEEN), color, new Position(3, row)));
        board.setPieceAt(new Position(4, row), new King(trans.apply(KING), color, new Position(4, row)));
    }

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
        int row = positionString.charAt(1) - '1';
        int column = positionString.charAt(0) - 'a';
        return new Position(row, column);
    }
}
