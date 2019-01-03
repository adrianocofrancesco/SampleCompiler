import java_cup.runtime.*;
import java.io.*;
import lt.macchina.*;
import static lt.macchina.Macchina.*;

/**
 * Classe utilizzata per generare il codice macchina, eseguibile attravero la classe Macchina, può leggere
 * un'espressione da riga di comando oppure da file in input.
 * Eventuali identificatori vengono inizializzati a 0.
 */
class Compilatore {
  
  public static void main(String[] args) throws java.io.IOException {

    //creazione della symbol factory
    ComplexSymbolFactory sf = new ComplexSymbolFactory();

    //creazione dell'analizzatore lessicale
    Scanner scanner;
    if (args.length == 0) {
      System.out.print("Espressione? ");
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      String s = in.readLine() + '\n';
      scanner = new Scanner(new StringReader(s), sf);
    } else
      scanner = new Scanner(new FileReader(args[0]), sf);

    //creazione del parser
    Parser p = new Parser(scanner, sf);

    try {
      Symbol ris = p.parse();
      ExprConTab risultato = (ExprConTab) ris.value;

      Programma albero = risultato.getProgramma();

      System.out.println("Parse tree generato correttamente");

      SymbolTable tabella = risultato.getSymbolTable();

      Codice c = new Codice("eseguibile");

      //assegna gli indirizzi alle variabili
      int proxIndirizzo = 0;
      for (Descrittore d : tabella)
        proxIndirizzo = d.assegnaIndirizzo(proxIndirizzo);

      //genera il codice per riservare lo spazio alle variabili che saranno inizializzate a zero
      for (Descrittore d : tabella) {

        String id = d.getIdentificatore();

        c.genera(PUSHIMM, 0);
        c.genera(POP, d.getIndirizzo());

        c.genera(PUSH, d.getIndirizzo());
      }

      //genera il codice per valutare l'espressione
      albero.generaCodice(c);

      //istruzione usata per stampare valore solo se l'espressione è inserita da riga di comando
      if (args.length == 0)
        c.genera(OUTPUT);

      c.genera(PUSHIMM, '\n');
      c.genera(OUTPUTCH);
      c.genera(HALT);

      //generazione del file con il codice del programma
      c.fineCodice();

    } catch (Exception e)  {
      System.out.println(e);
    }

  }
}