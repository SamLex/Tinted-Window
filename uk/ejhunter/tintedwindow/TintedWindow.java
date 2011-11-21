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

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import com.sun.awt.AWTUtilities;

public class TintedWindow {

	public static void main(String[] args) {

		//initialises program
		init();
	}

	private static void init() {

		//test to check the computer is suitable for program
		tests();
		
		//gets previous data from disk, also provides methods to write data
		try {
			new Disk();
		} catch(Exception e) { //catches any error that happen
			JOptionPane.showMessageDialog(null, "An error has occured while reading/writing from/to the disk", 
					"ERROR", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		//starts overlay in AWT-dispatch thread
		try {
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					new Overlay();
				}
			});
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Fatal Error! Please restart the program", 
					"ERROR", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		
		return;
	}

	private static void tests() {

		//checks the Java 7 is not in use, cause Java 7 dose some weird stuff to this program
		if(System.getProperty("java.version").contains("1.7")) {
			JOptionPane.showMessageDialog(null, "Sorry, this program does not work with Java 7", "ERROR", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		//checks that the system supports the translucency feature
		if(!AWTUtilities.isTranslucencySupported(AWTUtilities.Translucency.TRANSLUCENT)) {
			JOptionPane.showMessageDialog(null, "Sorry, your system does not support this program, please update your Java version and try again", "ERROR", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		return;
	}
}
