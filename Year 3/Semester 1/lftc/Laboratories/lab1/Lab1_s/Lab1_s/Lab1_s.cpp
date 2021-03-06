// Lab1_s.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"

using namespace std;

#define CODIFICATION_FILE "codification_table.dat"
#define SOURCE_CODE       "source_code.txt"


int main(int argc, char** argv)
{
    argc;
    argv;


    unique_ptr<Scanner> scanner(new Scanner(SOURCE_CODE, CODIFICATION_FILE));
    scanner->lexicalAnalyzer();

    scanner->printStIdentifiers();
    scanner->printStConstants();
    scanner->printPif();

    //string ident("+");
    //cout << ident << endl;
    //regex e(REGEX_MATCH_OPERATOR);

    //if (regex_match(ident, e))
    //    std::cout << "string object matched\n";

    return 0;
}
