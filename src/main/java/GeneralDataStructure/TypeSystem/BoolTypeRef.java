package GeneralDataStructure.TypeSystem;

import static Utilizer.ConstVar.boolLen;

public class BoolTypeRef extends SimpleTypeRef {
	public BoolTypeRef() {
		typeId = "bool";
		size = boolLen;
	}
	public BoolTypeRef copy() {
		return new BoolTypeRef();
	}
	@Override public boolean equals (TypeRef other) {
		return (other instanceof BoolTypeRef);
	}
}

