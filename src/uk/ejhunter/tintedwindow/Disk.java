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
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Disk {

	private static Color colour;
	private static byte percent;
	private static File dir, file;
	private static DataOutputStream dos;
	private static DataInputStream dis;

	protected Disk() throws IOException {

		//does disk operations
		start();
	}

	//tests to work out where to put file
	private static void start() throws IOException {

		//get APPDATA variable, which is null on non-windows systems
		String s = System.getenv("APPDATA");

		if(s == null) {
			//creates .tintedwindow for UNIX-like systems
			file = new File(System.getProperty("user.home"), ".tintedwindow");
		}else
			if(s != null) {
				//creates directory in APPDATA and then creates Tinted Window.dat
				dir = new File(s, "Tinted Window");

				if(!dir.exists()){
					dir.mkdir();
				}
				
				file = new File(dir, "Tinted Window.dat");

				dir = null;
			}

		//creates file if it does not exist and writes defaults to file
		if(!file.exists()) {
			file.createNewFile();
			startWrite();
		}

		//reads data
		startRead();

		return;
	}

	//writer for first time run
	private static void startWrite() throws IOException {

		dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));

		//writes the values for purple and the percentage to the file
		//purple
		dos.writeShort(160); //r
		dos.writeShort(35); //g
		dos.writeShort(240); //b
		//percent
		dos.writeByte(50);

		//flush,close,nullify
		dos.flush();
		dos.close();

		dos = null;

		return;
	}

	//reader for start of program
	private static void startRead() throws IOException {

		dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));

		//reads colour and percent data
		colour = new Color(dis.readShort(), dis.readShort(), dis.readShort());
		percent = dis.readByte();

		//close,nullify
		dis.close();

		dis = null;

		return;
	}

	//writer to be used externally
	protected static void write(Color c, byte p) throws Exception {

		//clears file for new writing
		file.delete();
		file.createNewFile();

		dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));

		//writes data
		dos.writeShort(c.getRed()); //r
		dos.writeShort(c.getGreen()); //g
		dos.writeShort(c.getBlue()); //b
		dos.writeByte(p); //percent

		//flush,close,nullify
		dos.flush();
		dos.close();

		dos = null;

		percent = p;
		colour = c;

		return;
	}

	//getters for values taken from disk
	protected static Color getColour() {
		return colour;
	}

	protected static byte getPercent() {
		return percent;
	}
}
