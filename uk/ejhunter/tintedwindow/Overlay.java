/*
 * This file is part of Tinted Window ©, a program for creating coloured overlays on screen
 * 
 * Copyright (C) 2011 Euan James Hunter <euanhunter117@gmail.com>
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

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import com.sun.awt.AWTUtilities;

public class Overlay implements MouseListener, FocusListener {

	private static Frame f;
	private static JPopupMenu menu;
	private static JMenuItem aB, oB, eB;
	private static boolean focus = true;
	private static float t;
	
	protected Overlay() {

		//Initialise overlay
		f = new Frame("Tinted Window ©");
		f.setSize(400, 400);
		f.setAlwaysOnTop(true);
		f.addFocusListener(this);
		f.addMouseListener(this);
		f.setLocationByPlatform(true);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});

		//popup menu
		menu = new JPopupMenu("Options");
		aB = new JMenuItem("About");
		aB.addMouseListener(this);
		oB = new JMenuItem("Options");
		oB.addMouseListener(this);
		eB = new JMenuItem("Exit");
		eB.addMouseListener(this);
		menu.add(aB);
		menu.add(oB);
		menu.add(eB);
		show();
	}

	//shows frame
	protected static void show() {
		f.setBackground(Disk.getColour());
		t = (float) Disk.getPercent() /100;
		AWTUtilities.setWindowOpacity(f, t);
		f.setVisible(true);

		return;
	}

	//hides frame
	protected static void hide() {
		f.setVisible(false);

		return;
	}

	//used to ensure the popup menu can't be used without window focus
	@Override
	public void focusGained(FocusEvent e) {
		focus = true;
	}

	//hides menu when window is not in use
	@Override
	public void focusLost(FocusEvent e) {
		menu.setVisible(false);
		aB.setForeground(Color.BLACK);
		oB.setForeground(Color.BLACK);
		eB.setForeground(Color.BLACK);
		focus = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	//popup menu right-click and actual menu button detection
	@Override
	public void mousePressed(MouseEvent e) {

		if(e.getComponent().equals(aB)) {
			hide();

			//shows or creates about frame
			if(About.isInit()){
				About.show();
			}else {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						new About();
						About.show();
					}
				});
			}
		}else if (e.getComponent().equals(oB)){
			hide();

			//shows or creates options frame
			if(Options.isInit()){
				Options.show();
			}else {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						new Options();
						Options.show();
					}
				});
			}
		}else if (e.getComponent().equals(eB)){
			System.exit(0);
		}

		//shows the popup menu
		if(focus){
			if(e.getButton() == MouseEvent.BUTTON3){
				menu.setLocation(e.getLocationOnScreen());
				menu.setVisible(true);
			}else if(menu.isVisible()){
				menu.setVisible(false);
			}
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {}

	//changes popup menu colour
	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getComponent().equals(aB) || e.getComponent().equals(oB) || e.getComponent().equals(eB)) {
			e.getComponent().setForeground(Disk.getColour());
		}
	}

	//changes popup menu colour
	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getComponent().equals(aB) || e.getComponent().equals(oB) || e.getComponent().equals(eB)) {
			e.getComponent().setForeground(Color.BLACK);
		}
	}
}
