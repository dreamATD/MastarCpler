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
	public MemAccess(Oprand b, Oprand c, Oprand s, Oprand o) {
		base = b;
		offsetCnt = c;
		offsetSize = s;
		offset = o;
		ac = "qword [" + b.get() + '+' + c.get() + '*' + s.get() + '+' + o.get() + ']';
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
		ac = "qword [";
		if (base != null) ac += base.get();
		if (offsetCnt != null) ac += "+" + String.format("%s*%s", offsetCnt.get(), offsetSize.get());
		if (offset != null) ac += "+" + offset.get();
		ac += "]";
		return ac;
	}
	@Override public void set(String v) {
		ac = v;
	}
	@Override public MemAccess copy() {
		if (base == null) return new MemAccess(ac);
		if (offset == null && offsetSize == null && offsetCnt == null) return new MemAccess(base.copy());
		if (offset != null && offsetCnt == null && offsetSize == null) return new MemAccess(base.copy(), offset.copy());
		if (offset == null && offsetCnt != null && offsetSize != null) return new MemAccess(base.copy(), offsetCnt.copy(), offsetSize.copy());
		return new MemAccess(base, offsetCnt, offsetSize, offset);
	}
}
