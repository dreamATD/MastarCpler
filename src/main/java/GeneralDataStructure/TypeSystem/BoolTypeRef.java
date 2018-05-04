package GeneralDataStructure.TypeSystem;

public class BoolTypeRef extends SimpleTypeRef {
	public BoolTypeRef() {
		typeId = "bool";
		size = 1;
	}
	public BoolTypeRef copy() {
		return new BoolTypeRef();
	}
	@Override public boolean equals (TypeRef other) {
		return (other instanceof BoolTypeRef);
	}
}

