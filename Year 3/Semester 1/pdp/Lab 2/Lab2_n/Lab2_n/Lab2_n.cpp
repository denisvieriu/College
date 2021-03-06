#include "pch.h"
#include <iostream>
#include <vector>
#include <thread>
#include <chrono>

extern int matrixA[MAX_MATRIX_COLUMS][MAX_MATRIX_COLUMS];
extern int matrixB[MAX_MATRIX_COLUMS][MAX_MATRIX_COLUMS];
extern int matrixResult[MAX_MATRIX_COLUMS][MAX_MATRIX_COLUMS];

int main()
{
    std::vector<std::thread> threads;

    PrintHeader();

    // Perform Matrix Addition single threaded
    PopulateMatrix(matrixA);
    PopulateMatrix(matrixB);

    InitializeMatrix(matrixResult, 0);
    // Single threaded addition
    MatrixOperation(MATRIX_ADDITION_SINGLE_THREADED);

    InitializeMatrix(matrixResult, 0);
    // Multi-threaded addition
    MatrixOperation(MATRIX_ADDITION_MULTI_THREADED);

    InitializeMatrix(matrixResult, 0);
    // Single threaded multiplication
    MatrixOperation(MATRIX_MULTIPLACTION_SINGLE_THREADED);

    InitializeMatrix(matrixResult, 0);
    // Multi threaded multiplication
    MatrixOperation(MATRIX_MULTIPLACTION_MULTI_THREADED);
}
