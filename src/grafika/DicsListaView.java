package grafika;


import javax.swing.*;
import java.awt.*;

/**
 * A dicsőséglista megjelenítéséért felelős függvény.
 */
public class DicsListaView extends JPanel {
    /**
     * A bal oldali menüpanel.
     */
    JPanel listaOldal;

    /**
     * A dicsőséglista-panel kirajzolására szolgáló konstruktor.
     */
    public DicsListaView() {
        setLayout(new GridLayout(1, 2));
        JPanel jobboldal = new JPanel(new BorderLayout());
        JButton kilep = new JButton("Kilépés a menübe");
        kilep.addActionListener(
                actionEvent -> {
                    MainFrame frame = (MainFrame) SwingUtilities.getWindowAncestor(DicsListaView.this);
                    CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
                    cl.show(frame.getContentPane(), "main");
                    frame.setSize(400, 500);
                    frame.setLocationRelativeTo(null);
                });
        kilep.setPreferredSize(new Dimension(200, 40));
        JPanel kilepPanel = new JPanel();
        kilepPanel.add(kilep);
        jobboldal.add(kilepPanel, BorderLayout.NORTH);
        add(jobboldal);
    }

    /**
     * A bal oldali menüpanel frissítésére szolgál, ha annak tartalma változott.
     */
    public void frissit() {
        if (listaOldal == null)//ha még nincs meg a baloldal, csinál
            balOldalRajzol();
        else {//ha megvan, frissíti
            MainFrame frame = (MainFrame) SwingUtilities.getWindowAncestor(DicsListaView.this);
            frame.getDicsoseglista().fireTableDataChanged();
        }

    }

    /**
     * A bal oldal kirajzolására szolgál.
     */
    public void balOldalRajzol() {
        if (listaOldal != null)
            remove(listaOldal);
        listaOldal = new JPanel(new BorderLayout());

        JLabel felirat = new JLabel("DICSŐSÉGLISTA", SwingConstants.CENTER);
        felirat.setFont(new Font("Arial", Font.BOLD, 30));
        felirat.setPreferredSize(new Dimension(200, 200));
        listaOldal.add(felirat, BorderLayout.NORTH);
        MainFrame frame = (MainFrame) SwingUtilities.getWindowAncestor(DicsListaView.this);
        JTable tablazat = new JTable(frame.getDicsoseglista());
        tablazat.setFillsViewportHeight(true);
        JScrollPane jsp = new JScrollPane();
        jsp.setViewportView(tablazat);
        listaOldal.add(jsp, BorderLayout.CENTER);

        JLabel also = new JLabel();
        also.setPreferredSize(new Dimension(200, 40));
        listaOldal.add(also, BorderLayout.SOUTH);
        add(listaOldal, 0);

    }
}
