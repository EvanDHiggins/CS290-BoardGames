package boardgame;

import java.util.Optional;

/**
 * Created by evan on 2/12/16.
 */
public class Tile {

    private char emptyTileChar = '.';

    private Optional<Piece> piece;
    private Position position;

    public Tile(Position position, char emptyTileChar) {
        piece = Optional.empty();
        this.position = position;
        this.emptyTileChar = emptyTileChar;
    }

    public void setPiece(Piece piece) {
        this.piece = Optional.ofNullable(piece);
    }

    public Optional<Piece> getPiece() {
        return piece;
    }

    public Position getPosition() {
        return position;
    }

    public boolean hasPiece(Piece piece) {
        return this.piece.equals(Optional.of(piece));
    }

    public boolean hasTileColor(char c) {
        return this.emptyTileChar == c;
    }

    @Override
    public String toString() {
        return piece.map(Piece::toString)
                    .orElse(Character.toString(emptyTileChar));
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Tile))
            return false;
        if(obj == this)
            return true;

        Tile that = (Tile)obj;
        return this.getPiece().equals(that.getPiece()) && this.getPosition().equals(that.getPosition());
    }

    public boolean isBlank(){
        return !piece.isPresent();
    }
}
