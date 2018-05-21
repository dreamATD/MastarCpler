package GeneralDataStructure.OprandClass;

public class NullImmOprand extends ImmOprand {
	public NullImmOprand(long v) {
		super(v);
		val = 0;
	}
	@Override public NullImmOprand copy() {
		return new NullImmOprand(val);
	}
}
