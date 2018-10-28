#ifndef _BINARY_TREE_H
#define _BINARY_TREE_H

// allow constant and identifiers use the same template
template <class T>
class Node
{
public:
    T data;
    Node* left;
    Node* right;
    int* key;

    Node() {}
    Node(T _data) : data(_data), left(nullptr), right(nullptr), key(nullptr) {}
    void Insert();

private:
    int currentKey;
    void _InorderAssign(Node* root);
    Node<T>* _Insert(Node<T>* node, T data);

public:
    void InoderAssign(Node* root);
    Node<T>* SearchNode(Node<T>* root, T data);
    void InorderTraversal(Node* root);
};

#endif
