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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent.EventType;

public class About {

	private static boolean init = false;
	private static Frame f;
	private static JEditorPane h;

	protected About() {
		
		//initialise About frame
		f = new Frame("Tinted Window © - About");
		f.setSize(650, 275);
		f.setResizable(false);
		f.setLocationByPlatform(true);
		f.setBackground(java.awt.Color.WHITE);
		f.setLayout(null);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				hide();
			}
		});

		//sets up HTML page
		h = new JEditorPane();
		h.setEditable(false);
		h.setLocation(20, 40);
		h.setSize(580, 275);
		h.addHyperlinkListener(new HyperlinkListener() {
			
			//listeners to handle hyperlink clicks properly
			@Override
			public void hyperlinkUpdate(HyperlinkEvent hle) {
				if(hle.getEventType().equals(EventType.ACTIVATED)) {
					try{
						if(hle.getDescription().equals("license")){
							if(java.awt.Desktop.isDesktopSupported()){
								java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
								if(desktop.isSupported( java.awt.Desktop.Action.BROWSE )){
									desktop.browse(new URI("http://www.gnu.org/licenses/gpl.txt"));
								}
							}
						}else if(hle.getDescription().equals("src")){
							if(java.awt.Desktop.isDesktopSupported()){
								java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
								if(desktop.isSupported( java.awt.Desktop.Action.BROWSE )){
									desktop.browse(new URI("https://github.com/SamLex/Tinted-Window"));
								}
							}
						}
					}catch(Exception e){
						e.printStackTrace();
						err();
					}
				}
			}
		});

		try{
			h.setPage(TintedWindow.class.getResource("About.html"));
		}catch(IOException ioe){
			err();
		};

		f.add(h);

		init = true;
	}

	protected static void show() {
		f.setVisible(true);

		return;
	}

	protected static void hide() {
		f.setVisible(false);
		Overlay.show();

		return;
	}

	protected static boolean isInit() {
		return init;
	}

	//used if an error occurs in using the hyperlinks
	private static void err(){
		JOptionPane.showMessageDialog(null, "Sorry, your computer does not support the feature nessesary to open this hyperlink", 
				"ERROR", JOptionPane.ERROR_MESSAGE);

		return;
	}
}
