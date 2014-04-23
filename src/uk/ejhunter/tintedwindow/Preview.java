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
import com.sun.awt.AWTUtilities;

public class Preview {

	private static boolean init = false;
	private static Frame f;
	private static float t;
	
	protected Preview() {
		
		//initialise Preview Frame
		f = new Frame("Preview");
		f.setSize(200, 200);
		f.setResizable(false);
		f.setAlwaysOnTop(true);
		f.setLocationByPlatform(true);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				hide();
			}
		});
		
		init = true;
	}

	protected static void show() {
		f.setBackground(Disk.getColour());
		t = (float) Disk.getPercent() /100;
		AWTUtilities.setWindowOpacity(f, t);
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

}
