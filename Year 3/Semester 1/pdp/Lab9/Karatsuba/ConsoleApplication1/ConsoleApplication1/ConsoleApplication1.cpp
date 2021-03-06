// ConsoleApplication1.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"
#include <iostream>
#include <mpi.h>
#include <vector>
#include <chrono>
#include <algorithm>

using namespace std;

#define MAX_POLYNOM_NUMBER  0xFFFF
#define POLYNOM_SIZE        4

#define MPI_ARRAY_SIZE      4
#define MPI_START_A_POS     0
#define MPI_END_A_POS       1
#define MPI_ARRAY_A         2
#define MPI_ARRAY_B         3
#define MPI_ARRAY_RESULT    5

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

/*
    Karatsuba Algorithm:
    Input A: polynomial of degree at most n - 1, n = 2^k
    Input B: polynomial of degree at most n - 1
    Output: C: polynomial

    if n = 1 then return Prod <- AB
    C1 <- A0 * B0; by a recursive call
    C2 <- A1 * B1; by a recursive call
    C3 <- A0 + A1;
    C4 <- B0 + B1;
    C5 <- C3 * C4; by a recursive call
    C6 <- C5 - C1 - C2
    C <- C1 + C6*X^(n/2) + C2*X^n
*/
void
_Karatsuba(
    _In_ const vector<int> &A,
    _In_ const vector<int> &B,
    _Out_ vector<int> &C
)
{
    if (A.size() == 1 && B.size() == 1)
    {
        C[0] = A[0] * B[0];
        return;
    }

    size_t half = A.size() / 2;

    vector<int> A0(A.begin(), A.begin() + half);    // A0
    vector<int> A1(A.begin() + half, A.end());      // A1

    vector<int> B0(B.begin(), B.begin() + half);    // B0
    vector<int> B1(B.begin() + half, B.end());      // B1

    vector<int> C1(A0.size() + B0.size() - 1);      // C1
    vector<int> C2(A1.size() + B1.size() - 1);      // C2

    _Karatsuba(A0, B0, C1);     // C1 = A0 * B0 - by recursive call
    _Karatsuba(A1, B1, C2);     // C2 = A1 * B1 - by recursive call

    for (size_t i = 0; i < C1.size(); ++i)
    {
        C[i] += C1[i];                        // C = C1;
    }

    for (size_t i = 0; i < A1.size(); ++i)
    {
        A0[i] += A1[i];                       // C3 = A0 + A1
        B0[i] += B1[i];                       // C4 = B0 + B1
    }
    vector<int> C5(A0.size() + B0.size() - 1);
    _Karatsuba(A0, B0, C5);     // C5 = C3 * C4 - by recursive call

    for (size_t i = 0; i < C5.size(); ++i)          // At this point C = C1;
    {
        C[i + half] += C5[i] - C1[i] - C2[i];     // C += C6; => C = C1 + C6; where C6 = C5 - C1 - C2
    }

    for (size_t i = 0; i < C2.size(); ++i)          // At this point C = C1 + C6*X^(n/2)
    {
        C[i + 2 * half] += C2[i];                   // C = C1 + C6*X^(n/2) + C2*X^n
    }
}

void Karatsuba(vector<int> &a, vector<int> &b, vector<int> &res)
{
    // Start measure time..
    _Karatsuba(a, b, res);

}

void
ResizeUntilPowerOf2(vector<int> &a, vector<int> &b, int &n)
{
    while (n & (n - 1))
    {
        ++n;
        a.push_back(0);
        b.push_back(0);
    }
}



void Worker(int me)
{
    MPI_Status mpiStatus;
    int left;
    int right;
    int n;

    MPI_Recv(&n, 1, MPI_INT, 0, MPI_ARRAY_SIZE, MPI_COMM_WORLD, &mpiStatus);

    vector<int> a(n);
    vector<int> b(n);

    MPI_Recv(&left, 1, MPI_INT, 0, MPI_START_A_POS, MPI_COMM_WORLD, &mpiStatus);
    MPI_Recv(&right, 1, MPI_INT, 0, MPI_END_A_POS, MPI_COMM_WORLD, &mpiStatus);

    MPI_Recv(a.data() + left, right - left, MPI_INT, 0, MPI_ARRAY_A, MPI_COMM_WORLD, &mpiStatus);
    MPI_Recv(b.data(), n, MPI_INT, 0, MPI_ARRAY_B, MPI_COMM_WORLD, &mpiStatus);

    vector<int> res(n * 2 - 1);
    Karatsuba(a, b, res);

    MPI_Send(res.data(), n * 2 - 1, MPI_INT, 0, MPI_ARRAY_RESULT, MPI_COMM_WORLD);
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


int main(int argc, char* argv[] )
{
    MPI_Init(0, 0);

    MPI_Status mpiStatus;;
    int me;
    int size;
    MPI_Comm_size(MPI_COMM_WORLD, &size);
    MPI_Comm_rank(MPI_COMM_WORLD, &me);

    if (me == 0)
    {
        MeasureElapsedTime(START_MEASURE);
        vector<int> a, b;
        int n;
        n = 4;

        //GenerateRandomPolyonms(a, b, n);
        a = aT;
        b = bT;
        ResizeUntilPowerOf2(a, b, n);

        // Send data to workers
        for (int i = 1; i < size; i++)
        {
            int left = i * n / size;
            int right = min(n, (i + 1) * n / size);

            MPI_Send(&n, 1, MPI_INT, i, MPI_ARRAY_SIZE, MPI_COMM_WORLD);
            MPI_Send(&left, 1, MPI_INT, i, MPI_START_A_POS, MPI_COMM_WORLD);
            MPI_Send(&right, 1, MPI_INT, i, MPI_END_A_POS, MPI_COMM_WORLD);
            MPI_Send(a.data() + left, right - left, MPI_INT, i, MPI_ARRAY_A, MPI_COMM_WORLD);
            MPI_Send(b.data(), n, MPI_INT, i, MPI_ARRAY_B, MPI_COMM_WORLD);
        }

        // Do some work on main thread too
        int left = 0;
        int right = n / size;
        for (right; right < a.size(); right++);
        {
            a[right] = 0;
        }
        vector<int> fresult(2 * n - 1);
        Karatsuba(a, b, fresult);

        for (int i = 1; i < size; i++)
        {
            vector<int> result(2 * n - 1, 0);
            int left = i * n / size;
            int right = min(n, (i + 1) * n / size);

            MPI_Recv(result.data(), 2 * n - 1, MPI_INT, i, MPI_ARRAY_RESULT, MPI_COMM_WORLD, &mpiStatus);

            for (int i = 0; i < 2 * n - 1; i) 
            {
                fresult[i] += result[i];
            }
        }

        cout << "All data received by master\n";
        PrintPoly(fresult);
        MeasureElapsedTime(END_MEASURE);
    }
    else
    {
        MeasureElapsedTime(START_MEASURE);
        Worker(me);
        MeasureElapsedTime(END_MEASURE);
    }

    MPI_Finalize();
    return 0;
}