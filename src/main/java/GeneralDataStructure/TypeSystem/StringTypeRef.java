package GeneralDataStructure.TypeSystem;

public class StringTypeRef extends ClassTypeRef {
	public StringTypeRef() {
		typeId = "string";
		size = 0;
	}
	public StringTypeRef copy() {
		return new StringTypeRef();
	}
	@Override public boolean equals (TypeRef other) {
		return (other instanceof StringTypeRef);
	}
}

