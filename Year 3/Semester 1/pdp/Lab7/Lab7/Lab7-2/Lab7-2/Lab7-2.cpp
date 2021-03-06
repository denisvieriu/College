#include "pch.h"
#include <iostream>
#include <fstream>
#include "producer_consumer_queue.h"

using namespace std;

// Default element type hold in queue
#define DEFAULT_QUEUE_DATA_TYPE          int8_t

// Default ending character for a number in queue
#define DEFAULT_ENDING_CHARACTER         '\0'

// Default data type for an element representing a number
typedef string DEFAULT_NUMBER_DATA_TYPE;

// Default container to hold all numbers
typedef vector<DEFAULT_NUMBER_DATA_TYPE> BIG_NUMBER_VECTOR;

// Default predefined list of numbers
BIG_NUMBER_VECTOR gBigNumbers = { "123", "10", "30", "20" };

static
void
DivideAndConquer(
    _In_  int Start,
    _In_  int End,
    _Out_ ProducerConsumerQueue<DEFAULT_QUEUE_DATA_TYPE> &q
    );

static
void
ReverseNumber(
    _Inout_ string& Number
    );

static
void
StartConsumer(
    _Inout_ ProducerConsumerQueue<DEFAULT_QUEUE_DATA_TYPE> &LeftQueue,
    _Inout_ ProducerConsumerQueue<DEFAULT_QUEUE_DATA_TYPE> &RightQueue,
    _Inout_ ProducerConsumerQueue<DEFAULT_QUEUE_DATA_TYPE> &ResultQueue
    );

static
void
PrintSolution(
    _In_ ProducerConsumerQueue<DEFAULT_QUEUE_DATA_TYPE> &ProducerConsumerQueue
    );

int main() {
    //
    // Create a safe queue where we we'll store the final result
    //
    ProducerConsumerQueue<DEFAULT_QUEUE_DATA_TYPE> finalResultQueue;

    //
    // The count of the numbers we have to add
    //
    int totalNumbers = (int)gBigNumbers.size();

    //
    // Reverses each number from the list
    //
    for (int i = 0; i < totalNumbers; i++)
    {
        ReverseNumber(gBigNumbers[i]);
    }

    auto start = std::chrono::system_clock::now();

    //
    // Main algorithm, uses divide et impera to split the work
    //
    DivideAndConquer(0, totalNumbers - 1, finalResultQueue);
    PrintSolution(finalResultQueue);

    auto end = std::chrono::system_clock::now();

    auto elapsed =
        std::chrono::duration_cast<std::chrono::milliseconds>(end - start);

    cout << "Total duration for the algorithm to add " << totalNumbers << " numbers took " << elapsed.count() << " milliseconds." << endl;
}

static
void
ReverseNumber(
    _Inout_ string& Number
    )
{
    reverse(Number.begin(), Number.end());
}

static
void
_AddNumberToQueue(
    _In_  DEFAULT_NUMBER_DATA_TYPE            &Number,
    _In_  char                                EndingCharacter,
    _Out_ ProducerConsumerQueue<DEFAULT_QUEUE_DATA_TYPE>  &ProducerConsumerQueue
    );


static
void
DivideAndConquer(
    _In_  int Start,
    _In_  int End,
    _Out_ ProducerConsumerQueue<DEFAULT_QUEUE_DATA_TYPE> &ResultQueue
    )
{
    if (Start == End)
    {
        _AddNumberToQueue(gBigNumbers[End], DEFAULT_ENDING_CHARACTER, ResultQueue);
        return;
    }

    int mid = (Start + End) / 2;
    ProducerConsumerQueue<DEFAULT_QUEUE_DATA_TYPE> leftQueueProducer;
    ProducerConsumerQueue<DEFAULT_QUEUE_DATA_TYPE> rightQueueProducer;

    std::thread t1(DivideAndConquer, Start, mid, std::ref(leftQueueProducer));
    std::thread t2(DivideAndConquer, mid + 1, End, std::ref(rightQueueProducer));

    StartConsumer(leftQueueProducer, rightQueueProducer, ResultQueue);

    t1.join();
    t2.join();
}

static
void
StartConsumer(
    _Inout_ ProducerConsumerQueue<DEFAULT_QUEUE_DATA_TYPE> &LeftQueue,
    _Inout_ ProducerConsumerQueue<DEFAULT_QUEUE_DATA_TYPE> &RightQueue,
    _Inout_ ProducerConsumerQueue<DEFAULT_QUEUE_DATA_TYPE> &ResultQueue
    )
{

    bool leftNumberConsumed  = false;
    bool rightNumberConsumed = false;

    DEFAULT_QUEUE_DATA_TYPE carry = 0;

    while (true) {
        DEFAULT_QUEUE_DATA_TYPE res = carry;

        //
        // Set carry flag = 0
        //
        carry = 0;

        //
        // Extract one digit from the left number
        // Waits if there should be more digits to get, but the main thread adding to this queue didn't finish yet
        // Stops when an ending character has been reached (Default '/0')
        //
        if (!leftNumberConsumed)
        {
            char digitLeftNumber = LeftQueue.dequeue();
            if (digitLeftNumber == DEFAULT_ENDING_CHARACTER)
            {
                leftNumberConsumed = true;
            }
            else
            {
                res += ASCII_TO_NUMBER(digitLeftNumber);
            }
        }

        //
        // Extract one digit from the right number
        // Waits if there should be more digits to get, but the main thread adding to this queue didn't finish yet
        // Stops when an ending character has been reached (Default '/0')
        //
        if (!rightNumberConsumed) {
            char e2 = RightQueue.dequeue();
            if (e2 == DEFAULT_ENDING_CHARACTER)
            {
                rightNumberConsumed = true;
            }
            else {
                res += ASCII_TO_NUMBER(e2);
            }
        }

        //
        // If both numbers were consumed, add the carry to result queue if there is any
        //
        if (leftNumberConsumed && rightNumberConsumed) {
            if (res > 0)
            {
                ResultQueue.enqueue(NUMBER_TO_ASCII(res));
            }

            break;
        }

        if (res > 9)
        {
            res -= 10;
            carry = 1;
        }

        ResultQueue.enqueue(NUMBER_TO_ASCII(res));
    }
    ResultQueue.enqueue(DEFAULT_ENDING_CHARACTER);
}

static
void
PrintSolution(
    _In_ ProducerConsumerQueue<DEFAULT_QUEUE_DATA_TYPE> &ProducerConsumerQueue
    )
{
    string result = "";
    char elem = ProducerConsumerQueue.dequeue();
    while (elem) {
        result += elem;
        elem = ProducerConsumerQueue.dequeue();
    }

    ReverseNumber(result);

    cout << "Solution found: " << result << endl;
}

static
void
_AddNumberToQueue(
    _In_  DEFAULT_NUMBER_DATA_TYPE            &Number,
    _In_  char                                EndingCharacter,
    _Out_ ProducerConsumerQueue<DEFAULT_QUEUE_DATA_TYPE>  &ProducerConsumerQueue
    )
{
    for (int i = 0; i < Number.size(); i++)
    {
        ProducerConsumerQueue.enqueue(Number[i]);
    }

    ProducerConsumerQueue.enqueue(EndingCharacter);
}
