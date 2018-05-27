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
		globals.add("F_getString");
		globals.add("F_printlnPc");
		globals.add("F_getString");
		globals.add("F_getInt");
		globals.add("F_toString");


		funcs = ir.getText();
		codes = new ArrayList<>();
		globalSize = ir.getVarSize();

		externs = ir.getExterns();
		externs.add("__sprintf_chk");
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

		codes.add("S_substring:\n" +
				"        push    r13\n" +
				"        push    r12\n" +
				"        mov     r12, rdi\n" +
				"        push    rbp\n" +
				"        mov     ebp, edx\n" +
				"        push    rbx\n" +
				"        sub     ebp, esi\n" +
				"        mov     ebx, esi\n" +
				"        mov     r13d, edx\n" +
				"        lea     edi, [rbp+1H]\n" +
				"        sub     rsp, 8\n" +
				"        movsxd  rdi, edi\n" +
				"        call    malloc\n" +
				"        cmp     r13d, ebx\n" +
				"        mov     rcx, rax\n" +
				"        jle     L_001\n" +
				"        lea     edx, [rbp-1H]\n" +
				"        movsxd  rsi, ebx\n" +
				"        mov     rdi, rax\n" +
				"        add     rsi, r12\n" +
				"        add     rdx, 1\n" +
				"        call    memcpy\n" +
				"        mov     rcx, rax\n" +
				"L_001:  movsxd  rbp, ebp\n" +
				"        mov     rax, rcx\n" +
				"        mov     byte [rcx+rbp], 0\n" +
				"        add     rsp, 8\n" +
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
				"        mov     esi, L_007\n" +
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
				"        movsxd  rsi, esi\n" +
				"        movsx   rax, byte [rdi+rsi]\n" +
				"        ret\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"ALIGN   8\n" +
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
				"        sub     rsp, 280\n" +
				"        mov     edi, L_008\n" +
				"\n" +
				"\n" +
				"        mov     rax, qword [fs:abs 28H]\n" +
				"        mov     qword [rsp+108H], rax\n" +
				"        xor     eax, eax\n" +
				"        mov     rsi, rsp\n" +
				"        call    scanf\n" +
				"        mov     rdx, qword [rsp+108H]\n" +
				"\n" +
				"\n" +
				"        xor     rdx, qword [fs:abs 28H]\n" +
				"        jnz     L_005\n" +
				"        add     rsp, 280\n" +
				"        ret\n" +
				"\n" +
				"L_005:  call    __stack_chk_fail\n" +
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
				"F_getString:\n" +
				"        mov     rsi, rdi\n" +
				"        xor     eax, eax\n" +
				"        mov     edi, L_008\n" +
				"        jmp     scanf\n" +
				"\n" +
				"\n" +
				"        nop\n" +
				"\n" +
				"ALIGN   16\n" +
				"F_getInt:\n" +
				"        sub     rsp, 24\n" +
				"        mov     edi, L_009\n" +
				"\n" +
				"\n" +
				"        mov     rax, qword [fs:abs 28H]\n" +
				"        mov     qword [rsp+8H], rax\n" +
				"        xor     eax, eax\n" +
				"        lea     rsi, [rsp+4H]\n" +
				"        call    scanf\n" +
				"        mov     rdx, qword [rsp+8H]\n" +
				"\n" +
				"\n" +
				"        xor     rdx, qword [fs:abs 28H]\n" +
				"        mov     eax, dword [rsp+4H]\n" +
				"        jnz     L_006\n" +
				"        add     rsp, 24\n" +
				"        ret\n" +
				"\n" +
				"L_006:  call    __stack_chk_fail\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"ALIGN   16\n" +
				"\n" +
				"F_toString:\n" +
				"\n" +
				"        db 41H, 89H, 0F0H\n" +
				"\n" +
				"        db 0B9H\n" +
				"        dd L_009\n" +
				"\n" +
				"\n" +
				"\n" +
				"        db 48H, 0C7H, 0C2H, 0FFH, 0FFH, 0FFH, 0FFH\n" +
				"\n" +
				"        db 0BEH, 01H, 00H, 00H, 00H\n" +
				"\n" +
				"        db 31H, 0C0H\n" +
				"\n" +
				"        db 0E9H\n" +
				"        dd __sprintf_chk-$-5H\n" +
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
				"L_007:\n" +
				"        db 25H, 6CH, 64H, 00H\n" +
				"\n" +
				"L_008:\n" +
				"        db 25H, 73H, 00H\n" +
				"\n" +
				"L_009:\n" +
				"        db 25H, 64H, 00H");

		return codes;
	}
}
