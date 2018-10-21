#include <thread>
#include <iostream>
#include <ctime>
#include <mutex>
#include <chrono>
#include <random>
#include <functional>
#include <vector>
#include <map>

std::mutex m;

typedef struct _PRODUCT_MARKET
{
    std::string     Product;
    uint64_t        Price;
    uint64_t        Quantity;
}PRODUCT_MARKET;

typedef struct _RANDOM_PRODUCT
{
    std::vector<int64_t> randomNumbers;
    std::vector<int64_t> randomQuantities;
}RANDOM_PRODUCT;


PRODUCT_MARKET gProductMarket[] =
{
    // Product          Price           Quantity
    {"Apple",           150,            6432},
    {"Orange",          235,            160},
    {"Mango",           500,            9110},
    {"Cherry",          130,            500},
    {"Lemon",           35,             9533},
    {"Peach",           221,            1102},
    {"Raspberry",       10,             1100},
    {"Pineapple",       641,            7300}
};

PRODUCT_MARKET gProductMarketCopy[] =
{
    // Product          Price           Quantity
    {"Apple",           150,            6432},
    {"Orange",          235,            160},
    {"Mango",           500,            9110},
    {"Cherry",          130,            500},
    {"Lemon",           35,             9533},
    {"Peach",           221,            1102},
    {"Raspberry",       10,             1100},
    {"Pineapple",       641,            7300}
};

#define TOTAL_MARKET_PRODUCTS           _countof(gProductMarket)
#define MAX_PRODUCTS_PER_TRANSACTION    20

#define MIN_NUMBER_THREADS	            24
#define MAX_NUMBER_THREAD	            64

std::mutex mtx;

std::map<uint64_t, std::vector<PRODUCT_MARKET>> gTotalBills;
int64_t gTotalSum = 0;
int64_t gPerformInventoryCheck;

void
PerformInventoryCheck()
{
    std::cout << "Running consistency check\n";
    std::vector<PRODUCT_MARKET> billPerThread;
    int64_t totalPricePaid = 0;
    for (std::map<uint64_t, std::vector<PRODUCT_MARKET>>::iterator it = gTotalBills.begin(); it != gTotalBills.end(); ++it) {
        billPerThread = it->second;

        for (auto &prodcMarket : billPerThread)
        {
            totalPricePaid += prodcMarket.Price;
        }
    }

    int sumPaid = 0;
    for (int i = 0; i < TOTAL_MARKET_PRODUCTS; i++)
    {
        sumPaid += ((gProductMarketCopy[i].Quantity - gProductMarket[i].Quantity) * gProductMarket[i].Price);
    }

    if (totalPricePaid != gTotalSum/* || sumPaid != gTotalSum*/)
    {
        printf("Consistency check failed! Total Price paid: %d, Total Sum: %d\n", totalPricePaid, gTotalSum);
    }
    else
    {
        std::cout << ">>>> Consistency check succeeded <<<<< \n";
    }
}

void StartProductMarket(
    uint16_t                    ThreadyId,
    uint16_t                    NumberOfOperations,
    RANDOM_PRODUCT              RandomProducts
    )
{
    std::unique_lock<std::mutex> lck(mtx, std::defer_lock);
    uint64_t indexPrMark;
    uint64_t totalPrice = 0;
    lck.lock();
    std::cout << "Thread #" << ThreadyId << " running " << NumberOfOperations << " number of operations" << std::endl;
    lck.unlock();

    std::vector<PRODUCT_MARKET> bill;
    PRODUCT_MARKET productMarketBought;

    for (uint32_t i = 0; i < NumberOfOperations; i++)
    {
            // While last chosen product == current chosen product try to get another random
        indexPrMark = RandomProducts.randomNumbers[i] % TOTAL_MARKET_PRODUCTS;

        lck.lock();
        PRODUCT_MARKET productMarket = gProductMarket[indexPrMark];

        // Make sure we don't buy more than the available quantity

        uint64_t quantityToBuy;

        quantityToBuy = (RandomProducts.randomQuantities[i] % (productMarket.Quantity + 1)) % MAX_PRODUCTS_PER_TRANSACTION;

        uint64_t totalPriceToPay = quantityToBuy * productMarket.Price;
        totalPrice += totalPriceToPay;
        productMarket.Quantity -= quantityToBuy;
        gProductMarket[indexPrMark] = productMarket;
        gPerformInventoryCheck++;

        lck.unlock();

        printf("Thread #%d bought %d %ss\n", ThreadyId, quantityToBuy, productMarket.Product.c_str());


        productMarketBought.Price = totalPriceToPay;
        productMarketBought.Product = productMarket.Product;
        productMarketBought.Quantity = quantityToBuy;

        bill.push_back(productMarketBought);

        lck.lock();
        if (gPerformInventoryCheck % 10 == 0)
        {
            PerformInventoryCheck();
        }
        lck.unlock();
    }

    lck.lock();
    gTotalSum += totalPrice;
    lck.unlock();

    gTotalBills[ThreadyId] = bill;
}

RANDOM_PRODUCT
GenerateRandomNumbers(uint16_t NoOfElements)
{
    RANDOM_PRODUCT randomProducts;

    for (int16_t i = 0; i < NoOfElements; i++)\
    {
        randomProducts.randomNumbers.push_back(rand());
        randomProducts.randomQuantities.push_back(rand());
    }

    return randomProducts;
}

int main()
{
    int seed = static_cast<int>(time(0));
    srand(seed);

    std::thread *threadArray;
    uint16_t numberOfThreads;
    numberOfThreads = std::rand() % ((MAX_NUMBER_THREAD - MIN_NUMBER_THREADS) + 1) + MIN_NUMBER_THREADS;
    printf("Choosing %d threads to run\n", numberOfThreads);

    threadArray = new std::thread[numberOfThreads];

    if (threadArray == nullptr)
    {
        return -1;
    }

    printf("Threads successfully allocated on heap\n");

    std::chrono::steady_clock::time_point begin = std::chrono::steady_clock::now();

    for (uint16_t i = 0; i < numberOfThreads; i++)
    {
        uint16_t randOp = std::rand() % TOTAL_MARKET_PRODUCTS;
        RANDOM_PRODUCT randomChosenProducts = GenerateRandomNumbers(randOp);
        threadArray[i] = std::thread(StartProductMarket, i, randOp, randomChosenProducts);
    }

    for (uint16_t i = 0; i < numberOfThreads; i++)
    {
        threadArray[i].join();
    }

    printf("Threads destroyed\n");

    PerformInventoryCheck();
    std::chrono::steady_clock::time_point end = std::chrono::steady_clock::now();

    std::cout << "\nTime difference seconds: " << (std::chrono::duration_cast<std::chrono::milliseconds>(end - begin).count() * 1.0) / 1000 << std::endl;
    std::cout << "Total threads: " << numberOfThreads << std::endl;

    delete [] threadArray;
    return 0;
}