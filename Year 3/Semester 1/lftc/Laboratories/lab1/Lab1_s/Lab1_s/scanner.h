#ifndef _SCANNER_H
#define _SCANNER_H

#include "codification_table.h"
#include <string>

// unique symbol talbe for identifiers
typedef BST<std::string>    StIdentifier;

// unique symbol table for constants
typedef BST<int64_t>        StConstant;

class Scanner
{
private:
    std::unique_ptr<CodificationTable> codificationTable;
    std::unique_ptr<StIdentifier> stIdentifiers;
    std::unique_ptr<StConstant> stConstants;

    std::string sourceCode;
public:
    Scanner(std::string _sourceCode, std::string _codifTable);

    void printCodifTable();

    // Performs lexical analysys
    void lexicalAnalyzer();

    void printStIdentifiers();
    void printStConstants();
private:
    std::vector<std::string> tokenize(std::string&, char);
};

#endif
