assume ds:data, cs:code

data segment
	PROMPT DB 'Current System time is : $' 
	TIME DB '00:00:00  $' 
data ends
code segment
start:
	mov ax, data
	mov ds,ax
	
	lea bx,time
	
	MOV AH, 2CH                   ; get the current system time
    INT 21H                       

    MOV AL, CH                    ; set AL=CH , CH=hours
	
	cmp al,12					  ; check if it's AM or PM
	jg PM						  ; transform it in PM if greater than 12
	
	
    MOV [BX], AX                  ; set [BX]=hr  , [BX] is pointing to hr
	CALL CONVERT 				  ; call the convert function(procedure)
	mov [bx+8], 'MA'			  ; little endian repr: ...|'A'|'M'|... at the end of the string
	jmp label2
	PM:

		CALL CONVERT 
		mov [BX], AX
		mov [bx+8], 'MP'

		
	label2:							
    MOV AL, CL                    ; set AL=CL , CL=minutes
    CALL CONVERT                  ; call the procedure CONVERT
    MOV [BX+3], AX                ; set [BX+3]=min  , [BX] is pointing to min
                                  ; in the string TIME
                                           
    MOV AL, DH                    ; set AL=DH , DH=seconds
    CALL CONVERT                  ; call the procedure CONVERT
	
    MOV [BX+6], AX                ; set [BX+6]=min  , [BX] is pointing to sec
                                  ; in the string TIME
	
	
	
	LEA DX, PROMPT               ; DX=offset address of string PROMPT
    MOV AH, 09H                  ; print the string PROMPT
    INT 21H                      

    LEA DX, TIME                 ; DX=offset address of string TIME
    MOV AH, 09H                  ; print the string TIME
    INT 21H                      

    MOV AH, 4CH                  ; return control to DOS
    INT 21H
			

	CONVERT PROC 
    ; this procedure will convert the given binary code into ASCII code
    ; input : AL=binary code
    ; output : AX=ASCII code
    MOV AH, 0                     ; set AH=0
    MOV DL, 10                    ; set DL=10
    DIV DL                        ; set AX=AX/DL
    OR AX, 3030H                  ; convert the binary code in AX into ASCII
	
    RET                           ; return control to the calling procedure
   CONVERT ENDP                   ; end of procedure CONVERT

code ends
end start