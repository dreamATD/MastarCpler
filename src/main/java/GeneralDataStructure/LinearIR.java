package GeneralDataStructure;

import Utilizer.ConstVar;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class LinearIR {
	ArrayList<FuncFrame> text;
	ArrayList<FuncFrame> init;
	ArrayList<String> globals;
	ArrayList<String> externs;
	HashMap<String, Integer> varSize;

	ArrayList<Pair<String, Integer>> uninitMem;
	ArrayList<Pair<String, String>> initMem;
	ArrayList<Pair<String, String>> roData;

	public LinearIR() {
		text = new ArrayList<>();
		init = new ArrayList<>();
		globals = new ArrayList<>();
		varSize = new HashMap<>();
//		varSize.put("getInt", ConstVar.intLen);
		externs = new ArrayList<>();
		uninitMem = new ArrayList<>();
		initMem = new ArrayList<>();
		roData = new ArrayList<>();
	}
	public String insertStrConst(String str) {
		String pos = "S_" + Integer.toString(roData.size());
		addRoData(pos, str.getBytes());
		return pos;
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
		return uninitMem;
	}
	public ArrayList<Pair<String,String>> getInitMem() {
		return initMem;
	}
	public ArrayList<Pair<String, String>> getRoData() {
		return roData;
	}
	public ArrayList<FuncFrame> getInit() {
		return init;
	}
	public void addUninitMem(String name, int len) {
		uninitMem.add(new Pair<>(name, len));
	}
	public void addInitMem(String name, byte[] list) {
		String tmp;
		String res = "";
		int len = list.length;
		for (int i = 0; i < 8; ++i) {
			tmp = i < list.length ? Integer.toHexString(list[i]).toUpperCase() : null;
			if (tmp != null && tmp.length() == 1) tmp = '0' + tmp;
			res += (i < list.length ? tmp : "00");
		}
		if (8 <= len) res += "H";
		for (int i = 8; i < len; i += 8) {
			res += ", ";
			for (int j = i; j - i < 8; ++j) {
				tmp = j < list.length ? Integer.toHexString(list[j]).toUpperCase() : null;
				if (tmp != null && tmp.length() == 1) tmp = '0' + tmp;
				res += j < list.length ? tmp : "00";
			}
			res += "H";
		}
		initMem.add(new Pair<>(name, res));
	}
	public void addInitMem(String name, byte[] list, int len) {
		String tmp;
		String res = "";
		for (int i = 0; i < 8; ++i) {
			tmp = i < list.length ? Integer.toHexString(list[i]).toUpperCase() : null;
			if (tmp != null && tmp.length() == 1) tmp = '0' + tmp;
			res += (i < list.length ? tmp : "00");
		}
		if (8 < len) res += "H";
		for (int i = 8; i < len; i += 8) {
			res += ", ";
			for (int j = i; j - i < 8; ++j) {
				tmp = j < list.length ? Integer.toHexString(list[j]).toUpperCase() : null;
				if (tmp != null && tmp.length() == 1) tmp = '0' + tmp;
				res += j < list.length ? tmp : "00";
			}
			res += "H";
		}
		initMem.add(new Pair<>(name, res));
	}
	public void addRoData(String name, byte[] list) {
		int len = list.length;
		String str = "";
		for (int i = 0; i < len; ++i) {
			String tmp = Integer.toHexString(list[i]).toUpperCase();
			if (tmp.length() == 1) tmp = '0' + tmp;
			str += tmp + "H, ";
		}
		str += "00H";
		roData.add(new Pair<>(name, str));
	}
	public void insertExterns(String func) {
		externs.add(func);
	}
	public ArrayList<String> getExterns() {
		return externs;
	}
}
