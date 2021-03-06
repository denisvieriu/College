// Lab3.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"
#include <iostream>
#include <thread>
#include <chrono>
#include "ctpl_stl.h"
#include <mutex>
#include <future>

#define MAX_MATRIX_LINES    15
#define MAX_MATRIX_COLUMS   15

typedef enum _TIME_ELAPSED_STATE
{
    START_MEASURE,
    END_MEASURE
}TIME_ELAPSED_STATE;

static unsigned int gMaxSupportedThreads = std::thread::hardware_concurrency();
std::mutex g_mutex;

std::chrono::time_point<std::chrono::steady_clock> gStart;
std::chrono::time_point<std::chrono::steady_clock> gEnd;
std::chrono::duration<double> gElapsedTime;

int gMatrixA[MAX_MATRIX_LINES][MAX_MATRIX_COLUMS], gMatrixB[MAX_MATRIX_LINES][MAX_MATRIX_COLUMS], gMatrixResult[MAX_MATRIX_LINES][MAX_MATRIX_COLUMS];

void GenerateRandomMatrix(int Matrix[][MAX_MATRIX_COLUMS])
{
    for (int i = 0; i < MAX_MATRIX_LINES; i++)
    {
        for (int j = 0; j < MAX_MATRIX_COLUMS; j++)
        {
            Matrix[i][j] = std::rand() % 255;
        }
    }
}

void InitializeMatrix(int Matrix[][MAX_MATRIX_COLUMS], int Value)
{
    memset(Matrix, Value, sizeof(int) * MAX_MATRIX_LINES * MAX_MATRIX_COLUMS);
}

void PrintMatrix(int Matrix[][MAX_MATRIX_COLUMS])
{
    for (int i = 0; i < MAX_MATRIX_LINES; i++, std::cout << std::endl)
    {
        for (int j = 0; j < MAX_MATRIX_COLUMS; j++, std::cout << " ")
        {
            std::cout << Matrix[i][j];
        }
    }
}

void PrintElapsedTime(void)
{
    std::cout << "----> Total elapsed time " << std::chrono::duration_cast<std::chrono::microseconds>(gEnd - gStart).count() << " microseconds\n";
}

void MeasureElapsedTime(TIME_ELAPSED_STATE TimeState)
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

void MatrixAdditionByLine(int id, int line)
{
 /*   std::unique_lock<std::mutex> l1(g_mutex, std::defer_lock);

    l1.lock();
    std::cout << "Thread with id: " << id << " performing addition for line: " << line << '\n';
    l1.unlock();*/

    for (int j = 0; j < MAX_MATRIX_COLUMS; j++)
    {
        gMatrixResult[line][j] = gMatrixA[line][j] + gMatrixB[line][j];
    }
}

#define MEMZERO_MATRIX(matrix)      InitializeMatrix(matrix, 0)
#define GENERATE_RANDOM_VALUES      GenerateRandomMatrix
#define PRINT_MATRIX                PrintMatrix
#define PRINT_MATRIX_RESULT()       PRINT_MATRIX(gMatrixResult)

void SingleThreadedMatrixAddition(bool MemzeroResult = false)
{
    if (MemzeroResult)
    {
        MEMZERO_MATRIX(gMatrixResult);
    }

    std::cout << "Running matrix addition single threaded\n";
    MeasureElapsedTime(START_MEASURE);
    for (int i = 0; i < MAX_MATRIX_LINES; i++)
    {
        for (int j = 0; j < MAX_MATRIX_COLUMS; j++)
        {
            gMatrixResult[i][j] = gMatrixA[i][j] + gMatrixB[i][j];
        }
    }
    MeasureElapsedTime(END_MEASURE);
}

void ThreadPoolMatrixAddition(bool MemzeroResult = false)
{
    if (MemzeroResult)
    {
        MEMZERO_MATRIX(gMatrixResult);
    }

    std::cout << "Running matrix addition on pool of " << gMaxSupportedThreads << " threads\n";
    ctpl::thread_pool p(gMaxSupportedThreads);

    MeasureElapsedTime(START_MEASURE);
    for (int i = 0; i < MAX_MATRIX_LINES; i++)
    {
        p.push(MatrixAdditionByLine, i);
    }

    p.stop(true);
    MeasureElapsedTime(END_MEASURE);

    p.clear_queue();
}

