package logic;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A tábla osztály egy játék reprezentációja. A játék lebonyolításáért felelős.
 */
public class Tabla {
    /**
     * A tábla mérete.
     */
    private int meret;
    /**
     * Az egy sorban, oszlopban vagy átlósan a nyeréshez kellő amőbák száma
     */
    private int kell;
    /**
     * A játékosokat tároló lista.
     */
    private List<Amoba> jatekosok = new ArrayList<>();
    /**
     * A következő amőba sorszáma. Alapból nulla, majd folyamatosan nő.
     */
    private int kovetkezoIndex = 0;
    /**
     * Az amőbákat tároló mátrix. Ha az adott koordinátán nincs amőba, null-t tárol.
     */
    private Amoba[][] tabla;
    /**
     * Azt tárolja, hogy az adott állás mellett megnyert-e a tábla.
     */
    private boolean nyert = false;

    /**
     * Konstruktor. Beállítja, hogy hány játékos játsszon, a paraméterként kapott méretű pályán.
     * Megállapítja, hogy hány amőba kell egymás mellett a nyeréshez.
     *
     * @param m A pálya mérete
     */
    public Tabla(int m) {
        char[] jelek = {'O', 'X'};
        int jatekosokSzama = jelek.length;
        for (int i = 0; i < jatekosokSzama; i++)
            jatekosok.add(new Amoba(jelek[i]));
        meret = m;
        tabla = new Amoba[meret][meret];
        for (Amoba[] array : tabla) {
            for (Amoba a : array) {
                a = null;
            }
        }
        kell = meret;
        if (meret >= 7) kell = 5;
        kovetkezoIndex = 0;
    }

    /**
     * Megmondja, hogy van-e lehetőség még amőba elhelyezésére a pályán.
     *
     * @return Igaz vagy hamis, aszerint, hogy van-e szabad hely vagy nincs.
     */
    public boolean vanMegLepes() {
        if (nyert)
            return false;
        for (int i = 0; i < meret; i++)
            for (int j = 0; j < meret; j++)
                if (tabla[i][j] == null)
                    return true;
        return false;
    }

    /**
     * Az adott koordinátára megpróbál elhelyezni egy amőbát. Ha már van ott amőba, nem sikerül.
     *
     * @param x A kívánt koordináta x része.
     * @param y A kívánt koordináta y része.
     * @return Hogy sikerült-e.
     */
    public boolean addAmoba(int x, int y) {
        if (nyertes() == null && tabla[x][y] == null) {
            tabla[x][y] = jatekosok.get(kovetkezoIndex);
            kovetkezoIndex = (kovetkezoIndex + 1) % jatekosok.size();
            return true;
        }
        return false;
    }

    /**
     * Megnézi, hogy melyik amőba nyert úgy, hogy egy sorban összegyűlt elegendő amőba.
     *
     * @return A nyertes amőba. Ha nincs ilyen, akkor null.
     */
    private Amoba sorban() {
        Amoba nyero = null;
        for (int i = 0; i < meret; i++) {
            Amoba elozo = null;
            int db = 1;
            for (int j = 0; j < meret; j++) {
                if (elozo != null && elozo.equals(tabla[i][j])) {
                    db++;
                } else {
                    db = 1;
                }
                elozo = tabla[i][j];
                if (db == kell) {
                    nyero = elozo;
                }
            }
        }
        return nyero;
    }

    /**
     * Megnézi, hogy melyik amőba nyert úgy, hogy egy oszlopban összegyűlt elegendő amőba.
     *
     * @return A nyertes amőba. Ha nincs, null.
     */
    private Amoba oszlopban() {
        for (int i = 0; i < meret; i++) {
            Amoba elozo = new Amoba('3');
            int db = 1;

            for (int j = 0; j < meret; j++) {
                if (tabla[j][i] != null && tabla[j][i].equals(elozo)) {
                    db++;
                } else db = 1;
                elozo = tabla[j][i];
                if (db == kell) {
                    return elozo;
                }
            }
        }
        return null;
    }

    /**
     * Megnézi, hogy melyik amőba nyert úgy, hogy átlósan összegyűlt elegendő amőba.
     *
     * @return A nyertes amőba, ha nincs, akkor null.
     */
    private Amoba atlosan() {
        for (int i = 0; i < meret; i++) {
            for (int j = 0; j < meret; j++) {
                Amoba vizsgalando = tabla[i][j];
                int db = 1;

                int k = i + 1;//jobbra és lefelé vizsgálat
                int l = j + 1;
                while (k < meret && l < meret && vizsgalando != null && vizsgalando.equals(tabla[k][l])) {
                    db++;
                    if (db == kell)
                        return vizsgalando;
                    k++;
                    l++;
                }

                db = 1;
                int s = i + 1;//balra és lefelé vizsgálat
                int t = j - 1;
                while (s < meret && t >= 0 && vizsgalando != null && vizsgalando.equals(tabla[s][t])) {
                    db++;
                    if (db == kell)
                        return vizsgalando;
                    s++;
                    t--;
                }

            }
        }
        return null;
    }

    /**
     * Megnézi, hogy melyik amőba nyert úgy, hogy bárhogyan összegyűlt elegendő amőba.
     *
     * @return A nyertes amőba. Ha nincs, null.
     */
    public Amoba nyertes() {
        Amoba sorbanNyero = sorban();
        if (sorbanNyero != null) {
            nyert = true;
            return sorbanNyero;
        }
        Amoba oszlopbanNyero = oszlopban();
        if (oszlopbanNyero != null) {
            nyert = true;
            return oszlopbanNyero;
        }
        Amoba atlosanNyero = atlosan();
        if (atlosanNyero != null) {
            nyert = true;
            return atlosanNyero;
        }
        return null;
    }

    /**
     * Megmondja, hogy mennyi amőba van egy fajtából.
     *
     * @param a A vizsgált amőba, amit megszámolunk.
     * @return Az amőbák száma a táblán.
     */
    public int amobaSzam(Amoba a) {
        int db = 0;
        for (int i = 0; i < meret; i++)
            for (int j = 0; j < meret; j++)
                if (a.equals(tabla[i][j]))
                    db++;
        return db;
    }

    /**
     * Megnézi, hogy mi a következő játékos jele
     *
     * @return A következő játékos jele karakterként.
     */
    public char getJel() {
        return jatekosok.get(kovetkezoIndex).getJel();
    }

    /**
     * A játékosokat tároló listát adja vissza.
     *
     * @return A játékosokat tároló lista nem módosítható másolata.
     */
    public List<Amoba> getJatekosok() {
        return Collections.unmodifiableList(jatekosok);
    }
}
