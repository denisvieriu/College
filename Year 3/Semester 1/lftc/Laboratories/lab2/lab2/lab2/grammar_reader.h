#ifndef _GRAMMAR_READER_H
#define _GRAMMAR_READER_H

#include <fstream>
#include <string>
#include <vector>
#include <unordered_map>
#include <unordered_set>

using namespace std;

class GrammarReader
{
private:
    string File;

    typedef enum _FILE_FORMAT {
        FF_STARTING_SYMBOL,
        FF_NON_TERMINALS,
        FF_TERMINALS,
        FF_PRODUCTION_SET
    } FILE_FORMAT;


    vector<string> splitLine(string& line);

public:
    string startingSymbol;
    unordered_set<string> terminals;
    unordered_set<string> nonTerminals;

    unordered_map<string, vector<string>> productionSet;

    GrammarReader(string File);

    void printMenu();

    void mainMenu();

    int readUserKey();

    void printNonTerminals();

    void printTerminals();

    void printProductions();

    void printProductionWithNonTerminal(string nonTerm);

    bool validateGrammar();

    vector<string> splitStringWithDelim(const std::string& s, char delimiter);

    void RgToFa();

    bool correctGrammar = false;
};

#endif //