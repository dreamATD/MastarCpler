package GeneralDataStructure;

import Utilizer.ConstVar;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class LinearIR {
	ArrayList<FuncFrame> text;
	ArrayList<FuncFrame> init;
	ArrayList<String> globals;
	ArrayList<String> externs;
	HashMap<String, Integer> varSize;

	ArrayList<Pair<String, Integer>> uninitMem;
	ArrayList<Pair<String, ArrayList<Byte>>> initMem;

	public LinearIR() {
		text = new ArrayList<>();
		init = new ArrayList<>();
		globals = new ArrayList<>();
		varSize = new HashMap<>();
		globals.add("getInt");
		varSize.put("getInt", ConstVar.intLen);
		externs = new ArrayList<>();
	}
	public void insertTextFunc(FuncFrame func) {
		text.add(func);
		globals.add(func.getName());
		varSize.put(func.getName(), func.getRetSize());
	}
	public void insertVar(String str, Integer sz) {
		globals.add(str);
		varSize.put(str, sz);
	}
	public void insertInitFunc(FuncFrame func) {
		init.add(func);
	}
	public void print() {
		System.out.println("General Symbols: ");
		for (int i = 0; i < text.size(); ++i) {
			text.get(i).print();
		}
	}
	public ArrayList<String> getGlobals() {
		return globals;
	}
	public ArrayList<FuncFrame> getText() {
		return text;
	}
	public HashMap<String, Integer> getVarSize() {
		return varSize;
	}
	public ArrayList<Pair<String, Integer>> getUninitMem() {
		return getUninitMem();
	}
	public ArrayList<Pair<String, ArrayList<Byte>>> getInitMem() {
		return initMem;
	}
	public void addUninitMem(String name, int len) {
		uninitMem.add(new Pair<>(name, len));
	}
	public void addInitMem(String name, ArrayList<Byte> list) {
		initMem.add(new Pair<>(name, list));
	}
	public void addInitMem(String name, byte[] list) {
		ArrayList<Byte> b = new ArrayList<>();
		for (byte by: list) b.add(by);
		initMem.add(new Pair<>(name, b));
	}
	public void insertExterns(String func) {
		externs.add(func);
	}
	public ArrayList<String> getExterns() {
		return externs;
	}
}
