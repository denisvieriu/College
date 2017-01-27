;Problema 4 Vieriu Denis Gabriel - grupa 915/2
;4. Generator de numere aleatoare
;Sa se scrie un generator de numere pseudoaleatoare. Algoritmul poate fi exprimat astfel: se porneste de la o valoare,
;numita "seed", initializata in functie de ora curenta. Mai departe, din valoarea curenta pentru "seed", se va calcula valoarea:
;    rand = seed[8..23]      ; extrag bitii 8-23 (16 biti)
;    seed = rand^2 + K       ; K - constanta pe 32 biti

[bits 32]

section .text 

extern  _printf
extern _exit
extern __time32
extern _getchar
global  _main 

_main: 

		push 0
		call __time32
		add esp,4
	
		push eax	   ; remember eax because after the call will take place it will modify
		call printProc ; prints the generated seed
	
		
		
		push DWORD LinieNoua ; jumps to a new line
		call _printf		 ; ___________________
		add esp,4
		
		pop eax			; get the correct eax (correct generated number)
		call isolateBits

		
		push eax		; save the value
		call printProc2
		
		
		pop eax		; get the original value
		mul eax		; seed = seed ^ 2
		mov DWORD[ebx], eax
		
		push ebx
		push DWORD LinieNoua ; jumps to a new line
		call _printf		 ; ___________________
		add esp,4
		
		pop ebx
		
		repeatt:
			push ebx
			
			call _getchar
			cmp eax,-1
			je exitt
			
			pop ebx
			add eax,ebx
			push eax
			push DWORD format
			call _printf
			add esp,8
			
			push DWORD LinieNoua ; jumps to a new line
			call _printf		 ; ___________________
			add esp,4
			
			jmp repeatt
			

		jmp exitt
		exitt:
			push 0
			call  _exit
			ret 
		
		printProc:
		push eax
		push DWORD format2
		call _printf
		add esp,4*2
		ret 
		
		printProc2:
		push eax
		push DWORD format3
		call _printf
		add esp,4*2
		ret 
		
		
		
		isolateBits:
		and eax, 00000000111111111111111100000000b		; isolate the bits
		shr eax,8										; move them on first position
		ret
		
		
		

section .data
LinieNoua dd 10,13,0
format dd "%d",0
format2 dd "The generated seed is: %i",0
format3 dd "The seed after the bits [8 - 23] were extracted and moved to first position is: %i",0
format4 dd "Enter a character: ",0

nr dd 0