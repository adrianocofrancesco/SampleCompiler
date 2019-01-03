/*********************** TokenTest.java **********************

  Elenca i token presenti nella sorgente di input.
  La sorgente di input e' un file il cui nome viene specificato
  sulla riga di comando. Se non viene specificato il nome la
  lettura avviene da tastiera.
  Al termine visualizza alcune informazioni relative a quanto
  letto.

 Passi da eseguire:
 - jflex expr_test.lex
 - javac Token.java
 - javac TokenTest.java
 - javac Scanner.java
 - java TokenTest nome_file
************************************************************/


/*
import java.io.*;

class TokenTest {
    public static void main(String args[]) throws IOException {
      Scanner scanner;
      if (args.length == 0) 
        scanner = new Scanner(new InputStreamReader(System.in));
      else
        scanner = new Scanner(new FileReader(args[0]));
      Token t;
      while ((t = scanner.getNext()).getTipo() != TipoToken.EOF)
         System.out.println(t.toString());
    }
}

*/