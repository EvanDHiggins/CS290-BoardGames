package boardgame;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by evan on 2/14/16.
 */
public abstract class Piece {

    public enum PieceColor {
        RED,
        BLACK,
        WHITE
    }

    protected String stringRepr;
    protected Set<IMoveGenerator> generators;
    protected Position position;
    protected PieceColor color;
    protected boolean hasMoved;

    protected Piece() {
        generators = new HashSet<>();
        this.position = new Position();
    }

    protected Piece(PieceColor color, Position position) {
        this();
        this.position = position;
        this.color = color;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position pos) {
        this.position = pos;
    }

    public boolean matchesColor(Piece piece) {
        return piece.color == this.color;
    }

    public boolean matchesColor(PieceColor c) {
        return this.color == c;
    }

    /**
     * Extends the piece's set of generators. Usable for
     * piece promotion among other things.
     */
    protected void addGenerator(IMoveGenerator gen) {
        this.generators.add(gen);
    }

    /**
     * Returns the union of moves from each IMoveGenerator
     * in generators. This is a very easily extensible movement
     * system and allows me to compose simple move generators
     * to create more complex movement behavior. For instance,
     * Kings are just a combination of both Up and Down checkers
     * movement generators.
     */
    public Set<Move> generateMoves(GameBoard board) {
        Set<Move> moves = new HashSet<>();
        for(IMoveGenerator gen : generators) {
            moves.addAll(gen.generate(board, this));
        }
        return moves;
    }

    public Set<Move> generateCaptures(GameBoard board) {
        return generateMoves(board).stream()
                    .filter(Move::hasCapture)
                    .collect(Collectors.toSet());
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void moved() {
        hasMoved = true;
    }

    @Override
    public String toString() {
        return stringRepr;
    }
}
