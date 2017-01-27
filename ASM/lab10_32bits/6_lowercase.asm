%include "msdn_defs.inc" ; microsoft developer framework

[bits 32]

section .text 

%define BYTES_TO_READ						1024

extern  _printf
extern _exit

extern CreateFileA, ReadFile, WriteFile, SetFilePointer, CloseHandle
extern GetLastError

global  _main 

_main: 
		;HANDLE CreateFileA(
		;	FileName,
		;	GENERIC_READ | GENERIC_WRITE,
		;	0,
		;	NULL,
		;	OPEN_EXISTING,
		;	FILE_ATTRIBUTE_NORMAL,
		;	NULL)
        
		
		push DWORD NULL ; push dword 0 
		push DWORD FILE_ATTRIBUTE_NORMAL
		push DWORD OPEN_EXISTING
		push DWORD NULL
		push DWORD 0
		push DWORD GENERIC_READ | GENERIC_WRITE
		push DWORD FileName
		
		call CreateFileA		;call createFile
		
        ; in case of failure, CreateFileA returns INVALID_HANDLE_VALUE
		cmp		eax, INVALID_HANDLE_VALUE
		je		.error
		
		mov		[hFile], eax  ; save handle in variable
		
		;BOOL ReadFile(
			;	Handle, ; returned by createfile
			;	Buffer  ; variabila definita unde se salveaza ce am citit din fisier
			;	noOfBytesToRead ; dimensiunea sirului
			;	noOfBytesRead ; adresa unei variabile in care se va completa cate caractere a citit
			; NULL)
		
		
		push DWORD NULL
		push DWORD bytesRead		; push the address where the bytesRead will be stored
		push DWORD BYTES_TO_READ	; push the BYTES_TO_READ (already defined, constant)
		push DWORD buf				; push the address of the string
		push DWORD eax				; push the handler
		
		
		call ReadFile 				; call ReadFile
        ; in case of failure, ReadFile returns 0
		cmp		eax, 0
		je		.finish
		
		push DWORD buf
		call _printf
		add esp,4
		
		push DWORD NewLine
		call _printf
		add esp,4
		
		;BOOL WriteFile(
		;	Handle ; returned by create file, value!
		;	Buffer ; variabila definita care se va scrie in fisier
		;	noOfBytesToWrite ; dimensiunea sirului - cat se va scrie in fisier
		;	noOfBytesWritten ; adresa unei variabile in care se va completa cate caractere a scris
		;	NULL)
        
		
		push DWORD NULL
		push DWORD bytesWritten ; push the adress of bytesWritten ( pointer where we'll store how many elements were written )
		push DWORD [bytesRead]  ; get the value of bytes read (not the adress)
		push DWORD buf			; the actual string to write
		push DWORD [hFile]		; the handler for file
		
		call WriteFile
		
        ; in case of failure, WriteFile returns 0
		cmp eax,0
		jz		.error
		
		; BOOL CloseHandle(
		;    handle) ; returned by CreateFile
		
		push DWORD [hFile]
		call CloseHandle
        
		jmp .success
	.error:
		
        call	GetLastError  ; obtain error code for last operation
        
		push	eax           ; eax = error code - see "msdn system error codes" for details
		push	fail_str
		call	_printf
        add     esp, 4*2
        
        jmp .finish
        
    .success:
        push    DWORD success_str
        call    _printf
        
	.finish:

        push    0
        call    _exit
        ret 

section .data

hFile:			dd		0
buf:			resb	BYTES_TO_READ
bytesRead:		dd		0
bytesWritten:	dd		0
FileName:		db		'exemplu_lung.txt', 0
fail_str:		db		'Failed with error code %d', 0xA, 0
success_str:    db      'Execution successfull!',0xA, 0
format: 		dd  	'%s',0
NewLine			dd 		10,13,0