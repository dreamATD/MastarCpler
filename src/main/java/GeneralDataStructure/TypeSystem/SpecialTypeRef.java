package GeneralDataStructure.TypeSystem;

import static Utilizer.ConstVar.addrLen;

public abstract class SpecialTypeRef extends SingleTypeRef {
	ClassDefTypeRef belong;
	public void setBelongClass(ClassDefTypeRef belong) {
		this.belong = belong;
		this.size = addrLen;
	}
	public ClassDefTypeRef getBelongClass() {
		return belong;
	}
	@Override public abstract SpecialTypeRef copy();
	@Override public abstract boolean equals (TypeRef other);
}
