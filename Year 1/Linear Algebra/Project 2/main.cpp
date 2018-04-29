#include <iostream>
#include <fstream>
#include <vector>
#include <string>

using namespace std;
/************************************
* Project realized by: Vieriu Denis *
*************************************
* Project 3 : Write all bases       *
***********   of Z2^n over Z2       *
************************************/


/*********************************************************************************************************************************
*Let n = dim V , where V is a vector space over any finite field F with |F|=r and W is a k-dimensional subspace of V             *
*There will be 2^k - 1 elements (without the 0 vector). The total number of basis will be equal to                               *
*s = (r^k-1)*(r^k - r)*...*(r^k - r^(k-1), in our case | Z2^n | = 2^n - 1 elements, and the s = (2^n-1)(2^n-2)*...*(2^n-2^(n-1)) *
*********************************************************************************************************************************/

ofstream g("basis.out");
typedef vector <int> my_vect;
int bases = 0;
bool firstRun = false;
char str[] = "_____________________";

struct my_matrix{
my_vect v;
};


long long nrBases(int n)
{
    long long p = (1<<n); //2^n
    long long product = 1 ;
    for(int i=0;i<n;i++)
        product *= (p - (1<<i));
    return product;
}

void read(int &n, int* &my_stack)
{
    cout<<"Enter n=";
    cin>>n;
    my_stack = new int[n];
}


/*******************************************/
/*******First part, elements of Z2^n********/
/*******************************************/
void printMyMatrix(int m, int n, my_matrix *M)
{
    for(int i = 0;i < m;i++)
    {
        for(int j = 0;j < n;j++)
            cout<<M[i].v[j]<<" ";
        cout<<'\n';
    }
}

void assignMyMatrix(int n, int *my_stack, my_matrix *&M)
{
    static int i=0;
    M[i++].v.assign(my_stack,my_stack+n);
}



int solution(int step, int n)
{
    return step==n-1;
}

void bkt(int step, int *my_stack, int n, my_matrix *M)
{
    for(int i = 0;i <= 1;i++)
    {
        my_stack[step] = i;
        if(solution(step, n))
        {
            if (firstRun == true)
                assignMyMatrix(n, my_stack, M);
            else
                firstRun = true;
        }
        else
            if(step<n-1)
                bkt(step+1, my_stack, n, M);
    }
}


/*******************************************/
/****Second part, bases of Z2^n over Z2*****/
/*******************************************/
void printV(int n, my_vect v)
{
    g<<"(";
    for(int i=0;i<n;i++)
        g<<v[i]<<" ";
    g<<")   ";

}

bool compare(int n,my_vect v1, my_vect v2)
{
    int i;
    for(i=0;i<n;i++)
        if(v1[i]!=v2[i])
        return true;
    return false;
}

void printBase(int n, int *my_stack, my_matrix *M)
{
    for(int i=0;i<n;i++)
        printV(n,M[my_stack[i]].v);
    g<<'\n';
}
my_vect sum(int n, my_vect v1, my_vect v2)
{
    for(int i=0; i<n; i++)
        v1[i] = (v1[i] + v2[i])%2;
    return v1;
}

my_vect bigSum(int n,int step, int *my_stack, my_matrix *M)
{
    int i;
    my_vect V(n);
    for(i=0;i<n;i++)
        V[i]=0;

    for(i=0; i<=step; i++)
        V = sum(n, V, M[my_stack[i]].v);

    return V;

}


int good(int n, int step,int *my_stack, my_matrix *M)
{
    my_vect V;
    if(step==0)
        return 1;
    for(int i=0;i<step;i++)
        if(my_stack[i]==my_stack[step])
            return 0;

    V = bigSum(n,step-1,my_stack,M);
    if(compare(n, V, M[my_stack[step]].v)==false)
        return 0;

    for(int i=0;i<step-1;i++)
        for(int j=i+1; j<step;j++)
        {
            V = sum(n, M[my_stack[i]].v, M[my_stack[j]].v);
            if(compare(n,V, M[my_stack[step]].v)==false)
                return 0;
        }
    return 1;
}


void bkt2(int m, int n, int step,int *my_stack, my_matrix *M)
{
    for(int i=0;i<m;i++)
    {

        my_stack[step] = i;
        if(good(n,step,my_stack,M))
            if(solution(step,n))
            {
              printBase(n,my_stack,M);
              bases++;
            }
            else
                bkt2(m,n,step+1,my_stack,M);
    }
}


int main()
{

    int n, *my_stack, m;
    read(n, my_stack);
    m = (1<<n)-1;
                                            // 2 at power of n (all elements from Z2 ^ n)
    my_matrix *M = new my_matrix[m];        // declaring the matrix used to remember the elements; m - number of lines
    bkt(0, my_stack, n, M);
    printMyMatrix(m,n,M);
    cout<<str<<'\n'<<"Number of basis = "<<nrBases(n)<<'\n'<<str;
    bkt2(m,n,0,my_stack,M);


    return 0;
}
