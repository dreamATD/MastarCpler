default rel

global C_helloWorld_func
global C_helloWorld_helloWorld
global V_TAT
global F_func2
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
extern strlen
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



C_helloWorld_func
        sub     rsp, 40
        mov     r8, rsi
        cmp     r8, rdx
        jg      lb12
lb0:    cmp     r8, rcx
        jg      lb2
lb1:    jmp     lb12
lb2:    lea     rdi, [rsi+rdx]
        cmp     rdi, rcx
        jne     lb4
lb3:  
lb5:    lea     r8, [r8+1]
        cmp     r8, rdx
        jg      lb12
lb6:    cmp     r8, rcx
        jg      lb8
lb7:    jmp     lb12
lb8:    lea     rdi, [rsi+rdx]
        cmp     rdi, rcx
        jne     lb10
lb9:  
lb11:   lea     r8, [r8+1]
        cmp     r8, rdx
        jle     lb6
lb10:   ret   
lb4:    ret   
lb12:   push    rdi
        mov     rdi, S_0
        sub     rsp, 8
        call    F_print
        add     rsp, 8
        pop     rdi
        ret   

C_helloWorld_helloWorld
        sub     rsp, 8
        mov     rdx, 1
        mov     rcx, 2
        mov     r9, 3
        mov     rdi, 160
        call    _Znam
        mov     rsi, rax
        mov     rdi, 80
        call    _Znam
        mov     r8, rax
        mov     qword [rsi+0], r8
        mov     rdi, 80
        call    _Znam
        mov     r8, rax
        mov     qword [rsi+16], r8
        mov     rdi, 80
        call    _Znam
        mov     r8, rax
        mov     qword [rsi+32], r8
        mov     rdi, 80
        call    _Znam
        mov     r8, rax
        mov     qword [rsi+48], r8
        mov     rdi, 80
        call    _Znam
        mov     r8, rax
        mov     qword [rsi+64], r8
        mov     rdi, 80
        call    _Znam
        mov     r8, rax
        mov     qword [rsi+80], r8
        mov     rdi, 80
        call    _Znam
        mov     r8, rax
        mov     qword [rsi+96], r8
        mov     rdi, 80
        call    _Znam
        mov     r8, rax
        mov     qword [rsi+112], r8
        mov     rdi, 80
        call    _Znam
        mov     r8, rax
        mov     qword [rsi+128], r8
        mov     rdi, 80
        call    _Znam
        mov     r8, rax
        mov     qword [rsi+144], r8
        mov     qword [rdi+0], rdx
        mov     qword [rdi+8], rcx
        mov     qword [rdi+16], r9
        mov     qword [rdi+24], rsi

F_func2
        push    r0
        sub     rsp, 56
        lea     rsi, [rdi*16]
        mov     rdi, rsi
        call    _Znam
        mov     rsi, rax
        mov     r0, 0
        cmp     r0, rdi
        jge     lb16
lb13:   lea     rcx, [rdi*16]
        push    rdi
        mov     rdi, rcx
        sub     rsp, 8
        call    _Znam
        mov     r9, rax
        add     rsp, 8
        pop     rdi
        mov     rax, 0
        cmp     rax, rdi
        jge     lb15
lb14:   lea     rcx, [rdi*8]
        push    rdi
        mov     rdi, rcx
        push    rax
        call    _Znam
        mov     rcx, rax
        pop     rax
        pop     rdi
        lea     r8, [r8+16]
        mov     qword [r9+r8], rcx
        lea     rax, [rax+1]
        cmp     rax, rdi
        jl      lb14
lb15:   lea     rdx, [rdx+16]
        mov     qword [rsi+rdx], r9
        lea     r0, [r0+1]
        cmp     r0, rdi
        jl      lb13
lb16:   mov     r8, 0
        cmp     r8, rdi
        jge     lb37
lb17:   mov     r9, 0
        cmp     r9, rdi
        jge     lb26
lb18:   mov     rcx, 0
        cmp     rcx, rdi
        jge     lb21
lb19:   mov     qword [rdx+rcx*8], 1
        lea     rcx, [rcx+1]
        cmp     rcx, rdi
        jge     lb21
lb20:   mov     qword [rdx+rcx*8], 1
        lea     rcx, [rcx+1]
        cmp     rcx, rdi
        jl      lb20
lb21:   lea     r9, [r9+1]
        cmp     r9, rdi
        jge     lb26
lb22:   mov     rcx, 0
        cmp     rcx, rdi
        jge     lb25
