package BackEnd;

import GeneralDataStructure.FuncFrame;
import GeneralDataStructure.LinearIR;
import GeneralDataStructure.MyListClass.MyList;
import Optimizer.FuncSSABuilder;

import java.util.ArrayList;
import java.util.HashMap;

public class CodeGen {
	private ArrayList<String> globals;
	private ArrayList<FuncFrame> funcs;
	private MyList<String> codes;
	private HashMap<String, Integer> globalSize;
	public CodeGen(LinearIR ir) {
		globals = ir.getGlobals();
		funcs = ir.getFuncs();
		codes = new MyList<>();
		globalSize = ir.getVarSize();
	}
	public MyList<String> generateCode() {
		codes.add("default rel");
		for (int i = 0; i < globals.size(); ++i) {
			codes.add("global " + globals.get(i));
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
