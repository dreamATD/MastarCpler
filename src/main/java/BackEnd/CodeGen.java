package BackEnd;

import GeneralDataStructure.FuncFrame;
import GeneralDataStructure.LinearIR;
import GeneralDataStructure.MyListClass.MyList;
import Optimizer.FuncSSABuilder;

import java.util.ArrayList;
import java.util.HashMap;

public class CodeGen {
	private ArrayList<String> globals;
	private ArrayList<String> externs;
	private ArrayList<FuncFrame> funcs;
	private MyList<String> codes;
	private HashMap<String, Integer> globalSize;
	public CodeGen(LinearIR ir) {
		globals = ir.getGlobals();
		funcs = ir.getText();
		codes = new MyList<>();
		globalSize = ir.getVarSize();
		externs = ir.getExterns();
		externs.add("memcpy");
		externs.add("malloc");
	}
	public MyList<String> generateCode() {
		codes.add("default rel");
		for (int i = 0; i < globals.size(); ++i) {
			codes.add("global " + globals.get(i));
		}

		for (int i = 0; i < externs.size(); ++i) {
			codes.add("extern " + externs.get(i));
		}

		codes.add("SECTION .text");
		for (int i = 0; i < funcs.size(); ++i) {
			FuncFrame func = funcs.get(i);
			FuncSSABuilder ssaBuilder = new FuncSSABuilder(func);
			ssaBuilder.buildSSAFunc();
			System.out.println();
			func.print();
			RegDistributor distributor = new RegDistributor(func);
			distributor.regDistribute();
			System.out.println();
			func.print();
			CodeGenFunc funcGenerator = new CodeGenFunc(func, globalSize);
			codes.addAll(funcGenerator.generateCode());
		}

		codes.add("SECTION .data");

		return codes;
	}
}
