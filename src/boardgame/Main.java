package boardgame;

import checkers.BlackChecker;
import checkers.CheckersGame;
import checkers.RedChecker;
import hex.BlackHexPiece;
import hex.HexGame;
import hex.WhiteHexPiece;

import java.util.*;

/**
 * Created by evan on 2/12/16.
 */
public class Main {

    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        TwoPlayerGame game = null;
        while(game == null) {
            System.out.println("(1) hex");
            System.out.println("(2) checkers");
            System.out.print("Enter your choice of game: ");
            String choice = Main.input.nextLine();
            if(choice.equals("1")) {
                game = new HexGame(new Player("White Player", new WhiteHexPiece()),
                                   new Player("Black Player", new BlackHexPiece()),
                                   11);
            } else if(choice.equals("2")) {
                game = new CheckersGame(new Player("Black Player", new BlackChecker()),
                                    new Player("Red Player", new RedChecker()),
                                    8);
            }
            if(game == null)
                System.out.println("Invalid choice. Try again.");
        }
        game.run();
    }
}
