#include "process_image.h"

static
void
ShowHelp()
{
	printf("[HELP]: Blur1.exe {image}\n");
	printf("[HELP]: {image} - path to the image\n");
}

void
Usage(
	_In_ int Argc,
	_In_ char **Argv,
	_Out_ std::string& FileName
)
{
	if (Argc != 2)
	{
		ShowHelp();
		exit(1);
	}

	FileName = Argv[1];
}