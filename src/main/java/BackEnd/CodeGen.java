package BackEnd;

import GeneralDataStructure.FuncFrame;
import GeneralDataStructure.LinearIR;
import GeneralDataStructure.MyListClass.MyList;
import Optimizer.FuncSSABuilder;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CodeGen {
	private ArrayList<String> globals;
	private ArrayList<String> externs;
	private ArrayList<FuncFrame> funcs;
	private ArrayList<FuncFrame> initFuncs;
	private ArrayList<String> codes;
	private HashMap<String, Integer> globalSize;
	private ArrayList<Pair<String, String>> initMem;
	private ArrayList<Pair<String, Integer>> uninitMem;
	private ArrayList<Pair<String, String>> roData;
	public CodeGen(LinearIR ir) {
		globals = ir.getGlobals();
		globals.add("S_substring");
		globals.add("S_parseInt");
		globals.add("S_ord");
		globals.add("F_print");
		globals.add("F_printlnPc");
		globals.add("F_getString");
		globals.add("F_getInt");
		globals.add("F_toString");


		funcs = ir.getText();
		initFuncs = ir.getInit();
		codes = new ArrayList<>();
		globalSize = ir.getVarSize();

		externs = ir.getExterns();
		externs.add("__sprintf_chk");
		externs.add("_IO_getc");
		externs.add("stdin");
		externs.add("puts");
		externs.add("scanf");
		externs.add("_IO_putc");
		externs.add("stdout");
		externs.add("__stack_chk_fail");
		externs.add("sscanf");
		externs.add("memcpy");
		externs.add("malloc");

		initMem = ir.getInitMem();
		uninitMem = ir.getUninitMem();
		roData = ir.getRoData();
	}

	public ArrayList<String> generateCode() {
		codes.add("default rel");
		codes.add("");
		for (int i = 0; i < globals.size(); ++i) {
			codes.add("global " + globals.get(i));
		}

		codes.add("");
		for (int i = 0; i < externs.size(); ++i) {
			codes.add("extern " + externs.get(i));
		}

		codes.add("");

		codes.add("SECTION .text");

		codes.add("\n" +
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
				"        jle     L_001\n" +
				"        lea     rsi, [r13+rbx]\n" +
				"        mov     rdx, r12\n" +
				"        mov     rdi, rax\n" +
				"        call    memcpy\n" +
				"        mov     rcx, rax\n" +
				"L_001:  mov     byte [rcx+r12], 0\n" +
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
				"        mov     esi, L_011\n" +
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
				"        jnz     L_002\n" +
				"        add     rsp, 24\n" +
				"        ret\n" +
				"\n" +
				"L_002:  call    __stack_chk_fail\n" +
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
				"        jz      L_004\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"ALIGN   8\n" +
				"L_003:  mov     rsi, qword [rel stdout]\n" +
				"        call    _IO_putc\n" +
				"        movsx   edi, byte [rbx]\n" +
				"        test    dil, dil\n" +
				"        jnz     L_003\n" +
				"L_004:  pop     rbx\n" +
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
				"        mov     edi, L_012\n" +
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
				"L_005:  mov     rdi, qword [rel stdin]\n" +
				"        call    _IO_getc\n" +
				"        lea     edx, [rax-30H]\n" +
				"        cmp     dl, 9\n" +
				"        ja      L_005\n" +
				"        movsx   rdx, al\n" +
				"        xor     ebx, ebx\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"ALIGN   8\n" +
				"L_006:  mov     rdi, qword [rel stdin]\n" +
				"        lea     rax, [rbx+rbx*4]\n" +
				"        lea     rbx, [rdx+rax*2-30H]\n" +
				"        call    _IO_getc\n" +
				"        movsx   rdx, al\n" +
				"        sub     eax, 48\n" +
				"        cmp     al, 9\n" +
				"        jbe     L_006\n" +
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
				"        xor     esi, esi\n" +
				"        test    rbx, rbx\n" +
				"        mov     r8, rax\n" +
				"        mov     rdi, qword 6666666666666667H\n" +
				"        jnz     L_008\n" +
				"        jmp     L_010\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"ALIGN   8\n" +
				"L_007:  mov     rsi, rax\n" +
				"L_008:  mov     rax, rbx\n" +
				"        imul    rdi\n" +
				"        mov     rax, rbx\n" +
				"        sar     rax, 63\n" +
				"        sar     rdx, 2\n" +
				"        sub     rdx, rax\n" +
				"        lea     rax, [rdx+rdx*4]\n" +
				"        add     rax, rax\n" +
				"        sub     rbx, rax\n" +
				"        lea     rax, [rsi+1H]\n" +
				"        add     ebx, 48\n" +
				"        test    rdx, rdx\n" +
				"        mov     byte [r8+rsi], bl\n" +
				"        mov     rbx, rdx\n" +
				"        jnz     L_007\n" +
				"        test    esi, esi\n" +
				"        jz      L_010\n" +
				"        movsxd  rax, esi\n" +
				"        mov     rdi, r8\n" +
				"        xor     edx, edx\n" +
				"        add     rax, r8\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"ALIGN   8\n" +
				"L_009:  movzx   ecx, byte [rdi]\n" +
				"        movzx   r9d, byte [rax]\n" +
				"        add     edx, 1\n" +
				"        add     rdi, 1\n" +
				"        sub     rax, 1\n" +
				"        mov     byte [rdi-1H], r9b\n" +
				"        mov     byte [rax+1H], cl\n" +
				"        mov     ecx, esi\n" +
				"        sub     ecx, edx\n" +
				"        cmp     ecx, edx\n" +
				"        jg      L_009\n" +
				"L_010:  mov     rax, r8\n" +
				"        pop     rbx\n" +
				"        ret\n" +
				"\n" +
				"\n" +
				"\n");

		for (int i = 0; i < funcs.size(); ++i) {
			FuncFrame func = funcs.get(i);
			FuncSSABuilder ssaBuilder = new FuncSSABuilder(func);
			ssaBuilder.buildSSAFunc();
//			System.out.println();
//			func.print();
			RegDistributor distributor = new RegDistributor(func, globals);
			distributor.regDistribute();
			if (func.getName().equals("F_gcd")) {
				System.out.println();
				func.print();
			}
			CodeGenFunc funcGenerator = new CodeGenFunc(func, globalSize);
			codes.addAll(funcGenerator.generateCode());
			codes.add("");
		}

		for (int i = 0; i < initFuncs.size(); ++i) {
			FuncFrame func = initFuncs.get(i);
			FuncSSABuilder ssaBuilder = new FuncSSABuilder(func);
			ssaBuilder.buildSSAFunc();
//			System.out.println();
//			func.print();
			RegDistributor distributor = new RegDistributor(func, globals);
			distributor.regDistribute();
//			System.out.println();
//			func.print();
			CodeGenFunc funcGenerator = new CodeGenFunc(func, globalSize);
			codes.addAll(funcGenerator.generateCode());
			codes.add("");
		}

		codes.add("\n" +
				"\n" +
				"SECTION .data    align=8");

		for (Pair<String, String> data: initMem) {
			codes.add(data.getKey() + ":");
			codes.add(String.format("%-8s dq %s", " ", data.getValue()));
		}

		codes.add("");
		codes.add("\n" +
				"\n" +
				"SECTION .bss     align=8");

		for (Pair<String, Integer> data: uninitMem) {
			codes.add(data.getKey() + ": ");
			codes.add(String.format("%-8s resq %d", " ", data.getValue()));
		}

		codes.add("");

		codes.add("SECTION .rodata");
		for (Pair<String, String> data: roData) {
			codes.add(data.getKey() + ": ");
			codes.add(String.format("%-8s db %s", " ", data.getValue()));
		}

		codes.add("\n" +
				"\n" +
				"SECTION .rodata.str1.1 \n" +
				"\n" +
				"L_011:\n" +
				"        db 25H, 6CH, 64H, 00H\n" +
				"\n" +
				"L_012:\n" +
				"        db 25H, 73H, 00H\n");

		codes.add("");
		codes.add("SECTION .init align=8");

		for (int i = 0; i < initFuncs.size(); ++i) {
			codes.add(String.format("%-8s%s", "dq", initFuncs.get(i).getName()));
		}

		return codes;
	}
}
