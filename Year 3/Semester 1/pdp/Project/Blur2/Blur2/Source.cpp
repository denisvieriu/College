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
    Image = cv::imread(FileName, cv::IMREAD_COLOR);
    if (!Image.data) {
        printf("Failed to read image\n");
        exit(2);
    }
}

int kernel[3][3] = { 1, 2, 1,
				   2, 4, 2,
				   1, 2, 1 };

int
AccessPixel(
	unsigned char * arr,
	int col,
	int row,
	int k,
	int width,
	int height
)
{
	int sum = 0;
	int sumKernel = 0;

	for (int j = -1; j <= 1; j++)
	{
		for (int i = -1; i <= 1; i++)
		{
			if ((row + j) >= 0 && (row + j) < height && (col + i) >= 0 && (col + i) < width)
			{
				int color = arr[(row + j) * 3 * width + (col + i) * 3 + k];
				sum += color * kernel[i + 1][j + 1];
				sumKernel += kernel[i + 1][j + 1];
			}
		}
	}

	return sum / sumKernel;
}

void
_MpiPixelWorker(
	_In_ unsigned char* Img,
	_Out_ unsigned char* Res,
	_In_ int Width,
	_In_ int Height,
	_In_ int StartIdx,
	_In_ int EndIdx
)
{
	for (int row = StartIdx; row < EndIdx; row++)
	{
		for (int col = 0; col < Width; col++)
		{
			for (int k = 0; k < 3; k++)
			{
				Res[3 * row * Width + 3 * col + k] = AccessPixel(Img, col, row, k, Width, Height);
			}
		}
	}
}

void
BlurImageGaussian2D(
	_In_ const cv::Mat3b& MatHeader,
	_In_ int StartRow,
	_In_ int EndRow,
	_In_ cv::Mat3b& Out,
	_In_ int TotalRows,
	_In_ int TotalColumns
)
{
	_MpiPixelWorker(MatHeader.data, Out.data, TotalColumns, TotalRows, StartRow, EndRow);
}

void
SendAndStartWork(
    _In_ cv::Mat3b& Image,
    _Inout_ cv::Mat3b& Out,
    _In_ int TotalProcs
    )
{
	MPI_Status mpiStatus;
    int imgRows = Image.rows;
    int imgCols = Image.cols;

	cout << "[0] - Initial image rows: " << imgRows << endl;
	cout << "[0] - Initial image columns: " << imgCols << endl;
    for (int i = 1; i < TotalProcs; i++)
    {
        int startRow = i * imgRows / TotalProcs;
        int endRow = (i + 1) * imgRows / TotalProcs;

        int endRowFix = min(endRow, imgRows);

        int noRowsToSend = endRowFix - startRow;

		//cout << "i: " << i << " " << ", start" << noRowsToSend << endl;
		cout << "[0] - Send i: " << i << " " << ", imgCols: " << imgCols<< endl;

        MPI_Send(&startRow, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
        MPI_Send(&endRowFix, 1, MPI_INT, i, 1, MPI_COMM_WORLD);
		MPI_Send(&imgRows, 1, MPI_INT, i, 2, MPI_COMM_WORLD);
        MPI_Send(&imgCols, 1, MPI_INT, i, 3, MPI_COMM_WORLD);
        //MPI_Send(Image.rowRange(startRow, endRow).data, noRowsToSend * imgCols * 3, MPI_BYTE, i, 4, MPI_COMM_WORLD);
        MPI_Send(Image.data, imgRows * imgCols * 3, MPI_BYTE, i, 4, MPI_COMM_WORLD);
    }

	BlurImageGaussian2D(Image, 0, imgRows / TotalProcs, Out, imgRows, imgCols);

	for (int i = 1; i < TotalProcs; i++)
	{
		cv::Mat3b temp(imgRows, imgCols);
		MPI_Recv(temp.data, imgRows * imgCols * 3, MPI_BYTE, i, 5, MPI_COMM_WORLD, &mpiStatus);

		for (int ii = 0; ii < imgRows; ii++)
		{
			for (int jj = 0; jj < imgCols; jj++)
			{
				Vec3b px = temp.at<Vec3b>(ii, jj);
				Out.at<Vec3b>(ii, jj) += px;
			}
		}
	}

	cout << "[0] - Received result from mpi pixel workers" << endl;
}





void
PixelWorker(
	_In_ int Rank
	)
{
    int rows, cols;
    int startRow, endRow;
    MPI_Status mpiStatus;

    printf("[%d] - Worker started", Rank);
	cout << endl;

	// Receive startRow & endRow
    MPI_Recv(&startRow, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &mpiStatus);
    MPI_Recv(&endRow, 1, MPI_INT, 0, 1, MPI_COMM_WORLD, &mpiStatus);
	 
	// Receive toalRows & totalCols
    MPI_Recv(&rows, 1, MPI_INT, 0, 2, MPI_COMM_WORLD, &mpiStatus);
    MPI_Recv(&cols, 1, MPI_INT, 0, 3, MPI_COMM_WORLD, &mpiStatus);

    printf("[%d] - Received totalRows: %d, totalCols: %d\n", Rank, rows, cols);
    printf("[%d] - Received startRow: %d, endRow: %d", Rank, startRow, endRow);
	cout << endl;

	int noRowstoReceive = endRow - startRow;
	// Create a copy matrix
    cv::Mat3b matHeader(rows, cols, CV_8UC3);
	cv::Mat3b out(rows, cols);

    MPI_Recv(matHeader.data, rows * cols * 3, MPI_BYTE, 0, 4, MPI_COMM_WORLD, &mpiStatus);
	BlurImageGaussian2D(matHeader, startRow, endRow, out, rows, cols);

	printf("[%d] - Finished to compute blur for rows [%d] - [%d]\n", Rank, startRow, endRow);
	printf("[%d] - Sending result to master", Rank);
	cout << endl;

	// Send result to Master
	MPI_Send(out.data, rows * cols * 3, MPI_BYTE, 0, 5, MPI_COMM_WORLD);


}

int main(int argc, char* argv[])
{
    MPI_Init(NULL, NULL);
    int me;
    int totalProcs;
    MPI_Comm_rank(MPI_COMM_WORLD, &me);
    MPI_Comm_size(MPI_COMM_WORLD, &totalProcs);

    if (me == MASTER_THREAD)
    {
        cout << "[0] - Master thread started!" << endl;
        string fileName;
        cv::Mat3b image;

        auto start = std::chrono::system_clock::now();
        Usage(argc, argv, fileName);

        cout << "[0] - Loading image in memory..." << endl;
        LoadImage(fileName, image);
        cout << "[0] - Image " << fileName << " loaded successfully!" << endl;

        cv::Mat3b out(image.rows, image.cols);

        SendAndStartWork(image, out, totalProcs);

        auto end = std::chrono::system_clock::now();
        auto elapsed =
            std::chrono::duration_cast<std::chrono::milliseconds>(end - start);

        std::cout << "[0] - Total run time in milliseconds: " << elapsed.count() << endl;


		cv::imshow("result", out);
		cv::imshow("initial", image);
		
		cout << "[0] - Waiting for a key to exit..." << endl;
		cv::waitKey(0);
    }
    else
    {
        PixelWorker(me);
    }

    MPI_Finalize();
    return 0;
}