package boardgame;

/**
 * Created by evan on 2/14/16.
 */
public class Player {

    private String name;
    private Piece piece;

    public Player(String name, Piece p) {
        this.name = name;
        this.piece = p;
    }

    public String getName() {
        return name;
    }

    public Piece getPiece() {
        return piece;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Player))
            return false;
        if(this == obj)
            return true;

        Player that = (Player)obj;
        return this.getName().equals(that.getName()) && this.getPiece().equals(that.getPiece());
    }
}
