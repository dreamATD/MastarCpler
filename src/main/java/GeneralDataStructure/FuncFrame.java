package GeneralDataStructure;

import FrontEnd.LabelTable;
import GeneralDataStructure.ScopeClass.LocalScope;
import GeneralDataStructure.ScopeClass.SpecialScope;

import java.util.ArrayList;
import java.util.LinkedList;

public class FuncFrame {
	LocalScope<Info> funcScope;
	SpecialScope<Info> envScope;
	ArrayList<String> params;
	LinkedList<Quad> code;
	String name;
	public FuncFrame(String funcName, LocalScope<Info> scope, SpecialScope<Info> env) {
		name = funcName;
		funcScope = scope;
		envScope = env;
		params = new ArrayList<>();
		code = new LinkedList<>();
	}
	public void insertCode(Quad ins) {
		code.add(ins);
	}
	public void print() {
		System.out.printf("Func_def %s (", name);
		for (int i = 0; i < params.size(); ++i) {
			System.out.print(params.get(i));
			if (i < params.size() - 1) System.out.print(", ");
		}
		System.out.println("):");
		for (int i = 0; i < code.size(); ++i) {
			code.get(i).print();
		}
		System.out.println("Func_def_done");
	}
	public void addParam(String reg) {
		params.add(reg);
	}
	public void updateLabel(LabelTable labels) {
		for (int i = 0; i < code.size(); ++i) {
			Quad qua = code.get(i);
			qua.updateLabel(labels);
		}
	}
}
