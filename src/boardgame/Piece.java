package boardgame;

import java.util.HashSet;
import java.util.Set;

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

    protected Piece() {
        generators = new HashSet<>();
        this.position = new Position();
    }

    protected Piece(Position position) {
        this();
        this.position = position;
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
     * in generators.
     */
    public Set<Move> generateMoves(GameBoard board) {
        Set<Move> moves = new HashSet<>();
        for(IMoveGenerator gen : generators) {
            moves.addAll(gen.generate(board, this));
        }
        return moves;
    }

    @Override
    public String toString() {
        return stringRepr;
    }
}
