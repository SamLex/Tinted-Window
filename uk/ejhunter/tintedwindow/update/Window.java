package uk.ejhunter.tintedwindow.update;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
            this.dragY = e.getXOnScreen();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (e.isShiftDown()) {
                setSize(getWidth() + (e.getXOnScreen() - dragX), getHeight());

                this.dragX = e.getXOnScreen();
                this.dragY = e.getXOnScreen();
            } else if (e.isControlDown()) {
                setLocation(e.getXOnScreen() - this.startX, e.getYOnScreen() - this.startY);
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // System.out.println("Clicked");
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if (e.isShiftDown()) {
                int x = e.getX();
                int y = e.getY();
                int h = getHeight();
                int w = getWidth();

                // if() //TODO: mouse sector choosing

                setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
            } else if (e.isControlDown()) {
                setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            } else {
                setCursor(Cursor.getDefaultCursor());
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
