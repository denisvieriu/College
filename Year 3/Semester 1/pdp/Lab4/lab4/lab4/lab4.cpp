// lab4.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"
#include <iostream>
#include <mutex>
#include <vector>
#include <condition_variable>
#include <chrono>

#define MATRIX_MAX_LINE     10
#define MATRIX_MAX_COLUMNS  10

unsigned int gSupportedThreads = std::thread::hardware_concurrency();
bool gProcessed = false;
bool gFinished = false;
int gI;

std::condition_variable gCv;
std::mutex gMutex;

std::chrono::time_point<std::chrono::steady_clock> gStart;
std::chrono::time_point<std::chrono::steady_clock> gEnd;
std::chrono::duration<double> gElapsedTime;

int gMatrixA[MATRIX_MAX_LINE][MATRIX_MAX_COLUMNS], gMatrixB[MATRIX_MAX_LINE][MATRIX_MAX_COLUMNS], gMatrixC[MATRIX_MAX_LINE][MATRIX_MAX_COLUMNS], gMatrixResult[MATRIX_MAX_LINE][MATRIX_MAX_COLUMNS];

void
PopulateMatrix(
    int Matrix[][MATRIX_MAX_COLUMNS]
)
{
    for (int i = 0; i < MATRIX_MAX_COLUMNS; i++)
    {
        for (int j = 0; j < MATRIX_MAX_COLUMNS; j++)
        {
            Matrix[i][j] = std::rand() % MATRIX_MAX_COLUMNS;
        }
    }
}

void ThreadWorker2()
{

}

void ThreadWorker1(int Line)
{
    while (true)
    {
        std::unique_lock<std::mutex> lk(gMutex);
        gCv.wait(lk, [] {return gProcessed == false; });

        if (gFinished) break;

        std::cout << "Thread line: " << gI << " running\n";
        gProcessed = true;

        lk.unlock();
        gCv.notify_one();
    }
}

int main()
{
    std::vector<std::thread> worker1;
    std::vector<std::thread> worker2;

    worker1.reserve(gSupportedThreads / 2);
    worker2.reserve(gSupportedThreads / 2);

    PopulateMatrix(gMatrixA);
    PopulateMatrix(gMatrixB);
    PopulateMatrix(gMatrixC);

    gI = (gSupportedThreads / 2) - 1;
    gI = 1;
    for (int i = 0; i < 1; i++)
    {
       /* (i < gSupportedThreads / 2) ?*/
        (worker1.emplace_back(ThreadWorker1, i)); //:
            //(worker2.emplace_back(ThreadWorker2));

    }

    gProcessed = false;

    while (true)
    {
        if (gI == MATRIX_MAX_LINE)
        {
            gFinished = true;
            gCv.notify_all();
            break;
        }
        else
        {
            std::unique_lock<std::mutex> lk(gMutex);
            gCv.wait(lk, [] { return gProcessed; });
            gI++;
            gProcessed = false;
            lk.unlock();
        }
        gCv.notify_one();
    }
    std::cout << "Hello World!\n";
}
