#include "pch.h"
#include "codification_table.h"
#include <fstream>

CodificationTable::CodificationTable(std::string codificationFile)
{
    this->codifFile = codificationFile;
    this->readCodificationTable(codificationFile);
}

void CodificationTable::printTable()
{
    std::ifstream infile(this->codifFile);
    std::string identifier;
    int code;

    printf("Codification table:\n");
    while (infile >> identifier >> code)
    {
        printf("%-20s %d\n", identifier.c_str(), code);
    }
    infile.close();
}

void CodificationTable::readCodificationTable(std::string file)
{
    std::ifstream infile(file);
    std::string identifier;
    int code;

    while (infile >> identifier >> code)
    {
        if (this->codificationMap[identifier] != NULL)
        {
            // to do
            // throw new error
        }

        this->codificationMap[identifier] = code;
    }
    infile.close();
}

const CODIFICATION_TABLE& CodificationTable::getCodificationTable()
{
    return this->codificationMap;
}
