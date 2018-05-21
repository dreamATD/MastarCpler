package GeneralDataStructure.TypeSystem;

import static Utilizer.ConstVar.addrLen;

public class ClassTypeRef extends SpecialTypeRef {
	ClassDefTypeRef belong;
	public ClassTypeRef(){
		size = 0;
	}
	public ClassTypeRef(String className) {
		typeId = className;
		size = addrLen;
	}
	public ClassTypeRef(String className, ClassDefTypeRef b) {
		typeId = className;
		size = addrLen;
		belong = b;
	}
	public void setBelongClass(ClassDefTypeRef belong) {
		this.belong = belong;
		this.size = addrLen;
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

