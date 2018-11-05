#include "pch.h"
#include <regex>

bool AtomMatch(const std::string& Atom, const char* Regex)
{
    std::regex e(Regex);
    return (std::regex_match(Atom, e));
}

ATOM_TYPE GetAtomType(const std::string& Atom)
{
    if (AtomMatch(Atom, REGEX_MATCH_RESERVED_OPERATOR_SEPARATOR))
    {
        return AT_RESERVED_OPERATOR_SEPARATOR;
    }

    if (AtomMatch(Atom, REGEX_MATCH_IDENTIFIER))
    {
        return AT_IDENTIFIER;
    }

    if (AtomMatch(Atom, REGEX_MATCH_CONSTANT))
    {
        return AT_CONSTANT;
    }

    // [to do]
    //throw new error;

    std::string v("Couldn't classify atom: ");
    v += Atom;
    throw exception(v.c_str());
}
