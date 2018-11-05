#ifndef _SCANNER_H
#define _SCANNER_H

#include "codification_table.h"
#include <string>
#include "program_internal_form.h"

// unique symbol talbe for identifiers
typedef BST<std::string>    StIdentifier;

// unique symbol table for constants
typedef BST<std::string>        StConstant;

class Scanner
{
private:
    std::unique_ptr<CodificationTable> codificationTable;
    std::unique_ptr<StIdentifier> stIdentifiers;
    std::unique_ptr<StConstant> stConstants;
    std::unique_ptr<PIF> pif;

    std::string sourceCode;
public:
    Scanner(std::string _sourceCode, std::string _codifTable);

    // Performs lexical analysys
    void lexicalAnalyzer();

    void printCodifTable();
    void printStIdentifiers();
    void printStConstants();
    void printPif();
private:
    std::vector<std::string> tokenize(std::string&, const char*);
};

#endif
