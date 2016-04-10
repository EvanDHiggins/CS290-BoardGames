package chess;

import boardgame.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by evan on 4/9/16.
 */
public class LinearMoveGenerator implements IMoveGenerator {
    @Override
    public Set<Move> generate(GameBoard board, Piece piece) {
        Set<Move> ret = new HashSet<>();

//        for(int i = 0; i < board.getSize(); i++) {
//            ret.add(new Move(piece.getPosition(), new Position(piece.getPosition().row(), i)));
//            ret.add(new Move(piece.getPosition(), new Position(i, piece.getPosition().column())));
//        }

        for(int i = 1; i < board.getSize() - 1; i++) {
            Position to = new Position(piece.getPosition().row(), piece.getPosition().column() + i);
            if(!board.withinBounds(to))
                break;
            Move mv = new Move(piece.getPosition(), to);
            if(board.pieceAt(to)) {
                board.getPieceAt(to).map(p -> {
                    if(!piece.matchesColor(p)) {
                        mv.setCapture(to);
                        ret.add(mv);
                    }
                    return null;
                });
                break;
            }
            ret.add(mv);
        }


        for(Move m : ret) {
            System.out.println(m);
        }

        System.out.println();
        System.out.println();
        System.out.println();


        for(Move m : ret) {
            System.out.println(m);
        }

        return ret;
    }
}
