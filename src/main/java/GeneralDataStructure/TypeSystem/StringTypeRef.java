package GeneralDataStructure.TypeSystem;

import static Utilizer.ConstVar.addrLen;

public class StringTypeRef extends SpecialTypeRef {
	public StringTypeRef() {
		typeId = "string";
		size = addrLen;
	}
	public StringTypeRef copy() {
		return new StringTypeRef();
	}
	@Override public boolean equals (TypeRef other) {
		return (other instanceof StringTypeRef);
	}
}

