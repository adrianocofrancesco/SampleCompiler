/**
* La classe astratta Expr definisce il parse tree per le espressioni.
* espressione   -> numero
*               | identificatore
*               | espressione + espressione
*               | espressione - espressione
*               | espressione * espressione
*               | espressione / espressione
*               | espressione % espressione
*               | - espressione
*               | + espressione
*               | ( espressione )
*               | identificatore = espressione
*               | espressione ? espressione : espressione
*               | input
*               | input stringa
* Le sottoclassi concrete si riferiscono a specifici tipi di espressioni.
* Il metodo generaCodice permette di generare il codice per calcolare l'espressione.
*/

import lt.macchina.Codice;
import static lt.macchina.Macchina.*;

import java.util.Date;

abstract class Expr {

  public abstract void generaCodice(Codice c);

}

/**
 * Classe utilizzata per calcolare l'espressione addizione
 */
class PiuExpr extends Expr {
  private Expr sx, dx;

  public PiuExpr(Expr sx, Expr dx) {
    this.sx = sx;
    this.dx = dx;
  }

  public void generaCodice(Codice c) {
    sx.generaCodice(c);
    dx.generaCodice(c);
    c.genera(ADD);
  }

  public String toString() {
    return sx.toString() + " " + dx.toString() + " +";
  }
}

/**
 * Classe utilizzata per calcolare l'espressione sottrazione
 */
class MenoExpr extends Expr {
  private Expr sx, dx;

  public MenoExpr(Expr sx, Expr dx) {
    this.sx = sx;
    this.dx = dx;
  }

  public void generaCodice(Codice c) {
    sx.generaCodice(c);
    dx.generaCodice(c);
    c.genera(SUB);
  }

  public String toString() {
    return sx.toString() + " " + dx.toString() + " -";
  }
}

/**
 * Classe utilizzata per calcolare l'espressione moltiplicazione
 */
class PerExpr extends Expr {
  private Expr sx, dx;

  public PerExpr(Expr sx, Expr dx) {
    this.sx = sx;
    this.dx = dx;
  }

  public void generaCodice(Codice c) {
    sx.generaCodice(c);
    dx.generaCodice(c);
    c.genera(MUL);
  }

  public String toString() {
    return sx.toString() + " " + dx.toString() + " *";
  }
}

/**
 * Classe utilizzata per calcolare l'espressione divisione
 */
class DivisoExpr extends Expr {
  private Expr sx, dx;

  public DivisoExpr(Expr sx, Expr dx) {
    this.sx = sx;
    this.dx = dx;
  }

  public void generaCodice(Codice c) {
    sx.generaCodice(c);
    dx.generaCodice(c);
    c.genera(DIV);
  }

  public String toString() {
    return sx.toString() + " " + dx.toString() + " /";
  }
}

/**
 * Classe utilizzata per calcolare l'espressione modulo
 */
class ModuloExpr extends Expr {
  private Expr sx, dx;
  private SymbolTable sym;
  private Descrittore d1, d2;

  public ModuloExpr(Expr sx, Expr dx, SymbolTable sym) {
    this.sx = sx;
    this.dx = dx;
    this.sym = sym;

    Date date = new Date();
    long time = date.getTime();

    //creazione di due variabili temporanee per evitare di rivalutare più volte una stessa espressione
    //a fronte di una sola occorrenza nel sorgente
    d1 = sym.trovaEAggiungi("modTempVarSx" + time);
    d2 = sym.trovaEAggiungi("modTempVarDx" + time);

  }

  public void generaCodice(Codice c) {
    sx.generaCodice(c);

    c.genera(POP, d1.getIndirizzo());
    c.genera(PUSH, d1.getIndirizzo());
    c.genera(PUSH, d1.getIndirizzo());

    dx.generaCodice(c);

    c.genera(POP, d2.getIndirizzo());
    c.genera(PUSH, d2.getIndirizzo());

    c.genera(DIV);

    c.genera(PUSH, d2.getIndirizzo());

    c.genera(MUL);

    c.genera(SUB);
  }

  public String toString() {
    return sx.toString() + " " + dx.toString() + " %";
  }
}

