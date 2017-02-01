/************************************
* Project realized by: Vieriu Denis *
*************************************
* Project 1 : Write all partitions  *
***********   of a set              *
************************************/

#include <iostream>
#include <fstream>
using namespace std;
ofstream g("partitions.out");

//global declared stack(all elements within my_stack are 0)
int my_stack[50], count_partitions;
char str[] = "***************************************************";

// Reads an integer given by the user
void readFromKeyboard(int &n)
{

    cout<<"Please enter n=";
    cin>>n;

}

//Calculates the max value within a stack ( within this value
//we know how many blocks to form and which numbers are in it
int valMax(int step)
{
    int vMax = 0;
    for(int i = 1; i < step; i++)
        if(vMax < my_stack[i])
            vMax = my_stack[i];
    return vMax;

}

//Prints a partition of set P
void print(int n)
{
    int vMax = valMax(n+1);
    for (int i = 1; i <= vMax; i++)
    {
        g<<"{ ";
        for(int j = 1; j<=n; j++)
            if(my_stack[j] == i)
                g<<j<<" ";
        g<<"}";
    }
    g<<'\n';
}

//Tests if a
int solution(int n, int step)
{
    return (step == n);
}


/* The principal function that calculates all the partitions of a set
   For solving the problem, this algorithm uses the combination algorithm.
   At every incrementation of the stack, we get a max < current_step and we'll
   use it to see how many numbers are in a block (Ex: {{1,1,1},{2,2}} will be
   {{1,2,3},{4,5}}).

   input  : step - an integer represanting the current step in finding all blocks
                   for the respective partition
   output : if the solution is correct ( meaning the union of all elements from partion
            is equal to P), so we'll print the respective partition
*/
void bkt(int step, int n)
{
    int vMax = valMax(step);
    for(int i=1; i<=vMax+1; i++)
    {
        my_stack[step] = i;
        if(solution(n, step))
        {
            print(n);
            count_partitions++;
        }
        else
            bkt(step+1,n);
    }
}
//main function
int main()
{
    int n;
    readFromKeyboard(n);
    bkt(1, n);
    g<<str<<'\n';
    g<<"Processed finished! Number of partitions found: "<<count_partitions<<'\n';
    g<<str;

    return 0;
}
