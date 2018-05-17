package GeneralDataStructure.OprandClass;

public class MemSpace extends Oprand {
	String sp;
	public MemSpace(String str) {
		sp = str;
	}
	@Override public String get() {
		return sp;
	}
	@Override public void set(String str) {
		sp = str;
	}
}
