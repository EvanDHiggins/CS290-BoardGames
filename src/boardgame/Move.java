package boardgame;

import java.util.Optional;

/**
 * Created by Evan on 3/2/2016.
 */
public class Move {

    private Position from;
    private Position to;

    //Some moves involve the capture of a piece. This contains the position of the
    //captured piece if it exists.
    private Optional<Position> capture;

    public Move(Position from, Position to) {
        this.from = from;
        this.to = to;
        this.capture = Optional.empty();
    }

    public Move(Position from, Position to, Position capture) {
        this(from, to);
        this.capture = Optional.ofNullable(capture);
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
