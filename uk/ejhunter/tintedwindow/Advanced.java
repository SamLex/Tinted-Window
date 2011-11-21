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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Advanced implements MouseListener {

	private static boolean init = false;
	private static Frame f;
	private static Integer[] n;
	private static String[] p;
	private static JButton o, c;
	private static JComboBox r, g, b, t;
	private static JLabel cl, tl;
	
	protected Advanced() {
		
		//initialise Advanced Frame
		f = new Frame("Tinted Window © - Advanced Options");
		f.setSize(400, 190);
		f.setResizable(false);
		f.setLocationByPlatform(true);
		f.setLayout(null);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				hide();
			}
		});
		
		//initialise lists for list boxes
		int x;
		n = new Integer[256];
		for(x=0;x<256;x++){
			n[x] = x;
		}
		p = new String[91];
		for(x=0;x<91;x++){
			p[x] = (x+10) + "%";
		}
		
		//set up all the GUI elements
		o = new JButton("Ok");
		c = new JButton("Cancel");
		r =  new JComboBox(n);
		g = new JComboBox(n);
		b = new JComboBox(n);
		cl = new JLabel("<html>" +
				"<b>" +
				"Please select the RGB combination for the colour desired:" +
				"</b>" +
				"</html>");
		tl = new JLabel("<html>" +
				"<b>" +
				"Please select the transparency level for the overlay:" +
				"</b>" +
				"</html>");
		t =  new JComboBox(p);
		
		o.setLocation(80, 150);
		o.setSize(90, 30);
		o.addMouseListener(this);

		c.setLocation(220, 150);
		c.setSize(90, 30);
		c.addMouseListener(this);

		r.setLocation(50, 50);
		r.setSize(90, 30);

		g.setLocation(141, 50);
		g.setSize(90, 30);

		b.setLocation(232, 50);
		b.setSize(90, 30);

		cl.setLocation(10, 20);
		cl.setSize(400, 30);

		tl.setLocation(25, 80);
		tl.setSize(400, 30);
		
		t.setLocation(141, 110);
		t.setSize(90, 30);
		
		f.add(t);
		f.add(tl);
		f.add(b);
		f.add(c);
		f.add(g);
		f.add(cl);
		f.add(o);
		f.add(r);
		
		init = true;
	}

	//shows the window and sets the listboxes to the right setting
	protected static void show() {
		r.setSelectedIndex(Disk.getColour().getRed());
		g.setSelectedIndex(Disk.getColour().getGreen());
		b.setSelectedIndex(Disk.getColour().getBlue());
		t.setSelectedIndex((Disk.getPercent()-10));
		
		f.setVisible(true);

		return;
	}
	
	protected static void hide() {
		f.setVisible(false);
		
		return;
	}
	
	protected static boolean isInit() {
		return init;
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	//handles button clicks
	@Override
	public void mouseReleased(MouseEvent me) {
		if(me.getComponent().equals(o)){
			try {
				Disk.write(new Color((Integer)r.getSelectedItem(), (Integer)g.getSelectedItem(), (Integer)b.getSelectedItem()), (byte) (t.getSelectedIndex()+10));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "An error has occured while reading/writing from/to the disk", 
						"ERROR", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
			
			Options.update();
			hide();
		}else if(me.getComponent().equals(c)){
			hide();
		}
	}
}