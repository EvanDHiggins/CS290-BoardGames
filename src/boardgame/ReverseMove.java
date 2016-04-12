package boardgame;

import java.util.Optional;

/**
 * Created by Evan on 4/12/2016.
 */
public class ReverseMove extends Move {

    private Move move;

    public ReverseMove(Move m) {
        super(m.getFrom(), m.getTo());
        move = m;
    }

    @Override
    public Optional<Piece> execute(GameBoard board) {
        move.unexecute(board);
        return Optional.empty();
    }
}
