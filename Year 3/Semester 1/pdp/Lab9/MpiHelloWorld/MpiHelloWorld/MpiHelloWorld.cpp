// MpiHelloWorld.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"
#include <iostream>
#include <mpi.h>
#include <time.h>
#include <vector>
#include <chrono>

using namespace std;

#define MAX_POLYNOM_NUMBER  0xFFFF
#define POLYNOM_SIZE        50

#define MPI_NO_ELEMS        0
#define MPI_A_VECTOR        1
#define MPI_B_VECTOR        2
#define MPI_RESULT_VECTOR   3

vector<int> aT = { 5, 0, 10, 6 };

// second polynom
vector<int> bT = { 1, 2, 4, 3 };

typedef enum _TIME_ELAPSED_STATE
{
    START_MEASURE,
    END_MEASURE
}TIME_ELAPSED_STATE;


std::chrono::time_point<std::chrono::steady_clock> gStart;
std::chrono::time_point<std::chrono::steady_clock> gEnd;
std::chrono::duration<double> gElapsedTime;


void
PrintPoly(
    _In_ const vector<int> Pol
)
{
    size_t n = Pol.size();
    for (size_t i = 0; i < n; i++)
    {
        if (Pol[i] == 0) continue;

        cout << Pol[i];
        if (i != 0)
            cout << "x^" << i;

        if (i != n - 1 && Pol[i + 1] != 0)
            cout << " + ";
    }

    cout << endl;
}


static
void
PrintElapsedTime(
    void
)
{
    std::cout << "----> Total elapsed time " << std::chrono::duration_cast<std::chrono::microseconds>(gEnd - gStart).count() << " microseconds\n";
}

static
void
MeasureElapsedTime(
    _In_ TIME_ELAPSED_STATE TimeState
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
GenerateRandomPolyonms(vector<int> &a, vector<int> &b, int n)
{
    a.resize(n);
    b.resize(n);

    for (int i = 0; i < n; i++)
    {
        a[i] = rand() % MAX_POLYNOM_NUMBER;
        b[i] = rand() % MAX_POLYNOM_NUMBER;
    }
}

#define T 3

void
MpiPolMult(int coefficientToStart, vector<int> &a, vector<int> &b, vector<int> &result)
{
    a.resize(result.size());
    b.resize(result.size());
    for (int i = coefficientToStart; i < result.size(); i += T)
    {
        for (int coeffInA = 0; coeffInA <= i; coeffInA++)
        {
            int coeffInB = i - coeffInA;
            result[i] += (a[coeffInA] * b[coeffInB]);
        }
    }

}

void
SlaveWorker(const int me_id)
{
    //
    // Slave worker just receives data
    //
    int n;
    MPI_Status mpiStatus;

    MPI_Recv(&n, 1, MPI_INT, 0, MPI_NO_ELEMS, MPI_COMM_WORLD, &mpiStatus);

    vector<int> a(n);
    vector<int> b(n);
    vector<int> result(n * 2 - 1, 0);

    MPI_Recv(a.data(), n, MPI_INT, 0, MPI_A_VECTOR, MPI_COMM_WORLD, &mpiStatus);
    MPI_Recv(b.data(), n, MPI_INT, 0, MPI_B_VECTOR, MPI_COMM_WORLD, &mpiStatus);

    MpiPolMult(me_id, a, b, result);

    MPI_Send(result.data(), result.size(), MPI_INT, 0, MPI_RESULT_VECTOR, MPI_COMM_WORLD);
}

int main()
{
    MPI_Init(0, 0);

    int me;
    int size;
    MPI_Status mpiStatus;

    MPI_Comm_size(MPI_COMM_WORLD, &size);
    MPI_Comm_rank(MPI_COMM_WORLD, &me);

    cout << "Hello, I am " << me << " out of " << size << "\n";

    if (me == 0)
    {
        vector<int> a, b;
        int n = POLYNOM_SIZE;


        vector<int> result(2 * n - 1, 0), finalResult(2 * n - 1, 0);
       // a = aT;
        //b = bT;
        GenerateRandomPolyonms(a, b, n);

        MeasureElapsedTime(START_MEASURE);

        // Send the data to the workers
        for (int i = 1; i < size; i++)
        {
            // send n
            MPI_Send(&n, 1, MPI_INT, i, MPI_NO_ELEMS, MPI_COMM_WORLD);

            // send a
            MPI_Send(a.data(), n, MPI_INT, i, MPI_A_VECTOR, MPI_COMM_WORLD);

            // send b
            MPI_Send(b.data(), n, MPI_INT, i, MPI_B_VECTOR, MPI_COMM_WORLD);
        }

        MpiPolMult(0, a, b, finalResult);

        // Collect the data from workers
        for (int i = 1; i < size; i++)
        {
            MPI_Recv(result.data(), result.size(), MPI_INT, i, MPI_RESULT_VECTOR, MPI_COMM_WORLD, &mpiStatus);
            for (int i = 0; i < result.size(); i++)
            {
                finalResult[i] |= result[i];
            }
        }

        PrintPoly(finalResult);

        MeasureElapsedTime(END_MEASURE);
    }
    else
    {
        MeasureElapsedTime(START_MEASURE);
        SlaveWorker(me);
        MeasureElapsedTime(END_MEASURE);
    }

    MPI_Finalize();
}