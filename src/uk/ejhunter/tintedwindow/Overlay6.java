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

import java.awt.event.MouseEvent;

import com.sun.awt.AWTUtilities;

public class Overlay6 extends Overlay {

    private static final long serialVersionUID = -4098419824066389063L;

    public Overlay6(Disk disk) {
        super(disk);
    }

    @Override
    public void makeVisible() {
        this.setBackground(getDisk().getColour());
        this.setOpacityValue((float) getDisk().getOpacityPercent() / 100);
        AWTUtilities.setWindowOpacity(this, getOpacity());
        this.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
}
