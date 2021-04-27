package logic;

import java.util.Objects;

/**
 * Az amőba osztály a játékosokat szimbolizálja. Egy játékos egy amőbával játszik az egész játékban.
 */
public class Amoba {
    /**
     * Az amőba jele, amit letesz a pályára
     */
    private char jel;

    /**
     * Az amőba osztály konstruktora.
     *
     * @param c Az a karakter, amit be akarunk állítani jelként.
     */
    public Amoba(char c) {
        jel = c;
    }

    /**
     * Az amőba jelével tér vissza
     *
     * @return Az amőba jele karakterként.
     */
    public char getJel() {
        return jel;
    }

    /**
     * Egyenlőséget vizsgáló függvény, ami az amőbákat összehasonlítja, a jelük szerint.
     *
     * @param o A másik amőba, amivel az egyenlőséget vizsgáljuk.
     * @return Igaz vagy hamis, aszerint, hogy egyenlők-e, vagy sem.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Amoba amoba = (Amoba) o;
        return jel == amoba.jel;
    }

    /**
     * Az amőba hashkódját adja meg. Az equals függvény felüldefiniálása miatt kell implementálni.
     *
     * @return Az amőba hashkódja.
     */
    @Override
    public int hashCode() {
        return Objects.hash(jel);
    }
}
