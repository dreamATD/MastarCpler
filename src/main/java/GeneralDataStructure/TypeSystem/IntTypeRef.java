package GeneralDataStructure.TypeSystem;

public class IntTypeRef extends SimpleTypeRef {
	public IntTypeRef() {
		typeId = "int";
		size = 4;
	}
	public IntTypeRef copy() {
		return new IntTypeRef();
	}
	@Override public boolean equals (TypeRef other) {
		return (other instanceof IntTypeRef);
	}
}

