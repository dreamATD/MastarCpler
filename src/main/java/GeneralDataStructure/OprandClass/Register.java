package GeneralDataStructure.OprandClass;

public class Register extends Oprand {
	String name;
	String memPos;
	String entity;
	boolean willUse;
	public Register(String n) {
		name = n;
	}
	public Register(String n, String m) {
		name = n;
		memPos = m;
	}
	public Register(String n, String m, String e) {
		name = n;
		memPos = m;
		entity = e;
	}
	public Register(String n, String m, String e, boolean w) {
		name = n;
		memPos = m;
		entity = e;
		willUse = w;
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
		if (name.charAt(0) != 'V' && name.charAt(0) != 'A') return false;
		char c = name.charAt(2);
		return '0' <= c && c <= '9';
	}
	public static boolean isAddrBase(String name) {
		return name.charAt(0) == 'A';
	}
	public void setWillUse(boolean use) {
		willUse = use;
	}

	public void setEntity(String str) {
		entity = str;
	}

	public String getEntity() {
		return entity;
	}
	@Override public Register copy() {
		return new Register(name, memPos, entity, willUse);
	}
	public boolean contains(Oprand r) {
		if (r instanceof Register && r.get().equals(name)) return true;
		return false;
	}
}
