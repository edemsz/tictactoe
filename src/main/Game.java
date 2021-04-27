package main;

import grafika.MainFrame;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 * A játék indításáért felelős osztály
 */
public class Game {
    /**
     * A main függvény egy MainFrame-et példányosít, ezzel elindítva a programot.
     *
     * @param args Parancssori argumentumok
     */
    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        LookAndFeel a = new NimbusLookAndFeel();
        UIManager.setLookAndFeel(a);
        new MainFrame();
    }
}
