#include "pch.h"

#include <algorithm>
#include <iostream>
#include <fstream>
#include <vector>
#include <thread>
#include <mutex>
#include <chrono>
#include <string>

using namespace std;

//
// using an int as a bitam >= 31 values
//
#define MAX_NODE_VALUE                         31
#define SET_NODE_AS_VISITED(bitmap, node)      (bitmap |= (1 << node))
#define NODE_ALREADY_VISITED(bitmap, node)     (bitmap & (1 << node))

int T = 0;
mutex gMtx;

#define CURRENT_USED_THREADS                 T
#define INCREMENT_CURRENT_USED_THREADS(x)   (T += x)
#define DECREMENT_CURRENT_USED_THREADS(x)   (T -= x)

const int NR_THREADS = 1000;

int n, m;
vector <int> gDefaultGraph[MAX_NODE_VALUE];

static
void
LoadDefaultGraph(
    void
    )
{
    /* Let us create the following graph
      (0)--(1)--(2)
       |   / \   |
       |  /   \  |
       | /     \ |
      (3)-------(4)    */

    n = 5;
    m = 7;
    gDefaultGraph[0].push_back(1);
    gDefaultGraph[1].push_back(0);

    //gDefaultGraph[0].push_back(3);
    //gDefaultGraph[3].push_back(0);

    gDefaultGraph[1].push_back(2);
    gDefaultGraph[2].push_back(1);

    gDefaultGraph[1].push_back(3);
    gDefaultGraph[3].push_back(1);

    gDefaultGraph[1].push_back(4);
    gDefaultGraph[4].push_back(1);

    gDefaultGraph[2].push_back(4);
    gDefaultGraph[4].push_back(2);

    gDefaultGraph[3].push_back(4);
    gDefaultGraph[4].push_back(3);

}

static
bool
IsEdge(
    _In_ int x,
    _In_ int y
    );

static
void
VisitNeighbours(
    _In_     vector<int> Node,
    _Inout_  vector<int> &a,
    _Inout_  int         &visitedNodesBitmap,
    _Inout_  bool        &sol1,
    _In_     int         startPoint,
    _In_opt_ int         IncreasePoint
    );

static
bool
HamCycleUtil(
    _In_    int          Node,
    _Inout_ vector <int> &Solution,
    _Inout_ int          VisitedNodesBitmap
    );

static
void
PrintSolution(
    _In_ bool        CycleFound,
    _In_ vector<int> Solution
    );

int main() {
    cout << "Using a parallel backtracking to find a hamiltonian cycle withing a graph of " << n << " nodes" << endl;

    bool cycleFound = false;
    vector <int> solution;

    LoadDefaultGraph();

    auto start = std::chrono::system_clock::now();

    // Start finding a cycle from node 0
    cycleFound = HamCycleUtil(
        0,
        solution,
        0
        );

    PrintSolution(cycleFound, solution);

    auto end = std::chrono::system_clock::now();
    auto elapsed =
        std::chrono::duration_cast<std::chrono::milliseconds>(end - start);

    if (cycleFound)
    {
        cout << "Detection of hamiltonian cycle took " << elapsed.count() << " milliseconds\n";
    }
    else
    {
        cout << "Algorithm took " << elapsed.count() << " milliseconds\n";
    }

    return 0;
}

static
bool
HamCycleUtil(
    _In_    int          node,
    _Inout_ vector <int> &Solution,
    _Inout_ int          VisitedNodesBitmap
    )
{
    Solution.push_back(node);
    SET_NODE_AS_VISITED(VisitedNodesBitmap, node);

    if (Solution.size() == n)
    {
        return IsEdge(Solution.back(), Solution[0]);
    }

    vector <int> a(Solution), b(Solution); // create two copies
    gMtx.lock();
    if (CURRENT_USED_THREADS < NR_THREADS)
    {
        bool solution1 = 0;
        bool solution2 = 0;

        INCREMENT_CURRENT_USED_THREADS(2);

        gMtx.unlock();

        //
        // Thread1 tries to build a solution with it's neighbours starting with its first neighbour
        //
        std::thread thread1(
            VisitNeighbours,
            gDefaultGraph[node],
            std::ref(a),
            std::ref(VisitedNodesBitmap),
            std::ref(solution1),
            0,
            2
        );

        //
        // Thread2 tries to build a solution starting from second neigbour
        //
        std::thread thread2(
            VisitNeighbours,
            gDefaultGraph[node],
            std::ref(b),
            std::ref(VisitedNodesBitmap),
            std::ref(solution2),
            1,
            2
        );

        thread1.join();
        thread2.join();

        gMtx.lock();
        DECREMENT_CURRENT_USED_THREADS(2);
        gMtx.unlock();

        if (solution1)
        {
            Solution = a;
            return 1;
        }

        else if (solution2)
        {
            Solution = b;
            return 1;
        }
    }
    else
    { // do it on current thread
        gMtx.unlock();
        bool ignoredBool;
        //VisitNeighbours(gDefaultGraph[node], Solution, VisitedNodesBitmap, ignoredBool, 0, 1);
        for (int i = 0; i < gDefaultGraph[node].size(); i++) {
            vector <int> bux(b);
            if (NODE_ALREADY_VISITED(VisitedNodesBitmap, gDefaultGraph[node][i]))
                continue;
            if (HamCycleUtil(gDefaultGraph[node][i], bux, VisitedNodesBitmap))
            {
                Solution = bux;
                return 1;
            }
        }
    }
    return 0;
}

static
void
VisitNeighbours(
    _In_     vector<int> Node,
    _Inout_  vector<int> &a,
    _Inout_  int         &visitedNodesBitmap,
    _Inout_  bool        &sol1,
    _In_     int         startPoint,
    _In_opt_ int         IncreasePoint = 2
    )
{
    for (int i = startPoint; i < Node.size(); i += IncreasePoint) {
        vector <int> aux(a);
        int neighbourNode = Node[i];
        if (!NODE_ALREADY_VISITED(visitedNodesBitmap, neighbourNode))
        {
            if (HamCycleUtil(Node[i], aux, visitedNodesBitmap)) {
                sol1 = 1;
                a = aux;
                return;
            }
        }
    }
}

static
bool
IsEdge(
    _In_ int X,
    _In_ int Y
    )
{
    return (std::find(gDefaultGraph[X].begin(), gDefaultGraph[X].end(), Y) != gDefaultGraph[X].end());
}

static
void
PrintSolution(
    _In_ bool        CycleFound,
    _In_ vector<int> Solution
    )
{
    string s = "The algorithm ";
    s += ((CycleFound) ? "Found" : "Did not found");
    s += " a cycle in the given undirected graph";

    cout << s << endl;

    if (CycleFound)
    {
        for (auto node : Solution)
        {
            cout << node << ' ';
        }
        cout << endl;
    }
}
