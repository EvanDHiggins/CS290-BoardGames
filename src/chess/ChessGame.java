package chess;

import boardgame.*;

import static java.lang.Character.toUpperCase;


/**
 * Created by evan on 4/1/16.
 */
public class ChessGame extends TwoPlayerGame {

    private static final char PAWN = 'p';
    private static final char ROOK = 'r';
    private static final char KNIGHT = 'n';
    private static final char BISHOP = 'b';
    private static final char KING = 'k';
    private static final char QUEEN = 'q';

    private static final int CHESS_BOARD_SIZE = 8;

    private ChessBoard board;

    public ChessGame(Player playerOne, Player playerTwo) {
        super("Chess", playerOne, playerTwo);
        initBoard(playerOne, playerTwo);
    }

    @Override
    public void run() {
        //board.printBoard();
        LinearMoveGenerator gen = new LinearMoveGenerator();
        board.getPieceAt(new Position(0, 0)).map(piece -> {
            gen.generate(board, piece);
            return null;
        });
    }

    private void initBoard(Player playerOne, Player playerTwo) {
        board = new ChessBoard(CHESS_BOARD_SIZE);
        initPlayerOneTeam(playerOne);
        initPlayerTwoTeam(playerTwo);
    }

    /**
     * Places player one's pieces in their starting location. Player 1 starts at the bottom of the board.
     */
    private void initPlayerOneTeam(Player player) {
        for(int column = 0; column < board.getSize(); column++) {
            Position pos = new Position(1, column);
            board.setPieceAt(pos, new UpPawn(PAWN, player.getPieceColor(), pos));
        }
        board.setPieceAt(new Position(0, 0), new Rook(ROOK, player.getPieceColor(), new Position(0, 0)));
        board.setPieceAt(new Position(0, 7), new Rook(ROOK, player.getPieceColor(), new Position(0, 7)));

        board.setPieceAt(new Position(0, 1), new Knight(KNIGHT, player.getPieceColor(), new Position(0, 1)));
        board.setPieceAt(new Position(0, 6), new Knight(KNIGHT, player.getPieceColor(), new Position(0, 6)));

        board.setPieceAt(new Position(0, 2), new Bishop(BISHOP, player.getPieceColor(), new Position(0, 2)));
        board.setPieceAt(new Position(0, 5), new Bishop(BISHOP, player.getPieceColor(), new Position(0, 5)));

        board.setPieceAt(new Position(0, 3), new Queen(QUEEN, player.getPieceColor(), new Position(0, 3)));
        board.setPieceAt(new Position(0, 4), new King(KING, player.getPieceColor(), new Position(0, 4)));
    }

    /**
     * Places player two's pieces in their starting location. Player 2 starts at the top of the board.
     */
    private void initPlayerTwoTeam(Player player) {
        for(int column = 0; column < board.getSize(); column++) {
            Position pos = new Position(6, column);
            board.setPieceAt(pos, new UpPawn(toUpperCase(PAWN), player.getPieceColor(), pos));
        }
        board.setPieceAt(new Position(7, 0), new Rook(toUpperCase(ROOK), player.getPieceColor(), new Position(7, 0)));
        board.setPieceAt(new Position(7, 7), new Rook(toUpperCase(ROOK), player.getPieceColor(), new Position(7, 7)));

        board.setPieceAt(new Position(7, 1), new Knight(toUpperCase(KNIGHT), player.getPieceColor(), new Position(7, 1)));
        board.setPieceAt(new Position(7, 6), new Knight(toUpperCase(KNIGHT), player.getPieceColor(), new Position(7, 6)));

        board.setPieceAt(new Position(7, 2), new Bishop(toUpperCase(BISHOP), player.getPieceColor(), new Position(7, 2)));
        board.setPieceAt(new Position(7, 5), new Bishop(toUpperCase(BISHOP), player.getPieceColor(), new Position(7, 5)));

        board.setPieceAt(new Position(7, 3), new Queen(toUpperCase(QUEEN), player.getPieceColor(), new Position(7, 3)));
        board.setPieceAt(new Position(7, 4), new King(toUpperCase(KING), player.getPieceColor(), new Position(7, 4)));
    }
}
