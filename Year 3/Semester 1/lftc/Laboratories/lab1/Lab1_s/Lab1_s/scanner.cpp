#include "pch.h"
#include <fstream>
#include <sstream>
#include "regex_id_ct.h"


#ifndef UNREFERENCED_PARAMETER
#define UNREFERENCED_PARAMETER(P)          (P)
#endif

Scanner::Scanner(std::string _sourceCode, std::string _codifTable)
{
    this->stIdentifiers = unique_ptr<StIdentifier>(new StIdentifier(2));
    this->stConstants = unique_ptr<StConstant>(new StConstant(2));
    this->codificationTable = unique_ptr<CodificationTable>(new CodificationTable(_codifTable));

    this->sourceCode = _sourceCode;
}

void Scanner::printCodifTable()
{
    this->codificationTable->printTable();
}

void Scanner::lexicalAnalyzer()
{
    std::ifstream infile(this->sourceCode);
    std::string line;


    while (!infile.eof())
    {
        std::getline(infile, line);
        std::vector<std::string> tokens = this->tokenize(line, ' ');

        for (auto token : tokens)
        {
            try
            {
                ATOM_TYPE atomType = GetAtomType(token);
                switch (atomType)
                {
                case AT_RESERVED_OPERATOR_SEPARATOR:
                    //AddToPif(this->codificationTable[token], -1);
                    std::cout << "Type 0: " << token << endl;
                    break;
                case AT_IDENTIFIER:
                    this->stIdentifiers->insertNode(token);
                    std::cout << "Type 1: " << token << endl;
                    break;
                case AT_CONSTANT:
                    this->stConstants->insertNode(stoi(token));
                    std::cout << "Type 2: " << token << endl;
                    break;
                }
            }
            catch (exception& e)
            {
                e;
            }
        }
    }
}

void Scanner::printStIdentifiers()
{
    this->stIdentifiers->traversal(BT_INORDER);
}

void Scanner::printStConstants()
{
    this->stConstants->traversal(BT_INORDER);
}

std::vector<std::string> Scanner::tokenize(std::string &line, char delimiter)
{
    UNREFERENCED_PARAMETER(delimiter);
    std::istringstream iss(line);
    std::vector<std::string> tokens((std::istream_iterator<std::string>(iss)),
        std::istream_iterator<std::string>());
    return tokens;
}
