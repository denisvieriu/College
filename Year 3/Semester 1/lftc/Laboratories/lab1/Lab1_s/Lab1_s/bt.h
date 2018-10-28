#ifndef _BINARY_TREE_H
#define _BINARY_TREE_H

#include "pch.h"
#include <iostream>
#include <string>

// allow constant and identifiers use the same template
template <class T>
class Node
{
public:
    T data;
    Node* left;
    Node* right;
    int* key;

    Node(T _data) : data(_data), left(nullptr), right(nullptr), key(nullptr) {}
    int* Insert(Node* node, T data);

private:
    int currentKey;
    void _InorderAssign(Node* root);
    Node<T>* _Insert(Node<T>* node, T data);

public:
    void InoderAssign(Node* root);
    Node<T>* SearchNode(Node<T>* root, T data);
    void InorderTraversal(Node* root);
};


using namespace std;

template<class T>
int* Node<T>::Insert(Node* root, T data)
{
    Node* nodePtr = SearchNode(root, data);
    if (nodePtr == nullptr)
    {
        root = _Insert(root, data);
        InoderAssign(root);
        return SearchNode(root, data)->key;
    }
    else
    {
        cout << root->data << endl;
        return nodePtr->key;
    }
}

template<class T>
inline void Node<T>::_InorderAssign(Node * root)
{
    if (root == nullptr)
        return;


    _InorderAssign(root->left);

    if (root->key == nullptr)
    {
        root->key = new int;
        *(root->key) = this->currentKey;
    }
    else
    {
        *(root->key) = this->currentKey;
    }

    this->currentKey++;

    _InorderAssign(root->right);
}

template<class T>
inline Node<T>* Node<T>::_Insert(Node *root, T data)
{
    if (root == nullptr) return new Node(data);

    /* Otherwise, recur down the tree */
    if (data < root->data)
        root->left = _Insert(root->left, data);
    else if (data > root->data)
        root->right = _Insert(root->right, data);

    /* return the (unchanged) node pointer */
    return root;
}

template<class T>
inline void Node<T>::InoderAssign(Node* root)
{
    this->currentKey = 0;
    _InorderAssign(root);
}

template<class T>
inline Node<T>* Node<T>::SearchNode(class Node<T>* root, T data)
{
    if (root == nullptr || root->data == data)
        return root;

    if (root->data < data)
        return SearchNode(root->right, data);

    return SearchNode(root->left, data);
}

template<class T>
inline void Node<T>::InorderTraversal(Node * root)
{
    if (root == nullptr)
        return;

    InorderTraversal(root->left);
    cout << root->data << " ";
    InorderTraversal(root->right);
}

#endif
