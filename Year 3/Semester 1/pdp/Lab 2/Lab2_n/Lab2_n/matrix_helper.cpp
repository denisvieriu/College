#include "pch.h"
#include <mutex>
#include <atomic>

std::chrono::time_point<std::chrono::steady_clock> gStart;
std::chrono::time_point<std::chrono::steady_clock> gEnd;
std::chrono::duration<double> gElapsedTime;

int matrixA[MAX_MATRIX_COLUMS][MAX_MATRIX_COLUMS];
int matrixB[MAX_MATRIX_COLUMS][MAX_MATRIX_COLUMS];
int matrixResult[MAX_MATRIX_COLUMS][MAX_MATRIX_COLUMS];
int threadAtomLine = 0;
std::mutex gMutex;

void
PrintMatrix(
    int Matrix[][MAX_MATRIX_COLUMS]
    )
{
    for (int i = 0; i < MAX_MATRIX_LINES; i++, std::cout << std::endl)
    {
        for (int j = 0; j < MAX_MATRIX_COLUMS; j++, std::cout << '\t')
        {
            std::cout << Matrix[i][j];
        }
    }
    std::cout << std::endl;
}

void
MatrixAdd(
    int MatrixResult[][MAX_MATRIX_COLUMS],
    int valFromA,
    int valFromB,
    int i,
    int j
    )
{
    MatrixResult[i][j] = valFromA + valFromB;
}

void
MatrixAdditionSingleThreaded(
    int MatrixA[][MAX_MATRIX_COLUMS],
    int MatrixB[][MAX_MATRIX_COLUMS],
    int MatrixResult[][MAX_MATRIX_COLUMS]
    )
{
    printf("--> Performing matrix addition single threaded, MAX_LINES: %d, MAX_COLUMS: %d\n", MAX_MATRIX_LINES, MAX_MATRIX_COLUMS);

    MeasureElapsedTime(START_MEASURE);
    {
        for (int i = 0; i < MAX_MATRIX_LINES; i++)
        {
            for (int j = 0; j < MAX_MATRIX_COLUMS; j++)
            {
                MatrixResult[i][j] = MatrixA[i][j] + MatrixB[i][j];
            }
        }
    }
    MeasureElapsedTime(END_MEASURE);
}

void
MatrixAdditionMultiThreaded(
    int MatrixA[][MAX_MATRIX_COLUMS],
    int MatrixB[][MAX_MATRIX_COLUMS],
    int MatrixResult[MAX_MATRIX_LINES][MAX_MATRIX_COLUMS]
    )
{
    std::vector<std::thread> threads;
    int totalThreads = MAX_MATRIX_LINES * MAX_MATRIX_COLUMS;
    threads.reserve(totalThreads);
    printf("--> Performing matrix addition multi threaded, MAX_LINES: %d, MAX_COLUMS: %d\n", MAX_MATRIX_LINES, MAX_MATRIX_COLUMS);

    MeasureElapsedTime(START_MEASURE);
    {
        for (int i = 0; i < MAX_MATRIX_LINES; i++)
        {
            for (int j = 0; j < MAX_MATRIX_COLUMS; j++)
            {
                threads.emplace_back(MatrixAdd, MatrixResult, MatrixA[i][j], MatrixB[i][j], i, j);
            }
        }
    }
    MeasureElapsedTime(END_MEASURE);

    for (int i = 0; i < totalThreads; i++)
    {
        threads[i].join();
    }
}

