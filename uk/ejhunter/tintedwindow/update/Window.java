package uk.ejhunter.tintedwindow.update;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame {

    protected Window(Dimension size, Color colour) {
        super("Tinted Window");

        this.setUndecorated(true);
        this.setSize(size);
        this.setLocationByPlatform(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setBackground(colour);
        this.setVisible(true);

        MouseAdapter drag = new Dragger();
        this.addMouseListener(drag);
        this.addMouseMotionListener(drag);
        this.addMouseWheelListener(drag);
    }

    private class Dragger extends MouseAdapter {

        private static final int BORDER_SIZE = 20;
        private static final int SCROLL_FACTOR = 5;

        private int startX;
        private int startY;

        private int dragX;
        private int dragY;

        private Sector mouseSector;

        @Override
        public void mousePressed(MouseEvent e) {
            this.startX = e.getX();
            this.startY = e.getY();

            this.dragX = e.getXOnScreen();
            this.dragY = e.getYOnScreen();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (e.isShiftDown()) {
                switch (mouseSector) {
                    case NW:
                        setSize(getWidth(), getHeight() - (e.getYOnScreen() - dragY));
                        setLocation(getX(), getY() + (e.getYOnScreen() - dragY));

                        setSize(getWidth() - (e.getXOnScreen() - dragX), getHeight());
                        setLocation(getX() + (e.getXOnScreen() - dragX), getY());
                        break;
                    case N:
                        setSize(getWidth(), getHeight() - (e.getYOnScreen() - dragY));
                        setLocation(getX(), getY() + (e.getYOnScreen() - dragY));
                        break;
                    case NE:
                        setSize(getWidth(), getHeight() - (e.getYOnScreen() - dragY));
                        setLocation(getX(), getY() + (e.getYOnScreen() - dragY));

                        setSize(getWidth() + (e.getXOnScreen() - dragX), getHeight());
                        break;
                    case E:
                        setSize(getWidth() + (e.getXOnScreen() - dragX), getHeight());
                        break;
                    case SE:
                        setSize(getWidth(), getHeight() + (e.getYOnScreen() - dragY));

                        setSize(getWidth() + (e.getXOnScreen() - dragX), getHeight());
                        break;
                    case S:
                        setSize(getWidth(), getHeight() + (e.getYOnScreen() - dragY));
                        break;
                    case SW:
                        setSize(getWidth(), getHeight() + (e.getYOnScreen() - dragY));

                        setSize(getWidth() - (e.getXOnScreen() - dragX), getHeight());
                        setLocation(getX() + (e.getXOnScreen() - dragX), getY());
                        break;
                    case W:
                        setSize(getWidth() - (e.getXOnScreen() - dragX), getHeight());
                        setLocation(getX() + (e.getXOnScreen() - dragX), getY());
                        break;
                    case NONE:
                    default:
                        break;
                }

                this.dragX = e.getXOnScreen();
                this.dragY = e.getYOnScreen();
            } else if (e.isControlDown()) {
                setLocation(e.getXOnScreen() - this.startX, e.getYOnScreen() - this.startY);
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            int h = getHeight();
            int w = getWidth();

            if (x < BORDER_SIZE && y < BORDER_SIZE)
                mouseSector = Sector.NW;
            else if (x > BORDER_SIZE && x < (w - BORDER_SIZE) && y < BORDER_SIZE)
                mouseSector = Sector.N;
            else if (x > (w - BORDER_SIZE) && y < BORDER_SIZE)
                mouseSector = Sector.NE;
            else if (x > (w - BORDER_SIZE) && y > BORDER_SIZE && y < (h - BORDER_SIZE))
                mouseSector = Sector.E;
            else if (x > (w - BORDER_SIZE) && y > (h - BORDER_SIZE))
                mouseSector = Sector.SE;
            else if (x > BORDER_SIZE && x < (w - BORDER_SIZE) && y > (h - BORDER_SIZE))
                mouseSector = Sector.S;
            else if (x < BORDER_SIZE && y > (h - BORDER_SIZE))
                mouseSector = Sector.SW;
            else if (x < BORDER_SIZE && y > BORDER_SIZE && y < (h - BORDER_SIZE))
                mouseSector = Sector.W;
            else
                mouseSector = Sector.NONE;

            if (e.isShiftDown()) {
                switch (mouseSector) {
                    case NW:
                        setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
                        break;
                    case N:
                        setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                        break;
                    case NE:
                        setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
                        break;
                    case E:
                        setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                        break;
                    case SE:
                        setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
                        break;
                    case S:
                        setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
                        break;
                    case SW:
                        setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
                        break;
                    case W:
                        setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
                        break;
                    case NONE:
                    default:
                        setCursor(Cursor.getDefaultCursor());
                        break;
                }
            } else if (e.isControlDown()) {
                setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            } else {
                setCursor(Cursor.getDefaultCursor());
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (e.isShiftDown()) {
                switch (mouseSector) {
                    case NW:
                        setLocation(e.getXOnScreen(), e.getYOnScreen());
                        break;
                    case N:
                        break;
                    case NE:
                        break;
                    case E:
                        break;
                    case SE:
                        break;
                    case S:
                        break;
                    case SW:
                        break;
                    case W:
                        break;
                    case NONE:
                    default:
                        break;
                }
            } else {
                mouseSector = Sector.NONE;
            }
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (e.isShiftDown()) {
                setSize(getWidth() - (e.getWheelRotation() * SCROLL_FACTOR), getHeight() - (e.getWheelRotation() * SCROLL_FACTOR));
                setLocation(getX() + (int) (e.getWheelRotation() * SCROLL_FACTOR * 0.5f), getY() + (int) (e.getWheelRotation() * SCROLL_FACTOR * 0.5f));
            }
        }
    }

    private enum Sector {
        NONE,
        N,
        E,
        S,
        W,
        NE,
        NW,
        SE,
        SW
    }
}
