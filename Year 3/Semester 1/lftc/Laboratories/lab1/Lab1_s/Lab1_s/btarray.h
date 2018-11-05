#ifndef _BTARRAY_H
#define _BTARRAY_H

#include <string>
#include <vector>
#include <algorithm>

using namespace std;
typedef enum _BT_TRAVERSAL
{
    BT_INORDER,
    BT_PREORDER,
    BT_POSTORDER
}BT_TRAVERSAL;

//
// Binary Search Tree represent as an array
// Search: O(1)
// Insertion: O(logn)
//
template <class T>
class BST
{

#ifndef BT_ROOT
#define BT_ROOT 0
#endif

private:
    void _resizeBt();
    void _inOrderTraversal(int idx);
    void _preOrderTraversal(int idx);
    void _postOrderTraversal(int idx);

    T compNull;
public:

    std::vector<T> btArray;
    int curReachedIdx;

    BST(const int Size = 2);

    //
    // Inserts a new node in BST
    // Returns the position in the array after insertion
    //
    int insertNode(T data);

    //
    // Returns the data from a given key (key = position in array)
    //
    T searchKey(int key);

    //
    // Possible prints of the tree
    //
    void traversal(BT_TRAVERSAL traversalType);

    bool contains(T data);
};

template<class T>
inline BST<T>::BST(const int Size)
{
    this->curReachedIdx = 0;
    this->btArray.resize(Size);

    if constexpr (std::is_same_v<T, std::string>) {
        this->compNull = "";
    }
    else if constexpr (std::is_same_v<T, int>) {
        this->compNull = NULL;
    }
}

template<class T>
inline void BST<T>::_resizeBt()
{
    this->btArray.resize(this->btArray.size() * 2);
}

template<class T>
inline int BST<T>::insertNode(T data)
{
    int crtIdx = 0;

    while (true)
    {
        while (crtIdx >= this->btArray.size())
        {
      /*      cout << "Resizing array from " << this->btArray.size() <<
                "elements, to " << this->btArray.size() * 2 << endl;*/
            this->_resizeBt();
        }

        if (this->btArray[crtIdx] == compNull)
        {
            this->btArray[crtIdx] = data;
            //cout << "Inserted data at btArray[" << crtIdx << "]\n";
            break;
        }
        else if (this->btArray[crtIdx] > data)
        {
            crtIdx = crtIdx * 2 + 1;
            //cout << "Inserting [" << data << "] in left subtree\n";
        }
        else if (this->btArray[crtIdx] < data)
        {
            crtIdx = crtIdx * 2 + 2;
            //cout << "Inserting [" << data << "] in right subtree\n";
        }
        else if (this->btArray[crtIdx] == data)
        {
            // throw new exception
            //cout << "Same element found\n";
            return crtIdx;
        }
    }

    this->curReachedIdx = max(crtIdx, this->curReachedIdx);

    return crtIdx;

}

template<class T>
inline T BST<T>::searchKey(int key)
{
    if (
        (this->btArray[key] == this->key) ||
        (key > this->curReachedIdx) ||
        (key >= this->btArray.size())
        )
    {
        // throw new exception
    }

    return this->btArray[key];
}

template<class T>
inline void BST<T>::traversal(BT_TRAVERSAL traversalType)
{
    switch (traversalType)
    {
    case BT_INORDER:   this->_inOrderTraversal(BT_ROOT);   cout << endl; break;
    case BT_PREORDER:  this->_preOrderTraversal(BT_ROOT);  cout << endl; break;
    case BT_POSTORDER: this->_postOrderTraversal(BT_ROOT); cout << endl; break;
    }
}

template<class T>
inline bool BST<T>::contains(T data)
{
    int crtIdex = 0;

    while (true)
    {
        if (
            (crtIdex >= this->btArray.size()) ||
            (this->btArray[crtIdex] == compNull) ||
            (crtIdex > this->curReachedIdx)
            )
        {
            return false;
        }
        if (this->btArray[crtIdex] == data)
        {
            return true;
        }
        if (this->btArray[crtIdex] < data)
        {
            // look right
            crtIdex = 2 * crtIdex + 2;
        }
        else if (this->btArray[crtIdex] > data)
        {
            crtIdex = 2 * crtIdex + 1;
        }
    }
}

template<class T>
inline void BST<T>::_inOrderTraversal(int idx)
{

    if (
        (idx >= this->btArray.size()) ||
        (this->btArray[idx] == compNull) ||
        (idx > this->curReachedIdx)
        )
    {
        return;
    }

    this->_inOrderTraversal(idx * 2 + 1);
    cout << this->btArray[idx] << "  |  " << idx << endl;
    this->_inOrderTraversal(idx * 2 + 2);
}

template<class T>
inline void BST<T>::_preOrderTraversal(int idx)
{
    idx;
}

template<class T>
inline void BST<T>::_postOrderTraversal(int idx)
{
    idx;

}


#endif
