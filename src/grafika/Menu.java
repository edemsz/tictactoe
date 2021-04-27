package grafika;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A menü osztály felel a menü megjelenítéséért. A játék és a dicsőséglista is innen érhető el.
 */
public class Menu extends JPanel {
    /**
     * A játék méretét beállító csúszka.
     */
    private JSlider slider;
    /**
     * A tábla mérete.
     */
    /**
     * A menü konstruktora, ami megjeleníti a menü négy fő részét:
     * 1. rész: AMŐBA felirat
     * 2. rész: Méretválasztásra felhívó szöveg és csúszka a beállításhoz
     * 3. rész: Játék indítására szolgáló gomb
     * 4. rész: Dicsőséglista megnyitására szolgáló gomb.
     */
    public Menu() {
        setLayout(new GridLayout(4, 1));
        JLabel label1 = new JLabel("Amőba");
        label1.setFont(new Font("Arial", Font.PLAIN, 20));
        add(label1);
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setVerticalAlignment(SwingConstants.CENTER);

        JPanel valaszto = new JPanel(new GridLayout(2, 1));
        JLabel valassz = new JLabel("Válaszd ki, hogy milyen nagy legyen a pálya!");
        valassz.setVerticalAlignment(SwingConstants.CENTER);
        valassz.setHorizontalAlignment(SwingConstants.CENTER);
        valaszto.add(valassz);
        slider = new JSlider(3, 11);
        slider.setMajorTickSpacing(2);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        JPanel sliderpanel = new JPanel();
        sliderpanel.add(slider);
        valaszto.add(sliderpanel);
        add(valaszto);


        JButton kezdes = new JButton("JÁTÉK KEZDÉSE");
        JPanel alpanel2 = new JPanel();
        alpanel2.add(kezdes);
        add(alpanel2);
        kezdes.addActionListener(new KezdesListener());
        kezdes.setPreferredSize(new Dimension(200, 40));

        JButton dicslista = new JButton("DICSŐSÉGLISTA");
        dicslista.setPreferredSize(new Dimension(200, 40));
        dicslista.addActionListener(new ListListener());
        JPanel alpanel3 = new JPanel();
        alpanel3.add(dicslista);
        add(alpanel3);
        MainFrame frame = (MainFrame) SwingUtilities.getWindowAncestor(Menu.this);

    }

    /**
     * A "Játék kezdése" gombhoz tartozó ActionListener
     */
    class KezdesListener implements ActionListener {
        /**
         * Ha rákattintunk, elindul a játék azzal a mérettel, ami felette kiválasztható.
         *
         * @param actionEvent Az esemény, ami történt.
         */
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            MainFrame frame = (MainFrame) SwingUtilities.getWindowAncestor(Menu.this);
            JPanel fopanel = (JPanel) frame.getContentPane();
            CardLayout cl = (CardLayout) fopanel.getLayout();
            TablaView t = new TablaView();
            t.setMeret(slider.getValue());
            fopanel.add(t, "table");
            cl.show(fopanel, "table");
            frame.setSize(new Dimension(1000, 800));
            frame.setLocationRelativeTo(null);
        }
    }

    /**
     * A "Dicsőséglista" gombhoz tartozó ActionListener
     */
    class ListListener implements ActionListener {
        /**
         * Ha rákattintunk, előjön a dicsőséglista.
         *
         * @param actionEvent Az esemény, ami történt.
         */
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            MainFrame frame = (MainFrame) SwingUtilities.getWindowAncestor(Menu.this);
            JPanel fopanel = (JPanel) frame.getContentPane();
            DicsListaView d = new DicsListaView();
            fopanel.add(d, "dicslista");
            CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
            d.frissit();
            cl.show(fopanel, "dicslista");
            frame.setSize(new Dimension(800, 700));
            frame.setLocationRelativeTo(null);
        }
    }

}