lb23:   mov     qword [rdx+rcx*8], 1
        lea     rcx, [rcx+1]
        cmp     rcx, rdi
        jge     lb25
lb24:   mov     qword [rdx+rcx*8], 1
        lea     rcx, [rcx+1]
        cmp     rcx, rdi
        jl      lb24
lb25:   lea     r9, [r9+1]
        cmp     r9, rdi
        jl      lb22
lb26:   lea     r8, [r8+1]
        cmp     r8, rdi
        jge     lb37
lb27:   mov     r9, 0
        cmp     r9, rdi
        jge     lb36
lb28:   mov     rcx, 0
        cmp     rcx, rdi
        jge     lb31
lb29:   mov     qword [rdx+rcx*8], 1
        lea     rcx, [rcx+1]
        cmp     rcx, rdi
        jge     lb31
lb30:   mov     qword [rdx+rcx*8], 1
        lea     rcx, [rcx+1]
        cmp     rcx, rdi
        jl      lb30
lb31:   lea     r9, [r9+1]
        cmp     r9, rdi
        jge     lb36
lb32:   mov     rcx, 0
        cmp     rcx, rdi
        jge     lb35
lb33:   mov     qword [rdx+rcx*8], 1
        lea     rcx, [rcx+1]
        cmp     rcx, rdi
        jge     lb35
lb34:   mov     qword [rdx+rcx*8], 1
        lea     rcx, [rcx+1]
        cmp     rcx, rdi
        jl      lb34
lb35:   lea     r9, [r9+1]
        cmp     r9, rdi
        jl      lb32
lb36:   lea     r8, [r8+1]
        cmp     r8, rdi
        jl      lb27
lb37:   mov     rax, rsi
        ret   
        pop     r0

main: 
        sub     rsp, 104
        mov     qword [rel V_TAT], 3
        mov     rdi, 32
        call    _Znam
        mov     rdi, rax
        call    C_helloWorld_helloWorld
        mov     rsi, 1
        mov     rdx, 2
        mov     rcx, 3
        call    C_helloWorld_func
lb38:   mov     rdi, 96
        call    _Znam
        mov     rdi, rax
        mov     rdx, 0
        cmp     rdx, 5
        jge     lb41
lb39:   lea     rsi, [rdx*8]
        push    rdi
        mov     rdi, rsi
        sub     rsp, 8
        call    _Znam
        mov     rsi, rax
        add     rsp, 8
        pop     rdi
        mov     qword [rdi+rdx*16], rsi
        lea     rdx, [rdx+1]
        cmp     rdx, 5
        jge     lb41
lb40:   lea     rsi, [rdx*8]
        push    rdi
        mov     rdi, rsi
        sub     rsp, 8
        call    _Znam
        mov     rsi, rax
        add     rsp, 8
        pop     rdi
        mov     qword [rdi+rdx*16], rsi
        lea     rdx, [rdx+1]
        cmp     rdx, 5
        jl      lb40
lb41: 
lb42:   push    rdi
        mov     rdi, 96
        sub     rsp, 8
        call    _Znam
        mov     rsi, rax
        add     rsp, 8
        pop     rdi
        mov     rdx, 0
        cmp     rdx, 5
        jge     lb45
lb43:   lea     rdi, [rdx*8]
        call    _Znam
        mov     rdi, rax
        mov     qword [rsi+rdx*16], rdi
        lea     rdx, [rdx+1]
        cmp     rdx, 5
        jge     lb45
lb44:   lea     rdi, [rdx*8]
        call    _Znam
        mov     rdi, rax
        mov     qword [rsi+rdx*16], rdi
        lea     rdx, [rdx+1]
        cmp     rdx, 5
        jl      lb44
lb45:   jmp     lb42



SECTION .data    align=8
S_0:
           dq 22H68656C6C6F576FH, 7272727272000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H
S_1:
           dq 22H41424322000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H
S_2:
           dq 22H41422200000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H
S_3:
           dq 22H42432200000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H
S_4:
           dq 22H4E6F2045717561H, 6C6C6C0000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H
S_5:
           dq 22H61626363642200H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H
S_6:
           dq 22H61626363642200H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H, 0000000000000000H


SECTION .bss     align=8
V_TAT: 
           resq 8


SECTION .rodata.str1.1 

L_007:
        db 25H, 6CH, 64H, 00H

L_008:
        db 25H, 73H, 00H

L_009:
        db 25H, 64H, 00H
