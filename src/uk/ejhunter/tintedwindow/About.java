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

import java.awt.Desktop;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent.EventType;

public class About extends Frame {

    private static final long serialVersionUID = -786287918665383811L;
    private JEditorPane htmlViewer;
    private Overlay overlay;

    public About(Overlay overlay) {
        super("Tinted Window - About");
        this.overlay = overlay;
        this.setSize(650, 275);
        this.setResizable(false);
        this.setLocationByPlatform(true);
        this.setBackground(java.awt.Color.WHITE);
        this.setLayout(null);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                makeInvisible();
            }
        });

        htmlViewer = new JEditorPane();
        htmlViewer.setEditable(false);
        htmlViewer.setLocation(20, 40);
        htmlViewer.setSize(580, 275);
        htmlViewer.addHyperlinkListener(new AboutLinkHandler());

        try {
            htmlViewer.setPage(TintedWindow.class.getResource("About.html"));
        } catch (IOException io) {
            io.printStackTrace();
            loadError();
        }

        this.add(htmlViewer);
    }

    public void makeVisible() {
        this.setVisible(true);
    }

    public void makeInvisible() {
        this.setVisible(false);
        overlay.makeVisible();
    }

    private void hyperlinkError() {
        JOptionPane.showMessageDialog(this, "Sorry, your computer does not support the feature nessesary to open this hyperlink", "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    private void loadError() {
        JOptionPane.showMessageDialog(this, "Sorry, there was an error loading this the About page", "ERROR", JOptionPane.ERROR_MESSAGE);
    }
    
    private class AboutLinkHandler implements HyperlinkListener {
        @Override
        public void hyperlinkUpdate(HyperlinkEvent hle) {
            Desktop desk;
            URI license, src;
            try {
                license = new URI("http://www.gnu.org/licenses/gpl.txt");
                src = new URI("https://github.com/SamLex/Tinted-Window");
            } catch (URISyntaxException use) {
                return;
            }

            if (Desktop.isDesktopSupported())
                desk = Desktop.getDesktop();
            else {
                hyperlinkError();
                return;
            }
            if (!desk.isSupported(Desktop.Action.BROWSE))
                return;

            if (hle.getEventType().equals(EventType.ACTIVATED)) {
                try {
                    if (hle.getDescription().equals("license")) {
                        desk.browse(license);
                    } else if (hle.getDescription().equals("src")) {
                        desk.browse(src);
                    }
                } catch (IOException io) {
                    io.printStackTrace();
                    return;
                }
            }
        }

    }
}
