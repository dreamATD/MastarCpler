package GeneralDataStructure.OprandClass;

public class MemAccess extends Oprand {
	String ac;
	Oprand base;
	Oprand offset;
	Oprand offsetCnt;
	Oprand offsetSize;
	public MemAccess(Oprand b) {
		base = b;
		offset = offsetCnt = offsetSize = null;
		ac = "qword [" + b.get() + ']';
	}
	public MemAccess(Oprand b, Oprand o) {
		base = b;
		offset = o;
		offsetCnt = offsetSize = null;
		ac = "qword [" + b.get() + '+' + o.get() + ']';
	}
	public MemAccess(Oprand b, Oprand c, Oprand s) {
		base = b;
		offset = null;
		offsetCnt = c;
		offsetSize = s;
		ac = "qword [" + b.get() + '+' + c.get() + '*' + s.get() + ']';
	}
	public MemAccess(String str) {
		ac = str;
	}
	public Oprand getBase() {
		return base;
	}
	public Oprand getOffset() {
		return offset;
	}
	public Oprand getOffsetCnt() {
		return offsetCnt;
	}
	public Oprand getOffsetSize() {
		return offsetSize;
	}
	@Override public String get() {
		if (base != null && offsetCnt != null && offsetSize != null) {
			ac = "qword [" + base.get() + '+' + offsetCnt.get() + '*' + offsetSize.get() + ']';
		} else if (base != null && offset != null) {
			ac = "qword [" + base.get() + '+' + offset.get() + "]";
		} else if (base != null) {
			ac = "qword [" + base.get() + "]";
		}
		return ac;
	}
	@Override public void set(String v) {
		ac = v;
	}
	@Override public MemAccess copy() {
		if (base == null) return new MemAccess(ac);
		if (offset == null && offsetSize == null && offsetCnt == null) return new MemAccess(base);
		if (offset != null) return new MemAccess(base, offset);
		return new MemAccess(base, offsetCnt, offsetSize);
	}
}
