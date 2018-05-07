package GeneralDataStructure;

import GeneralDataStructure.ScopeClass.LocalScope;
import GeneralDataStructure.ScopeClass.SpecialScope;

import java.util.ArrayList;
import java.util.LinkedList;

public class FuncFrame {
	LocalScope<Info> funcScope;
	SpecialScope<Info> envScope;
	ArrayList<String> params;
	LinkedList<Quad> code;
	public FuncFrame(LocalScope<Info> scope, SpecialScope<Info> env) {
		funcScope = scope;
		envScope = env;
		params = new ArrayList<>();
		code = new LinkedList<>();
	}
	public void insertCode(Quad ins) {
		code.add(ins);
	}
	void print() {
		System.out.println("Func_def: ");
		for (int i = 0; i < code.size(); ++i) {
			code.get(i).print();
		}
	}
}
