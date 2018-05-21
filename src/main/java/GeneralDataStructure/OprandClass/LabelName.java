package GeneralDataStructure.OprandClass;

public class LabelName extends Oprand {
	String label;
	public LabelName(String l) {
		label = l;
	}
	@Override public String get() {
		return label;
	}
	@Override public void set(String l) {
		label = l;
	}
	@Override public LabelName copy() {
		return new LabelName(label);
	}
}
