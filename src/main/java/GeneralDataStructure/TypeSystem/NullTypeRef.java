package GeneralDataStructure.TypeSystem;

// null isn't void type;

public class NullTypeRef extends SingleTypeRef {
	public NullTypeRef() {
		typeId = "null";
		size = 4;
	}
	public NullTypeRef copy() {
		return new NullTypeRef();
	}
	@Override public boolean equals (TypeRef other) {
		return (other instanceof NullTypeRef);
	}
}

