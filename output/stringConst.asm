default rel

global V_b
global main
global S_substring
global S_parseInt
global S_ord
global F_print
global F_getString
global F_printlnPc
global F_getString
global F_getInt
global F_toString

extern strcpy
extern __sprintf_chk
extern puts
extern scanf
extern _IO_putc
extern stdout
extern __stack_chk_fail
extern sscanf
extern memcpy
extern malloc

SECTION .text
S_substring:
        push    r13
        push    r12
        mov     r12, rdi
        push    rbp
        mov     ebp, edx
        push    rbx
        sub     ebp, esi
        mov     ebx, esi
        mov     r13d, edx
        lea     edi, [rbp+1H]
        sub     rsp, 8
        movsxd  rdi, edi
        call    malloc
        cmp     r13d, ebx
        mov     rcx, rax
        jle     L_001
        lea     edx, [rbp-1H]
        movsxd  rsi, ebx
        mov     rdi, rax
        add     rsi, r12
        add     rdx, 1
        call    memcpy
        mov     rcx, rax
L_001:  movsxd  rbp, ebp
        mov     rax, rcx
        mov     byte [rcx+rbp], 0
        add     rsp, 8
        pop     rbx
        pop     rbp
        pop     r12
        pop     r13
        ret







ALIGN   16

S_parseInt:
        sub     rsp, 24
        mov     esi, L_007


        mov     rax, qword [fs:abs 28H]
        mov     qword [rsp+8H], rax
        xor     eax, eax
        mov     rdx, rsp
        call    sscanf
        mov     rcx, qword [rsp+8H]


        xor     rcx, qword [fs:abs 28H]
        mov     rax, qword [rsp]
        jnz     L_002
        add     rsp, 24
        ret

L_002:  call    __stack_chk_fail
        nop
ALIGN   16

S_ord:
        movsxd  rsi, esi
        movsx   rax, byte [rdi+rsi]
        ret






ALIGN   8

F_print:
        push    rbx
        mov     rbx, rdi
        movsx   edi, byte [rdi]
        test    dil, dil
        jz      L_004




ALIGN   8
L_003:  mov     rsi, qword [rel stdout]
        call    _IO_putc
        movsx   edi, byte [rbx]
        test    dil, dil
        jnz     L_003
L_004:  pop     rbx
        ret







ALIGN   16

F_getString:
        sub     rsp, 280
        mov     edi, L_008


        mov     rax, qword [fs:abs 28H]
        mov     qword [rsp+108H], rax
        xor     eax, eax
        mov     rsi, rsp
        call    scanf
        mov     rdx, qword [rsp+108H]


        xor     rdx, qword [fs:abs 28H]
        jnz     L_005
        add     rsp, 280
        ret

L_005:  call    __stack_chk_fail





ALIGN   16

F_println:
        jmp     puts


        nop





ALIGN   16

F_getString:
        mov     rsi, rdi
        xor     eax, eax
        mov     edi, L_008
        jmp     scanf


        nop

ALIGN   16
F_getInt:
        sub     rsp, 24
        mov     edi, L_009


        mov     rax, qword [fs:abs 28H]
        mov     qword [rsp+8H], rax
        xor     eax, eax
        lea     rsi, [rsp+4H]
        call    scanf
        mov     rdx, qword [rsp+8H]


        xor     rdx, qword [fs:abs 28H]
        mov     eax, dword [rsp+4H]
        jnz     L_006
        add     rsp, 24
        ret

L_006:  call    __stack_chk_fail





ALIGN   16

F_toString:

        db 41H, 89H, 0F0H

        db 0B9H
        dd L_009



        db 48H, 0C7H, 0C2H, 0FFH, 0FFH, 0FFH, 0FFH

        db 0BEH, 01H, 00H, 00H, 00H

        db 31H, 0C0H

        db 0E9H
        dd __sprintf_chk-$-5H



main: 
        sub     rsp, 24
        mov     rdi, 256
        call    _Znam
        mov     rdi, rax
        mov     rsi, S_0
        call    strcpy
        push    rdi
        mov     rdi, S_2
        sub     rsp, 8
        call    F_println
        add     rsp, 8
        pop     rdi
        xor     rax, rax
        ret   



SECTION .data    align=8
V_b:
         dq 6161610000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H



SECTION .bss     align=8

SECTION .rodata
S_0: 
         db 22H, 62H, 62H, 62H, 22H, 00H
S_1: 
         db 22H, 62H, 62H, 62H, 22H, 00H
S_2: 
         db 22H, 61H, 62H, 63H, 63H, 22H, 00H


SECTION .rodata.str1.1 

L_007:
        db 25H, 6CH, 64H, 00H

L_008:
        db 25H, 73H, 00H

L_009:
        db 25H, 64H, 00H
