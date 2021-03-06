#include "pch.h"
#include <iostream>
#include <fstream>
#include <vector>
#include <thread>
#include <assert.h>
#include <algorithm>
#include <chrono>


const int maxlg = 22;
const int maxn = 500005;

int n;
long long dp[maxlg][maxn], sum[maxn], psum[maxn];

const int T = 5;

//#define debug

using namespace std;



void doIt(int idx) {
    for (int i = idx; i < n; i += T) {
        int act = 0;
        int now = i + 1;
        for (int bit = 0; (1 << bit) <= now; ++bit) {
            if (now & (1 << bit)) {
                sum[i] += dp[bit][act];
                act += (1 << bit);
            }
        }
    }
}

int main() {
    auto start = std::chrono::system_clock::now();

    //ifstream fin("sums.in");

    n = 5;
    dp[0][0] = 1;
    dp[0][1] = 2;
    dp[0][2] = 3;
    dp[0][3] = 4;
    dp[0][4] = 5;

    for (int i = 0; i < n; ++i) {
        psum[i] = psum[i - 1] + dp[0][i];
    }
    /// dp[k][i] = sum(a[j]) where i <= j <= i + 2^k - 1

    // dp[k][i] =
    for (int k = 1; (1 << k) < maxn; ++k) {
        for (int i = 0; i < n; ++i) {
            dp[k][i] = dp[k - 1][i] + dp[k - 1][i + (1 << (k - 1))];
        }
    }

    vector <thread> th;
    for (int i = 0; i < min(T, n); ++i) {
        th.push_back(thread(doIt, i));
    }
    for (int i = 0; i < th.size(); ++i) {
        th[i].join();
    }

    for (int i = 0; i < n; ++i) {
        cout << sum[i] << '\n';
        assert(sum[i] == psum[i]);
    }

    auto end = std::chrono::system_clock::now();
    auto elapsed =
        std::chrono::duration_cast<std::chrono::milliseconds>(end - start);

    cout << "Computing partial sums of an array with " << n
        << " elements took me " << elapsed.count() << " milliseconds";

    return 0;
}