package GeneralDataStructure.OprandClass;

public abstract class Oprand {
	public abstract String get();
	public abstract void set(String v);
	public abstract Oprand copy();
	public boolean contains(Oprand r) {
		return false;
	}
}
