/**
 * Tipo enumerativo e classe per rappresentare i Token
 */
enum TipoToken {
  EOF,
  PIU, MENO, PER, DIVISO, MODULO,
  UNARIO, APERTA,CHIUSA, ASSEGNA,
  IDENT, STRING, INPUT, OUTPUT, NEWLINE,
  NUMERO,
  CR, ALTRO,
  LOOP, ENDLOOP, IFTHEN, IFELSE;
}

/**
 * Classe che rappresenta i Token restituiti dall'analizzatore lessicale
 */
class Token {

  private TipoToken tipo;
  private Object valore;

  //costruttore per token generico
  public Token(TipoToken t) {
    tipo = t;
    valore = null;
  }

  //costruttore per token IDENT, STRING o ALTRO
  public Token(TipoToken t, String s) {
    tipo = t;
    valore = s;
  }

  //costruttore per token NUMERO
  public Token(TipoToken t, Integer num) {
    tipo = t;
    valore = num;
  }
 
  //restituisce il tipo del token
  public TipoToken getTipo() {
    return tipo;
  }

  //restituisce il valore associato al token, come Object
  public Object getValore() {
    return valore;
  }

  //restituisce la stringa associata al token
  //(errore in esecuzione se non vi e' associata una stringa)
  public String getString() {
    return (String) valore;
  }

  //restituisce l'intero associato al token
  //(errore in esecuzione se non vi e' associata una stringa)
  public Integer getInteger() {
    return (Integer) valore;
  }

  public String toString() {
    if (valore == null)
      return tipo.toString();
    else
      return tipo.toString() + "  " + valore;
   }
}

