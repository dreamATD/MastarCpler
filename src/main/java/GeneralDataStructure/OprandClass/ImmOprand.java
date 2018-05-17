package GeneralDataStructure.OprandClass;

public class ImmOprand extends Oprand {
	int val;
	public ImmOprand(int v) {
		val = v;
	}
	@Override public String get() {
		return Integer.toString(val);
	}
	@Override public void set(String v) {
		val = Integer.parseInt(v);
	}
	public int getVal() {
		return val;
	}
}
