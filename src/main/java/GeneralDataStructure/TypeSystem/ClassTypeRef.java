package GeneralDataStructure.TypeSystem;

public class ClassTypeRef extends SingleTypeRef {
	public ClassTypeRef(){
		size = 0;
	}
	public ClassTypeRef(String className) {
		typeId = className;
		size = 0;
	}
	public ClassTypeRef copy() {
		return new ClassTypeRef(typeId);
	}
	@Override public boolean equals (TypeRef other) {
		return (other instanceof ClassTypeRef) && ((ClassTypeRef) other).typeId.equals(this.typeId);
	}
}

