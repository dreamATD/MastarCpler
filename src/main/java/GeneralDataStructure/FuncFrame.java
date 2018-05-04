package GeneralDataStructure;

import GeneralDataStructure.ScopeClass.LocalScope;

import java.util.ArrayList;
import java.util.LinkedList;

public class FuncFrame {
	LocalScope<Info> funcScope;
	ArrayList<String> params;
	LinkedList<Quad> code;
	public FuncFrame(LocalScope<Info> scope) {
		funcScope = scope;
		params = new ArrayList<>();
		code = new LinkedList<>();
	}
	public void insertCode(Quad ins) {
		code.add(ins);
	}
}
