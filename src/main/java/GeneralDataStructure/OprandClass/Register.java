package GeneralDataStructure.OprandClass;

public class Register extends Oprand {
	String name;
	String memPos;
	String entity;
	boolean willUse;
	public Register(String n) {
		name = n;
	}
	public Register(String n, String e) {
		name = n;
		memPos = e;
	}
	@Override public String get() {
		return name;
	}
	@Override public void set(String n) {
		name = n;
	}

	public void setMemPos(String e) {
		memPos = e;
	}

	public String getMemPos() {
		return memPos;
	}

	public static boolean isTempReg(String name) {
		if (name.charAt(0) != '@' && name.charAt(0) != '%') return false;
		char c = name.charAt(1);
		return '0' <= c && c <= '9';
	}
	public void setWillUse(boolean use) {
		willUse = use;
	}
	public boolean getWillUse() {
		return willUse;
	}

	public void setEntity(String str) {
		entity = str;
	}

	public String getEntity() {
		return entity;
	}
}
