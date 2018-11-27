#ifndef _FINITE_AUTOMATA_READER_H
#define _FINITE_AUTOMATA_READER_H

#include <string>
#include <vector>
#include <map>
#include <unordered_set>

using namespace std;

class FaReader
{
private:
    string file;

    typedef enum _FA_FILE_FORMAT
    {
        FA_FINITE_STATES,
        FA_FINITE_ALPHABET,
        FA_INITIAL_STATE,
        FA_FINAL_STATES,
        FA_TRANS_FUNC
    } FA_FILE_FORMAT;

public:
    FaReader(string faFile);

    unordered_set<string> finiteStates;   // Q
    unordered_set<string> finiteAlphabet; // E

    map<pair<string, string>, string> transitionFunc; // sigma
    string initialState; // q0

    unordered_set<string> finalStates; // F

    vector<string> splitLine(string & line);

    void printMenu();

    void mainMenu();

    int readUserKey();

    void printSetOfStates();
    void printAlphabet();
    void printAllTransitions();
    void printFinalStates();

    void FaToRg();
};

#endif