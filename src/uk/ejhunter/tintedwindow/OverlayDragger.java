/*
 * This file is part of Tinted Window, a program for creating coloured overlays on screen
 * 
 * Copyright (C) 2014 Euan James Hunter <euanhunter117@gmail.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ejhunter.tintedwindow;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class OverlayDragger extends MouseAdapter {

    private final int BORDER_SIZE = 20;
    private final int SCROLL_FACTOR = 5;

    private int startX;
    private int startY;
    private int dragX;
    private int dragY;
    private Sector mouseSector;
    private Overlay overlay;

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

    public OverlayDragger(Overlay overlay) {
        super();
        this.overlay = overlay;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.startX = e.getX();
        this.startY = e.getY();

        this.dragX = e.getXOnScreen();
        this.dragY = e.getYOnScreen();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getComponent() instanceof Overlay) {
            if (e.isShiftDown()) {
                switch (mouseSector) {
                    case NW:
                        overlay.setSize(overlay.getWidth(), overlay.getHeight() - (e.getYOnScreen() - dragY));
                        overlay.setLocation(overlay.getX(), overlay.getY() + (e.getYOnScreen() - dragY));

                        overlay.setSize(overlay.getWidth() - (e.getXOnScreen() - dragX), overlay.getHeight());
                        overlay.setLocation(overlay.getX() + (e.getXOnScreen() - dragX), overlay.getY());
                        break;
                    case N:
                        overlay.setSize(overlay.getWidth(), overlay.getHeight() - (e.getYOnScreen() - dragY));
                        overlay.setLocation(overlay.getX(), overlay.getY() + (e.getYOnScreen() - dragY));
                        break;
                    case NE:
                        overlay.setSize(overlay.getWidth(), overlay.getHeight() - (e.getYOnScreen() - dragY));
                        overlay.setLocation(overlay.getX(), overlay.getY() + (e.getYOnScreen() - dragY));

                        overlay.setSize(overlay.getWidth() + (e.getXOnScreen() - dragX), overlay.getHeight());
                        break;
                    case E:
                        overlay.setSize(overlay.getWidth() + (e.getXOnScreen() - dragX), overlay.getHeight());
                        break;
                    case SE:
                        overlay.setSize(overlay.getWidth(), overlay.getHeight() + (e.getYOnScreen() - dragY));

                        overlay.setSize(overlay.getWidth() + (e.getXOnScreen() - dragX), overlay.getHeight());
                        break;
                    case S:
                        overlay.setSize(overlay.getWidth(), overlay.getHeight() + (e.getYOnScreen() - dragY));
                        break;
                    case SW:
                        overlay.setSize(overlay.getWidth(), overlay.getHeight() + (e.getYOnScreen() - dragY));

                        overlay.setSize(overlay.getWidth() - (e.getXOnScreen() - dragX), overlay.getHeight());
                        overlay.setLocation(overlay.getX() + (e.getXOnScreen() - dragX), overlay.getY());
                        break;
                    case W:
                        overlay.setSize(overlay.getWidth() - (e.getXOnScreen() - dragX), overlay.getHeight());
                        overlay.setLocation(overlay.getX() + (e.getXOnScreen() - dragX), overlay.getY());
                        break;
                    case NONE:
                    default:
                        break;
                }
            } else {
                overlay.setLocation(e.getXOnScreen() - this.startX, e.getYOnScreen() - this.startY);
            }

            this.dragX = e.getXOnScreen();
            this.dragY = e.getYOnScreen();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        int h = overlay.getHeight();
        int w = overlay.getWidth();

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
                    overlay.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
                    break;
                case N:
                    overlay.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                    break;
                case NE:
                    overlay.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
                    break;
                case E:
                    overlay.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                    break;
                case SE:
                    overlay.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
                    break;
                case S:
                    overlay.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
                    break;
                case SW:
                    overlay.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
                    break;
                case W:
                    overlay.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
                    break;
                case NONE:
                default:
                    overlay.setCursor(Cursor.getDefaultCursor());
                    break;
            }
        } else {
            overlay.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.isShiftDown()) {
            overlay.setSize(overlay.getWidth() - (e.getWheelRotation() * SCROLL_FACTOR), overlay.getHeight() - (e.getWheelRotation() * SCROLL_FACTOR));
            overlay.setLocation(overlay.getX() + (int) (e.getWheelRotation() * SCROLL_FACTOR * 0.5f), overlay.getY() + (int) (e.getWheelRotation() * SCROLL_FACTOR * 0.5f));
        }
    }
}
