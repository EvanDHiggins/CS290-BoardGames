package chess;

import boardgame.Player;
import boardgame.Position;
import boardgame.Tile;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import static javax.swing.SwingUtilities.*;

/**
 * Created by Evan on 4/27/2016.
 */
public class GuiChessLauncher extends ChessGame {
    public GuiChessLauncher(Player playerOne, Player playerTwo) {
        super(playerOne, playerTwo);
        setName("GUI Chess");
    }

    @Override
    public void run() {
        invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndExecuteGUI();
            }
        });
    }

    private void createAndExecuteGUI() {
        JFrame frame = new JFrame("Chess");
        frame.add(new BoardPanel());

        frame.pack();
        frame.setVisible(true);
    }

    public class BoardPanel extends JPanel {
        int rows = board.getSize();
        int columns = board.getSize();

        //There are two empty rows along the top and two empty rows along the bottom.
        int gridSize = board.getSize() + 2;

        public BoardPanel() {
            setLayout(new GridLayout(0, gridSize)); //2 squares of edge padding
            initLayout();
        }

        private void initLayout() {
            for(int i = 0; i < gridSize; i++) {
                add(new JPanel());
            }

            for(int row = rows - 1; row >= 0; row--) {
                add(new JPanel());
                for(int col = 0; col < columns; col++) {
                    add(new TilePanel(board.tileAt(new Position(col, row))));
                }
                add(new JPanel());
            }

            for(int i = 0; i < gridSize; i++) {
                add(new JPanel());
            }
        }
    }

    public class TilePanel extends JPanel implements Observer {
        private Tile observedTile;

        public TilePanel(Tile tile) {
            super();
            observedTile = tile;
            observedTile.addObserver(this);
            setBackground(tile.getColor());
            setOpaque(true);
            updatePieceGraphic();
        }

        @Override
        public void update(Observable o, Object arg) {
            updatePieceGraphic();
            System.out.println("Changed");
        }

        private void updatePieceGraphic() {

        }
    }

    public class RecordsPanel extends JPanel {

    }
}
