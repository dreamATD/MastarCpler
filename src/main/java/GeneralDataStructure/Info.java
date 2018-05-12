package GeneralDataStructure;

import GeneralDataStructure.TypeSystem.*;

public class Info {
	String reg;
	TypeRef type;
	String val;
	boolean certain;
	public Info(String regName, TypeRef tp) {
		reg = regName;
		type = tp;
		certain = false;
	}
	public void setReg(String name) {
		reg = name;
	}
	public String getRegName() {
		return reg;
	}
	public void setCertainValue(String str) {
		certain = true;
		val = str;
	}
	public void setUncertainValue(String str) {
		certain = false;
		val = str;
	}
	public void modifyRegName(String nReg) {
		reg = nReg;
	}
	public boolean isCertain() {
		return certain;
	}
	public String getValue() {
		return val;
	}

}
