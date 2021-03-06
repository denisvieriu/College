// lab4.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"
#include <iostream>
#include <mutex>
#include <vector>
#include <condition_variable>
#include <windows.h>
#include <chrono>
#include <queue>

#define MATRIX_MAX_LINE     10
#define MATRIX_MAX_COLUMNS  10

using namespace std;

unsigned int gSupportedThreads = 8;
bool gCanProcess = true;
bool gFinished = false;
int gI = -1;
int gIProcessed = 0;

std::condition_variable gCv;
std::mutex gMutex;

std::chrono::time_point<std::chrono::steady_clock> gStart;
std::chrono::time_point<std::chrono::steady_clock> gEnd;
std::chrono::duration<double> gElapsedTime;

queue<int> gQueue;
bool terminated = false;

int gMatrixA[MATRIX_MAX_LINE][MATRIX_MAX_COLUMNS], gMatrixB[MATRIX_MAX_LINE][MATRIX_MAX_COLUMNS], gMatrixC[MATRIX_MAX_LINE][MATRIX_MAX_COLUMNS];
int gMatrixR1[MATRIX_MAX_LINE][MATRIX_MAX_COLUMNS] = { 0 }, gMatrixR2[MATRIX_MAX_LINE][MATRIX_MAX_COLUMNS] = { 0 };

void PopulateMatrix(
    int Matrix[][MATRIX_MAX_COLUMNS]
)
{
    for (int i = 0; i < MATRIX_MAX_COLUMNS; i++)
    {
        for (int j = 0; j < MATRIX_MAX_COLUMNS; j++)
        {
            Matrix[i][j] = std::rand() % 256;
        }
    }
}

void MatrixMult(int MatrixA[][MATRIX_MAX_LINE], int MatrixB[][MATRIX_MAX_COLUMNS], int MatrixResult[][MATRIX_MAX_COLUMNS], int line)
{
    for (int j = 0; j < MATRIX_MAX_COLUMNS; j++)
    {
        for (int k = 0; k < MATRIX_MAX_COLUMNS; k++)
        {
            MatrixResult[line][j] += (MatrixA[line][k] * MatrixB[k][j]);
        }
    }
}

void Consumer()
{
    while (true)
    {
        std::unique_lock<std::mutex> lk(gMutex);

        if (gQueue.empty())
        {
            if (terminated)
            {
                cout << "Terminated signaled" << endl;
                lk.unlock();
                break;
            }
            else
            {
                cout << "Queue empty! Waiting to be signaled.." << endl;
                gCv.wait(lk);
                continue;
            }
        }


        int line = gQueue.front();
        gQueue.pop();

        cout << "Processing line: " << line << ", thread id" << std::this_thread::get_id() << endl;

        MatrixMult(gMatrixR1, gMatrixC, gMatrixR2, line);


        lk.unlock();
    }
}

void Producer()
{
    while (gI <= MATRIX_MAX_LINE)
    {
        std::unique_lock<std::mutex> lk(gMutex);
        gI++;

        if (gI >= MATRIX_MAX_LINE)
        {

            terminated = true;

            lk.unlock();
            gCv.notify_all();
            break;
        }

        MatrixMult(gMatrixA, gMatrixB, gMatrixR1, gI);
        gQueue.push(gI);

        // in case of an empty queue for the consumer, this will help
        lk.unlock();
        gCv.notify_all();
    }
}

int main()
{
    std::vector<std::thread> worker1;
    std::vector<std::thread> worker2;

    // Initialize matrices with random values between 0 and 255
    PopulateMatrix(gMatrixA);
    PopulateMatrix(gMatrixB);
    PopulateMatrix(gMatrixC);

    //
    // Create <gSupportedThreads / 2> consumers, in my case is 2 (2 logical processors)
    // By starting the customers first, they will have to wait untill the producers signals them that they can start processing
    //
    for (int i = 0; i < gSupportedThreads / 2; i++)
    {
        worker2.emplace_back(Consumer);
    }

    // <gSupportedThreads / 2> Producers
    for (int i = 0; i < gSupportedThreads / 2; i++)
    {
        worker1.emplace_back(Producer);
    }

    // Try to close the consumers and producers
    for (int i = 0; i < gSupportedThreads / 2; i++)
    {
        if (worker1[i].joinable())
        {
            worker1[i].join();
        }

        if (worker2[i].joinable())
        {
            worker2[i].join();
        }
    }
}
