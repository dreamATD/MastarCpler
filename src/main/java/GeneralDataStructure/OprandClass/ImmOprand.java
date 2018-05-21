package GeneralDataStructure.OprandClass;

import Utilizer.ConstVar;

public class ImmOprand extends Oprand {
	long val;
	public ImmOprand(long v) {
		val = v;
	}
	@Override public String get() {
		return Long.toString(val);
	}
	@Override public void set(String v) {
		val = Integer.parseInt(v);
	}
	public long getVal() {
		return val;
	}
	public int getSize() {
		return this instanceof BoolImmOprand ? ConstVar.boolLen : ConstVar.intLen;
	}
	@Override public ImmOprand copy() {
		return new ImmOprand(val);
	}
}
