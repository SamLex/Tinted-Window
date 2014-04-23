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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ejhunter.tintedwindow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public abstract class Overlay extends Frame implements MouseListener, FocusListener {

    private static final long serialVersionUID = -1198140418579551621L;
    private JPopupMenu popupMenu;
    private JMenuItem aboutButton, optionsButton, exitButton;
    private boolean focus;
    private float opacity;
    private About about;
    private Options options;
    
    public Overlay(Dimension size) {
        super("Tinted Window");
        
        this.focus = false;
        this.opacity = 0f;
        
        this.setSize(size);
        this.addFocusListener(this);
        this.addMouseListener(this);  
        this.setLocationByPlatform(true);
        
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                exit();
            }
        });
        
        this.popupMenu = new JPopupMenu("Options");
        
        this.aboutButton = new JMenuItem("About");
        this.optionsButton = new JMenuItem("Options");
        this.exitButton = new JMenuItem("Exit");
        
        getAboutButton().addMouseListener(this);
        getExitButton().addMouseListener(this);
        getOptionsButton().addMouseListener(this);
        
        getPopupMenu().add(getAboutButton());
        getPopupMenu().add(getOptionsButton());
        getPopupMenu().add(getExitButton());
        
        this.about = new About();
        this.options = new Options();
    }

    public abstract void makeVisible();
    
    public void makeInvisible() {
        this.setVisible(false);
    }
    
    public void exit() {
        this.makeInvisible();
        this.dispose();
        System.exit(0);
    }
    
    @Override
    public void focusGained(FocusEvent e) {
        setFocus(true);
    }
    
    @Override
    public void focusLost(FocusEvent e) {
        getPopupMenu().setVisible(false);
        getAboutButton().setForeground(Color.BLACK);
        getExitButton().setForeground(Color.BLACK);
        getOptionsButton().setForeground(Color.BLACK);
        
        setFocus(false);
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getComponent().equals(getAboutButton())) {
            this.makeInvisible();
            getAbout().show();
        }else if (e.getComponent().equals(getOptionsButton())){
            this.makeInvisible();
            getOptions().show();
        }else if (e.getComponent().equals(getExitButton())){
            this.exit();
        }

        if(isFocus()){
            if(getPopupMenu().isVisible()){
                getPopupMenu().setVisible(false);
            }
            
            if(e.getButton() == MouseEvent.BUTTON3){
                getPopupMenu().setLocation(e.getLocationOnScreen());
                getPopupMenu().setVisible(true);
            }
        }
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getComponent().equals(getAboutButton()) || e.getComponent().equals(getOptionsButton()) || e.getComponent().equals(getExitButton())) {
            e.getComponent().setForeground(Disk.getColour());
        }
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getComponent().equals(getAboutButton()) || e.getComponent().equals(getOptionsButton()) || e.getComponent().equals(getExitButton())) {
            e.getComponent().setForeground(Color.BLACK);
        }
    }
    
    public JPopupMenu getPopupMenu() {
        return popupMenu;
    }

    public JMenuItem getAboutButton() {
        return aboutButton;
    }

    public JMenuItem getOptionsButton() {
        return optionsButton;
    }

    public JMenuItem getExitButton() {
        return exitButton;
    }

    public boolean isFocus() {
        return focus;
    }

    public float getOpacity() {
        return opacity;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }

    public void setOpacityValue(float opacity) {
        this.opacity = opacity;
    }

    public About getAbout() {
        return about;
    }

    public Options getOptions() {
        return options;
    }

}
