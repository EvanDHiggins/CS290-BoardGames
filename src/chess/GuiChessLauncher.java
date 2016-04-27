package chess;

import boardgame.*;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

import static javax.swing.SwingUtilities.*;

/**
 * Created by Evan on 4/27/2016.
 */
public class GuiChessLauncher extends ChessGame {

    private List<Position> playerClicks = new ArrayList<>();

    private TilePanel selectedTile = null;

    private RecordsPanel recordsPanel;

    public GuiChessLauncher(Player playerOne, Player playerTwo) {
        super(playerOne, playerTwo);
        setName("GUI Chess");
    }

    @Override
    public void run() {
        invokeLater(this::createAndExecuteGUI);
    }

    private void createAndExecuteGUI() {
        JFrame frame = new JFrame("Chess");
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
        recordsPanel = new RecordsPanel();
        frame.add(new BoardPanel());
        frame.add(recordsPanel);
        frame.setPreferredSize(new Dimension(740, 640));
        frame.setResizable(false);

        frame.pack();
        frame.setVisible(true);
    }

    private void tileClicked(TilePanel panel) {
        if(panel == selectedTile) {
            selectedTile = null;
            panel.borderEnabled(false);
            return;
        }

        if(selectedTile == null) {
            selectedTile = panel;
            panel.borderEnabled(true);
            return;
        }

        Move playerMove = new Move(selectedTile.getTile().getPosition(), panel.getTile().getPosition());
        if(attemptPlayerMove(playerMove))
            nextPlayer();

        selectedTile.borderEnabled(false);
        selectedTile = null;
    }

    public class BoardPanel extends JPanel {
        int rows = board.getSize();
        int columns = board.getSize();

        //There are two empty rows along the top and two empty rows along the bottom.
        int gridSize = board.getSize() + 2;

        public BoardPanel() {
            setLayout(new GridLayout(0, gridSize)); //2 squares of edge padding
            setPreferredSize(new Dimension(640, 640));
            initLayout();
        }

        private void initLayout() {
            for(int i = 0; i < gridSize; i++) {
                add(new JPanel());
            }

            for(int row = rows - 1; row >= 0; row--) {
                add(new NumberedJPanel(row));
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

    public class TilePanel extends JPanel implements MyObserver {
        private Tile observedTile;
        //private Border highlightBorder = BorderFactory.createLineBorder(Color.RED);
        private Border highlightBorder = BorderFactory.createLineBorder(Color.RED, 3);
        private boolean selected = false;
        private JLabel pieceImageLabel = null;

        public TilePanel(Tile tile) {
            super();
            setLayout(new BorderLayout());
            observedTile = tile;
            observedTile.addObserver(this);
            setBackground(tile.getColor());
            setOpaque(true);
            updatePieceGraphic();

            addMouseListener(new MouseClickListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    repaint();
                    tileClicked((TilePanel)e.getComponent());
                    if(!observedTile.getPiece().isPresent())
                        return;
                }
            });
        }

        public void borderEnabled(boolean enable) {
            if(enable)
                setBorder(BorderFactory.createLineBorder(Color.RED, 3));
            else
                setBorder(BorderFactory.createLineBorder(observedTile.getColor(), 3));
        }

        public Tile getTile() {
            return observedTile;
        }

        @Override
        public void update(MyObservable o, Object arg) {
            updatePieceGraphic();
        }

        private void updatePieceGraphic() {
            if(pieceImageLabel != null)
                remove(pieceImageLabel);

            pieceImageLabel = observedTile.getPiece().map(PieceImageLoader::getImage)
                                                     .map(img -> new JLabel(new ImageIcon(img)))
                                                     .orElse(new JLabel());
            add(pieceImageLabel, BorderLayout.CENTER);
            revalidate();
            repaint();
        }
    }

    public class RecordsPanel extends JPanel implements MyObserver {
        JTextArea moveDisplay;
        public RecordsPanel() {
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(100, 640));
            add(new JLabel("Previous Moves"), BorderLayout.PAGE_START);
            moveDisplay = new JTextArea();
            moveDisplay.setEditable(false);
            add(moveDisplay, BorderLayout.CENTER);
            oldMoveStack.addObserver(this);
        }

        @Override
        public void update(MyObservable o, Object arg) {
            Stack<Move> stack = (Stack<Move>)arg;
            moveDisplay.setText("");
            for(Move m : stack) {
                moveDisplay.append(m.toString());
            }
        }
    }

    public class NumberedJPanel extends JPanel {
        public NumberedJPanel(int number) {
            super();
            setLayout(new BorderLayout());
            add(new JLabel(Integer.toString(number)), BorderLayout.CENTER);
        }
    }
}
