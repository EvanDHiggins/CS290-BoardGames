package hex;

import boardgame.TwoPlayerGame;
import boardgame.Player;
import boardgame.Application;

/**
 * Created by evan on 2/14/16.
 *
 */
public class HexGame extends TwoPlayerGame {

    //boardgame.Player order is maintained by cycling through a queue.
    HexBoard board;

    public HexGame(Player player1, Player player2, int boardSize) {
        super("Hex", player1, player2);
        board = new HexBoard(player1, player2, boardSize);
    }

    @Override
    public void run() {
        while(true) {
            board.printBoard();
            playerTurn(currentPlayer);
            if(currentPlayerHasWon()) {
                playerWins(currentPlayer);
            }
            nextPlayer();
        }
    }

    /**
     * Executes a player's turn until a valid position has been chosen.
     */
    private void playerTurn(Player player) {
        while(true) {
            playerPrompt(player);
            String input = Application.input.nextLine();
            if(input.toLowerCase().equals("exit"))
                exitGame();
            if(board.tryPlayPosition(input, HexPiece.createPiece(player.getPieceColor())))
                return;
            System.out.println("Invalid position, try again.");
        }
    }

    private void exitGame() {
        System.out.println("Thanks for playing!");
        System.exit(0);
    }

    private  void playerPrompt(Player player) {
        System.out.println("Choose a position, " + player.getName() + ".");
        System.out.print("Your piece is " + HexPiece.createPiece(player.getPieceColor()) + ":");
    }

    private void playerWins(Player player) {
        System.out.println("Congratulations, " + player.getName() + "! You won!");
        System.exit(0);
    }

    private boolean currentPlayerHasWon() {
        return board.doesPlayerWin(currentPlayer);
    }
}
