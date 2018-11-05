#include "pch.h"
#include <iostream>
#include "program_internal_form.h"

void PIF::addToPif(int codifTableKey, int positionInSt)
{
    this->pif.push_back(std::make_pair(codifTableKey, positionInSt));
}

void PIF::printPif()
{
    std::cout << "Program Internal Form:\n";
    for (auto &pifEl : this->pif)
    {
        printf("%-3d|%3d\n", pifEl.first, pifEl.second);
    }

    std::cout << std::endl;
}

void PIF::removePreviousPair()
{
    this->pif.pop_back();
}

vector<std::pair<int, int>> PIF::getPif()
{
    return this->pif;
}
