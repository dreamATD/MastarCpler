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
			"push    r14\n" +
			"        push    r13\n" +
			"        mov     r14, rdi\n" +
			"        push    r12\n" +
			"        push    rbp\n" +
			"        mov     r12, rdx\n" +
			"        push    rbx\n" +
			"        mov     rbp, rdx\n" +
			"        mov     rbx, rsi\n" +
			"        mov     edi, 256\n" +
			"        sub     rbp, rsi\n" +
			"        call    malloc\n" +
			"        cmp     r12, rbx\n" +
			"        mov     rcx, rax\n" +
			"        jl      L_009\n" +
			"        lea     r13, [rbp+1H]\n" +
			"        lea     rsi, [r14+rbx]\n" +
			"        mov     rdi, rax\n" +
			"        mov     rdx, r13\n" +
			"        call    memcpy\n" +
			"        mov     rcx, rax\n" +
			"L_009:  mov     byte [rcx+rbp+1H], 0\n" +
			"        mov     rax, rcx\n" +
			"        pop     rbx\n" +
			"        pop     rbp\n" +
			"        pop     r12\n" +
			"        pop     r13\n" +
			"        pop     r14\n" +
			"        ret\n" +
			"\n" +
			"\n" +
			"S_parseInt:\n" +
			"        sub     rsp, 24\n" +
			"        mov     esi, L_021\n" +
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
			"F_print:\n" +
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
			"        add     rbx, 1\n" +
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
			"ALIGN   8\n" +
			"\n" +
			"F_getString:\n" +
			"        push    rbx\n" +
			"        mov     edi, 256\n" +
			"        call    malloc\n" +
			"        mov     edi, L_022\n" +
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
			"        sub     rsp, 24\n" +
			"        mov     edi, L_021\n" +
			"\n" +
			"\n" +
			"        mov     rax, qword [fs:abs 28H]\n" +
			"        mov     qword [rsp+8H], rax\n" +
			"        xor     eax, eax\n" +
			"        mov     rsi, rsp\n" +
			"        call    scanf\n" +
			"        mov     rdx, qword [rsp+8H]\n" +
			"\n" +
			"\n" +
			"        xor     rdx, qword [fs:abs 28H]\n" +
			"        mov     rax, qword [rsp]\n" +
			"        jnz     L_013\n" +
			"        add     rsp, 24\n" +
			"        ret\n" +
			"\n" +
			"L_013:  call    __stack_chk_fail\n" +
			"        nop\n" +
			"ALIGN   16\n" +
			"\n" +
			"F_toString:\n" +
			"        push    rbx\n" +
			"        mov     rbx, rdi\n" +
			"        mov     edi, 256\n" +
			"        call    malloc\n" +
			"        test    rbx, rbx\n" +
			"        mov     r9, rax\n" +
			"        je      L_019\n" +
			"        js      L_020\n" +
			"        xor     edi, edi\n" +
			"L_014:  movsxd  rsi, edi\n" +
			"        mov     ecx, edi\n" +
			"        mov     r11, qword 6666666666666667H\n" +
			"        add     rsi, r9\n" +
			"        mov     r8, rsi\n" +
			"        jmp     L_016\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"ALIGN   8\n" +
			"L_015:  mov     ecx, r10d\n" +
			"L_016:  mov     rax, rbx\n" +
			"        add     r8, 1\n" +
			"        lea     r10d, [rcx+1H]\n" +
			"        imul    r11\n" +
			"        mov     rax, rbx\n" +
			"        sar     rax, 63\n" +
			"        sar     rdx, 2\n" +
			"        sub     rdx, rax\n" +
			"        lea     rax, [rdx+rdx*4]\n" +
			"        add     rax, rax\n" +
			"        sub     rbx, rax\n" +
			"        add     ebx, 48\n" +
			"        mov     byte [r8-1H], bl\n" +
			"        test    rdx, rdx\n" +
			"        mov     rbx, rdx\n" +
			"        jnz     L_015\n" +
			"        movsxd  rax, ecx\n" +
			"        add     rax, r9\n" +
			"        cmp     edi, ecx\n" +
			"        jge     L_018\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"ALIGN   8\n" +
			"L_017:  movzx   edx, byte [rsi]\n" +
			"        movzx   r8d, byte [rax]\n" +
			"        add     edi, 1\n" +
			"        sub     ecx, 1\n" +
			"        add     rsi, 1\n" +
			"        sub     rax, 1\n" +
			"        mov     byte [rsi-1H], r8b\n" +
			"        mov     byte [rax+1H], dl\n" +
			"        cmp     edi, ecx\n" +
			"        jl      L_017\n" +
			"L_018:  mov     rax, r9\n" +
			"        pop     rbx\n" +
			"        ret\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"ALIGN   8\n" +
			"L_019:  mov     byte [rax], 48\n" +
			"        mov     rax, r9\n" +
			"        pop     rbx\n" +
			"        ret\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"\n" +
			"ALIGN   16\n" +
			"L_020:  neg     rbx\n" +
			"        mov     byte [rax], 45\n" +
			"        mov     edi, 1\n" +
			"        jmp     L_014\n" +
			"\n" +
			"\n" +
			"\n";
	static public String roDataString = "\n" +
			"L_021:\n" +
			"        db 25H, 6CH, 64H, 00H\n" +
			"\n" +
			"L_022:\n" +
			"        db 25H, 73H, 00H\n" +
			"\n";
}
