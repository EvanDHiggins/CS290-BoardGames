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

        for(int i = 0; i < board.getSize(); i++) {
            ret.add(new Move(piece.getPosition(), new Position(piece.getPosition().row(), i)));
            ret.add(new Move(piece.getPosition(), new Position(i, piece.getPosition().column())));
        }

        for(Move m : ret) {
            System.out.println(m);
        }

        System.out.println();
        System.out.println();
        System.out.println();

        ret = ret.stream().filter(mv -> !mv.getFrom().equals(mv.getTo()))
                          .collect(Collectors.toSet());

        for(Move m : ret) {
            System.out.println(m);
        }

        return ret;
    }
}
