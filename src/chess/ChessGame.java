package chess;

import boardgame.*;

import java.util.Observable;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Character.toUpperCase;


/**
 * Created by evan on 4/1/16.
 *
 * Note on vocabulary:
 *
 * In this project a "valid" move is one which fits the move
 * semantics of a specific piece. Any move contained in the return
 * value of a Piece::generateMoves is a "valid" move.
 *
 * A "legal" move is a subset of all valid moves. A legal move cannot
 * put the king into check. And if the king is in check, a legal move
 * must move the king out of check.
 */
public class ChessGame extends TwoPlayerGame {

    private final String positionRegex = "[A-Ha-h][1-8]";
    private final String UNDO_MOVE_STR = "UNDO";

    public static final String PAWN = "p";
    public static final String ROOK = "r";
    public static final String KNIGHT = "n";
    public static final String BISHOP = "b";
    public static final String KING = "k";
    public static final String QUEEN = "q";

    private static final int CHESS_BOARD_SIZE = 8;

    protected ChessBoard board;


    //Holds previous moves. Can be unexecuted.
    //private Stack<Move> oldMoveStack = new Stack<>();
    protected ObservableStack<Move> oldMoveStack = new ObservableStack<>();

    public ChessGame(Player playerOne, Player playerTwo) {
        super("Chess", playerOne, playerTwo);
        initBoard(playerOne, playerTwo);
    }

    @Override
    public void run() {
        //At the beginning of this loop currentPlayer is guaranteed to have
        //at least one legal move.
        do {
            board.printBoard();

            System.out.println("It is " + currentPlayer.getName() + "'s turn.");

            if(inCheck(currentPlayer))
                System.out.println("You are in check.");

            String userInput = Application.input.nextLine();

            if(UNDO_MOVE_STR.equalsIgnoreCase(userInput) && oldMoveStack.size() > 0) {
                oldMoveStack.pop().unexecute(board);
                nextPlayer();
                continue;
            }

            if(!isValidMoveString(userInput)) {
                System.out.println("That is not a valid input.");
                System.out.println("Please input a move in algebraic notation.");
                continue;
            }

            Move parsedMove = parseMove(userInput);

            if(!attemptPlayerMove(parsedMove)) {
                continue;
            }
//
//            Optional<Move> maybeMove = findMatchingMove(parsedMove);
//
//            if(!maybeMove.isPresent()) {
//                System.out.println("That is not a valid move.");
//                continue;
//            }
//            Move playerMove = maybeMove.get();
//
//            if(!isLegalMove(playerMove)) {
//                System.out.println("That is not a legal move. It would leave you in check.");
//                continue;
//            }
//
//            playerMove.execute(board);
//            oldMoveStack.push(playerMove);
//
//            promotions(board);
//
//            if(isStalemate(otherPlayer))
//                stalemate();

            nextPlayer();
        } while(!hasLost(currentPlayer));

        playerWins(otherPlayer);
    }

    protected boolean attemptPlayerMove(Move move) {
        Optional<Move> maybeMove = findMatchingMove(move);

        if(!maybeMove.isPresent()) {
            System.out.println("That is not a valid move.");
            return false;
        }
        Move playerMove = maybeMove.get();

        if(!isLegalMove(playerMove)) {
            System.out.println("That is not a legal move. It would leave you in check.");
            return false;
        }

        playerMove.execute(board);
        oldMoveStack.push(playerMove);

        promotions(board);

        if(isStalemate(otherPlayer))
            stalemate();

        return true;
    }

    private void promotions(GameBoard board) {
        for(int column = 0; column < board.getSize(); column++) {
            Position pos1 = new Position(column, 0);
            Position pos2 = new Position(column, board.getSize() - 1);
            board.getPieceAt(pos1).ifPresent(piece -> promote(board, piece));
            board.getPieceAt(pos2).ifPresent(piece -> promote(board, piece));
        }
    }

    private void promote(GameBoard board, Piece piece) {
        if(!(piece instanceof Pawn))
            return;
        if(piece.toString().equals(PAWN))
            board.setPieceAt(piece.getPosition(), new Queen(QUEEN, piece.getColor(), piece.getPosition()));
        else
            board.setPieceAt(piece.getPosition(), new Queen(QUEEN.toUpperCase(), piece.getColor(), piece.getPosition()));
    }

    private void playerWins(Player player) {
        System.out.println("Checkmate. " + player.getName() + " has won!");
    }

    /**
     * A stalemate arrises when a player is NOT in check, but has
     * no legal moves.
     */
    private boolean isStalemate(Player player) {
        return !inCheck(player) && !hasLegalMoves(player);
    }

