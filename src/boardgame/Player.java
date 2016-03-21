package boardgame;

/**
 * Created by evan on 2/14/16.
 */
public class Player {

    private String name;
    private Piece.PieceColor pieceColor;

    public Player(String name, Piece.PieceColor color) {
        this.name = name;
        this.pieceColor = color;
    }

    public String getName() {
        return name;
    }

    public Piece.PieceColor getPieceColor() {
        return pieceColor;
    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if(!(obj instanceof Player))
//            return false;
//        if(this == obj)
//            return true;
//
//        Player that = (Player)obj;
//        return this.getName().equals(that.getName()) && this.getPiece().equals(that.getPiece());
//    }
}
