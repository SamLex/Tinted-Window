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

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice.WindowTranslucency;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.sun.awt.AWTUtilities;

public class TintedWindow {

    private static OS os;
    private static int javaVersion;
    private static boolean perpixel;

    public enum OS {
        WINDOWS,
        OSX,
        LINUX,
        UNKNOWN
    };

    public static void main(String[] args) {
        init();
    }

    private static void init() {
        if (testCompatibility()) {
            final Disk disk = new Disk();

            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    Overlay overlay;
                    if (javaVersion == 7)
                        overlay = new Overlay7(disk);
                    else
                        overlay = new Overlay6(disk);
                    overlay.makeVisible();
                }
            });
        }
    }

    private static boolean testCompatibility() {
        if (System.getProperty("java.version").contains("1.7")) {
            javaVersion = 7;

            GraphicsDevice dev = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            if (dev.isWindowTranslucencySupported(WindowTranslucency.PERPIXEL_TRANSLUCENT)) {
                perpixel = true;
            } else if (dev.isWindowTranslucencySupported(WindowTranslucency.TRANSLUCENT)) {
                perpixel = false;
            } else {
                JOptionPane.showMessageDialog(null, "Sorry, your system does not support this program, please update your Java version and try again", "ERROR", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            javaVersion = 6;

            if (!AWTUtilities.isTranslucencySupported(AWTUtilities.Translucency.TRANSLUCENT)) {
                JOptionPane.showMessageDialog(null, "Sorry, your system does not support this program, please update your Java version and try again", "ERROR", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        String osString = System.getProperty("os.name").toLowerCase();

        if (osString.contains("windows"))
            os = OS.WINDOWS;
        else if (osString.contains("os x"))
            os = OS.OSX;
        else if (osString.contains("linux"))
            os = OS.LINUX;
        else
            os = OS.UNKNOWN;

        return true;
    }

    public static OS getOS() {
        return os;
    }

    public static boolean getPerpixel() {
        return perpixel;
    }
}
