#include "pch.h"
#include <fstream>
#include <sstream>
#include "regex_id_ct.h"
#include <exception>

#ifndef UNREFERENCED_PARAMETER
#define UNREFERENCED_PARAMETER(P)          (P)
#endif

Scanner::Scanner(std::string _sourceCode, std::string _codifTable)
{
    this->stIdentifiers = unique_ptr<StIdentifier>(new StIdentifier(2));
    this->stConstants = unique_ptr<StConstant>(new StConstant(2));
    this->codificationTable = unique_ptr<CodificationTable>(new CodificationTable(_codifTable));
    this->pif = unique_ptr<PIF>(new PIF());
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
    int lineFile = 0;
    int64_t openScopeBrackets = 0;
    std::vector<std::string> errors;

    int pos = 0;
    int keyCodifTable = 0;

    while (!infile.eof())
    {
        std::getline(infile, line);
        lineFile++;
        std::vector<std::string> tokens = this->tokenize(line, " ;=\t(),+-{}");
        int tokPos = -1;
        for (auto token : tokens)
        {
            tokPos++;

            if (token == "{")
            {
                openScopeBrackets++;
            }
            else if (token == "}")
            {
                openScopeBrackets--;
            }

            try
            {
                ATOM_TYPE atomType = GetAtomType(token);

                switch (atomType)
                {
                case AT_RESERVED_OPERATOR_SEPARATOR:
                    if (token == "main")
                    {
                        if (tokPos != 1)
                        {
                            std::string err("Invalid main() function signature. Line: ");
                            err += to_string(lineFile);
                            errors.push_back(err);
                            goto _outLbl;
                        }
                        else if
                            (
                                (tokPos - 1 >= 0) &&
                                (tokPos - 1 < tokens.size()) &&
                                !(AtomMatch(tokens[tokPos - 1], REGEX_MATCH_DATA_TYPE))
                            )
                        {
                            std::string err("Invalid main() function signature. Line: ");
                            err += to_string(lineFile);
                            errors.push_back(err);
                            goto _outLbl;
                        }
                    }

                    keyCodifTable = this->codificationTable->getValue(token);

                    pif->addToPif(keyCodifTable, -1);

                    break;
                case AT_IDENTIFIER:
                    if (!this->stIdentifiers->contains(token) && tokPos == 0)
                    {
                        std::string err;
                        err = std::string("Identifier: '") + token + "' is not a data type. Line: " + to_string(lineFile);
                        errors.push_back(err);

                        goto _outLbl;
                    }

                    pos = this->stIdentifiers->insertNode(token);
                    keyCodifTable = this->codificationTable->getValue(KEY_IDENTIFIER);
                    pif->addToPif(keyCodifTable, pos);

                    break;
                case AT_CONSTANT:

                    int neg = 1;

                    if (
                        (tokPos > 3) &&
                        ((tokens[3] == "+") || (tokens[3] == "-")) &&
                        (tokens[2] == "=") &&
                        (AtomMatch(tokens[1], REGEX_MATCH_IDENTIFIER)) &&
                        (AtomMatch(tokens[0], REGEX_MATCH_DATA_TYPE))
                        )
                    {
                        pif->removePreviousPair();
                        if (tokens[3] == "-")
                        {
                            neg *= -1;
                        }
                    }
                    else if
                        (
                        (tokPos > 2) &&
                            ((tokens[2] == "+") || (tokens[2] == "-")) &&
                            (tokens[1] == "=") &&
                            (AtomMatch(tokens[0], REGEX_MATCH_IDENTIFIER)) &&
                            (this->stIdentifiers->contains(tokens[0]))
                        )
                    {
                        pif->removePreviousPair();
                        if (tokens[2] == "-")
                        {
                            neg *= -1;
                        }
                    }

                    pos = this->stConstants->insertNode(token);
                    keyCodifTable = this->codificationTable->getValue(KEY_CONSTANT);

                    pif->addToPif(keyCodifTable, pos);

                    break;
                }
            }
            catch (exception& e)
            {
                cout << e.what() << endl;
            }
        }
    }

    if (openScopeBrackets)
    {
        errors.push_back("Unmatched number of open/closed brackets\n");
    }

    _outLbl:

    for (auto err : errors)
    {
        cout << err << endl;
    }
}

void Scanner::printStIdentifiers()
{
    std::cout << "ST Identifiers:\n";
    this->stIdentifiers->traversal(BT_INORDER);
}

void Scanner::printStConstants()
{
    std::cout << "ST Constants:\n";
    this->stConstants->traversal(BT_INORDER);
}

void Scanner::printPif()
{
    //this->pif->printPif();
    std::vector<std::pair<int, int>> pifR = this->pif->getPif();

    std::cout << "Program Internal Form:\n";
    for (auto &pifEl : pifR)
    {
        printf("%-10s|%-3d|%3d\n", this->codificationTable->getKey(pifEl.first).c_str(), pifEl.first, pifEl.second);
    }

    std::cout << std::endl;

}

std::vector<std::string> Scanner::tokenize(std::string &inputString, const char* delimiter)
{
    std::stringstream stringStream(inputString);
    std::string line;
    std::vector<std::string> wordVector;
    while (std::getline(stringStream, line))
    {
        std::size_t prev = 0, pos;
        while ((pos = line.find_first_of(delimiter, prev)) != std::string::npos)
        {
            if (pos > prev)
            {
                wordVector.push_back(line.substr(prev, pos - prev));
            }

            if (line[pos] != ' ' && line[pos] != '\t')
            {
                wordVector.push_back(line.substr(pos, 1));
            }


            prev = pos + 1;
        }
        if (prev < line.length())
        {
            wordVector.push_back(line.substr(prev, std::string::npos));
        }
    }

   /* std::cout << "............" << std::endl;
    for (auto tok : wordVector)
    {
        std::cout << tok << " || ";
    }
    std::cout << "\n............" << std::endl;*/

    return wordVector;
}
