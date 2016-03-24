package hex;

import boardgame.GameBoard;
import boardgame.Piece;
import boardgame.Player;
import boardgame.Tile;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by evan on 2/12/16.
 *
 * hex.HexBoard encapsulates the logic of a hex game board, the console IO, and
 * the algorithms to determine a winner.
 */
public class HexBoard extends GameBoard {

    private static char emptyTile = '.';

    Tile board[][];

    Player leftToRightPlayer;
    Player topToBottomPlayer;

    public HexBoard(Player player1, Player player2, int boardSize) {
        super(boardSize);

        leftToRightPlayer = player1;
        topToBottomPlayer = player2;

        board = new Tile[this.getSize()][this.getSize()];

        for(int i = 0; i < this.getSize(); ++i) {
            for(int j = 0; j < this.getSize(); ++j) {
                board[i][j] = new Tile(new HexPosition(i, j), emptyTile);
            }
        }
    }

    /**
     * This function is ugly.
     */
    public void printBoard() {
        System.out.print(String.format("%1$" + (getSize() +2) + "s ", " "));
        for(int i = 0; i < getSize(); ++i) {
            System.out.print(HexPiece.createPiece(topToBottomPlayer.getPieceColor()) + " ");
        }
        System.out.println();
        for(int i = getSize() - 1; i >= 0; i--) {
            int lineNumber = i + 1;
            System.out.print(String.format("%1$" + lineNumber + "s " + HexPiece.createPiece(leftToRightPlayer.getPieceColor()) + " ", lineNumber));

            for(Tile tile : board[i]) {
                System.out.print(tile.toString() + " ");
            }

            System.out.print(" " + HexPiece.createPiece(leftToRightPlayer.getPieceColor()));
            System.out.println();
        }

        System.out.print("    ");
        for(int i = 0; i < getSize(); ++i) {
            System.out.print(HexPiece.createPiece(topToBottomPlayer.getPieceColor()) + " ");
        }
        System.out.println();

        System.out.print("    ");
        for(int i = 0; i < getSize(); ++i) {
            System.out.print((char)('A' + i) + " ");
        }
        System.out.println();
    }

    /**
     * Parses the input string and attempts to place the passed piece
     * at the parsed location if the location exists.
     * @return If boardgame.Piece was successfully placed.
     */
    public boolean tryPlayPosition(String positionString, Piece piece) {
        if(!matchesPattern(positionString))
            return false;

        Optional<HexPosition> position = parseInput(positionString);

        return position.map(pos -> {
                    setPosition(pos, piece);
                    return true;
               }).orElse(false);
    }

    public boolean doesPlayerWin(Player player) {
        if(player.equals(leftToRightPlayer)) {
            return regionsConnected(getColumn(0), getColumn(getSize() - 1), player.getPieceColor());
        } else if(player.equals(topToBottomPlayer)) {
            return regionsConnected(Arrays.asList(board[0]), Arrays.asList(board[getSize() - 1]), player.getPieceColor());
        } else {
            System.out.println("That player is not involved in this game.");
            return false;
        }
    }

    public Tile getTile(HexPosition bp) {
        return board[bp.row()][bp.column()];
    }

    private List<Tile> getColumn(int colNum) {
        List<Tile> column = new ArrayList<>();
        for(int i = 0; i < getSize(); i++) {
            column.add(board[i][colNum]);
        }
        return column;
    }

    /**
     * Determines if two arbitrary lists of tiles "from", "to" are connected by
     * a path of piece "pieceType" through the recursive function pathBetween.
     */
    private boolean regionsConnected(List<Tile> from, List<Tile> to, Piece.PieceColor pieceType) {
        to.stream().filter(tile -> tile.getPiece().map(p -> p.matchesColor(pieceType)).orElse(false))
                   .collect(Collectors.toList());

        for(Tile t : from) {
            if(pathBetween((HexPosition)t.getPosition(), to, pieceType, new ArrayList<>()))
                return true;
        }
        return false;
    }

    private boolean pathBetween(HexPosition from, List<Tile> to, Piece.PieceColor pieceType, List<HexPosition> searched) {
        if(!withinBounds(from)) return false;
        if(searched.contains(from)) return false;

        Tile fromTile = getTile(from);
        if(fromTile.isBlank()) return false;
        if(fromTile.getPiece().get().matchesColor(pieceType)) return false;

        List<HexPosition> adjacencies = from.getAdjacencies();
        if(to.contains(fromTile)) return true;

        searched.add(from);
        for(HexPosition bp : adjacencies) {
            if(pathBetween(bp, to, pieceType, searched))
                return true;
        }

        return false;
    }

    /**
     * setPosition does now bounds checking. It assumes that pos is a
     * position which fits on the board which can be checked with validPosition.
     */
    private void setPosition(HexPosition pos, Piece piece) {
        board[pos.row()][pos.column()].setPiece(piece);
    }

    /**
     * If a wrapped hex.HexPosition is returned it is guaranteed to fit on
     * the board. Strings which parse correctly are still considered invalid
     * if they do not fit on the board.
     */
    private Optional<HexPosition> parseInput(String positionString) {
        int columnIdx = positionString.toUpperCase().charAt(0) - 'A';
        int rowIdx = Integer.parseInt(positionString.substring(1, positionString.length())) - 1;
        System.out.println(rowIdx);

        HexPosition position = new HexPosition(rowIdx, columnIdx);

        if(validPosition(position))
            return Optional.of(position);
        else
            return Optional.empty();
    }

    private boolean matchesPattern(String positionString) {
        Pattern regex = Pattern.compile("[A-Za-z]\\d{1,2}");
        return regex.matcher(positionString).matches();
    }

    private boolean validPosition(HexPosition bp) {
        return withinBounds(bp) && tileIsEmpty(bp);
    }

    private boolean tileIsEmpty(HexPosition bp) {
        return board[bp.row()][bp.column()].isBlank();
    }

    private boolean withinBounds(HexPosition bp) {
        return bp.column() >= 0 && bp.column() < getSize() &&
               bp.row() >= 0 && bp.row() < getSize();
    }
}
