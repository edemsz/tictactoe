package logic;

import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A dicsőséglista osztály tárolja a dicsőséglistát. Szerializálható. Az AbstractTableModel-ből származik le.
 */
public class Dicsoseglista extends AbstractTableModel implements Serializable {
    /**
     * A dicsőséglista egy pontszámokat tároló listában.
     */
    List<Pontszam> dicsoseglista = new ArrayList<>();

    /**
     * Hozzáadja a paraméterként kapott pontszámot a dicsőséglistához, ha van még benne van elég hely,
     * vagy ha olyan jó, hogy bele kell tenni.
     *
     * @param p A hozzáaadandó pontszám.
     */
    public void add(Pontszam p) {
        if (dicsoseglista.size() == 5 && dicsoseglista.get(4).getPont() > p.getPont()) {
            dicsoseglista.remove(4);
            dicsoseglista.add(p);
        }
        if (dicsoseglista.size() < 5) dicsoseglista.add(p);
        Collections.sort(dicsoseglista);
    }

    /**
     * A beolvasófüggvény a paraméterként kapott nevű fájlból beolvassa a dicsőséglista listáját.
     *
     * @param name A fájlnév, amiben szerializálva található a dicsőséglista listája.
     */
    public void load(String name) {
        List<Pontszam> ret = new ArrayList<>();
        try {
            FileInputStream f = new FileInputStream(name);
            ObjectInputStream in = new ObjectInputStream(f);
            ret = (ArrayList<Pontszam>) in.readObject();
            in.close();
        } catch (Exception e) {
        }
        dicsoseglista = ret;
    }

    /**
     * A kimentő függvény a paraméterként kapott nevű fájlba kimenti a dicsőséglista listáját.
     *
     * @param name A fájlnév, amibe menti a dicsőséglistát.
     */
    public void save(String name) {
        try {
            FileOutputStream f = new FileOutputStream(name);
            ObjectOutputStream out = new ObjectOutputStream(f);
            out.writeObject(dicsoseglista);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Eldönti a paraméterként kapott pontról, hogy felfér-e a dicsőséglistára.
     *
     * @param pont A vizsgálandó pont.
     * @return Igaz vagy hamis, aszerint, hogy felfér-e vagy sem.
     */
    public boolean beleteendo(int pont) {
        if (dicsoseglista.size() < 5) return true;
        return dicsoseglista.get(dicsoseglista.size() - 1).getPont() > pont;
    }

    /**
     * Megadja, hogy hány sora van a dicsőséglistának.
     *
     * @return A dicsőséglista sorainak száma.
     */
    @Override
    public int getRowCount() {
        return dicsoseglista.size();
    }

    /**
     * Megadja, hogy hány oszlopa van a dicsőséglistának: kettő (pontszám és név).
     *
     * @return A dicsőséglista oszlopainak száma: mindig kettő.
     */
    @Override
    public int getColumnCount() {
        return 2;
    }

    /**
     * Adott helyen lévő adat lekérdezésére szolgáló függvény.
     *
     * @param i  Az adat sorszáma a listában.
     * @param i1 Az adat oszlopának száma. Ha 0, a névre, ha 1, a pontszámra kíváncsi a függvényhívó.
     * @return A megfelelő adat.
     */
    @Override
    public Object getValueAt(int i, int i1) {
        if (i1 == 0) return dicsoseglista.get(i).nev;
        return dicsoseglista.get(i).pont;
    }

    /**
     * Az oszlopok nevét megadó függvény.
     *
     * @param i Az oszlop sorszáma.
     * @return Az adott oszlop neve.
     */
    public String getColumnName(int i) {
        if (i == 0) return "Név";
        return "Pont";
    }
}
