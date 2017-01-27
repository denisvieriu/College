;Problema 3 Vieriu Denis Gabriel - grupa 915/2 
[bits 32]

section .text 

extern  _printf
extern _exit
extern _fopen

global  _main 

_main: 
		mov ecx,len
		mov eax,0
		mov esi,v
		again:
			mov ebx,DWORD [esi]
			cmp ebx,0
			jl NEGATE
			jmp NONEGATE
			NEGATE:
				neg ebx
			NONEGATE:
			add eax,ebx
			add esi,4
			loop again
			
		push DWORD eax
		push DWORD format
		call _printf
		add esp,8
        push    0
        call    _exit
        ret 

section .data

format dd "%d",0
v dd 1,-10,5,2,5,0,-9,17
len EQU ($-v)/4