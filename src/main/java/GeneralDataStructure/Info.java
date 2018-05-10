package GeneralDataStructure;

import GeneralDataStructure.TypeSystem.*;

public class Info {
	String reg;
	TypeRef type;
	boolean certain;
	String valForString;
	boolean valForBoolean;
	int valForInt;
	public Info(String regName, TypeRef tp) {
		reg = regName;
		type = tp;
		certain = false;
	}
	public String getRegName() {
		return reg;
	}
	public void setValue(String str) {
		certain = true;
		valForString = str;
	}
	public void setValue(int i) {
		certain = true;
		valForInt = i;
	}
	public void setBoolean(boolean b) {
		certain = true;
		valForBoolean = b;
	}

}
