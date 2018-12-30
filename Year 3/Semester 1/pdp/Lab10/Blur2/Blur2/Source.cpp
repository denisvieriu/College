#include <mpi.h>
#include <opencv2/opencv.hpp>
#include <string>

using namespace std;
using namespace cv;

#define MASTER_THREAD 0

void ShowHelp()
{
    printf("[HELP]: mpiexec -n {noMpiThreads} Blur2.exe {image}\n");
    printf("[HELP]: {image} - path to the image\n");
}

void
Usage(
    _In_ int Argc,
    _In_ char **Argv,
    _Out_ string& FileName
)
{
    if (Argc != 2)
    {
        ShowHelp();
        exit(1);
    }

    FileName = Argv[1];
}

void 
LoadImage(
    _In_ const string& FileName,
    _Out_ cv::Mat3b& Image
    )
{
    cv::Mat3b img = cv::imread(FileName, cv::IMREAD_COLOR);
    if (!img.data) {
        printf("Failed to read image\n");
        exit(2);
    }
}

void
SendDataToWorkers(
    _In_ cv::Mat3b& Image,
    _Inout_ cv::Mat3b& Out,
    _In_ int TotalProcs
    )
{
    int imgRows = Image.rows;
    int imgCols = Image.cols;
    for (int i = 1; i < TotalProcs; i++)
    {
        int startRow = i * imgRows / TotalProcs;
        int endRow = (i + 1) * imgRows / TotalProcs;

        int endRowFix = min(endRow, imgRows);

        int noRowsToSend = endRow - startRow;
        MPI_Send(&noRowsToSend, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
        MPI_Send(&imgCols, 1, MPI_INT, i, 1, MPI_COMM_WORLD);
        MPI_Send(Image.rowRange(startRow, endRow).data, noRowsToSend * imgCols * 3, MPI_BYTE, i, 2, MPI_COMM_WORLD);

        /*MPI_Send()*/
    }
}

void
PixelWorker(int Rank)
{
    int rows, cols;
    MPI_Status mpiStatus;

    printf("Worker %d started\n", Rank);

    MPI_Recv(&rows, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &mpiStatus);
    MPI_Recv(&cols, 1, MPI_INT, 0, 1, MPI_COMM_WORLD, &mpiStatus);

    printf("Worker %d recieved %d rows, %d cols\n", Rank, rows, cols);

    cv::Mat3b matHeader(rows, cols, CV_8UC3);

    MPI_Recv(&cols, 1, MPI_INT, 0, 2, MPI_COMM_WORLD, &mpiStatus);
    MPI_Recv(matHeader.data, rows * cols * 3, MPI_BYTE, 0, 3, MPI_COMM_WORLD, &mpiStatus);
}

int main(int argc, char* argv[])
{
  
    MPI_Init(0, 0);
    int me;
    int totalProcs;
    cout << "W1\n";
    MPI_Comm_rank(MPI_COMM_WORLD, &me);
    MPI_Comm_size(MPI_COMM_WORLD, &totalProcs);

    if (me == MASTER_THREAD)
    {
        printf("Master thread started!\n");
        string fileName;
        cv::Mat3b image;

        auto start = std::chrono::system_clock::now();
        Usage(argc, argv, fileName);

        printf("Loading image in memory...\n");
        LoadImage(fileName, image);
        printf("Image %s loaded successfully!\n", fileName.c_str());

        cv::Mat3b out = image.clone();

        SendDataToWorkers(image, out, totalProcs);

        std::this_thread::sleep_for(std::chrono::seconds(5));


        auto end = std::chrono::system_clock::now();
        auto elapsed =
            std::chrono::duration_cast<std::chrono::milliseconds>(end - start);

        std::cout << "Total run time in milliseconds: " << elapsed.count() << endl;



    }
    else
    {
        PixelWorker(me);
    }


    MPI_Finalize();
    return 0;
}