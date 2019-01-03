import lt.macchina.Codice;
import static lt.macchina.Macchina.*;

/**
 * La classe astratta Programma definisce il parse tree per il linguaggio preso in esempio.
 * Nella grammatica è il simbolo iniziale.
 *
 * programma -> seqIstruzioni
*/
abstract class Programma {

  public abstract void generaCodice(Codice c);

}

/**
 * Classe utilizzata per gestire il risultato del Parser
 */
class ExprConTab {

  //Symbol Table utilizzata dal compilatore
  private SymbolTable tabella;

  private Programma programma;

  public ExprConTab(Programma p, SymbolTable s) {
    programma = p;
    tabella = s;
  }

  public Programma getProgramma() {
    return programma;
  }

  public SymbolTable getSymbolTable() {
    return tabella;
  }

}