    /**
     * Determines if a valid move (one that meets a single pieces move/capture
     * semantics) is a legal move.
     */
    private boolean hasLegalMoves(Player player) {
        King king = findPlayersKing(player);
        Set<Piece> pieces = getPlayerPieces(player);
        for(Piece piece : pieces) {
            for(Move mv : piece.generateMoves(board)) {
                mv.execute(board);
                if(!inCheck(king)) {
                    mv.unexecute(board);
                    return true;
                }
                mv.unexecute(board);
            }
        }
        return false;
    }

    private void stalemate() {
        System.out.println(currentPlayer.getName() + "has no moves remaining.");
        System.out.println("The game is a stalemate.");
        System.exit(0);
    }

    /**
     * Small note: This function performs an unchecked Optional::get call. It
     * is safe to do so because kings should never be removed from the board.
     * If they have been the game has been placed in a failed state anyway.
     */
    private King findPlayersKing(Player player) {
        return (King)board.getAllPieces().stream()
                                .filter(piece -> ((ChessPiece)piece).isKing())
                                .filter(player::owns)
                                .findFirst().get();
    }

    /**
     * A legal move is any valid move which results in you not being in
     * check.
     */
    private boolean isLegalMove(Move move) {
        move.execute(board);
        boolean isLegal = !inCheck(currentPlayer);
        move.unexecute(board);
        return isLegal;
    }

    /**
     * Determines if the move is valid. It also is a little sneaky and, if
     * the move is equal, sets the passed move's capture to the discovered
     * move's capture. The reason for this is that when a user inputs a move
     * there is no way of knowing if that move is a capture before this step
     * without adding capture logic into the main game loop. That would
     * completely eliminate the point of putting movement logic in the
     * IMoveGenerator objects. So it needs to be found if the move is found.
     * This is a very awkward place to put it, I understand.
     *
     * This also does not determine if a move is LEGAL based on Chess checkmate
     * rules.
     */
    private Optional<Move> findMatchingMove(Move move) {
        Optional<Piece> maybePiece = board.getPieceAt(move.getFrom());
        if(!maybePiece.map(currentPlayer::owns).orElse(false)) {
            return Optional.empty();
        }
        return maybePiece.map(piece -> {
            for(Move mv : piece.generateMoves(board)) {
                if(mv.equals(move)) {
                    return mv;
                }
            }
            return null;
        });
    }

    /**
     * A player has lost the game when their king is in check and any available
     * move still results in their king being in check.
     */
    private boolean hasLost(Player player) {
        return inCheck(findPlayersKing(player)) && !hasLegalMoves(player);
    }

    private Set<Piece> getPlayerPieces(Player player) {
        return board.getAllPieces().stream()
                                   .filter(player::owns)
                                   .collect(Collectors.toSet());
    }

    private boolean inCheck(Player player) {
        return inCheck(findPlayersKing(player));
    }

    private boolean inCheck(King king) {
        Set<Piece> opponentPieces = board.getAllPieces().stream()
                                            .filter(piece -> !piece.matchesColor(king))
                                            .collect(Collectors.toSet());

        for(Piece piece : opponentPieces) {
            for(Move mv : piece.generateCaptures(board)) {
                if(mv.getCapture().map(pos -> pos.equals(king.getPosition())).orElse(false))
                    return true;
            }
        }
        return false;
    }

    private void initBoard(Player playerOne, Player playerTwo) {
        board = new ChessBoard(CHESS_BOARD_SIZE);
        initPlayerOneTeam(playerOne);
        initPlayerTwoTeam(playerTwo);
    }

    /**
     * Places player one's pieces in their starting location.
     * Player 1 starts at the bottom of the board.
     */
    private void initPlayerOneTeam(Player player) {
        for(int column = 0; column < board.getSize(); column++) {
            Position pos = new Position(column, 1);
            board.setPieceAt(pos, new UpPawn(PAWN, player.getPieceColor(), pos));
        }
        initTeamAtRow(0, player.getPieceColor(), c -> c);
    }

    /**
     * Places player two's pieces in their starting location.
     * Player 2 starts at the top of the board.
     */
    private void initPlayerTwoTeam(Player player) {
        for(int column = 0; column < board.getSize(); column++) {
            Position pos = new Position(column, board.getSize() - 2);
            board.setPieceAt(pos, new DownPawn(PAWN.toUpperCase(), player.getPieceColor(), pos));
        }
        initTeamAtRow(board.getSize() - 1, player.getPieceColor(), String::toUpperCase);
    }

    /**
     * Initializes a team at a given getY for an 8x8 chess board.
     * @param row Row that the team is placed at.
     * @param color Color of the team
     * @param trans Transformation to apply to team representation. Allows one team to have capitalized pieces.
     */
    private void initTeamAtRow(int row, Piece.PieceColor color, UnaryOperator<String> trans) {

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
        return new Position(column, row);
    }
}
