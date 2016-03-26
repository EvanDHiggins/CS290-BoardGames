package boardgame;

import checkers.CheckersGame;
import hex.HexGame;

import java.util.*;

/**
 * Created by evan on 2/12/16.
 *
 */
public class Application {

    List<TwoPlayerGame> games = new ArrayList<>();

    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }

    Application() {
        games = initGames();
    }

    public void run() {
        String userInput;
        System.out.println("What would you like to play?");
        for(int i = 0; i < games.size(); i++) {
            System.out.println((i+1) + ": " + games.get(i).getName());
        }
        do {
            userInput = input.nextLine();
            if(!validInput(userInput))
                System.out.println("Invalid input, try again.");
        } while(!validInput(userInput));

        int choice = Integer.parseInt(userInput);

        games.get(choice - 1).run();
    }

    private boolean validInput(String input) {
        if(!isNumberString(input))
            return false;

        int choice = Integer.parseInt(input);
        return choice >= 1 && choice <= games.size();
    }

    private boolean isNumberString(String str) {
        if(str.isEmpty())
            return false;

        for(int i = 0; i < str.length(); i++) {
            if(!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;
    }

    private List<TwoPlayerGame> initGames() {
        List<TwoPlayerGame> ret = new ArrayList<>();

        ret.add(new CheckersGame(new Player("Black Player", Piece.PieceColor.BLACK),
                                 new Player("Red Player", Piece.PieceColor.RED),
                                 8));

        ret.add(new HexGame(new Player("White Player", Piece.PieceColor.WHITE),
                            new Player("Black Player", Piece.PieceColor.BLACK),
                            11));

        return ret;
    }
}