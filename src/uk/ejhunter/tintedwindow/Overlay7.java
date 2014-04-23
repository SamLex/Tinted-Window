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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Overlay7 extends Overlay {

    private static final long serialVersionUID = 7003031752022343735L;
    private MouseAdapter overlayDragger;

    public Overlay7(Disk disk) {
        super(disk);

        this.setUndecorated(true);

        this.overlayDragger = new OverlayDragger(this);
        this.addMouseListener(overlayDragger);
        this.addMouseMotionListener(overlayDragger);
        this.addMouseWheelListener(overlayDragger);
    }

    @Override
    public void makeVisible() {
        this.setBackground(getDisk().getColour());
        this.setOpacityValue((float) getDisk().getOpacityPercent() / 100);
        this.setOpacity(getOpacity());
        this.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
}
