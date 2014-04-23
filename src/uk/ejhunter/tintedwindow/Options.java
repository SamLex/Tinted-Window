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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ejhunter.tintedwindow;

import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

public class Options extends Frame implements MouseListener {
    
    private static final long serialVersionUID = -1968605702154541195L;
    private JButton advancedButton, startButton;
	private JColorChooser jcc;
	private JPanel jccPanel, buttonPanel;
	private Disk disk;
	private Overlay overlay;
	private Advanced advanced;
	
	public Options(Disk disk, Overlay overlay) {
	    super("Tinted Window - Options");
	    
	    this.disk = disk;
		this.overlay = overlay;
		
		this.jcc = new JColorChooser(disk.getColour());
		this.jccPanel = new JPanel();
		this.buttonPanel = new JPanel();

		this.advancedButton = new JButton("Advanced");
		this.startButton = new JButton("Start");

		this.setSize(450, 350);
		this.setResizable(false);
		this.setLayout(null);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			    startButton();
			}
		});

		this.jcc.setPreviewPanel(new JPanel());

		this.jccPanel.setLocation(0, 15);
		this.jccPanel.setSize(450, 350);
		this.jccPanel.add(jcc);

		this.advancedButton.setSize(90, 30);
		this.advancedButton.setLocation(30, 10);
		this.advancedButton.addMouseListener(this);

		this.startButton.setSize(70, 30);
		this.startButton.setLocation(300, 10);
		this.startButton.addMouseListener(this);

		this.buttonPanel.setLocation(20, 300);
		this.buttonPanel.setSize(450, 50);
		this.buttonPanel.setLayout(null);
		this.buttonPanel.add(advancedButton);
		this.buttonPanel.add(startButton);

		this.add(buttonPanel);
		this.add(jccPanel);
		
		this.advanced = new Advanced(this, disk);
	}

	public void advancedUpdate() {
	    jcc.setColor(disk.getColour());
	}
	
	private void startButton() {
	    this.makeInvisible();
	    disk.setColour(jcc.getColor());
	    overlay.makeVisible();
	}
	
	public void makeVisible() {
		this.setLocationByPlatform(true);
		this.setVisible(true);
	}

	public void makeInvisible() {
		this.setVisible(false);
		advanced.makeInvisible();
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getComponent().equals(this.advancedButton)){
		    advanced.makeVisible();
		}else if(e.getComponent().equals(startButton)){
		    startButton();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
