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

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class Advanced extends Frame implements MouseListener {

    private static final long serialVersionUID = 2950924051052762381L;
    private JButton okButton, cancelButton;
    private JComboBox<Integer> redBox, greenBox, blueBox;
    private JComboBox<String> opacityBox;
    private JLabel colourLabel, opacityLabel;
    private Disk disk;
    private Options options;
    
    public Advanced(Options options, Disk disk) {
        super("Tinted Window - Advanced Options");

        this.disk = disk;
        this.options = options;
        
        this.setSize(400, 190);
        this.setResizable(false);
        this.setLocationByPlatform(true);
        this.setLayout(null);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                makeInvisible();
            }
        });

        Integer[] colourList = new Integer[256];
        for (int x = 0; x < 256; x++)
            colourList[x] = x;

        String[] percentList = new String[91];
        for (int x = 0; x < 91; x++)
            percentList[x] = (x + 10) + "%";

        this.okButton = new JButton("Ok");
        this.cancelButton = new JButton("Cancel");
        this.redBox = new JComboBox<Integer>(colourList);
        this.greenBox = new JComboBox<Integer>(colourList);
        this.blueBox = new JComboBox<Integer>(colourList);
        this.colourLabel = new JLabel("<html><b>Please select the RGB combination for the colour desired:</b></html>");
        this.opacityLabel = new JLabel("<html><b>Please select the transparency level for the overlay:</b></html>");
        this.opacityBox = new JComboBox<String>(percentList);

        this.okButton.setLocation(80, 150);
        this.okButton.setSize(90, 30);
        this.okButton.addMouseListener(this);

        this.cancelButton.setLocation(220, 150);
        this.cancelButton.setSize(90, 30);
        this.cancelButton.addMouseListener(this);

        this.redBox.setLocation(50, 50);
        this.redBox.setSize(90, 30);

        this.greenBox.setLocation(141, 50);
        this.greenBox.setSize(90, 30);

        this.blueBox.setLocation(232, 50);
        this.blueBox.setSize(90, 30);

        this.colourLabel.setLocation(10, 20);
        this.colourLabel.setSize(400, 30);

        this.opacityLabel.setLocation(25, 80);
        this.opacityLabel.setSize(400, 30);

        this.opacityBox.setLocation(141, 110);
        this.opacityBox.setSize(90, 30);

        this.add(this.redBox);
        this.add(this.greenBox);
        this.add(this.blueBox);
        this.add(this.opacityBox);
        this.add(this.colourLabel);
        this.add(this.opacityLabel);
        this.add(this.okButton);
        this.add(this.cancelButton);
    }

    public void makeVisible() {
        Color colour = disk.getColour();
        int r, g, b;
        r = colour.getRed();
        g = colour.getGreen();
        b = colour.getBlue();

        redBox.setSelectedIndex(r);
        greenBox.setSelectedIndex(g);
        blueBox.setSelectedIndex(b);
        opacityBox.setSelectedIndex((disk.getOpacityPercent() - 10));

        this.setVisible(true);
    }

    public void makeInvisible() {
        this.setVisible(false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if (me.getComponent().equals(okButton)) {
            disk.setColour(new Color(redBox.getSelectedIndex(), greenBox.getSelectedIndex(), blueBox.getSelectedIndex()));
            disk.setOpacityPercent((byte) (opacityBox.getSelectedIndex() + 10));
            options.advancedUpdate();
            makeInvisible();
        } else if (me.getComponent().equals(cancelButton)) {
            makeInvisible();
        }
    }
}