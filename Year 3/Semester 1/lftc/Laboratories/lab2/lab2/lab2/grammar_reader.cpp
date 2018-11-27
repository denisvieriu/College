#include "pch.h"
#include "grammar_reader.h"
#include <sstream>
#include <iterator>
#include <iostream>

#define KEY     0
#define VALUE   1

#define EPSILON "e"

GrammarReader::GrammarReader(string File)
{
    ifstream inFile(File);
    string line;

    if (inFile.is_open())
    {
        int fileIdx = 0;
        vector<string> splitLine;
        while (getline(inFile, line))
        {
            switch (fileIdx)
            {
            case FF_STARTING_SYMBOL:
                this->startingSymbol = line;
                break;
            case FF_NON_TERMINALS:
                splitLine = this->splitLine(line);
                for (auto str : splitLine) this->nonTerminals.insert(str);
                break;
            case FF_TERMINALS:
                splitLine = this->splitLine(line);
                for (auto str : splitLine) this->terminals.insert(str);
                break;
            default:
                // FF_PRODUCTION_SET only here
                vector<string> lineSplit = this->splitLine(line);
                if (productionSet.find(lineSplit[KEY]) != productionSet.end())
                {
                    productionSet[lineSplit[KEY]].push_back(lineSplit[VALUE]);
                }
                else
                {
                    vector<string> temp;
                    temp.push_back(lineSplit[VALUE]);
                    productionSet[lineSplit[KEY]] = temp;
                }
            }

            fileIdx++;
        }
    }

    cout << "Grammar read successfully\n";

    bool grammarCorrect = this->validateGrammar();
    if (grammarCorrect)
    {
        correctGrammar = true;
        cout << "Grammar read is a REGULAR GRAMMAR\n";
    }
    else
    {
        cout << "Grammar read is NOT a REGULAR GRAMMAR\n";
    }

    cout << endl;
}

void GrammarReader::printMenu()
{
    printf("Menu:\n");
    printf("\t1 - Set of non - terminals\n");
    printf("\t2 - Set of terminals\n");
    printf("\t3 - Set of productions\n");
    printf("\t4 - Production of a given non-terminal symbol\n");
    printf("\t0 - Stop\n");
    printf("\n");
}

void GrammarReader::mainMenu()
{
    this->printMenu();
    string nonTerm = "";
    while (true)
    {
        int command = this->readUserKey();

        switch (command)
        {
        case 0:
            cout << "Finishing this menu..\n";
            return;
        case 1:
            this->printNonTerminals();
            break;
        case 2:
            this->printTerminals();
            break;
        case 3:
            this->printProductions();
            break;
        case 4:
            cout << "Enter Non-terminal: ";
            cin >> nonTerm;

            this->printProductionWithNonTerminal(nonTerm);
            break;
        default:
            cout << "Invalid command\n";
            break;
        }
    }
}

int GrammarReader::readUserKey()
{
    int x;
    cout << ">> ";
    cin >> x;

    return x;
}

void GrammarReader::printNonTerminals()
{
    cout << "N = {";
    int size = this->nonTerminals.size();

    int idx = 0;
    for (auto str : this->nonTerminals)
    {
        idx++;
        if (idx != size)
        {
            cout << str << ", ";
        }
        else
        {
            cout << str << "}";
        }
    }

    cout << endl;
}

void GrammarReader::printTerminals()
{
    cout << "E = {";
    int size = this->terminals.size();

    int idx = 0;
    for (auto str : this->terminals)
    {
        idx++;
        if (idx != size)
        {
            cout << str << ", ";
        }
        else
        {
            cout << str << "}";
        }
    }

    cout << endl;
}

void GrammarReader::printProductions()
{
    for (auto kv : this->productionSet) {
        vector<string> prods = this->productionSet[kv.first];

        for (auto p : prods)
        {
            cout << kv.first << "->" << p << endl;
        }
    }

    cout << endl;
}

void GrammarReader::printProductionWithNonTerminal(string nonTerm)
{
    if (this->productionSet.find(nonTerm) == this->productionSet.end())
    {
        cout << "Non terminal not found!\n";
        return;
    }

    cout << "Production sets for non terminal: " << nonTerm << endl;

    vector<string> productionSets = this->productionSet[nonTerm];

    for (auto prods : productionSets)
    {
        cout << nonTerm << "->" << prods << endl;
    }

    cout << endl;
}

