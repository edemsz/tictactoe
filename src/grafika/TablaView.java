package grafika;

import logic.Amoba;
import logic.Pontszam;
import logic.Tabla;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.*;

/**
 * A játéktábla megjelenítésére szolgáló osztály
 */
public class TablaView extends JPanel {
    /**
     * A tábla, amin a játék zajlik.
     */
    Tabla tabla;
    /**
     * A tábla mérete
     */
    int meret = 3;
    /**
     * A kovetkező játékos jelét kirajzoló címke
     */
    JLabel kovetkezoJel;
    /**
     * A hibajelzésre és a nyertes kiírására szolgáló címke
     */
    JLabel hiba;
    /**
     * A gombok mátrixa, ezzel lehet játszani.
     */
    JButton[][] gombok;
    /**
     * A gombokat tároló panel
     */
    JPanel mezok;
    /**
     * A jobboldali irányító és kommunikációs célokat szolgáló panel
     */
    JPanel jobboldal;
    /**
     * Az a panel, amin a következő jelet kiírja a program.
     */
    JPanel kovetkezoJelPanel = new JPanel();
    /**
     * Az amőbák jeléhez színeket tároló map.
     */
    Map<Character, Color> szinek = new HashMap<Character, Color>();

    /**
     * A tábla kirajzolásáért felelős konstruktor. A táblát és a jobb oldali kommunikációs és vezérlő célokat ellátó
     * panelt is kirajzolja.
     */
    public TablaView() {
        tablarajzol();
        setSzinek();
        setLayout(new GridLayout(1, 2));
        jobboldal = new JPanel(new GridLayout(5, 1));
        JPanel kilepPanel = new JPanel();
        JButton kilep = new JButton("Kilépés a játékból");
        kilep.addActionListener(actionEvent -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(TablaView.this);
            CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
            cl.show(frame.getContentPane(), "main");
            hiba.setText("");
            frame.setSize(new Dimension(400, 500));
            frame.setLocationRelativeTo(null);
        });
        kilep.setPreferredSize(new Dimension(200, 40));
        kilepPanel.add(kilep);
        JButton ujjatek = new JButton("Új játék!");
        ujjatek.addActionListener(actionEvent -> {
            tablarajzol();
            String kiirando = Character.toString(tabla.getJel());
            kovetkezoJel.setText(Character.toString(tabla.getJel()));

            kovetkezoJelPanel.setBackground(szinek.get(tabla.getJel()));
            if (isColorDark(szinek.get(tabla.getJel())))
                kovetkezoJel.setForeground(Color.white);
            else kovetkezoJel.setForeground(Color.black);
            hiba.setText("");
        });

