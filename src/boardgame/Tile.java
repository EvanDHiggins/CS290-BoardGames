package boardgame;

import java.awt.*;
import java.util.*;

/**
 * Created by evan on 2/12/16.
 *
 * I think my decision to use Optional<Piece> to represent a
 * piece was ultimately a bad idea. Just using the null object
 * pattern would have resulted in a lot less headaches. As it
 * stands I have to do lots of funky map stuff for even basic
 * operations on tiles. It just clutters up the code. I also
 * think there's a pretty good argument for not having tiles
 * at all and just representing a board as a 2D array of
 * Pieces with NullPiece representing an empty space. But
 * hindsight is 20/20.
 */
public class Tile implements MyObservable {

    private char emptyTileChar = '.';

    private Optional<Piece> piece;
    private Position position;

    private Color color = new Color(100, 100, 100);

    public Tile(Position position, char emptyTileChar) {
        piece = Optional.empty();
        this.position = position;
        this.emptyTileChar = emptyTileChar;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setPiece(Piece piece) {
        this.piece = Optional.ofNullable(piece);
        this.piece.map(p -> {
            p.setPosition(this.position);
            return null;
        });
        notifyObservers();
    }

    public Optional<Piece> getPiece() {
        return piece;
    }

    public Position getPosition() {
        return position;
    }

    public boolean hasTileColor(char c) {
        return this.emptyTileChar == c;
    }


    public boolean pieceMatchesColor(Piece piece) {
        return this.piece.map(p -> p.matchesColor(piece)).orElse(false);
    }

    public void clearPiece() {
        piece = Optional.empty();
        notifyObservers();
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

    private Set<MyObserver> observers = new HashSet<>();

    public void addObserver(MyObserver o) {
        observers.add(o);
    }

    public void notifyObservers() {
        for(MyObserver o : observers) {
            o.update(this, null);
        }
    }

    public void removeObserver(MyObserver o) {
        observers.remove(o);
    }
}
