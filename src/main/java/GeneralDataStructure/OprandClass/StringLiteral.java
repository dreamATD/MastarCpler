package GeneralDataStructure.OprandClass;

public class StringLiteral extends Oprand {
	String str;
	public StringLiteral(String s) {
		str = s;
	}
	@Override public String get() {
		return str;
	}
	@Override public void set(String s) {
		str = s;
	}
}
