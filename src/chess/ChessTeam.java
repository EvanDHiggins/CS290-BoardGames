package chess;

import java.util.function.Consumer;

/**
 * Created by evan on 4/15/16.
 */
public class ChessTeam {

    private static final char PAWN = 'p';
    private static final char ROOK = 'r';
    private static final char KNIGHT = 'n';
    private static final char BISHOP = 'b';
    private static final char KING = 'k';
    private static final char QUEEN = 'q';

    private Consumer<Character> nameTransformation;

    public ChessTeam(Consumer<Character> transformation) {
        nameTransformation = transformation;
    }

    public Pawn getPawn() {
    }
}
