%{
#include "home.tab.h"
#include<stdio.h>
extern int yylval;
%}

%%
"while" {return WHILE;}
"printf" {return PRINTF;}
"scanf"	{return SCANF;}
"if"	{return IF;}
"else"	{return ELSE;}
"main" {return MAIN;}
"return" {return RETURN;}

"+" {return ADD;}
"-" {return DEC;}
"<=" {return LE;}
">=" {return GE;}
">" {return GT;}
"<" {return LT;}
"!=" {return NE;}
"==" {return EQ;}
"=" {return EQU;}

"(" {return OPBR;}
")" {return CLBR;}
"{" {return CRLOP;}
"}" {return CRLCL;}
";" {return SEMIC;}

"char"	{return CHAR;}
"int"	{return INT;}
"float"	{return FLOAT;}

[0-9]{0,7}	    {yylval=atoi(yytext);return NUM;}
[_a-zA-Z][_a-zA-Z0-9]{0,8} {return ID;}
%%
