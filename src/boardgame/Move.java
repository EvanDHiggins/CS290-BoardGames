package boardgame;

import java.util.Optional;

/**
 * Created by Evan on 3/2/2016.
 */
public class Move {

    private final Position from;
    private final Position to;

    //Flag indicating if the move has been executed. Unmove is a no-op if this is false.
    boolean executed = false;

    //Some moves involve the capture of a piece.
    private Optional<Position> capture;

    /**
     * These are the values needed to unexecute a move.
     */
    private Optional<Piece> captured;
    private Optional<Piece> originalFrom;
    private Optional<Piece> originalTo;

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
     * For chess I realized that it becomes very convenient if moves follow
     * the command pattern. Being able to contain the logic of a move within a
     * move makes it simple to add arbitrarily complex moves (important for castling)
     * and not clutter up my main game loop with specific move logic. It also
     * gives me the ability to incorporate and "unmove" method which makes
     * determining check easier as well.
     *
     * It is assumed that to, from, and capture are all within bounds of the board.
     * @param board the board which is being operated on.
     * @return The Captured piece, if any.
     */
    public Optional<Piece> execute(GameBoard board) {
        executed = true;
        captured = capture.map(board::getPieceAt).orElse(Optional.empty());
        capture.map(pos -> {board.clearTile(pos); return null;});
        originalTo = board.getPieceAt(to);
        originalFrom = board.getPieceAt(from);
        board.clearTile(to);
        board.getPieceAt(from).map(p -> {
            board.setPieceAt(to, p);
            board.clearTile(from);
            return null;
        });
        return captured;
    }

    public void unexecute(GameBoard board) {
        if(!executed)
            return;
        originalFrom.map(piece -> {
            board.setPieceAt(from, piece);
            return null;
        });
        if(originalTo.isPresent()) {
            originalTo.ifPresent(piece -> board.setPieceAt(to, piece));
        } else {
            board.clearTile(to);
        }
        capture.ifPresent(pos -> captured.ifPresent(piece -> board.setPieceAt(pos, piece)));
    }

    public void setCapture(Position p) {
        this.capture = Optional.ofNullable(p);
    }

    public void setCapture(Optional<Position> p) {
        this.capture = p;
    }

    /**
     * Returns if this move has a capture at the given position
     */
    public boolean capturesAt(Position position) {
        return capture.map(pos -> pos.equals(position)).orElse(false);
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
        return "From: " + from + ", To: " + to + capture.map(c -> " Capture: " + c.toString()).orElse("");
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

    public boolean hasCapture() {
        return capture.isPresent();
    }
}
