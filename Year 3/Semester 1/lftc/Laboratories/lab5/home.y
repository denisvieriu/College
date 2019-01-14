%{
#include <stdio.h>
#include <stdlib.h>
int yylex();
void yyerror(const char* s);
%}

%token WHILE
%token PRINTF
%token SCANF
%token IF
%token ELSE
%token ADD
%token DEC
%token LE
%token GE
%token GT
%token LT
%token NE
%token EQ
%token EQU
%token OPBR
%token CLBR
%token CRLOP
%token CRLCL
%token SEMIC
%token CHAR
%token INT
%token FLOAT
%token ID
%token NUM
%token MAIN
%token RETURN

%right '='
%left GE NE LT GT EQ
%left '+' '-'
%left '*' '/' 

%%
S : E

E : TYPE1 MAIN OPBR CLBR CMPDSTMT {printf("\nPROGRAM ACCEPTED! \n"); exit(0);}

TYPE1 : INT
| FLOAT
| CHAR

CMPDSTMT : CRLOP STMTLIST CRLCL

STMTLIST : STMT
| STMT STMTLIST

STMT : SIMPLESTMT 
| RETURNSTMT
| WHILESTMT
| IFSTMT
| IOSTMT
| STRUCT

SIMPLESTMT : DECLARATION 
| ASSIGNMENT

DECLARATION : TYPE1 ID SEMIC

ASSIGNMENT : TYPE1 ID EQU EXPRESSION SEMIC
| ID EQU EXPRESSION SEMIC

EXPRESSION : ID 
| NUM

RETURNSTMT : RETURN EXPRESSION SEMIC

WHILESTMT : WHILE OPBR CONDITION CLBR CMPDSTMT

CONDITION : EXPRESSION RELATION EXPRESSION

RELATION : LE 
| GE 
| GT 
| LT 
| NE 
| EQ

IFSTMT : IF OPBR CONDITION CLBR CMPDSTMT
| IF OPBR CONDITION CLBR CMPDSTMT ELSE CMPDSTMT

IOSTMT : WRITESTMT 
| READSTMT

WRITESTMT : PRINTF OPBR ID CLBR SEMIC

READSTMT : SCANF OPBR ID CLBR SEMIC

STRUCT : CMPDSTMT ID SEMIC

%%

#include "lex.yy.c"
int main(){
yyparse();
return yylex();
}

void yyerror(const char *s){ printf("\n SEQUENCE NOT ACCEPTED! \n"); exit(0); }
int yywrap(){ return 1; }

