[bits 32]
%include "msdn_defs.inc"
section .text 

extern  _printf
extern _strcmp
extern _exit
extern FindFirstFileA, FindNextFileA
extern GetLastError

struc FILETIME
.dwLowDateTime resd 1
.dwHighDateTime resd 1
endstruc

struc WIN32_FIND_DATA
.dwFileAttributes 	resd 1
.ftCreationTime	  	resb FILETIME_size
.ftLastAccessTime	resb FILETIME_size
.ftLastWriteTime	resb FILETIME_size
.nFileSizeHigh 		resd 1
.nFileSizeLow		resd 1
.dwReserved0		resd 1
.dwReserved1		resd 1
.cFileName 			resb 260
.cAlternateFileName resb 14
endstruc

global  _main 

; void printFile(WIN32_FIND_DATA * FindData)
; TODO: implement a function that receives as parameter the address of a WIN32_FIND_DATA structure
;  and displays the following:
; 	if the WIN32_FIND_DATA structure indicates a file, display its name preceded by “- “ 
;   if the structure indicates a directory, display its name precede by: “D “. 
;   The “.” and “..” (current/parent directory) hard links should not be part of the output.

printFile:
	push    ebp
    mov     ebp, esp
    
	; TODO: obtain the file attributes from the WIN32_FIND_DATA structure given as parameter
	; store them in the fileAttributes variable	
	mov ebx,[ebp+8]
	movzx edx, BYTE[EBX+WIN32_FIND_DATA.dwFileAttributes]  ; mov the struct content into eds
	mov [fileAttributes],edx
	
	; TODO: obtain the file name from the WIN32_FIND_DATA structure given as parameter
	; store it in the fileNameAddr variable
	
	add ebx,WIN32_FIND_DATA.cFileName ;ebx pointeaza catre fisier acum
	mov [fileNameAddr],ebx
	
	movzx edx,BYTE [fileAttributes]
	test edx, 0x10 ; != NULL -> Director ; else fisier
	jnz .directoryPrint
	push DWORD [fileNameAddr]
	push DWORD filetemplate
	call _printf
	add esp,4*2
	; Exclude . and ..
	; TODO: _strcmp(currentDir, fileName)
	;       _strcmp(parentDir, fileName	
	
	
	
	; TODO: check if the current file is a directory
	; hint: compare the fileAttributes with 0x10 ( = FILE_ATTRIBUTE_DIRECTORY) - use test instruction
		
	; TODO: display the file name using filetemplate variable as format
	;jmp 	.endPrintFile
	
	jmp 	.exitPrintFile
	
.directoryPrint
	; TODO: display the directory name using the dirtemplate variable as format
	push DWORD [fileNameAddr]
	push DWORD dirtemplate
	call _printf
	add esp,8

.exitPrintFile
	pop 	ebp
	ret		0x4
	
	
_main:
	; TODO
	; HANDLE FindFirstFileA(
	;	_in_  folderPath ; adresa unei variabile cu calea folder-ului pe care il vom parcurge
	;   _out_ fileData   ; adresa unei structuri de tip LPWIN32_FIND_DATA
	; )
	
	push DWORD fileData ; adr of the struct
	push DWORD folderPath
	
	call FindFirstFileA
	
	; Check return value - in case of error, FindFirstFileA returns 0
	cmp 	eax, 0
	jz  	.error_condition

	; TODO: save the search handle in a variable defined in the data section (handle)
	mov [handle],eax
	
.next_file:
	push 	DWORD fileData
	call 	printFile
	
	; TODO
	; BOOL FindNextFileA(
	;  _in_  HANDLE hFindFile ; handle returned by FindFirstFileA
	;  _out_ fileData   	  ; adresa unei structuri de tip LPWIN32_FIND_DATA
	; )
	push DWORD fileData
	push DWORD [handle]
	call FindNextFile
	; Check the return value - in case of error, FindNextFileA returns 0
	cmp 	eax, 0
	jne 	.next_file
	
	
.error_condition:
	; error scenarios are handled here
	
	call 	GetLastError	; obtain the code for the last erro
	
	cmp 	eax, 0x12 ; When all the files in a folder are parsed, FindNextFileA returns ERROR_NO_MORE_FILES
					  ; we don't print error for that, everything went fine
	je 		.function_exit_success
	
	; display the error message
	mov 	ebx, eax
	push 	eax
	push 	DWORD error_tmpl
	call 	_printf	; print error status
	add 	esp, 8
	
	mov 	eax, ebx ; saved error value
	jmp 	.function_exit
	
.function_exit_success:
	mov 	eax, 0
	
.function_exit:
	push    eax
	call    _exit
	ret 

section .data

folderPath:   		db      'c:\\windows\\system32\\*',0 
filetemplate: 	db		'- %s',0xd,0xa,0
dirtemplate: 	db 		'D %s',0xd,0xa,0
error_tmpl: 	db 		'Function failed with status: 0x%X',0
fileData: 		times 	WIN32_FIND_DATA_size db 0
handle: 		dd 		0
current_dir		db 		'.',0
parent_dir 		db 		'..',0
fileAttributes 	db 		0
fileNameAddr  	dd 		0