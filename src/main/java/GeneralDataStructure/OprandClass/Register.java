package GeneralDataStructure.OprandClass;

public class Register extends Oprand {
	String name;
	public Register(String n) {
		name = n;
	}
	@Override public String get() {
		return name;
	}
	@Override public void set(String n) {
		name = n;
	}
}
