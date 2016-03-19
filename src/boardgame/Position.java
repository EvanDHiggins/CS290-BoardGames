package boardgame;

/**
 * Created by Evan on 2/29/2016.
 *
 * Position is an immutable location on a game board. It is a dumb 2D tuple with
 * a few extra tools defined. It contains no knowledge of the board it is used with.
 */
public class Position {

    protected int columnIdx;
    protected int rowIdx;

    public Position(int rowIdx, int columnIdx) {
        this.rowIdx = rowIdx;
        this.columnIdx = columnIdx;
    }

    public int column() {
        return columnIdx;
    }

    public int row() {
        return rowIdx;
    }

    public Position plus(Position that) {
        return new Position(this.row() + that.row(), this.column() + that.column());
    }

    @Override
    public String toString() {
        return String.format("(%1d, %2d)", row(), column());
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Position))
            return false;
        if(obj == this)
            return true;

        Position that = (Position)obj;
        return this.row() == that.row() && this.column() == that.column();
    }

    @Override
    public int hashCode() {
        int p1 = 37;
        int p2 = 47;
        int n = row();
        int q = column();
        if(n >= 0)
            n += 1;
        if(q >= 0)
            q += 1;
        return n*p1 + q*p2;
    }
}
