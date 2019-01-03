import lt.macchina.Codice;
import static lt.macchina.Macchina.*;
/**
 * La classe astratta Scrittura definisce il parse tree per le istruzioni di scrittura.
 * La classe estende Istruzione poichè nella grammatica ne è una derivazione.
 *
 * scrittura    -> output stringa espressione
 *               | output stringa
 *               | output espressione
 *               | newLine
 */
abstract class Scrittura extends Istruzione {

    public abstract void generaCodice(Codice c);

}

/**
 * La classe ScritturaStringa viene utilizzata per gestire la produzione  scrittura -> output stringa
 */
class ScritturaStringa extends Scrittura {

    private String stringa;

    public ScritturaStringa(String s) {this.stringa = s;}

    public void generaCodice(Codice c) {

        if (this.stringa.equals("newLine")) {
            c.genera(PUSHIMM, '\n');
            c.genera(OUTPUTCH);
        } else {

            for (int k = 0; k < stringa.length(); k++) {
                c.genera(PUSHIMM, stringa.charAt(k));
                c.genera(OUTPUTCH);
            }
            c.genera(PUSHIMM, ' ');
            c.genera(OUTPUTCH);
        }
    }

    public String toString() {
        return stringa;
    }
}

/**
 * La classe ScritturaExpr viene utilizzata per gestire la produzione  scrittura -> output espressione
 */
class ScritturaExpr extends Scrittura {

    private Expr espressione;

    public ScritturaExpr(Expr e) {this.espressione = e;}

    public void generaCodice(Codice c) {

        espressione.generaCodice(c);

        c.genera(OUTPUT);
    }

    public String toString() { return espressione.toString();}
}

/**
 * La classe ScritturaStringaExpr viene utilizzata per gestire la produzione  scrittura -> output stringa espressione
 */
class ScritturaStringaExpr extends Scrittura {

    private String stringa;
    private Expr espressione;

    public ScritturaStringaExpr(String s, Expr e) {
        this.stringa = s;
        this.espressione = e;
    }

    public void generaCodice(Codice c) {

        for (int k = 0; k < stringa.length(); k++) {
            c.genera(PUSHIMM, stringa.charAt(k));
            c.genera(OUTPUTCH);
        }
        c.genera(PUSHIMM, ' ');
        c.genera(OUTPUTCH);

        espressione.generaCodice(c);

        c.genera(OUTPUT);
    }

    public String toString() { return stringa + " " + espressione.toString();}
}
