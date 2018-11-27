#include "pch.h"
#include "finite_automata_reader.h"

#include <fstream>
#include <utility>
#include <sstream>
#include <iterator>
#include <iostream>

FaReader::FaReader(string faFile)
{
    ifstream inFile(faFile);
    string line;

    if (inFile.is_open())
    {
        int fileIdx = 0;
        vector<string> splitLine;
        while (getline(inFile, line))
        {
            switch (fileIdx)
            {
            case FA_FINITE_STATES:
                splitLine = this->splitLine(line);
                for (auto str : splitLine) this->finiteStates.insert(str);
                break;
            case FA_FINITE_ALPHABET:
                splitLine = this->splitLine(line);
                for (auto str : splitLine) this->finiteAlphabet.insert(str);
                break;
            case FA_INITIAL_STATE:
                this->initialState = line;
                break;
            case FA_FINAL_STATES:
                splitLine = this->splitLine(line);
                for (auto str : splitLine)  this->finalStates.insert(str);
                break;
            default:
                // FA_TRANS_FUNC onlu
                splitLine = this->splitLine(line);
                string leftS = "";
                string rightS = "";
                string value = "";

                leftS += splitLine[0];
                rightS += splitLine[1];
                value += splitLine[2];

                pair<string, string> tmpPair = make_pair(leftS, rightS);
                this->transitionFunc[tmpPair] = value;
            }

            fileIdx++;
        }
    }
}

vector<string> FaReader::splitLine(string & line)
{
    istringstream iss(line);
    vector<string> results((istream_iterator<string>(iss)),
        istream_iterator<string>());

    return results;
}

void FaReader::printMenu()
{
    printf("FA Menu:\n");
    printf("\t1: Print set of states\n");
    printf("\t2: Print the alphabet\n");
    printf("\t3: Print all the transitions\n");
    printf("\t4: Print the set of final state\n");
    printf("\t0: Exit\n");
    printf("\n");
}

void FaReader::mainMenu()
{
    this->printMenu();
    while (true)
    {
        int command = this->readUserKey();

        switch (command)
        {
        case 0:
            cout << "Finishing this menu..\n";
            return;
        case 1:
            this->printSetOfStates();
            break;
        case 2:
            this->printAlphabet();
            break;
        case 3:
            this->printAllTransitions();
            break;
        case 4:
            this->printFinalStates();
            break;
        default:
            cout << "Invalid command\n";
            break;
        }
    }
}

int FaReader::readUserKey()
{
    int x;
    cout << ">> ";
    cin >> x;

    return x;
}

void FaReader::printSetOfStates()
{
    cout << "Q = {";
    int size = this->finiteStates.size();

    int idx = 0;
    for (auto str : this->finiteStates)
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

void FaReader::printAlphabet()
{
    cout << "E = {";
    int size = this->finiteAlphabet.size();

    int idx = 0;
    for (auto str : this->finiteAlphabet)
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

void FaReader::printAllTransitions()
{
    for (auto fs : this->finiteStates)
    {
        for (auto fa : this->finiteAlphabet)
        {
            pair<string, string> pairTemp = make_pair(fs, fa);
            if (this->transitionFunc.find(pairTemp) != this->transitionFunc.end())
            {
                string val = this->transitionFunc[pairTemp];
                cout << "sigma(" << fs << ", " << fa << ") = " << val << endl;
            }
        }
    }
}

void FaReader::printFinalStates()
{
    cout << "F = {";
    int size = this->finalStates.size();

    int idx = 0;
    for (auto str : this->finalStates)
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

void FaReader::FaToRg()
{
    //
    // A->aB is sigma(A, a) = B and B not belonging to F
    // A->a|aB if sigma(A, a) = B and B belongs to F
    // if q0 belongs to F then S = q0->e
    //

    ofstream g("fa_to_rg.txt");
    bool q0Final = false;

    g << "N = {";

    int size = this->finiteStates.size();

    int idx = 0;
    for (auto str : this->finiteStates)
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

    // ----------------------

    g << "E = {";

    size = this->finiteAlphabet.size();

    idx = 0;
    for (auto str : this->finiteAlphabet)
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

    // ---------------------

    g << "S = " << this->initialState << endl;

    // ---------------------

    g << "Production set:" << endl;

    for (auto kv : this->transitionFunc)
    {
        pair<string, string> tempPair = kv.first;
        string res = kv.second;

        if (this->finalStates.find(res) != this->finalStates.end())
        {
            g << tempPair.first << "->" << tempPair.second << "|" << tempPair.second << res << endl;
        }
        else
        {
            g << tempPair.first << "->" << tempPair.second << res << endl;
        }

    }

    if (this->finalStates.find(this->initialState) != this->finalStates.end())
    {
        q0Final = true;
    }

    if (q0Final)
    {
        g << this->initialState << "->" << "e" << endl;
    }

    // -------------------
}
