; Project realised by Vieriu Denis Gabriel
; Problem:
; Read from the standard input a string of numbers, given in base 10.

assume ds:data, cs:code
data segment public
	msg db 'Enter the string of numbers: $'
	format db 12,?,13 dup(?)
	newLine db 10,13,'$'
data ends	
PUBLIC msg,format,newLine
EXTRN ReadFromSTD:PROC
code segment public
start:
	mov ax,data
	mov ds,ax
	
	call ReadFromSTD
	
	mov ax,4c00h
	int 21h
code ends
end start