void
MatrixMultiplactionSingleThreaded(
    int MatrixA[][MAX_MATRIX_COLUMS],
    int MatrixB[][MAX_MATRIX_COLUMS],
    int MatrixResult[MAX_MATRIX_LINES][MAX_MATRIX_COLUMS]
    )
{
    printf("--> Performing matrix multipilication single threaded\n");

    MeasureElapsedTime(START_MEASURE);
    {
        for (int i = 0; i < MAX_MATRIX_LINES; i++)
        {
            for (int j = 0; j < MAX_MATRIX_COLUMS; j++)
            {
                MatrixResult[i][j] = 0;

                // columns from first / lines from second (n,m) * (m,p) = (n, p); MAX_MATRIX_LINES = m
                for (int k = 0; k < MAX_MATRIX_LINES; k++)
                {
                    MatrixResult[i][j] += (MatrixA[i][k] * MatrixB[k][j]);
                }
            }
        }
    }
    MeasureElapsedTime(END_MEASURE);
}

void
ThreadMultiplication()
{
    // each threads gets one line
    std::unique_lock<std::mutex> lck(gMutex, std::defer_lock);

    int i;
    lck.lock();
    i = threadAtomLine++;
    lck.unlock();

    for (i; (i < i + 1) && (i < MAX_MATRIX_LINES); i++)
    {
        for (int j = 0; j < MAX_MATRIX_COLUMS; j++)
        {
            matrixResult[i][j] = 0;

            // columns from first / lines from second (n,m) * (m,p) = (n, p); MAX_MATRIX_LINES_A = m | MAX_MATRIX_COLUMNS_B = m
            for (int k = 0; k < MAX_MATRIX_LINES; k++)
            {
                matrixResult[i][j] += (matrixA[i][k] * matrixB[k][j]);
            }
        }
    }
}

void
MatrixMultiplicationMultiThreaded()
{
    printf("--> Performing matrix multipilication multi threaded\n");

    std::vector<std::thread> threads;
    threads.reserve(MAX_MATRIX_LINES);

    MeasureElapsedTime(START_MEASURE);
    {
        for (int i = 0; i < MAX_MATRIX_LINES; i++)
        {
            threads.emplace_back(ThreadMultiplication);
        }

        for (int i = 0; i < MAX_MATRIX_LINES; i++)
        {
            threads[i].join();
        }
    }
    MeasureElapsedTime(END_MEASURE);
}

void MatrixOperation(
    MATRIX_OPERATION_TYPE MatrixOperationType
    )
{
    switch (MatrixOperationType)
    {
    case MATRIX_ADDITION_SINGLE_THREADED:
        MatrixAdditionSingleThreaded(matrixA, matrixB, matrixResult);
        break;
    case MATRIX_ADDITION_MULTI_THREADED:
        MatrixAdditionMultiThreaded(matrixA, matrixB, matrixResult);
        break;
    case MATRIX_MULTIPLACTION_SINGLE_THREADED:
        MatrixMultiplactionSingleThreaded(matrixA, matrixB, matrixResult);
        break;
    case MATRIX_MULTIPLACTION_MULTI_THREADED:
        MatrixMultiplicationMultiThreaded();
        break;
    default:
        break;
    }
}

void
PopulateMatrix(
    int Matrix[][MAX_MATRIX_COLUMS]
    )
{
    for (int i = 0; i < MAX_MATRIX_LINES; i++)
    {
        for (int j = 0; j < MAX_MATRIX_COLUMS; j++)
        {
            Matrix[i][j] = std::rand() % MAX_NUMBER_IN_MATRIX;
        }
    }
}

void
PrintElapsedTime(
    void
    )
{
    std::cout << "----> Total elapsed time " << std::chrono::duration_cast<std::chrono::microseconds>(gEnd - gStart).count() << " microseconds\n";
}

void
MeasureElapsedTime(
    TIME_ELAPSED_STATE TimeState
    )
{
    switch (TimeState)
    {
    case START_MEASURE:
        gStart = std::chrono::steady_clock::now();
        break;
    case END_MEASURE:
        gEnd = std::chrono::steady_clock::now();
        PrintElapsedTime();
    }
}

void
InitializeMatrix(
    int Matrix[][MAX_MATRIX_COLUMS],
    int Value
    )
{
    memset(Matrix, Value, sizeof(int) * MAX_MATRIX_COLUMS * MAX_MATRIX_LINES);
}
