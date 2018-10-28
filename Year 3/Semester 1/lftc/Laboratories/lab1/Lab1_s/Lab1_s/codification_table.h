#ifndef _CODIFICATION_TABLE_H
#define _CODIFICATION_TABLE_H

#include <unordered_map>
#include <string>

typedef std::unordered_map<std::string, int> CODIFICATION_TABLE;

class CodificationTable
{
private:
    std::unordered_map<std::string, int> codificationMap;
    std::string codifFile;

public:
    CodificationTable(std::string codificationFile);
    const CODIFICATION_TABLE& getCodificationTable();
    void printTable();

private:
    void readCodificationTable(std::string file);
};

#endif