#ifndef _MATRIX_HELPER_H
#define _MATRIX_HELPER_H

#include <iostream>
#include <vector>
#include <thread>
#include <chrono>

#define MAX_MATRIX_LINES        100
#define MAX_MATRIX_COLUMS       100
#define MAX_THREADS             4
#define MAX_NUMBER_IN_MATRIX    255

typedef enum _TIME_ELAPSED_STATE
{
    START_MEASURE,
    END_MEASURE
}TIME_ELAPSED_STATE;

typedef enum _MATRIX_OPERATION_TYPE
{
    MATRIX_ADDITION_SINGLE_THREADED,
    MATRIX_ADDITION_MULTI_THREADED,
    MATRIX_MULTIPLACTION_SINGLE_THREADED,
    MATRIX_MULTIPLACTION_MULTI_THREADED
}MATRIX_OPERATION_TYPE;

__forceinline
void PrintHeader()
{
    printf("Perform test with 2 matrices of form: A(%d,%d)\n", MAX_MATRIX_LINES, MAX_MATRIX_COLUMS);
    printf("_____________________________________________________________________________________\n");
}

void
PrintMatrix(
    int Matrix[][MAX_MATRIX_COLUMS]
    );

void
MatrixOperation(
    MATRIX_OPERATION_TYPE MatrixOperationType
    );

void
PopulateMatrix(
    int Matrix[][MAX_MATRIX_COLUMS]
    );

void
PrintElapsedTime(
    void
    );

void
MeasureElapsedTime(
    TIME_ELAPSED_STATE TimeState
    );

void
InitializeMatrix(
    int Matrix[][MAX_MATRIX_COLUMS],
    int Value
    );


#endif // _MATRIX_HELPER_H