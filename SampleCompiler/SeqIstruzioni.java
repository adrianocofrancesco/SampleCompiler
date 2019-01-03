import lt.macchina.Codice;
import static lt.macchina.Macchina.*;
/**
 * La classe astratta SeqIstruzioni definisce il parse tree per le sequenze di istruzioni del linguaggio.
 * La classe estende Programma poichè nella grammatica ne è una derivazione.
 *
 * seqIstruzioni      -> istruzione cr
 *                    | seqIstruzioni istruzione cr
 */
abstract class SeqIstruzioni extends Programma {
    public abstract void generaCodice(Codice c);
}

/**
 * La classe SeqIstruzioniBase viene utilizzata per gestire la produzione  seqIstruzioni -> istruzione cr
 */
class SeqIstruzioniBase extends SeqIstruzioni {

    private Istruzione istruzione;

    public SeqIstruzioniBase(Istruzione i) {this.istruzione = i;}

    public void generaCodice(Codice c) {istruzione.generaCodice(c);}

    public String toString() {return istruzione.toString();}
}

/**
 * La classe SeqIstruzioniComposte viene utilizzata per gestire la produzione  seqIstruzioni -> seqIstruzioni istruzione cr
 */
class SeqIstruzioniComposte extends SeqIstruzioni {

    private Istruzione istruzione;
    private SeqIstruzioni seqIstruzioni;

    public SeqIstruzioniComposte(SeqIstruzioni sq, Istruzione i) {
        this.istruzione = i;
        this.seqIstruzioni = sq;
    }

    public void generaCodice(Codice c) {
        seqIstruzioni.generaCodice(c);
        istruzione.generaCodice(c);
    }

    public String toString() {return seqIstruzioni.toString() + " " + istruzione.toString();}
}
