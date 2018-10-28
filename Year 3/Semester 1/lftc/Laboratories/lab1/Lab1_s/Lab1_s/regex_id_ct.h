#ifndef _REGEX_ID_CT_H
#define _REGEX_ID_CT_H

// Regex to match identifiers
#define REGEX_MATCH_IDENTIFIER                      "[_a-zA-Z][_a-zA-Z0-9]{0,8}"

// Regex to match constants
#define REGEX_MATCH_CONSTANT_NUMBER                 "[0-9]{1,7}"
#define REGEX_MATCH_CONSTANT_CHAR                   "'[_a-z0-9A-Z]'"
#define REGEX_MATCH_CONSTANT_STRING                 "\"[_a-z0-9A-Z]*\""

#define REGEX_MATCH_CONSTANT                        \
    REGEX_MATCH_CONSTANT_NUMBER "|"                 \
    REGEX_MATCH_CONSTANT_CHAR   "|"                 \
    REGEX_MATCH_CONSTANT_STRING

// Regex to match reserved words, operator, or separator
#define REGEX_MATCH_RESERVED_WORD                   "char|const|int|printf|int|scanf|if|else|return|while|true|false|main"
#define REGEX_MATCH_OPERATOR                        "\\+|\\-|\\*|\\/|=|>|<|>=|<=|\\%"
#define REGEX_MATCH_SEPARATOR                       "\\[|\\]|\\{|\\}|;"

#define REGEX_MATCH_RESERVED_OPERATOR_SEPARATOR     \
    REGEX_MATCH_RESERVED_WORD "|"                   \
    REGEX_MATCH_OPERATOR      "|"                   \
    REGEX_MATCH_SEPARATOR

typedef enum _ATOM_TYPE
{
    AT_RESERVED_OPERATOR_SEPARATOR,
    AT_IDENTIFIER,
    AT_CONSTANT
}ATOM_TYPE;

ATOM_TYPE GetAtomType(const std::string& Atom);

#endif // _REGEX_ID_CT_H
