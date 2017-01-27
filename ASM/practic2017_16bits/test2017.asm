;Vieriu Denis-Gabriel
;Problem : counting the number of words, also works for more than 1 delimitator between words

assume ds:data, cs:code
data segment
	string db "  ana    are  ,   14    mere   ,   si    7    pere   .  Maria    are    9  portocale     .  si multe alte mere. In total sunt      doar   ,  22   de    cuvinte."
	len EQU $-string
	words db ?
	letter db 0
	zece dw 10
data ends

code segment
start:
	mov ax,data					;start of the program
	mov ds,ax
	
	mov si,0					;get the offset of the actual string, in our case it's the same with lea si,string  (it's at the beginning of ds)
	my_while:					;main loop which counts the number of words
		cmp string[si],','		;compare if it's a separator
			je isword			
		cmp string[si],'.'		;compare if it's a separator
			je isword
		cmp string[si],' '		;compare if it's a separator
			je isword
		cmp string[si],';'		;compare if it's a separator
			je isword
		cmp si,len		    	;compare if it's the end of the string
			je exit
		
		inc letter 				;check if we've got letters before the actual separator
		jmp pass				;we didn't find any separators
		
		isword:					;actual function that tests if the previous number is a words
			cmp letter,0		;compare if the previous character was not a separator, so we can increment the number of words
			je pass				;if it was a separator then we don't count the word.
			increment_words:	;useless label(it's here for better understanding)
				inc words		;increment the number of words
				mov letter,0	;we counted the actual word, so the number of letters in the new word starts from 0.
				
		pass:
			inc si
			
		
		jmp my_while
		
	exit:
	mov ah,0
	mov al,words
	mov cx,0			;using cx to determine the number of digits in ax(number of words)
	
	
	put_on_stack:		;we put the digit on stack,to print them later in the correct order
		mov dx,0
		div zece		;the rest is kept in dx
		push dx			;put dx on stack
		inc cx		
		cmp ax,0		;while the quotient!=0 we put on stack
		jne put_on_stack
	
	print_from_stack:		
		pop dx
		add dx,'0'		;adding a '0' to make the actual character an '0'-'9' ascii character
		mov ah,02h		;print it
		int 21h		
		loop print_from_stack
		
		
	
	mov ax,4c00h
	int 21h
code ends
end start