[bits 32]

section .text 

extern  _printf
extern _exit
extern _scanf

global  _main 

_main: 
		
        push DWORD nr1  ; adr
		push DWORD format
		call _scanf
		add esp,4*2
		
		push DWORD nr2
		push DWORD format
		call _scanf
		add esp,4*2
		
		
		mov eax,[nr1]
		add eax,[nr2]
		
		push DWORD eax
		push DWORD format
		call _printf
		add esp, 4*2
		
		
        push    0
        call    _exit
        ret 

section .data

format dd "%d",0
nr1 dd 0
nr2 dd 0
