import lt.macchina.Codice;
import static lt.macchina.Macchina.*;
/**
 * La classe astratta Istruzione definisce il parse tree per le istruzioni del linguaggio.
 * La classe estende SeqIstruzioni poichè nella grammatica ne è una derivazione.
 *
 * istruzione      -> assegnamento
 *                  | scrittura
 *                  | ripetizione
 */
abstract class Istruzione extends SeqIstruzioni {

    public abstract void generaCodice(Codice c);

}

/**
 * La classe IstruzioneExpr viene utilizzata per gestire la produzione  istruzione -> assegnamento
 */
class IstruzioneExpr extends Istruzione {

    private Expr espressione;

    public  IstruzioneExpr (Expr e) {
        espressione = e;
    }

    public Expr getExpr() {
        return espressione;
    }

    public void generaCodice(Codice c) {
        espressione.generaCodice(c);
    }

    public String toString() {
        return espressione.toString();
    }
}

/**
 * La classe IstruzioneScrittura viene utilizzata per gestire la produzione  istruzione -> scrittura
 */
class IstruzioneScrittura extends Istruzione {

    private Scrittura scrittura;

    public  IstruzioneScrittura (Scrittura s) {
        scrittura = s;
    }

    public void generaCodice(Codice c) {
        scrittura.generaCodice(c);
    }

    public String toString() {
        return scrittura.toString();
    }
}

/**
 * La classe IstruzioneRipetizione viene utilizzata per gestire la produzione  istruzione -> ripetizione
 */
class IstruzioneRipetizione extends Istruzione {

    private Ripetizione rip;

    public  IstruzioneRipetizione (Ripetizione r) {
        rip = r;
    }

    public void generaCodice(Codice c) {
        rip.generaCodice(c);
    }

    public String toString() {
        return rip.toString();
    }
}