/**
 * Classe utilizzata per calcolare l'espressione assegnamento
 */
class AssegnaExpr extends Expr {
  private Expr dx;
  private Descrittore descrittore;

  public AssegnaExpr(Descrittore d, Expr dx) {
    this.dx = dx;
    this.descrittore = d;
  }

  public void generaCodice(Codice c) {
    dx.generaCodice(c);
    c.genera(POP, descrittore.getIndirizzo());
    c.genera(PUSH, descrittore.getIndirizzo());
  }

  public String toString() {
    return descrittore.getIdentificatore() + " " + dx.toString() + " =";
  }
}

/**
 * Classe utilizzata per calcolare l'espressione meno unario
 */
class UnMenoExpr extends Expr {
  private Expr e;

  public UnMenoExpr(Expr e) {
    this.e = e;
  }

  public void generaCodice(Codice c) {
    c.genera(PUSHIMM, 0);
    e.generaCodice(c);
    c.genera(SUB);
  }

  public String toString() {
    return e.toString() + "-";
  }
}

/**
 * Classe utilizzata per calcolare l'espressione più unario
 */
class UnPiuExpr extends Expr {
  private Expr e;

  public UnPiuExpr(Expr e) {
    this.e = e;
  }

  public void generaCodice(Codice c) {
    e.generaCodice(c);
  }

  public String toString() {
    return e.toString() + "+";
  }
}

/**
 * Classe utilizzata per gestire numeri interi o esadecimali
 */
class NumExpr extends Expr {
  private Integer num;

  public NumExpr(String n) {

    if (n.contains("0x") || n.contains("0X")) {

      String numStr = n.substring(2);
      this.num = Integer.parseInt(numStr, 16);

    } else
      this.num = Integer.parseInt(n);

  }

  public void generaCodice(Codice c) {

    c.genera(PUSHIMM, num.intValue());
  }
  
  public String toString() {
    return num.toString();
  }
}

/**
 * Classe utilizzata per gestire identificatori
 */
class IdExpr extends Expr {
  private Descrittore descrittore;

  public IdExpr(Descrittore d) {
    descrittore = d;
  }

  public void generaCodice(Codice c) {
    c.genera(PUSH, descrittore.getIndirizzo());
  }

  public String toString() {
    return descrittore.getIdentificatore();
  }
}

/**
 * Classe utilizzata per gestire l'struzione di input
 */
class InputExpr extends Expr {
  private String stringa;

  public InputExpr(String s) {this.stringa = s;}

  public void generaCodice(Codice c) {

    for (int k = 0; k < stringa.length(); k++) {
      c.genera(PUSHIMM, stringa.charAt(k));
      c.genera(OUTPUTCH);
    }
    c.genera(PUSHIMM, '?');
    c.genera(OUTPUTCH);
    c.genera(PUSHIMM, ' ');
    c.genera(OUTPUTCH);
    c.genera(INPUT);
  }

  public String toString() {
    return stringa;
  }
}

/**
 * Classe utilizzata per calcolare l'espressione condizionale ternaria
 */
class IfCondExpr extends Expr {
  private Expr espr1, espr2, espr3;

  public IfCondExpr(Expr e1, Expr e2, Expr e3) {
    this.espr1 = e1;
    this.espr2 = e2;
    this.espr3 = e3;
  }

  public void generaCodice(Codice c) {

    espr1.generaCodice(c);

    //salta se il risultato dell'espressione precedente è uguale a zero
    int addrJumpZThen = c.generaParziale(JZERO);

    espr2.generaCodice(c);

    //salta se è stato eseguito il ramo then
    int addrJumpZElse = c.generaParziale(JUMP);

    //indirizzo di arrivo se la condizione è stata valutata falsa
    int zeroVal = c.indirizzoProssimaIstruzione();

    espr3.generaCodice(c);

    //indirizzo di fine condizione
    int fine = c.indirizzoProssimaIstruzione();

    c.completaIstruzione(addrJumpZThen, zeroVal);

    c.completaIstruzione(addrJumpZElse, fine);
  }

  public String toString() {
    return espr1.toString() + " " + espr2.toString() + " " + espr3.toString() + " ? :";
  }
}
