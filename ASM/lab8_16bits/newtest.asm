.MODEL SMALL
 .STACK 100H

 .DATA
   PROMPT  DB  'Current System Time is : $'     
   TIME    DB  '00:00:00$'        ; time format hr:min:sec

 .CODE
   MAIN PROC
     MOV AX, @DATA                ; initialize DS
     MOV DS, AX

     LEA BX, TIME                 ; BX=offset address of string TIME

     CALL GET_TIME                ; call the procedure GET_TIME

     LEA DX, PROMPT               ; DX=offset address of string PROMPT
     MOV AH, 09H                  ; print the string PROMPT
     INT 21H                      

     LEA DX, TIME                 ; DX=offset address of string TIME
     MOV AH, 09H                  ; print the string TIME
     INT 21H                      

     MOV AH, 4CH                  ; return control to DOS
     INT 21H
   MAIN ENDP

 ;**************************************************************************;
 ;**************************************************************************;
 ;-------------------------  Procedure Definitions  ------------------------;
 ;**************************************************************************;
 ;**************************************************************************;

 ;**************************************************************************;
 ;------------------------------  GET_TIME  --------------------------------;
 ;**************************************************************************;

   GET_TIME PROC
    ; this procedure will get the current system time 
    ; input : BX=offset address of the string TIME
    ; output : BX=current time

    PUSH AX                       ; PUSH AX onto the STACK
    PUSH CX                       ; PUSH CX onto the STACK 

    MOV AH, 2CH                   ; get the current system time
    INT 21H                       

    MOV AL, CH                    ; set AL=CH , CH=hours
    CALL CONVERT                  ; call the procedure CONVERT
    MOV [BX], AX                  ; set [BX]=hr  , [BX] is pointing to hr
                                  ; in the string TIME

    MOV AL, CL                    ; set AL=CL , CL=minutes
    CALL CONVERT                  ; call the procedure CONVERT
    MOV [BX+3], AX                ; set [BX+3]=min  , [BX] is pointing to min
                                  ; in the string TIME
                                           
    MOV AL, DH                    ; set AL=DH , DH=seconds
    CALL CONVERT                  ; call the procedure CONVERT
    MOV [BX+6], AX                ; set [BX+6]=min  , [BX] is pointing to sec
                                  ; in the string TIME
                                                      
    POP CX                        ; POP a value from STACK into CX
    POP AX                        ; POP a value from STACK into AX

    RET                           ; return control to the calling procedure
   GET_TIME ENDP                  ; end of procedure GET_TIME

 ;**************************************************************************;
 ;-------------------------------  CONVERT  --------------------------------;
 ;**************************************************************************;

   CONVERT PROC 
    ; this procedure will convert the given binary code into ASCII code
    ; input : AL=binary code
    ; output : AX=ASCII code

    PUSH DX                       ; PUSH DX onto the STACK 

    MOV AH, 0                     ; set AH=0
    MOV DL, 10                    ; set DL=10
    DIV DL                        ; set AX=AX/DL
    OR AX, 3030H                  ; convert the binary code in AX into ASCII

    POP DX                        ; POP a value from STACK into DX 

    RET                           ; return control to the calling procedure
   CONVERT ENDP                   ; end of procedure CONVERT

 ;**************************************************************************;
 ;--------------------------------------------------------------------------;
 ;**************************************************************************;

 END MAIN