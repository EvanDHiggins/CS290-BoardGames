package boardgame;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by evan on 2/14/16.
 */
public abstract class Piece {

    protected String stringRepr;
    protected Set<IMoveGenerator> generators;
    protected Position position;

    protected Piece() {
        generators = new HashSet<>();
    }

    protected Piece(Position position) {
        this();
        this.position = position;
    }

    public Position getPosition() {
        return position;
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
    protected Set<Move> generateMoves(GameBoard board) {
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

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Piece))
            return false;
        if(this == obj)
            return true;

        Piece that = (Piece)obj;
        return this.toString().equals(that.toString());
    }
}
