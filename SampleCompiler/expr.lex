/* File di specifica lessicale */

import java_cup.runtime.*;

%% 

%cup
%class Scanner

%ignorecase

%{  //codice per associare la Symbol Factory
    ComplexSymbolFactory sf;
    public Scanner(java.io.Reader in, ComplexSymbolFactory sf) {
	this(in);
	this.sf = sf;
    }

    public int currentLineNumber() {
        return yyline + 1;
    }
%}
%line


INTERO = [:digit:]
LETTERA = [:letter:]
SPAZIATURA = [ \t\f]
STRING = \"([^\\\"]|\\.)*\"
HEXINT = 0[xX][0-9a-fA-F]+

FINERIGA = \r | \n | \r\n

CIFRA = ({INTERO} | {HEXINT})

NEXTLINE = "&" .* {FINERIGA}?

COMMENT = "//" .* {FINERIGA}?

%% 

"/"        {return sf.newSymbol("DIVISO",  ParserSym.DIVISO);}
"-"        {return sf.newSymbol("MENO",    ParserSym.MENO);}
"*"        {return sf.newSymbol("PER",     ParserSym.PER);}
"+"        {return sf.newSymbol("PIU",     ParserSym.PIU);}
"%"        {return sf.newSymbol("MODULO",  ParserSym.MODULO);}
"="        {return sf.newSymbol("ASSEGNA", ParserSym.ASSEGNA);}
"("        {return sf.newSymbol("APERTA",  ParserSym.TONDA_APERTA);}
")"        {return sf.newSymbol("CHIUSA",  ParserSym.TONDA_CHIUSA);}
"input"    {return sf.newSymbol("INPUT",  ParserSym.INPUT);}
"output"   {return sf.newSymbol("OUTPUT",  ParserSym.OUTPUT);}
"newLine"  {return sf.newSymbol("NEWLINE",  ParserSym.NEWLINE);}
"loop"     {return sf.newSymbol("LOOP",  ParserSym.LOOP);}
"endLoop"  {return sf.newSymbol("ENDLOOP",  ParserSym.ENDLOOP);}
"?"        {return sf.newSymbol("IFTHEN",  ParserSym.IFTHEN);}
":"        {return sf.newSymbol("IFELSE",  ParserSym.IFELSE);}


{CIFRA}+ {return sf.newSymbol("NUMERO", ParserSym.NUMERO, yytext());}
{LETTERA}({LETTERA}|{CIFRA})* {return sf.newSymbol("IDENT", ParserSym.IDENT, yytext());}


{STRING}  {return sf.newSymbol("STRING", ParserSym.STRING, yytext());}


{FINERIGA} {return sf.newSymbol("CR", ParserSym.CR);}

{SPAZIATURA} { }

{NEXTLINE} { }

{COMMENT} { }

.        {return sf.newSymbol("err", ParserSym.error);}

<<EOF>>  {return sf.newSymbol("EOF", ParserSym.EOF);}


