package GeneralDataStructure;

public class BuiltinCode {
	static public String text = "\n" +
			"S_strlen:\n" +
			"        xor     eax, eax\n" +
			"        cmp     byte [rdi], 0\n" +
			"        jz      L_002\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"ALIGN   16\n" +
			"L_001:  add     rax, 1\n" +
			"        cmp     byte [rdi+rax], 0\n" +
			"        jnz     L_001\n" +
			"\n" +
			"        ret\n" +
			"\n" +
			"L_002:\n" +
			"\n" +
			"        ret\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"ALIGN   8\n" +
			"\n" +
			"S_strcat:\n" +
			"        jmp     L_004\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"ALIGN   8\n" +
			"L_003:  add     rdi, 1\n" +
			"L_004:  cmp     byte [rdi], 0\n" +
			"        jnz     L_003\n" +
			"        movzx   eax, byte [rsi]\n" +
			"        test    al, al\n" +
			"        jz      L_006\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"ALIGN   16\n" +
			"L_005:  add     rdi, 1\n" +
			"        add     rsi, 1\n" +
			"        mov     byte [rdi-1H], al\n" +
			"        movzx   eax, byte [rsi]\n" +
			"        test    al, al\n" +
			"        jnz     L_005\n" +
			"L_006:  mov     byte [rdi], 0\n" +
			"        ret\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"ALIGN   16\n" +
			"\n" +
			"S_strcpy:\n" +
			"        jmp     L_008\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"ALIGN   8\n" +
			"L_007:  add     rdi, 1\n" +
			"        add     rsi, 1\n" +
			"        mov     byte [rdi-1H], al\n" +
			"L_008:  movzx   eax, byte [rsi]\n" +
			"        test    al, al\n" +
			"        jnz     L_007\n" +
			"        mov     byte [rdi], 0\n" +
			"        ret\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"ALIGN   8\n" +
			"\n" +
			"S_substring:\n" +
			"        push    r13\n" +
			"        push    r12\n" +
			"        mov     r13, rdi\n" +
			"        push    rbp\n" +
			"        push    rbx\n" +
			"        mov     rbp, rdx\n" +
			"        mov     rbx, rsi\n" +
			"        mov     r12, rdx\n" +
			"        mov     edi, 256\n" +
			"        sub     rsp, 8\n" +
			"        sub     r12, rsi\n" +
			"        call    malloc\n" +
			"        cmp     rbp, rbx\n" +
			"        mov     rcx, rax\n" +
			"        jle     L_009\n" +
			"        lea     rsi, [r13+rbx]\n" +
			"        mov     rdx, r12\n" +
			"        mov     rdi, rax\n" +
			"        call    memcpy\n" +
			"        mov     rcx, rax\n" +
			"L_009:  mov     byte [rcx+r12], 0\n" +
			"        add     rsp, 8\n" +
			"        mov     rax, rcx\n" +
			"        pop     rbx\n" +
			"        pop     rbp\n" +
			"        pop     r12\n" +
			"        pop     r13\n" +
			"        ret\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"ALIGN   16\n" +
			"\n" +
			"S_parseInt:\n" +
			"        sub     rsp, 24\n" +
			"        mov     esi, L_020\n" +
			"\n" +
			"\n" +
			"        mov     rax, qword [fs:abs 28H]\n" +
			"        mov     qword [rsp+8H], rax\n" +
			"        xor     eax, eax\n" +
			"        mov     rdx, rsp\n" +
			"        call    sscanf\n" +
			"        mov     rcx, qword [rsp+8H]\n" +
			"\n" +
			"\n" +
			"        xor     rcx, qword [fs:abs 28H]\n" +
			"        mov     rax, qword [rsp]\n" +
			"        jnz     L_010\n" +
			"        add     rsp, 24\n" +
			"        ret\n" +
			"\n" +
			"L_010:  call    __stack_chk_fail\n" +
			"        nop\n" +
			"ALIGN   16\n" +
			"\n" +
			"S_ord:\n" +
			"        movsx   rax, byte [rdi+rsi]\n" +
			"        ret\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"ALIGN   16\n" +
			"\n" +
			"_Z5printPc:\n" +
			"        push    rbx\n" +
			"        mov     rbx, rdi\n" +
			"        movsx   edi, byte [rdi]\n" +
			"        test    dil, dil\n" +
			"        jz      L_012\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"ALIGN   8\n" +
			"L_011:  mov     rsi, qword [rel stdout]\n" +
			"        call    _IO_putc\n" +
			"        movsx   edi, byte [rbx]\n" +
			"        test    dil, dil\n" +
			"        jnz     L_011\n" +
			"L_012:  pop     rbx\n" +
			"        ret\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"ALIGN   16\n" +
			"\n" +
			"F_getString:\n" +
			"        push    rbx\n" +
			"        mov     edi, 256\n" +
			"        call    malloc\n" +
			"        mov     edi, L_021\n" +
			"        mov     rbx, rax\n" +
			"        mov     rsi, rax\n" +
			"        xor     eax, eax\n" +
			"        call    scanf\n" +
			"        mov     rax, rbx\n" +
			"        pop     rbx\n" +
			"        ret\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"ALIGN   16\n" +
			"\n" +
			"F_println:\n" +
			"        jmp     puts\n" +
			"\n" +
			"\n" +
			"        nop\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"ALIGN   16\n" +
			"\n" +
			"F_getInt:\n" +
			"        push    rbx\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"ALIGN   8\n" +
			"L_013:  mov     rdi, qword [rel stdin]\n" +
			"        call    _IO_getc\n" +
			"        lea     edx, [rax-30H]\n" +
			"        cmp     dl, 9\n" +
			"        ja      L_013\n" +
			"        movsx   rdx, al\n" +
			"        xor     ebx, ebx\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"ALIGN   8\n" +
			"L_014:  mov     rdi, qword [rel stdin]\n" +
			"        lea     rax, [rbx+rbx*4]\n" +
			"        lea     rbx, [rdx+rax*2-30H]\n" +
			"        call    _IO_getc\n" +
			"        movsx   rdx, al\n" +
			"        sub     eax, 48\n" +
			"        cmp     al, 9\n" +
			"        jbe     L_014\n" +
			"        mov     rax, rbx\n" +
			"        pop     rbx\n" +
			"        ret\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"ALIGN   8\n" +
			"\n" +
			"F_toString:\n" +
			"        push    rbx\n" +
			"        mov     rbx, rdi\n" +
			"        mov     edi, 256\n" +
			"        call    malloc\n" +
			"        test    rbx, rbx\n" +
			"        mov     r8, rax\n" +
			"        jnz     L_016\n" +
			"        mov     byte [rax], 48\n" +
			"L_015:  mov     rax, r8\n" +
			"        pop     rbx\n" +
			"        ret\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"ALIGN   8\n" +
			"L_016:  xor     edi, edi\n" +
			"        mov     rsi, qword 6666666666666667H\n" +
			"        jmp     L_018\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"ALIGN   8\n" +
			"L_017:  mov     rdi, rax\n" +
			"L_018:  mov     rax, rbx\n" +
			"        imul    rsi\n" +
			"        mov     rax, rbx\n" +
			"        sar     rax, 63\n" +
			"        sar     rdx, 2\n" +
			"        sub     rdx, rax\n" +
			"        lea     rax, [rdx+rdx*4]\n" +
			"        add     rax, rax\n" +
			"        sub     rbx, rax\n" +
			"        lea     rax, [rdi+1H]\n" +
			"        add     ebx, 48\n" +
			"        test    rdx, rdx\n" +
			"        mov     byte [r8+rdi], bl\n" +
			"        mov     rbx, rdx\n" +
			"        jnz     L_017\n" +
			"        test    edi, edi\n" +
			"        jz      L_015\n" +
			"        movsxd  rax, edi\n" +
			"        mov     rsi, r8\n" +
			"        xor     edx, edx\n" +
			"        add     rax, r8\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"ALIGN   8\n" +
			"L_019:  movzx   ecx, byte [rsi]\n" +
			"        movzx   r9d, byte [rax]\n" +
			"        add     edx, 1\n" +
			"        add     rsi, 1\n" +
			"        sub     rax, 1\n" +
			"        mov     byte [rsi-1H], r9b\n" +
			"        mov     byte [rax+1H], cl\n" +
			"        mov     ecx, edi\n" +
			"        sub     ecx, edx\n" +
			"        cmp     ecx, edx\n" +
			"        jg      L_019\n" +
			"        mov     rax, r8\n" +
			"        pop     rbx\n" +
			"        ret\n" +
			"\n" +
			"\n" +
			"\n";
	static public String roDataString = "L_020:\n" +
			"        db 25H, 6CH, 64H, 00H\n" +
			"\n" +
			"L_021:\n" +
			"        db 25H, 73H, 00H\n" +
			"\n" +
			"L_022:\n" +
			"        db 61H, 62H, 63H, 63H, 66H, 6AH, 67H, 6BH\n" +
			"        db 73H, 6BH, 73H, 6BH, 00H";
}
