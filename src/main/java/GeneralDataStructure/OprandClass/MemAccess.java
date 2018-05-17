package GeneralDataStructure.OprandClass;

public class MemAccess extends Oprand {
	String ac;
	public MemAccess(String str) {
		ac = str;
	}
	@Override public String get() {
		return ac;
	}
	@Override public void set(String v) {
		ac = v;
	}
}
