// ConsoleApplication1.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"
#include <iostream>
#include <mpi.h>
#include <vector>
#include <algorithm>

using namespace std;

#define MAX_POLYNOM_NUMBER  0xFFFF
#define POLYNOM_SIZE        4

#define MPI_ARRAY_SIZE      4
#define MPI_START_A_POS     0
#define MPI_END_A_POS       1
#define MPI_ARRAY_A         2
#define MPI_ARRAY_B         3

vector<int> aT = { 5, 0, 10, 6 };

// second polynom
vector<int> bT = { 1, 2, 4, 3 };


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

void Karatsuba(vector<int> &a, vector<int> &b, int left, int right, vector<int> &res)
{

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
    Karatsuba(a, b, left, right, res);
}

int main(int argc, char* argv[] )
{
    MPI_Init(0, 0);

    int me;
    int size;
    MPI_Comm_size(MPI_COMM_WORLD, &size);
    MPI_Comm_rank(MPI_COMM_WORLD, &me);

    if (me == 0)
    {
        vector<int> a, b;
        int n;
        n = 4;

        //GenerateRandomPolyonms(a, b, n);
        a = aT;
        b = bT;
        ResizeUntilPowerOf2(a, b, n);

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
    }
    else
    {
        Worker(me);
    }

    MPI_Finalize();
    return 0;
}

