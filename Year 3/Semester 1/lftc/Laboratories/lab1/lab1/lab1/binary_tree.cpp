#include "binary_tree.h"
#include <iostream>

using namespace std;

template<class T>
void Node<T>::Insert()
{
}

template<class T>
inline void Node<T>::_InorderAssign(Node * root)
{
    if (root = nullptr)
        return;

    _InorderAssign(root->left);

    if (root->key == nullptr)
    {
        root->key = new int;
        *(root->key) = this->currentKey++;
    }
    else
    {
        *(root->key) = this->currentKey++;
    }

    _InorderAssign(root->left);
}

template<class T>
Node<T>* Node<T>::_Insert(Node * node, T data)
{
    return nullptr;
}

template<class T>
void Node<T>::InoderAssign(Node* root)
{
    this->key = 0;
    _InorderAssign(root);
}



template<class T>
Node<T>* Node<T>::SearchNode(Node<T>* root, T data)
{
    if (root == nullptr || root->key == key)
        return root;

    if (root->key < key)
        return search(root->right, data);

    return search(root->left, data);
}

template<class T>
void Node<T>::InorderTraversal(Node * root)
{
    if (root == nullptr)
        return;

    InorderTraversal(root->left);
    cout << root->data << " ";
    InorderTraversal(root->right);
}
