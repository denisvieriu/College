#ifndef PRODUCER_CONSUMER_QUEUE_H
#define PRODUCER_CONSUMER_QUEUE_H

#include "pch.h"

template <class T>
class ProducerConsumerQueue
{
private:
    std::mutex qM;
    std::queue<T> stlQueue;
    std::condition_variable cv;

public:
      // Add an element to the queue.
    void enqueue(T t)
    {
        std::lock_guard<std::mutex> qLock(this->qM);
        this->stlQueue.push(t);
        this->cv.notify_one();
    }

    T dequeue()
    {
        std::unique_lock<std::mutex> qLock(qM);
        while (this->stlQueue.empty())
        {
            // wait for an enqueue.
            this->cv.wait(qLock);
        }

        T val = this->stlQueue.front();
        this->stlQueue.pop();

        return val;
    }
};

#endif // PRODUCER_CONSUMER_QUEUE_H