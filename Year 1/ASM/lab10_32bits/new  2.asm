[bits 32]

section .text 

extern  _printf
extern _exit
extern _scanf
global  _main 

_main: 
		push DWORD nr1
		push DWORD format
		call _scanf
		add esp,8
		
		push DWORD nr2
		push DWORD format
		call _scanf
		add esp,8
		
		mov eax,[nr1]
		my_while:
			cmp eax,[nr2]
			je my_exit
			cmp eax,[nr2]
			ja eaxsubnr2
			sub [nr2],eax
			jmp my_while
			
			eaxsubnr2:
				sub eax,[nr2]
				jmp my_while
				
	
		
		my_exit:
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