package BackEnd;

import GeneralDataStructure.BuiltinCode;
import GeneralDataStructure.FuncFrame;
import GeneralDataStructure.LinearIR;
import Optimizer.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

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

	/*
	* debug
	* */
	private String printFuncName = "main";
	private boolean printS = false;
	private boolean printC = true;
	private boolean printD = false;
	private boolean printR = false;
	private boolean printT = false;

	public CodeGen(LinearIR ir) {
		globals = ir.getGlobals();
		globals.add("S_substring");
		globals.add("S_parseInt");
		globals.add("S_ord");
		globals.add("S_strcpy");
		globals.add("S_strcat");
		globals.add("S_strlen");
		globals.add("F_print");
		globals.add("F_println");
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

		codes.add(BuiltinCode.text);

		for (int i = 0; i < funcs.size(); ++i) {
			FuncFrame func = funcs.get(i);

			if (func.getName().equals("main")) func.addInit(initFuncs);

			/*
			* Build dominating domain
			* */
			DomainEdgeBuilder domainEdgeBuilder = new DomainEdgeBuilder(func);
			domainEdgeBuilder.work();
			domainEdgeBuilder.rwork();

			FuncSSABuilder ssaBuilder = new FuncSSABuilder(func);
			ssaBuilder.buildSSAFunc();
			if (func.getName().equals(printFuncName) && printS) {
				System.err.println();
				func.print();
			}

			System.err.println("^ SSA ----------------------------");

			LocalConstTrans constTrans = new LocalConstTrans(func);
			constTrans.transConst();
			if (func.getName().equals(printFuncName) && printC) {
				System.err.println();
				func.print();
			}

			System.err.println("^ const transfer ----------------------------");

			DeadCodeResolver codeResolver = new DeadCodeResolver(func);
			codeResolver.updateUseful();

			if (func.getName().equals(printFuncName) && printD) {
				System.err.println();
				func.print();
			}

			System.err.println("^ Deadcode Resolver ----------------------------");

//			LabelResolver labelResolver = new LabelResolver(func);
//			labelResolver.labelResolve();
//
//			if (func.getName().equals(printFuncName) && printL) {
//				System.err.println();
//				func.print();
//			}
//
//			System.err.println("^ Label Resolver ----------------------------");

//			CodeRebuilder codeRebuilder = new CodeRebuilder(func);
//			codeRebuilder.rebuildFunc();
//
//			if (func.getName().equals(printFuncName) && printC) {
//				System.err.println();
//				func.print();
//			}
//
//			System.err.println("^ Code Rebuilder ----------------------------");

			RegDistributor distributor = new RegDistributor(func, globals);
			distributor.regDistribute();
			if (func.getName().equals(printFuncName) && printR) {
				System.err.println();
				func.print();
			}
			if (func.getName().equals(printFuncName) && printT) {
				System.err.println();
				distributor.printRegTable();
			}
//			System.err.println("^ Register coloring ----------------------------");
			CodeGenFunc funcGenerator = new CodeGenFunc(func, globalSize);
			codes.addAll(funcGenerator.generateCode());
			codes.add("");
		}

		for (int i = 0; i < initFuncs.size(); ++i) {
			FuncFrame func = initFuncs.get(i);

			/*
			 * Build dominating domain
			 * */
//			func.print();
			DomainEdgeBuilder domainEdgeBuilder = new DomainEdgeBuilder(func);
			domainEdgeBuilder.work();
			domainEdgeBuilder.rwork();

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

		codes.add(BuiltinCode.roDataString);

		codes.add("");
//		codes.add("SECTION .init align=8");
//
//		for (int i = 0; i < initFuncs.size(); ++i) {
//			codes.add(String.format("%-8s%s", "dq", initFuncs.get(i).getName()));
//		}

		return codes;
	}
}
