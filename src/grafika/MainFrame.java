package grafika;

import logic.Dicsoseglista;

import javax.swing.*;
import java.awt.*;

/**
 * A fő ablakot megjelenítő osztály.
 */
public class MainFrame extends JFrame {
    /**
     * Annak a fájlnak a neve, amiben a dicsőséglistát tároljuk.
     */
    private final String filename = "dicsoseglista.txt";
    /**
     * A játék mindenkori dicsőséglistája
     */
    Dicsoseglista dicsoseglista = new Dicsoseglista();

    /**
     * Az osztály konstruktora felépíti az ablakot CardLayout-ban, és beolvassa a dicsőséglistát.
     */
    public MainFrame() {
        dicsoseglista.load(filename);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Amőba");
        Menu m = new Menu();
        CardLayout cl = new CardLayout();
        JPanel fopanel = new JPanel(cl);
        fopanel.add(m, "main");

        cl.show(fopanel, "main");

        setContentPane(fopanel);

        pack();
        setSize(new Dimension(400, 500));
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    /**
     * Bezáráskor először elmentjük a dicsőséglistát, majd bezárjuk az ablakot.
     */
    @Override
    public void dispose() {
        dicsoseglista.save(filename);
        super.dispose();
    }

    /**
     * A dicsőséglista lekérésére szolgáló függvény.
     *
     * @return A dicsőséglista
     */
    public Dicsoseglista getDicsoseglista() {
        return dicsoseglista;
    }
}
