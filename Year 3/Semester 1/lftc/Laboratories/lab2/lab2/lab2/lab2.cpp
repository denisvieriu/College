// lab2.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"
#include <iostream>
#include "grammar_reader.h"
#include "finite_automata_reader.h"

#define GRAMMAR_SRC "grammar.txt"
#define FA_SRC      "fa.txt"


int readUserKey()
{
    int x;
    cout << ">> ";
    cin >> x;

    return x;
}


void menuMainP()
{
    cout << "Read a grammar or use file\n";

    cout << "\t1: Read from keyboard\n";
    cout << "\t2: Read from file\n";
}

int main()
{
    menuMainP();
    int x = readUserKey();

    unique_ptr<GrammarReader> grammarReaderFile(new GrammarReader(GRAMMAR_SRC));
    switch (x)
    {
    case 1:
        break;
    case 2:
        grammarReaderFile->mainMenu();
    default:
        cout << "Invalid command, will use file grammar\n";
        grammarReaderFile->mainMenu();
    }

    unique_ptr<FaReader> faReader(new FaReader(FA_SRC));
    faReader->mainMenu();

    if (grammarReaderFile->correctGrammar)
    {
        grammarReaderFile->RgToFa();
    }

    faReader->FaToRg();
}

// Run program: Ctrl + F5 or Debug > Start Without Debugging menu
// Debug program: F5 or Debug > Start Debugging menu

// Tips for Getting Started:
//   1. Use the Solution Explorer window to add/manage files
//   2. Use the Team Explorer window to connect to source control
//   3. Use the Output window to see build output and other messages
//   4. Use the Error List window to view errors
//   5. Go to Project > Add New Item to create new code files, or Project > Add Existing Item to add existing code files to the project
//   6. In the future, to open this project again, go to File > Open > Project and select the .sln file
