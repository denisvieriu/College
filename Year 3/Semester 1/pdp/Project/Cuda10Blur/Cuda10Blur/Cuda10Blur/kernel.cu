
#include "cuda_runtime.h"
#include "device_launch_parameters.h"

#include <stdio.h>

#include <iostream>
#include <math.h>
#include <string>

#include "process_image.h"


// Kernel function to add the elements of two arrays
__global__
void add(int n, float *x, float *y)
{
	int index = blockDim.x * blockIdx.x + threadIdx.x;
	int stride = blockDim.x * gridDim.x;

	printf("%d\n", index);
	//std::cout << std::endl;

	for (int i = index; i < n; i += stride)
		y[i] = x[i] + y[i];
}


__device__
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

	int kernel[3][3] = { 1, 2, 1,
				   2, 4, 2,
				   1, 2, 1 };

	int sum = 0;
	int sumKernel = 0;

	for (int j = -1; j <= 1; j++)
	{
		for (int i = -1; i <= 1; i++)
		{
			if ((row + j) >= 0 && (row + j) < height && (col + i) >= 0 && (col + i) < width)
			{
				int color = arr[(row + j) * 3 * width + (col + i) * 3 + k];
				//printf("%d\n", color);
				sum += color * kernel[i + 1][j + 1];
				sumKernel += kernel[i + 1][j + 1];
			}
		}
	}

	return sum / sumKernel;
}

__global__
void CudaPixelWorker(
	_In_ unsigned char* Img,
	_Out_ unsigned char* Res,
	_In_ int Width,
	_In_ int Height
)
{
	// blockDim.x - dimension of a block (256; must me multiple of 32)
	// blockIdx.x - current block index
	// threadIdx.x - current id of the thread in the 1D array (nvidia GRID)

	// gridDim.x - total blocks

	int startIndex = blockDim.x * blockIdx.x + threadIdx.x;
	int stride = blockDim.x * gridDim.x;


	for (int row = startIndex; row < Height; row += stride)
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
	_In_ const std::string& FileName
)
{
	cv::Mat3b img = cv::imread(FileName, cv::IMREAD_COLOR);
	cv::Mat3b out(img.rows, img.cols);

	uchar *buffer;
	uchar *buffer2;

	if (!img.data) {
		printf("Failed to read image\n");
		exit(2);
	}
	cudaError_t cudaError;
	cudaError = cudaMallocManaged(&buffer, img.rows * img.cols * 3 * sizeof(uchar));
	cudaError = cudaMallocManaged(&buffer2, img.rows * img.cols * 3 * sizeof(uchar));

	if (cudaError != cudaSuccess)
	{
		std::cout << "[Error] - Cuda Error!" << std::endl;
	}

	out.data = buffer2;
	cudaMemcpy(buffer, img.data, img.rows * img.cols * 3, cudaMemcpyHostToDevice);
	img.data = buffer;

	int threadsPerBlock = 256;
	int totalBlocks = (img.rows + threadsPerBlock - 1) / threadsPerBlock;
	CudaPixelWorker<<<totalBlocks, threadsPerBlock>>>(img.data, out.data, img.cols, img.rows);
	cudaDeviceSynchronize();
	std::cout << "Copying back out.data to img.data" << std::endl;

	//memcpy(img.data, out.data, img.rows * img.cols * 3);

	std::cout << "Trying to save image to output file" << std::endl;
	cv::imwrite("output.bmp", out);

	cudaFree(buffer);
	cudaFree(buffer2);
}


int main(int argc, char** argv)
{
	std::string fileName;

	std::cout << "Cuda started" << std::endl;
	Usage(argc, argv, fileName);
	std::cout << "Filename: " << fileName << std::endl;

	GaussianBlur2D(fileName);

	//int N = 1 << 20;
	//float *x, *y;

	//// Allocate Unified Memory – accessible from CPU or GPU
	//cudaMallocManaged(&x, N * sizeof(float));
	//cudaMallocManaged(&y, N * sizeof(float));

	//// initialize x and y arrays on the host
	//for (int i = 0; i < N; i++) {
	//	x[i] = 1.0f;
	//	y[i] = 2.0f;
	//}

	//// Run kernel on 1M elements on the GPU

	//int noThreadsPerBlock = 256;
	//int totalBlocks = (N + noThreadsPerBlock - 1) / noThreadsPerBlock;

	//add<<<totalBlocks, noThreadsPerBlock>>> (N, x, y);

	//// Wait for GPU to finish before accessing on host
	//cudaDeviceSynchronize();

	//// Check for errors (all values should be 3.0f)
	//float maxError = 0.0f;
	//for (int i = 0; i < N; i++)
	//	maxError = fmax(maxError, fabs(y[i] - 3.0f));
	//std::cout << "Max error: " << maxError << std::endl;

	//// Free memory
	//cudaFree(x);
	//cudaFree(y);

	return 0;
}