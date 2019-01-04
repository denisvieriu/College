
#include "cuda_runtime.h"
#include "device_launch_parameters.h"

#include <iostream>
#include <stdio.h>

// Must be a power of 2
#define POLYNOM_SIZE 4

#define FINAL_POLYNOM_SIZE (POLYNOM_SIZE * 2 - 1)

typedef unsigned long long int  uint64_t;

static
void
PrintPoly(
	_In_ const uint64_t* Pol
)
{
	size_t n = POLYNOM_SIZE * 2 - 1;

	for (size_t i = 0; i < n; i++)
	{
		if (Pol[i] == 0) continue;

		std::cout << Pol[i];
		if (i != 0)
			std::cout << "x^" << i;

		if (i != n - 1 && Pol[i + 1] != 0)
			std::cout << " + ";
	}

	std::cout << std::endl;
}

void
GenerateRandomNums(uint64_t* a, uint64_t* b)
{
	a[0] = 5;
	a[1] = 0;
	a[2] = 10;
	a[3] = 6;

	b[0] = 1;
	b[1] = 2;
	b[2] = 4;
	b[3] = 3;
}

__global__
void
CudaNaiveMultiplication(
	_In_ uint64_t* A,
	_In_ uint64_t* B,
	_In_ uint64_t* Prod,
	_In_ int End)
{
	int coefficientToStart = blockDim.x * blockIdx.x + threadIdx.x;
	int stride = gridDim.x * blockDim.x;

	for (int i = coefficientToStart; i < End; i += stride)
	{
		// eg for coefficient 3, we would have (a[0] * b[3]), (a[1] * b[2]), (a[2] * B[1]), (a[3] * b[0]))

		for (int coeffInA = 0; coeffInA <= i; ++coeffInA)
		{
			int coeffInB = i - coeffInA;
			Prod[i] += (A[coeffInA] * B[coeffInB]);
		}
	}
}

void
CudaPolynomMultiplication(
	_In_ uint64_t* A,
	_In_ uint64_t* B,
	_In_ uint64_t* Prod
	)
{
	printf("Cuda naive algorithm polynom multiplication\n");

	int noThreadsPerBlock = 32;
	int totalBlocks = ((POLYNOM_SIZE * 2 - 1) + noThreadsPerBlock - 1) / noThreadsPerBlock;
	CudaNaiveMultiplication <<<1, 32>>> (A, B, Prod, POLYNOM_SIZE * 2 - 1);
	cudaDeviceSynchronize();

	PrintPoly(Prod);
}

void AllocateBuffers(
	_In_ uint64_t* &A,
	_In_ uint64_t* &B,
	_In_ uint64_t* &Prod
	)
{
	int polSize;
	cudaError_t cudaError;

	polSize = POLYNOM_SIZE * 2 - 1;
	cudaError = cudaMallocManaged(&A, polSize * sizeof(uint64_t));
	if (cudaError != cudaSuccess)
	{
		printf("[ERROR] Couldn not allocate buffer. Aborting\n");
		return;
	}
	cudaMemset(A, 0, polSize * sizeof(uint64_t));

	cudaError = cudaMallocManaged(&B, polSize * sizeof(uint64_t));
	if (cudaError != cudaSuccess)
	{
		printf("[ERROR] Couldn not allocate buffer. Aborting\n");
		cudaFree(A);
		return;
	}
	cudaMemset(B, 0, polSize * sizeof(uint64_t));

	cudaError = cudaMallocManaged(&Prod, polSize * sizeof(uint64_t));
	if (cudaError != cudaSuccess)
	{
		printf("[ERROR] Couldn not allocate buffer. Aborting\n");
		cudaFree(A);
		cudaFree(B);
		return;
	}
	cudaMemset(Prod, 0, polSize * sizeof(uint64_t));
}

void
FreeBuffers(
	_In_ uint64_t* &A,
	_In_ uint64_t* &B,
	_In_ uint64_t* &Prod
	)
{
	cudaFree(A);
	cudaFree(B);
	cudaFree(Prod);
}


__device__
void
_Karatsuba(
	_In_ const vector<int> &A,
	_In_ const vector<int> &B,
	_Out_ vector<int> &C
)
{
	if (A.size() == 1 && B.size() == 1)
	{
		C[0] = A[0] * B[0];
		return;
	}

	size_t half = A.size() / 2;

	vector<int> A0(A.begin(), A.begin() + half);    // A0
	vector<int> A1(A.begin() + half, A.end());      // A1

	vector<int> B0(B.begin(), B.begin() + half);    // B0
	vector<int> B1(B.begin() + half, B.end());      // B1

	vector<int> C1(A0.size() + B0.size() - 1);      // C1
	vector<int> C2(A1.size() + B1.size() - 1);      // C2

	_Karatsuba(A0, B0, C1);     // C1 = A0 * B0 - by recursive call
	_Karatsuba(A1, B1, C2);     // C2 = A1 * B1 - by recursive call

	for (size_t i = 0; i < C1.size(); ++i)
	{
		C[i] += C1[i];                        // C = C1;
	}

	for (size_t i = 0; i < A1.size(); ++i)
	{
		A0[i] += A1[i];                       // C3 = A0 + A1
		B0[i] += B1[i];                       // C4 = B0 + B1
	}
	vector<int> C5(A0.size() + B0.size() - 1);
	_Karatsuba(A0, B0, C5);     // C5 = C3 * C4 - by recursive call

	for (size_t i = 0; i < C5.size(); ++i)          // At this point C = C1;
	{
		C[i + half] += C5[i] - C1[i] - C2[i];     // C += C6; => C = C1 + C6; where C6 = C5 - C1 - C2
	}

	for (size_t i = 0; i < C2.size(); ++i)          // At this point C = C1 + C6*X^(n/2)
	{
		C[i + 2 * half] += C2[i];                   // C = C1 + C6*X^(n/2) + C2*X^n
	}
}

__global__
void
CudaKaratsuba(
	_In_ uint64_t* A,
	_In_ uint64_t* B,
	_In_ uint64_t* Prod
	)
{
	int threadId = blockDim.x * blockIdx.x + threadIdx.x;
	int stride = gridDim.x * blockDim.x;

	for (int i = threadId; i < FINAL_POLYNOM_SIZE; i += stride)
	{
		unsigned char* temp;
		cudaMallocManaged(&temp, FINAL_POLYNOM_SIZE * sizeof(uint64_t));
		cudaMemset(temp, 0, FINAL_POLYNOM_SIZE * sizeof(uint64_t));
		temp[i] = A[i];


	}
}

void
CudaPolynomMultiplicationKaratsuba(
	_In_ uint64_t* &A,
	_In_ uint64_t* &B,
	_In_ uint64_t* &Prod
	)
{
}


int main(int argc, char** argv)
{
	uint64_t *a, *b, *result;
	
	AllocateBuffers(a, b, result);
	GenerateRandomNums(a, b);

	CudaPolynomMultiplication(a, b, result);

	cudaMemset(result, 0, FINAL_POLYNOM_SIZE * sizeof(uint64_t));

	CudaPolynomMultiplicationKaratsuba(a, b, result);

	FreeBuffers(a, b, result);
	return 0;
}