bool GrammarReader::validateGrammar()
{

    // the only non term to go in epsilon in Starting symbol
    for (auto kv : productionSet)
    {
        vector<string> productions = this->productionSet[kv.first];

        string nonTerminalLeftSide = kv.first;
        if (this->nonTerminals.find(nonTerminalLeftSide) == this->nonTerminals.end())
        {
            cout << "Left side <"<< nonTerminalLeftSide <<"> is not a NON TERMINAL\n";
            return false;
        }

        for (auto rightSide : productions)
        {
            vector<string> rightSideAsVect = this->splitStringWithDelim(rightSide, '|');

            for (string memberRHS : rightSideAsVect)
            {
                if (memberRHS.size() > 2)
                {
                    cout << "Right side production set should be formed of MAX a <term><non-term> | <term>\n";
                    return false;
                }

                if (memberRHS.size() == 2)
                {
                    string terminal = "";
                    string nonTerm = "";
                    terminal += memberRHS[0];
                    nonTerm += memberRHS[1];

                    if ((this->terminals.find(terminal) == this->terminals.end()) ||
                        (this->nonTerminals.find(nonTerm) == this->nonTerminals.end()))
                    {
                        cout << "RHS: " << memberRHS << " is not valid\n";
                        return false;
                    }
                }
                else if (memberRHS.size() == 1)
                {
                    string terminal = "";
                    terminal += memberRHS[0];

                    // search for epsilon
                    if (terminal == EPSILON && nonTerminalLeftSide != this->startingSymbol)
                    {
                        cout << "Only the starting symbol can go in epsilon" << endl;
                        return false;
                    }

                    if (this->terminals.find(terminal) == this->terminals.end() && terminal != EPSILON)
                    {
                        cout << "RHS: " << memberRHS << " is not valid\n";
                        return false;
                    }
                }
            }
        }
    }

    return true;
}

vector<string> GrammarReader::splitStringWithDelim(const std::string& s, char delimiter)
{

    std::vector<std::string> tokens;
    std::string token;
    std::istringstream tokenStream(s);
    while (std::getline(tokenStream, token, delimiter))
    {
        tokens.push_back(token);
    }
    return tokens;
}

void GrammarReader::RgToFa()
{
    //
    // sigma(A, a) = B if A -> aB
    // sigma(A, a) = K if A -> a
    // if S->e then q0 = S belongs to F
    //

    bool q0finalS = false;
    ofstream g("rg_to_fa.txt");

    //
    // Q = N U {K}, K a final state
    //
    g << "Q = {";

    int size = this->nonTerminals.size();

    int idx = 0;
    for (auto str : this->nonTerminals)
    {
        idx++;
        if (idx != size)
        {
            g << str << ", ";
        }
        else
        {
            g << str << "}";
        }
    }

    g << " U {K}, where K is a final state";
    g << endl;
    // ---------------------------------

    //
    // E - The alphabet are the terminals from the grammar
    //
    g << "E = {";
    size = this->terminals.size();

    idx = 0;
    for (auto str : this->terminals)
    {
        idx++;
        if (idx != size)
        {
            g << str << ", ";
        }
        else
        {
            g << str << "}";
        }
    }

    g << endl;
    // ---------------------------------

    //
    // q0 = S
    //
    g << "q0 = " << this->startingSymbol << endl;
    // ---------------------------------


    g << "Transition functions:" << endl;

    for (auto kv : productionSet)
    {
        vector<string> productions = this->productionSet[kv.first];

        string nonTerminalLeftSide = kv.first;

        for (auto rightSide : productions)
        {
            vector<string> rightSideAsVect = this->splitStringWithDelim(rightSide, '|');

            for (string memberRHS : rightSideAsVect)
            {
                if (memberRHS.size() == 2)
                {
                    string terminal = "";
                    string nonTerm = "";
                    terminal += memberRHS[0];
                    nonTerm += memberRHS[1];

                    g << "sigma(" << nonTerminalLeftSide << ", " << terminal << ") = " << nonTerm << endl;
                }
                else if (memberRHS.size() == 1)
                {
                    string terminal = "";
                    terminal += memberRHS[0];

                    if (terminal == EPSILON)
                    {
                        if (nonTerminalLeftSide == this->startingSymbol)
                        {
                            q0finalS = true;
                            continue;
                        }

                    }

                    g << "sigma(" << nonTerminalLeftSide << ", " << terminal << ") = " << "K" << endl;

                    // search for epsilon
                }
            }
        }
    }

    // ---------------------------------

    g << "F = {K";

    if (q0finalS)
    {
        g << ", " << this->startingSymbol << "}" << endl;
    }
    else
    {
        g << "}" << endl;
    }
}

vector<string> GrammarReader::splitLine(string & line)
{
    istringstream iss(line);
    vector<string> results((istream_iterator<string>(iss)),
        istream_iterator<string>());

    return results;
}
