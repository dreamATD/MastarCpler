package GeneralDataStructure.TypeSystem;

public class ClassTypeRef extends SingleTypeRef {
	ClassDefTypeRef belong;
	public ClassTypeRef(){
		size = 0;
	}
	public ClassTypeRef(String className) {
		typeId = className;
		size = 0;
	}
	public ClassTypeRef(String className, ClassDefTypeRef b) {
		typeId = className;
		size = b.getSize();
		belong = b;
	}
	public void setBelongClass(ClassDefTypeRef belong) {
		this.belong = belong;
		this.size = belong.getSize();
	}
	public ClassDefTypeRef getBelongClass() {
		return belong;
	}
	public ClassTypeRef copy() {
		return new ClassTypeRef(typeId, belong);
	}
	@Override public boolean equals (TypeRef other) {
		return (other instanceof ClassTypeRef) && ((ClassTypeRef) other).typeId.equals(this.typeId);
	}
}

