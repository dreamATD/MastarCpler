package GeneralDataStructure.OprandClass;

public class MemAccess extends Oprand {
	String ac;
	Oprand base;
	Oprand offset;
	Oprand offsetCnt;
	Oprand offsetSize;
	public MemAccess(Oprand b, Oprand o) {
		base = b;
		offset = o;
		offsetCnt = offsetSize = null;
		ac = b.get() + '$' + o.get();
	}
	public MemAccess(Oprand b, Oprand c, Oprand s) {
		base = b;
		offset = null;
		offsetCnt = c;
		offsetSize = s;
		ac = b.get() + '$' + c.get() + 'x' + s.get();
	}
	public MemAccess(String str) {
		ac = str;
	}
	@Override public String get() {
		return ac;
	}
	@Override public void set(String v) {
		ac = v;
	}
	@Override public MemAccess copy() {
		return new MemAccess(base, offset);
	}
}
