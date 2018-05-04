package GeneralDataStructure;

import GeneralDataStructure.TypeSystem.*;

public class Info {
	String reg;
	TypeRef type;
	public Info(String regName, TypeRef tp) {
		reg = regName;
		type = tp;
	}
	public String getRegName() {
		return reg;
	}
}