        JLabel kovetkezoKiiras = new JLabel("A következő játékos jele:");
        kovetkezoKiiras.setFont(new Font("Arial", Font.BOLD, 15));
        kovetkezoJel = new JLabel(Character.toString(tabla.getJel()));
        kovetkezoJel.setFont(new Font("Arial", Font.BOLD, 20));
        kovetkezoJelPanel.setBackground(szinek.get(tabla.getJel()));
        if (isColorDark(szinek.get(tabla.getJel())))
            kovetkezoJel.setForeground(Color.white);
        hiba = new JLabel("");
        hiba.setFont(new Font("Arial", Font.BOLD, 20));
        jobboldal.add(kilepPanel);
        JPanel ujjatekPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ujjatek.setPreferredSize(new Dimension(200, 40));
        ujjatekPanel.add(ujjatek);
        jobboldal.add(ujjatekPanel);
        kovetkezoJelPanel.add(kovetkezoJel);
        jobboldal.add(kovetkezoKiiras);
        jobboldal.add(kovetkezoJelPanel);
        jobboldal.add(hiba);
        add(jobboldal);
    }

    /**
     * A nyerést levezénylő függvény. Kiírja a nyertest, és bele is teszi a dicsőséglistába a pontszámot, ha kell,
     * miután megkérdezte a játékos nevét.
     */
    private void nyer() {
        MainFrame frame = (MainFrame) SwingUtilities.getWindowAncestor(this);
        hiba.setText("Vége! A nyertes a(z) " + tabla.nyertes().getJel() + " játékos! Gratulálok!");
        hiba.setForeground(Color.green.darker().darker());
        if (frame.getDicsoseglista().beleteendo(tabla.amobaSzam(tabla.nyertes()))) {
            JFrame nevkerdezo = new JFrame("Add meg a neved!");
            nevkerdezo.setLocationRelativeTo(frame);
            nevkerdezo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JPanel p = new JPanel(new GridLayout(3, 1));
            JLabel szoveg = new JLabel("<html>Gratulálok!<br>Az eredményed felkerül a dicsőséglistára. <br>Írd be a neved!</html>");
            szoveg.setFont(new Font("Arial", Font.BOLD, 12));
            JTextField beiros = new JTextField();
            JButton ok = new JButton("OK");
            ok.addActionListener(actionEvent -> {
                frame.getDicsoseglista().add(new Pontszam(beiros.getText(), tabla.amobaSzam(tabla.nyertes())));
                nevkerdezo.dispose();
            });
            p.add(szoveg);
            p.add(beiros);
            p.add(ok);
            nevkerdezo.setContentPane(p);
            nevkerdezo.pack();
            nevkerdezo.setSize(new Dimension(300, 200));
            nevkerdezo.setVisible(true);
        }
    }

    /**
     * Megadja, hogy az adott szín sötét-e. A https://stackoverflow.com/questions/24260853/check-if-color-is-dark-or-light-in-android minta alapján készült.
     *
     * @param color A vizsgálandó szín
     * @return Igaz vagy hamis, aszerint, hogy sötét-e.
     */
    private boolean isColorDark(Color color) {
        return (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue()) / 255 < 0.5;
    }

    /**
     * Ez a függvény minden egyes amőbához egy színt rendel hozzá: feltölti a szinek map-et.
     */
    public void setSzinek() {
        List<Amoba> jatekosok = tabla.getJatekosok();
        List<Color> szin = new ArrayList<>();
        szin.add(Color.BLUE);
        szin.add(Color.ORANGE);
        szin.add(Color.RED);
        for (int i = 0; i < szin.size() && i < jatekosok.size(); i++)
            szinek.put(jatekosok.get(i).getJel(), szin.get(i));
        for (int i = szin.size(); i < jatekosok.size(); i++) {
            Random rnd = new Random();
            szinek.put(jatekosok.get(i).getJel(), new Color(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)));
        }
    }

    /**
     * Beállítja a méretet a paraméterként kapott értékre, és kirajzolja a táblát.
     *
     * @param i A méret kívánt értéke
     */
    public void setMeret(int i) {
        meret = i;
        tablarajzol();
    }

    /**
     * Kirajzolja a tárolt méretű táblát.
     */
    private void tablarajzol() {
        if (mezok != null)
            remove(mezok);
        tabla = new Tabla(meret);
        mezok = new JPanel(new GridLayout(meret, meret));
        gombok = new JButton[meret][meret];
        for (int i = 0; i < meret; i++) {
            for (int j = 0; j < meret; j++) {
                gombok[i][j] = new JButton();
                gombok[i][j].setFont(new Font("Arial", Font.BOLD, 15));
                gombok[i][j].addActionListener(new GombListener(gombok[i][j], i, j));
                gombok[i][j].setEnabled(true);
                gombok[i][j].setText("");
                mezok.add(gombok[i][j]);
            }
        }
        add(mezok, 0);
        revalidate();
    }

    /**
     * A gombokra kattintást felügyelő osztály.
     */
    class GombListener implements ActionListener {
        /**
         * A gomb, amire rá van kötve az ActionListener.
         */
        JButton gomb;
        /**
         * A gomb koordinátái.
         */
        int x, y;

        /**
         * Konstruktor, beállítja az adattagokat.
         *
         * @param b A gomb, amire rá van kötve az ActionListener
         * @param x A gomb x koordinátája
         * @param y A gomb y koordinátája
         */
        public GombListener(JButton b, int x, int y) {
            gomb = b;
            this.x = x;
            this.y = y;
        }

        /**
         * Ha rákattintanak a gombra, megpróbál a táblára helyezni az adott helyre egy amőbát.
         *
         * @param actionEvent A bekövetkezett esemény.
         */
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            char jel = tabla.getJel();//ha sikerül letenni, akkor mit írjon ki a program
            boolean siker = tabla.addAmoba(x, y);
            if (siker) {
                gomb.setText(Character.toString(jel));
                kovetkezoJel.setText(Character.toString(tabla.getJel()));
                kovetkezoJelPanel.setBackground(szinek.get(tabla.getJel()));
                hiba.setText("");
                gomb.setBackground(szinek.get(jel));
                if (isColorDark(szinek.get((jel)))) {
                    gomb.setForeground(Color.WHITE);
                }
                if (isColorDark(szinek.get(tabla.getJel())))
                    kovetkezoJel.setForeground(Color.WHITE);
                else kovetkezoJel.setForeground(Color.BLACK);
                if (!tabla.vanMegLepes()) {

                    hiba.setText("<html>A játéknak vége, nincs több <br>lépési lehetőség! Döntetlen.</html>");
                    hiba.setForeground(Color.RED);
                }
            } else if (tabla.vanMegLepes()) {
                hiba.setText("Rossz helyre tetted!");
                hiba.setForeground(Color.red);
            }
            if (tabla.nyertes() != null) {
                nyer();
            }
        }
    }
}
