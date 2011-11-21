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

import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Options implements MouseListener {

	private static boolean init = false;
	private static Frame f;
	private static JButton a, p, s;
	private static JColorChooser jcc;
	private static byte percent = Disk.getPercent();
	private static JPanel jccp, buttons;

	protected Options() {

		//Initialise options
		f = new Frame("Tinted Window © - Options");
		jcc = new JColorChooser(Disk.getColour());
		jccp = new JPanel();
		buttons = new JPanel();

		a = new JButton("Advanced");
		p = new JButton("Preview");
		s = new JButton("Start");

		f.setSize(450, 350);
		f.setResizable(false);
		f.setLayout(null);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				hide();

				Overlay.show();
			}
		});

		//colour chooser and buttons
		jcc.setPreviewPanel(new JPanel());
		jcc.removeChooserPanel(jcc.getChooserPanels()[2]);

		jccp.setLocation(0, 25);
		jccp.setSize(450, 350);
		jccp.add(jcc);

		a.setSize(90, 30);
		a.setLocation(30, 10);
		a.addMouseListener(this);

		s.setSize(70, 30);
		s.setLocation(300, 10);
		s.addMouseListener(this);

		p.setSize(90, 30);
		p.setLocation(165, 10);
		p.addMouseListener(this);

		buttons.setLocation(20, 290);
		buttons.setSize(450, 50);
		buttons.setLayout(null);
		buttons.add(p);
		buttons.add(a);
		buttons.add(s);

		f.add(buttons);
		f.add(jccp);

		//allows to determine whether options has been initialised
		init = true;
	}

	//shows options
	protected static void show() {
		f.setLocationByPlatform(true);
		f.setVisible(true);

		return;
	}

	//hides options
	protected static void hide() {
		f.setVisible(false);
		
		if(Preview.isInit())
			Preview.hide();
		if(Advanced.isInit())
			Advanced.hide();

		//ensure others are hidden
		try{
			Disk.write(jcc.getColor(), percent);
		}catch(Exception e){
			JOptionPane.showMessageDialog(f, "An error has occured while reading/writing from/to the disk", 
					"ERROR", JOptionPane.ERROR_MESSAGE);
			
			System.exit(1);
		}

		return;
	}

	//getters
	protected static boolean isInit() {
		return init;
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getComponent().equals(a)){

			try{
				Disk.write(jcc.getColor(), percent);
			}catch(Exception e2){
				JOptionPane.showMessageDialog(f, "An error has occured while reading/writing from/to the disk", 
						"ERROR", JOptionPane.ERROR_MESSAGE);
				
				System.exit(1);
			}
			
			//shows or creates advanced frame
			if(Advanced.isInit()){
				Advanced.show();
			}else {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						new Advanced();
						Advanced.show();
					}
				});
			}
		}else if(e.getComponent().equals(p)){

			try{
				Disk.write(jcc.getColor(), percent);
			}catch(Exception e1){
				JOptionPane.showMessageDialog(f, "An error has occured while reading/writing from/to the disk", 
						"ERROR", JOptionPane.ERROR_MESSAGE);
				
				System.exit(1);
			}
			
			//shows or creates preview frame
			if(Preview.isInit()){
				Preview.show();
			}else {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						new Preview();
						Preview.show();
					}
				});
			}
		}else if(e.getComponent().equals(s)){
			hide();

			Overlay.show();
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
	//updates the colour of the chooser and the stored percentage for when writing to the disk
	protected static void update(){
		jcc.setColor(Disk.getColour());
		percent = Disk.getPercent();
		
		return;
	}
}
