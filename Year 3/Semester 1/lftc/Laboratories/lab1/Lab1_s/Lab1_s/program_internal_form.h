#ifndef _PROGRAM_INTERNAL_FORM
#define _PROGRAM_INTERNAL_FORM

#include <vector>

#define KEY_IDENTIFIER "identifier"
#define KEY_CONSTANT    "constant"

class PIF
{
private:
    vector<std::pair<int, int>> pif;
public:
    PIF() {}

    void addToPif(int codifTableKey, int positionInSt);
    void printPif();
    void removePreviousPair();

    vector<std::pair<int, int>> getPif();
};

#endif // !_PROGRAM_INTERNAL_FORM
