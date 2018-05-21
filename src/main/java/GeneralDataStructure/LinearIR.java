package GeneralDataStructure;

import Utilizer.ConstVar;

import java.util.ArrayList;
import java.util.HashMap;

public class LinearIR {
	ArrayList<FuncFrame> funcs;
	ArrayList<String> globals;
	HashMap<String, Integer> varSize;

	public LinearIR() {
		funcs = new ArrayList<>();
		globals = new ArrayList<>();
		varSize = new HashMap<>();
		globals.add("getInt");
		varSize.put("getInt", ConstVar.intLen);
	}
	public void insertFunc(FuncFrame func) {
		funcs.add(func);
		globals.add(func.getName());
		varSize.put(func.getName(), func.getRetSize());
	}
	public void insertVar(String str, Integer sz) {
		globals.add(str);
		varSize.put(str, sz);
	}
	public void print() {
		System.out.println("General Symbols: ");
		for (int i = 0; i < funcs.size(); ++i) {
			funcs.get(i).print();
		}
	}
	public ArrayList<String> getGlobals() {
		return globals;
	}
	public ArrayList<FuncFrame> getFuncs() {
		return funcs;
	}
	public HashMap<String, Integer> getVarSize() {
		return varSize;
	}
}
