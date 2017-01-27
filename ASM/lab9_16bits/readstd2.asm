

assume cs:code, ds:data

data segment
data ends

EXTRN format:BYTE
EXTRN newLine:BYTE
EXTRN msg:BYTE
PUBLIC ReadFromSTD

code segment
	ReadFromSTD PROC
		mov ah,09h
		mov dx,offset msg
		int 21h
		
		mov ah,0ah
		mov dx, offset format
		int 21h
		
		mov bl, format[1]
		mov bh,0
		add bx, offset format
		add bx,2
		
		mov byte ptr [bx], '$' 
		
		mov ah,09h
		lea dx, newLine
		int 21h
		
		;mov cl,0
		;lea si,[format+2]
		;my_loop:
		;	cmp cl,format[1]
		;	je go_out
		;	inc cl
		;	sub format[1],'0'
		;	jmp my_loop
					
		;go_out:
		lea dx,[format+2]
		mov ah,09h
		int 21h
		ret
ReadFromSTD endp
code ends
end