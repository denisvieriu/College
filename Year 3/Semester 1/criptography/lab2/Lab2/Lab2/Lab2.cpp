// Lab2.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"
#include <iostream>
#include <assert.h>

using namespace std;

int gcd(int a, int b)
{
    if (b == 0)
    {
        return a;
    }

    return gcd(b, a % b);
}

// Iterative C++ program to find modular
// inverse using extended Euclid algorithm

// Returns modulo inverse of a with respect
// to m using extended Euclid Algorithm
// Assumption: a and m are coprimes, i.e.,
// gcd(a, m) = 1
int modInverse(int a, int m)
{
    int m0 = m;
    int y = 0, x = 1;

    if (m == 1)
        return 0;

    while (a > 1)
    {
        // q is quotient
        int q = a / m;
        int t = m;

        // m is remainder now, process same as
        // Euclid's algo
        m = a % m, a = t;
        t = y;

        // Update y and x
        y = x - q * y;
        x = t;
    }

    // Make x positive
    if (x < 0)
        x += m0;

    return x;
}

int main()
{
    // ax = b (mod n)

    int a, b, n, d, x, modInverA;

    cout << "a=";
    cin >> a;

    cout << "b=";
    cin >> b;

    cout << "n=";
    cin >> n;

    d = gcd(a, n);
    cout << "Gcd(" << a << ", " << n << ")= " << d << endl;

    if (d == 1)
    {
        // soll here
    }
    else // d > 1
    {
        if ((b % d) != 0)
        {
            cout << "No solution found\n";
            exit(1);
        }
    }

    n = n / d;

    a = (a / d) % n;
    b = (b / d) % n;

    // x = a^(-1) * b (mod n)

    cout << "General solution is: Xg = " << a << "^(-1) * " << b << " (mod " << n << ")\n";

    modInverA = modInverse(a, n);

    x = (a * b) % n;

    cout << "General solution is: Xg = " << x << "(mod " << n << ")\n";

    assert((abs(a - b)) == 0);
    return 0;
}