package chess;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Evan on 4/27/2016.
 *
 * The default implementation requires so many methods and clutters up important code.
 * This just implements everything but mouseClicked as a no-op.
 */
public abstract class MouseClickListener implements MouseListener {

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
