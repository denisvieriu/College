;2. Two byte strings S1 and S2 are given, having the same length. Obtain the string D in the following way: each element found on the even positions of D is the sum 
;of the corresponding elements from S1 and S2, and each element found on the odd positions of D is the difference of the corresponding elements from S1 and S2.
;Exemple:
;S1: 1, 2, 3, 4
;S2: 5, 6, 7, 8
;D: 6, -4, 10, -4
assume DS:data, CS:code
data segment
	a db 1,2,3,4
	b db 5,6,7,8
	len EQU $-b
	d db len DUP(?)
data ends
code segment
start:
	mov ax,data    
	mov ds,ax
	mov cx,len    	    ; cx = length of array
	jcxz MY_END         ; cmp cx,0   je MY_END
	mov si,0	  	    ; starting index 
	Repeat:		   		; main loop
		mov ax,0        ; useless line (just for easier testing)
		mov al,a[si]	; AL = a[SI]
		mov bx,si		; bx = si
		and bx,1		; isolating all bits of bx with 0, excepting the most right one
		cmp bx,1		; bx = 1 will mean odd, bx = 0 will mean even
		jne pass		; if bx == 0 jump to pass(even function)
		sbb al,b[si]	; al -= b[si]
		mov d[si],al	
		inc si			; si++
		dec cx			; cx--
		cmp cx,0		; 
		je MY_END		;	if cx == 0 jump to My end
		jne Repeat		; 	else Repeat
		pass:			; 	function for even indexes
			add al,b[si]
			mov d[si],al
			inc si
			loop Repeat
		MY_END:
		mov ax,4c00h
		int 21h
code ends
end start