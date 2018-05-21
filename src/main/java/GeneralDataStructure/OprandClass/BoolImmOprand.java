package GeneralDataStructure.OprandClass;

public class BoolImmOprand extends ImmOprand {
	public BoolImmOprand(long t) {
		super(t);
	}
	@Override public BoolImmOprand copy() {
		return new BoolImmOprand(val);
	}
}
