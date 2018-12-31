#include <stdio.h>
#include <opencv2/opencv.hpp>
#include <algorithm>

using namespace cv;
using namespace std;

void ShowHelp()
{
    printf("[HELP]: Blur1.exe {image} {noThreads}\n");
    printf("[HELP]: {image} - path to the image\n");
}

void
Usage(
    _In_ int Argc,
    _In_ char **Argv,
    _Out_ string& FileName,
    _Out_ int& NoThreads
    )
{
    if (Argc != 3)
    {
        ShowHelp();
        exit(1);
    }

    FileName = Argv[1];
    NoThreads = atoi(Argv[2]);
}

int kernel[3][3] = { 1, 2, 1,
                   2, 4, 2,
                   1, 2, 1 };


int
accessPixel(
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
PixelWorker(
    _In_ unsigned char* Img,
    _Out_ unsigned char* Res,
    _In_ int Width,
    _In_ int Height,
    _In_ int StartIdx,
    _In_ int GrowRate
)
{
    for (int row = StartIdx; row < Height; row += GrowRate)
    {
        for (int col = 0; col < Width; col++)
        {
            for (int k = 0; k < 3; k++)
            {
                Res[3 * row * Width + 3 * col + k] = accessPixel(Img, col, row, k, Width, Height);
            }
        }
    }
}

void
GaussianBlur2D(
    _In_ const string& FileName,
    _In_ int NoThreads
    )
{
    cv::Mat3b img = cv::imread(FileName, cv::IMREAD_COLOR);

	uchar *buffer = (uchar*)malloc(img.rows * img.cols * 3);
	memcpy(buffer, img.data, img.rows * img.cols * 3);
    if (!img.data) {
        printf("Failed to read image\n");
        exit(2);
    }

	cv::Mat3b out = img.clone();
	out.data = buffer;


	//for (int i = 0; i < out.rows; i++)
	//{
	//	for (int j = 0; j < out.cols; j++)
	//	{
	//		Vec3b px = out.at<Vec3b>(i, j); 
	//		//int gray = (px[0] + px[1] + px[2]) / 3;
	//		cout << (int)px[0] << " " << (int)px[1] << " " << (int)px[2] << endl;
	//	}
	//}
	//printf("Creating %d threads...\n", NoThreads);
	vector <std::thread> threadV;

	int totalThreads = min(NoThreads, img.rows);

	for (int t = 0; t < totalThreads; t++)
	{
		threadV.emplace_back(PixelWorker, img.data, out.data, img.cols, img.rows, t, NoThreads);
	}

	for (auto & t : threadV) {
		t.join();
	}

    printf("Threads finished work\n");

	cv::imwrite("output.jpg", out);


    cv::imshow("out", out);
    cv::imshow("img", img);
}

int
main(
    _In_ int Argc,
    _In_ char** Argv
    )
{
    string fileName;
    int noThreads;

    auto start = std::chrono::system_clock::now();

    Usage(Argc, Argv, fileName, noThreads);

    printf("Blurring image: %s with %d threads.\n", fileName.c_str(), noThreads);

    GaussianBlur2D(fileName, noThreads);

    auto end = std::chrono::system_clock::now();
    auto elapsed =
        std::chrono::duration_cast<std::chrono::milliseconds>(end - start);
    std::cout << "Total run time in milliseconds: " << elapsed.count() << endl;

    std::cout << "Waiting for a key to exit\n";
    cv::waitKey(0);

    return 0;
}
