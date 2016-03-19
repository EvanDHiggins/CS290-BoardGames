package hex;

import boardgame.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evan on 2/15/16.
 */
public class HexPosition extends Position {

    public HexPosition(int rowIdx, int columnIdx) {
        super(rowIdx, columnIdx);
    }

    /**
     * hex adjacencies are the positions directly adjacent within a
     * row/column to a given position, and the positions adjacent along
     * one of the two diagonals. In this case the forward leaning
     * diagonal is considered an adjacency while the backward leaning
     * diagonal is not.
     */
    public List<HexPosition> getAdjacencies() {
        List<HexPosition> result = new ArrayList<>();
        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                if(i + j != 0)
                    result.add((HexPosition)this.plus(new HexPosition(i, j)));
            }
        }
        return result;
    }


}

