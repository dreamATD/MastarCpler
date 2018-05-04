package GeneralDataStructure.TypeSystem;

public class VoidTypeRef extends SingleTypeRef {
	public VoidTypeRef() {
		typeId = "void";
		size = 0;
	}
	public VoidTypeRef copy() {
		return new VoidTypeRef();
	}
	@Override public boolean equals (TypeRef other) {
		return (other instanceof VoidTypeRef);
	}
}

