package boardgame;

import sun.tools.tree.EqualExpression;

import java.util.Optional;

/**
 * Created by Evan on 3/2/2016.
 */
public class Move {

    private final Position from;
    private final Position to;

    //Some moves involve the capture of a piece.
    private final Optional<Position> capture;

    public Move(Position from, Position to) {
        this.from = from;
        this.to = to;
        this.capture = Optional.empty();
    }

    public Move(Position from, Position to, Position capture) {
        this.from = from;
        this.to = to;
        this.capture = Optional.ofNullable(capture);
    }

    /**
     * Two moves are considered equal if their from and to
     * positions are equal. No attention is paid to capture for
     * the sake of equality comparison.
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Move))
            return false;
        if(this == obj)
            return true;

        final Move that = (Move) obj;

        return this.from.equals(that.from) && this.to.equals(that.to);
    }

    /**
     * I'm a little uncertain if this is an effective way of
     * generating a hashCode, but I think its good enough.
     */
    @Override
    public int hashCode() {
        return from.hashCode() + to.hashCode();
    }

    @Override
    public String toString() {
        return "From: " + from + ", To: " + to;
    }

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }

    public Optional<Position> getCapture() {
        return capture;
    }
}
