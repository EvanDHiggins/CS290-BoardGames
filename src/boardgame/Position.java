package boardgame;

/**
 * Created by Evan on 2/29/2016.
 *
 * Position is an immutable location on a game board. It is a dumb 2D tuple with
 * a few extra tools defined. It contains no knowledge of the board it is used with.
 */
public class Position {

    protected final int x;
    protected final int y;

    public Position() {
        this(0, 0);
    }

    public Position(int rowIdx, int columnIdx) {
        this.y = rowIdx;
        this.x = columnIdx;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position plus(Position that) {
        return new Position(this.getY() + that.getY(), this.getX() + that.getX());
    }

    @Override
    public String toString() {
        return String.format("(%1d, %2d)", getY(), getX());
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Position))
            return false;
        if(obj == this)
            return true;

        Position that = (Position)obj;
        return this.getY() == that.getY() && this.getX() == that.getX();
    }

    @Override
    public int hashCode() {
        int p1 = 37;
        int p2 = 47;
        int n = getY();
        int q = getX();
        if(n >= 0)
            n += 1;
        if(q >= 0)
            q += 1;
        return n*p1 + q*p2;
    }
}
