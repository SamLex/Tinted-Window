package uk.ejhunter.tintedwindow.update;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.SwingUtilities;

public class TintedWindow implements Runnable {

    public static void main(String[] args) {
        // TODO: checking

        SwingUtilities.invokeLater(new TintedWindow());
    }

    @Override
    public void run() {
        Window tintedwindow = new Window(new Dimension(400, 400), new Color(0f, 0f, 1f, 0.3f), true); //temp parameters
        tintedwindow.setVisible(true);
    }
}
