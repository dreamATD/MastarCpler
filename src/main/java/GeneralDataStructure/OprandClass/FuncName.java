package GeneralDataStructure.OprandClass;

public class FuncName extends Oprand {
	String func;
	public FuncName(String f) {
		func = f;
	}
	@Override public String get() {
		return func;
	}
	@Override public void set(String f) {
		func = f;
	}
	@Override public FuncName copy() {
		return new FuncName(func);
	}
}
