;Vieriu Denis-Gabriel
;p02-lab07
;2. A string of doublewords is given. Order in decreasing order the string of the low words 
;(least significant) from these doublewords. The high words (most significant) remain unchanged. 
;Ex: being given: sir DD 12345678h 1256ABCDh, 12AB4344h 
;the result will be 1234ABCDh, 12565678h, 12AB4344h.
assume ds:data, cs:code
data segment
	sir  dd  12300011h,0abcd0h,2218acach,7a082ae3h,0c820ddd1h,7828ddd1h,0c8d8ddd1h,0c8b0ddd1h,7260ddd1h,2a70ddd1h,7850ddd1h,0c840ddd1h
	len equ ($-sir)/4	;the length of the string (in doublewords)
	sort db ?
	u dw len dup(?)
data ends
code segment
start:
	mov ax, data
	mov ds, ax
 
	push ds
	pop es
	mov si, offset sir	;in ds:si we will store the FAR address of the string "sir" 
	cld			;parse the string from left to right(DF=0).    
	mov cx, len		;we will parse the elements of the string in a loop with len iterations.
	mov di,offset u
	repeta:
		lodsw		;in ax we will have the low word (least significant) of the current doubleword from the string
		stosw		;we need only the low word
		lodsw		;pass over the high word
		loop repeta	
	
	;bubble sort (do.... while(sort==0);)
	cld
	
	Repetaa:
		lea si, u  ;; same as mov si, offset sir
		mov sort,0
		Do:
			lodsw
			mov bx,ax
			lodsw
			cmp ax,bx 
			sub si,4
			ja Interchange
			jmp Increment
			
			Interchange:
				xchg ax,bx
				mov di,si
				stosw
				mov ax,bx
				stosw
				mov sort,1
			Increment:
				add si,2
			
			cmp si,len-1
			jne Do
			
			cmp sort,0
			je ExitForMoment
			jmp Repetaa
	
	ExitForMoment:
		mov si, offset sir	;in ds:si we will store the FAR address of the string "sir" 
		cld			;parse the string from left to right(DF=0).    
		mov cx, len		;we will parse the elements of the string in a loop with len iterations.
		lea di,sir
		lea si,u
		repetaf: 		;put back in the original string the sorted low words
			lodsw
			stosw
			loop repetaf
			
		
	mov ax,4c00h
	int 21h
code ends
end start
