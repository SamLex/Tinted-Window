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
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import uk.ejhunter.tintedwindow.TintedWindow.OS;

public class Disk {

    private Color colour;
    private byte opacityPercent;
    private File dataDirectory, dataFile;
    private OS os;

    public Disk() {
        this.colour = null;
        this.opacityPercent = 0;

        this.os = TintedWindow.getOS();

        if (os == OS.LINUX || os == OS.OSX || os == OS.UNKNOWN)
            this.dataFile = new File(System.getProperty("user.home"), ".tintedwindow");
        else {
            this.dataDirectory = new File(System.getenv("APPDATA"), "Tinted Window");

            if (!this.dataDirectory.exists())
                this.dataDirectory.mkdir();

            this.dataFile = new File(dataDirectory, "Tinted Window.dat");
        }

        if (!dataFile.exists())
            try {
                dataFile.createNewFile();
                writeDefaults();
                assignDefaults();
            } catch (IOException io) {
                io.printStackTrace();
            }
    }

    private void writeDefaults() throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dataFile)))) {

            // purple
            dos.writeShort(160); // r
            dos.writeShort(35); // g
            dos.writeShort(240); // b
            // percent
            dos.writeByte(50);

            dos.flush();
            dos.close();
        }
    }

    private void readFromDisk() {
        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFile)))) {

            int r, g, b;
            r = dis.readShort();
            g = dis.readShort();
            b = dis.readShort();
            this.colour = new Color(r, g, b);
            this.opacityPercent = dis.readByte();

            dis.close();

        } catch (IOException io) {
            io.printStackTrace();
            assignDefaults();
        }
    }

    private void assignDefaults() {
        this.colour = new Color(160, 35, 240);
        this.opacityPercent = 50;
    }

    public void save() {
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dataFile)))) {

            int r, g, b;
            r = this.getColour().getRed();
            g = this.getColour().getGreen();
            b = this.getColour().getBlue();
            dos.writeShort(r);
            dos.writeShort(g);
            dos.writeShort(b);
            dos.writeByte(this.getOpacityPercent());

            dos.flush();
            dos.close();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public byte getOpacityPercent() {
        if (opacityPercent == 0)
            readFromDisk();
        return opacityPercent;
    }

    public Color getColour() {
        if (colour == null)
            readFromDisk();
        return colour;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }

    public void setOpacityPercent(byte opacityPercent) {
        this.opacityPercent = opacityPercent;
    }
}
