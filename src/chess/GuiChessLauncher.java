package chess;

import boardgame.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.*;

import static javax.swing.SwingUtilities.*;

/**
 * Created by Evan on 4/27/2016.
 */
public class GuiChessLauncher extends ChessGame {

    private TilePanel selectedTile = null;

    private JFrame frame;

    public GuiChessLauncher(Player playerOne, Player playerTwo) {
        super(playerOne, playerTwo);
        setName("GUI Chess");
    }

    @Override
    public void run() {
        invokeLater(this::initializeGUI);
    }

    private void initializeGUI() {
        frame = new JFrame("Chess");
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
        RecordsPanel recordsPanel = new RecordsPanel();
        frame.add(new BoardPanel());
        frame.add(recordsPanel);
        frame.setPreferredSize(new Dimension(760, 640));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * This is the main driver for the game logic. It forms a move based on player
     * clicks by ignoring clicks which cannot yield a move until two have been entered.
     */
    private void tileClicked(TilePanel panel) {
        if(panel == selectedTile) {
            selectedTile = null;
            panel.borderEnabled(false);
            return;
        }

        if(selectedTile == null) {
            if(panel.getTile().isBlank())
                return;
            if(!panel.getTile().pieceMatchesColor(currentPlayer.getPieceColor()))
                return;
            selectedTile = panel;
            panel.borderEnabled(true);
            return;
        }

        Move playerMove = new Move(selectedTile.getTile().getPosition(), panel.getTile().getPosition());
        Optional<String> errorMsg = attemptPlayerMove(playerMove);
        if(errorMsg.isPresent())
            JOptionPane.showMessageDialog(frame, errorMsg.get());
        else
            nextPlayer();

        selectedTile.borderEnabled(false);
        selectedTile = null;
        if(hasLost(currentPlayer)) {
            JOptionPane.showMessageDialog(frame, "Congratulations " + otherPlayer.getName() + "! You win!");
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }

    }

    /**
     * JPanel subclass that represents a chessboard.
     */
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
                if(i >= 1 && i <= gridSize - 2) {
                    JPanel columnLabel = new JPanel();
                    columnLabel.setLayout(new GridBagLayout());
                    columnLabel.add(new JLabel(Character.toString((char)(i + 'a' - 1))));
                    add(columnLabel);
                } else {
                    add(new JPanel());
                }
            }

            for(int row = rows - 1; row >= 0; row--) {
                JPanel rowLabel = new JPanel();
                rowLabel.setLayout(new GridBagLayout());
                rowLabel.add(new JLabel(Integer.toString(row + 1)));
                add(rowLabel);
                for(int col = 0; col < columns; col++) {
                    add(new TilePanel(board.tileAt(new Position(col, row))));
                }
                rowLabel = new JPanel();
                rowLabel.setLayout(new GridBagLayout());
                rowLabel.add(new JLabel(Integer.toString(row + 1)));
                add(rowLabel);
            }

            for(int i = 0; i < gridSize; i++) {
                if(i >= 1 && i <= gridSize - 2) {
                    JPanel columnLabel = new JPanel();
                    columnLabel.setLayout(new GridBagLayout());
                    columnLabel.add(new JLabel(Character.toString((char)(i + 'a' - 1))));
                    add(columnLabel);
                } else {
                    add(new JPanel());
                }
            }
        }
    }

    /**
     * There is a tile panel for each tile on the board. They serve as mainly a means
     * of observing an individual tile to separate that from the board.
     */
    public class TilePanel extends JPanel implements MyObserver {
        private Tile observedTile;
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
                    tileClicked((TilePanel)e.getComponent());
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
        public void update(MyObservable o) {
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
            add(new JLabel("Previous Moves"), BorderLayout.PAGE_START);
            moveDisplay = new JTextArea();
            moveDisplay.setEditable(false);
            add(new JScrollPane(moveDisplay,
                                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
            oldMoveStack.addObserver(this);
        }

        @Override
        public void update(MyObservable o) {
            Stack<Move> stack = (ObservableStack<Move>)o;
            moveDisplay.setText("");
            for(Move m : stack) {
                moveDisplay.append(m.toString() + "\n");
            }
        }
    }
}
