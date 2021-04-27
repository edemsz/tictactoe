package logic;

import java.io.Serializable;

/**
 * Egy eredménypontszámot tároló osztály. Szerializálható, összehasonlítható.
 */
public class Pontszam implements Comparable<Pontszam>, Serializable {

    /**
     * A játékos neve, aki elérte a pontszámot.
     */
    String nev;
    /**
     * A pontszám mennyisége, amit a játékos elért.
     */
    Integer pont;

    /**
     * Konstruktor, beállítja az adattagokat.
     *
     * @param nev  A beállítandó név
     * @param pont A beállítandó pontszám
     */
    public Pontszam(String nev, Integer pont) {
        this.nev = nev;
        this.pont = pont;
    }

    /**
     * Getter a nev attribútumra
     *
     * @return A pontszámot elérő játékos neve
     */
    public String getNev() {
        return nev;
    }

    /**
     * Getter a pont attribútumra
     *
     * @return A pontszámot elérő játékos pontja
     */

    public Integer getPont() {
        return pont;
    }

    /**
     * Két Pontszam típusú objektumot hasonlít össze a pont adattag értéke szerint.
     *
     * @param pontszam A másik összehasonlítandó Pontszam
     * @return Egy egész szám, ami a szokásos módon jelzi, hogy melyik objektum a nagyobb.
     */
    @Override
    public int compareTo(Pontszam pontszam) {
        return pont.compareTo(pontszam.pont);
    }
}
