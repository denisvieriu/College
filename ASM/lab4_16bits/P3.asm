;3. (8-a*b*100+c)/d ;;;;;;;  3+15=
;a,b,d-byte; c-doubleword

assume ds:data, cs:code
data segment
	a db 30
	b db 25
	c dd 60
	d db 67
	helper dw ?
	final dw ?
data ends

code segment
start:
	mov ax,data
	mov ds,ax
	
	mov al,a     ; al = a
	imul b       ; ax = al * b
	mov dl,100   ; dl = 100
	imul dl      ; dx:ax = ax * dl
	
	mov bx,word ptr c
	mov cx,word ptr c+2  ; bx:cx
	
	add ax,bx ; ax += bx
	adc dx,cx ; dx += cx
	
	mov bx,ax
	mov cx,dx
	
	mov al,8
	cbw
	cwd
	
	sub ax,bx
	sbb dx,cx
	
	mov bx,ax
	mov cx,dx
	
	mov al,d
	cbw
	mov helper,ax
	
	mov ax,bx
	mov dx,cx
	idiv helper
	mov final,ax
	
	mov ax,4c00h
	int 21h
code ends
end start
	