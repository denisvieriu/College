#include "binary_tree.h"
#include <vector>
#include <string>

using namespace std;

//vector<int, int*> gPif;

int main()
{
    Node<string>* root = new Node<string>(string("b"));
    root->SearchNode(NULL, string("b"));

    //root->InorderTraversal(root);

    return 0;
}