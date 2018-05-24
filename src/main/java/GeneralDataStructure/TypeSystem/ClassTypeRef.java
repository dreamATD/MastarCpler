package GeneralDataStructure.TypeSystem;

import static Utilizer.ConstVar.addrLen;

public class ClassTypeRef extends SpecialTypeRef {
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
	public ClassTypeRef copy() {
		return new ClassTypeRef(typeId, belong);
	}
	@Override public boolean equals (TypeRef other) {
		return (other instanceof ClassTypeRef) && ((ClassTypeRef) other).typeId.equals(this.typeId);
	}
}