void MatrixMultiplicationSingleThreaded(bool MemzeroResult = false)
{
    if (MemzeroResult)
    {
        MEMZERO_MATRIX(gMatrixResult);
    }

    std::cout << "Running matrix multiplication single threaded\n";

    MeasureElapsedTime(START_MEASURE);
    for (int i = 0; i < MAX_MATRIX_LINES; i++)
    {
        for (int j = 0; j < MAX_MATRIX_COLUMS; j++)
        {
            for (int k = 0; k < MAX_MATRIX_COLUMS; k++)
            {
                gMatrixResult[i][j] += (gMatrixA[i][k] * gMatrixB[k][j]);
            }
        }
    }
    MeasureElapsedTime(END_MEASURE);
}

void MatrixMultiplicationByLine(int id, int line)
{
    for (int j = 0; j < MAX_MATRIX_COLUMS; j++)
    {
        for (int k = 0; k < MAX_MATRIX_COLUMS; k++)
        {
            gMatrixResult[line][j] += (gMatrixA[line][k] * gMatrixB[k][j]);
        }
    }
}

void ThreadPoolMatrixMultiplication(bool MemzeroResult = false)
{
    if (MemzeroResult)
    {
        MEMZERO_MATRIX(gMatrixResult);
    }

    std::cout << "Running matrix multiplication on thread pool\n";

    ctpl::thread_pool p(gMaxSupportedThreads);

    MeasureElapsedTime(START_MEASURE);
    for (int i = 0; i < MAX_MATRIX_LINES; i++)
    {
        p.push(MatrixMultiplicationByLine, i);
    }

    p.stop(true);
    MeasureElapsedTime(END_MEASURE);

    p.clear_queue();

    p.~thread_pool();
}

void AsyncMatrixAddition(bool MemzeroResult = false)
{
    std::cout << "Running matrix addition with std::async\n";

    if (MemzeroResult)
    {
        MEMZERO_MATRIX(gMatrixResult);
    }

    std::vector<std::future<void>> asyncThreads;

    MeasureElapsedTime(START_MEASURE);
    for (int i = 0; i < MAX_MATRIX_LINES; i++)
    {
        asyncThreads.push_back(std::async(MatrixAdditionByLine, i, i));
    }

    for (int i = 0; i < MAX_MATRIX_LINES; i++)
    {
        // Wait for the threads to finish
        asyncThreads[i].get();
    }
    MeasureElapsedTime(END_MEASURE);
}

void AsyncMatrixMultiplication(bool MemzeroResult = false)
{
    std::cout << "Running matrix multiplication with std::async\n";

    if (MemzeroResult)
    {
        MEMZERO_MATRIX(gMatrixResult);
    }

    std::vector<std::future<void>> asyncThreads;
    MeasureElapsedTime(START_MEASURE);
    for (int i = 0; i < MAX_MATRIX_LINES; i++)
    {
        asyncThreads.push_back(std::async(MatrixMultiplicationByLine, i, i));
    }

    for (int i = 0; i < MAX_MATRIX_LINES; i++)
    {
        // Wait for the threads to finish
        asyncThreads[i].get();
    }
    MeasureElapsedTime(END_MEASURE);
}

int main()
{
    printf("Total threads supported on this system: %u\n", gMaxSupportedThreads);
    printf("Matrix of form M(%d,%d)\n", MAX_MATRIX_LINES, MAX_MATRIX_COLUMS);

    GENERATE_RANDOM_VALUES(gMatrixA);
    GENERATE_RANDOM_VALUES(gMatrixB);

    // C++ - Thread pool
    {
        SingleThreadedMatrixAddition(true);

        ThreadPoolMatrixAddition(true);

        MatrixMultiplicationSingleThreaded(true);

        ThreadPoolMatrixMultiplication(true);
    }

    // C++ - Async
    {
        AsyncMatrixAddition(true);

        AsyncMatrixMultiplication(true);
    }
}
