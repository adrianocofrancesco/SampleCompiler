import lt.macchina.Codice;
import static lt.macchina.Macchina.*;

/**
 * La classe astratta Ripetizione definisce il parse tree per l'istruzione di ripetizione.
 * La classe estende Istruzione poichè nella grammatica ne è una derivazione.
 *
 * ripetizione    -> loop espressione cr seqIstruzioni endLoop
 */
abstract class Ripetizione extends Istruzione {

    public abstract void generaCodice(Codice c);

}

/**
 * La classe RipetizioneIstr viene utilizzata per gestire l'istruzione di ripetizione
 */
class RipetizioneIstr extends Ripetizione {

    private Expr espressione;
    private SeqIstruzioni seqIstruzioni;

    public RipetizioneIstr(Expr e, SeqIstruzioni sq) {
        this.espressione = e;
        this.seqIstruzioni = sq;
    }

    public void generaCodice(Codice c) {

        int inizio = c.indirizzoProssimaIstruzione();

        //condizione
        espressione.generaCodice(c);

        int addrJumpZ = c.generaParziale(JZERO);

        //istruzioni interne al loop
        seqIstruzioni.generaCodice(c);

        c.genera(JUMP, inizio);

        int fine = c.indirizzoProssimaIstruzione();

        c.completaIstruzione(addrJumpZ, fine);
    }

    public String toString() {return "loop " + espressione.toString() + " cr " + seqIstruzioni.toString() + " endLoop";}
}
