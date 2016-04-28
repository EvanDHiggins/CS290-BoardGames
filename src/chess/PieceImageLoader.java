package chess;

import boardgame.Piece;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Evan on 4/27/2016.
 *
 * Loads piece images on request from file.
 */
public class PieceImageLoader {

    private static final Map<String, String> pieceMap = initPieceMap();

    static Map<String, String> initPieceMap() {
        Map<String, String> map = new HashMap<>();

        map.put(ChessGame.PAWN, "pawn");
        map.put(ChessGame.BISHOP, "bishop");
        map.put(ChessGame.QUEEN, "queen");
        map.put(ChessGame.KING, "king");
        map.put(ChessGame.KNIGHT, "knight");
        map.put(ChessGame.ROOK, "rook");

        map.put(ChessGame.PAWN.toUpperCase(), "pawn");
        map.put(ChessGame.BISHOP.toUpperCase(), "bishop");
        map.put(ChessGame.QUEEN.toUpperCase(), "queen");
        map.put(ChessGame.KING.toUpperCase(), "king");
        map.put(ChessGame.KNIGHT.toUpperCase(), "knight");
        map.put(ChessGame.ROOK.toUpperCase(), "rook");

        return map;
    }

    public static BufferedImage getImage(Piece piece) {

        StringBuilder imagePath = new StringBuilder();
        imagePath.append("src" + File.separator + "chess" + File.separator + "chessimages" + File.separator);
        if(piece.getColor() == Piece.PieceColor.BLACK) {
            imagePath.append("black_");
        } else {
            imagePath.append("white_");
        }

        imagePath.append(pieceMap.get(piece.getRepresentation()));

        imagePath.append(".png");

        BufferedImage ret = null;
        try {
            ret = ImageIO.read(new File(imagePath.toString()));
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Couldn't find image for piece at postition: " + piece.getPosition());
            System.out.println("Exiting.");
            System.exit(0);
        }
        return ret;
    }
}
