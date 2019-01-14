%{
#include "home.tab.h"
#include<stdio.h>
int yylval = 0;

int leftBracket = 0;
int rightBracket = 0;
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
"{" {leftBracket++; return CRLOP;}
"}" {rightBracket++; return CRLCL;}
";" {return SEMIC;}

"char"	{return CHAR;}
"int"	{return INT;}
"float"	{return FLOAT;}

[0-9]{0,7}   {yylval=atoi(yytext); printf("Number found: %d\n", yylval); return NUM;}
[_a-zA-Z][_a-zA-Z0-9]{0,8} {printf("Identifier found: %s\n", yytext); return ID;}
%%
int yywrap()
{
return 1;
}

main()
{
printf("Enter a string (0 to exit):\n");
int class;
while (class = yylex())
{
	printf("Current token: %s | Class: %d\n", yytext, (int)class); 
}

printf("Found %d left brackets { and %d right brackets }", leftBracket, rightBracket);
}
