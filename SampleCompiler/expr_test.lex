/* File di specifica lessicale */

%% 

%unicode

%class Scanner
%function getNext
%type Token

%char
%line
%column


%{
  //Codice copiato testualmente nella classe generata.
  //Contiene un campo e metodi aggiuntivi

  private int nToken;  // campo per il conteggio token

  //metodi per restituire il numero di caratteri, righe e token  
  public int nCaratteri() {
    return yychar;
  }

  public int nRighe() {
    return yyline;
  }

  public int nToken() {
    return nToken;
  }

%}

%init{
  //codice copiato testualmente nel costruttore:

  //messaggio iniziale
  System.out.println("Inizio analisi lessicale");

%init}

%eof{
  //codice eseguito quando viene raggiunta la fine del file
  System.out.println("Fine analisi lessicale");
%eof}

FINERIGA = \r | \n | \r\n

INTERO = [:digit:]
LETTERA = [:letter:]
SPAZIATURA = [ \t\f] | {FINERIGA}
STRING = \"([^\\\"]|\\.)*\"
HEXINT = 0[xX][0-9a-fA-F]+

CIFRA = ({INTERO} | {HEXINT})

NEXTLINE = "&" .* {FINERIGA}?

COMMENT     = "//" .* {FINERIGA}?

%% 

"/"        {return new Token(TipoToken.DIVISO);}
"-"        {return new Token(TipoToken.MENO);}
"*"        {return new Token(TipoToken.PER);}
"+"        {return new Token(TipoToken.PIU);}
"%"        {return new Token(TipoToken.MODULO);}
"="        {return new Token(TipoToken.ASSEGNA);}
"("        {return new Token(TipoToken.APERTA);}
")"        {return new Token(TipoToken.CHIUSA);}
"input"    {return new Token(TipoToken.INPUT);}
"output"   {return new Token(TipoToken.OUTPUT);}
"newLine"  {return new Token(TipoToken.NEWLINE);}
"loop"     {return new Token(TipoToken.LOOP);}
"endLoop"  {return new Token(TipoToken.ENDLOOP);}
"?"        {return new Token(TipoToken.IFTHEN);}
":"        {return new Token(TipoToken.IFELSE);}

{CIFRA}+ {return new Token(TipoToken.NUMERO, yytext());}
{LETTERA}({LETTERA}|{CIFRA})* {return new Token(TipoToken.IDENT, yytext());}


{STRING}  {return new Token(TipoToken.STRING, yytext());}


{FINERIGA} {return new Token(TipoToken.CR, yytext());}

{SPAZIATURA} { }

{NEXTLINE} { }

{COMMENT} { }

.        {return new Token(TipoToken.ALTRO, yytext());}

<<EOF>>  {return new Token(TipoToken.EOF);}


