package GeneralDataStructure.TypeSystem;

import static Utilizer.ConstVar.intLen;

public class IntTypeRef extends SimpleTypeRef {
	public IntTypeRef() {
		typeId = "int";
		size = intLen;
	}
	public IntTypeRef copy() {
		return new IntTypeRef();
	}
	@Override public boolean equals (TypeRef other) {
		return (other instanceof IntTypeRef);
	}
}

