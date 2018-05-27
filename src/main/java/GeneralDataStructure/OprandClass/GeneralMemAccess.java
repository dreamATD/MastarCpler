package GeneralDataStructure.OprandClass;

public class GeneralMemAccess extends MemAccess {
	public GeneralMemAccess(String name) {
		super(name);
	}
	@Override public String get() {
		return String.format("qword [rel %s]", ac);
	}
	public String getName() {
		return ac;
	}
	@Override public void set(String name) {
		ac = name;
	}
	@Override public GeneralMemAccess copy() {
		return new GeneralMemAccess(ac);
	}
}
