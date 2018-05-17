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
}
