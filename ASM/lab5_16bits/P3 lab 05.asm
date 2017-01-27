;3. The words A and B are given. Obtain the word C in the following way:
;- the bits 0-2 of C are the same as the bits 12-14 of A
;- the bits 3-8 of C are the same as the bits 0-5 of B
;- the bits 9-15 of C are the same as the bits 3-9 of A

assume DS:data, CS:code
data segment
    ;    111111
	;	 5432109876543210
	a dw 0110110010100011b
	b dw 1101011111001010b
	
data ends
code segment
start:
	mov ax,data
	mov ds,ax
	mov ax,0       ; ax = 0000 0000 0000 0000
				   ;         AH        AL
	mov bx,a       ; bx = a
	and bx,0111000000000000b
	mov cl,12
	shr bx,cl
	or ax,bx;       ; ax := 0000 0000 0000 0110
	mov bx,b
	and bx,0000000000111111b
	mov cl,3
	shl bx,cl
	or ax,bx
	mov bx,b
	and bx,0000001111111000b
	mov cl,6
	shl bx,cl
	or ax,bx
	mov ax,4c00h
	int 21h
code ends
end start
	
	
	