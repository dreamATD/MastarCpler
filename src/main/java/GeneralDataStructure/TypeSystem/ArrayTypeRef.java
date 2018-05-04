package GeneralDataStructure.TypeSystem;


public class ArrayTypeRef extends VarTypeRef {
	VarTypeRef type;
	int scale;
	FuncTypeRef getSize;
	public ArrayTypeRef(VarTypeRef tp) {
		type = tp.copy();
		scale = -1;
		size = 0;
	}
	public ArrayTypeRef(VarTypeRef tp, int sc) {
		type = tp.copy();
		scale = sc;
		size = tp.size * sc;
	}
	public ArrayTypeRef(String typeId, int dim) {
		VarTypeRef tmp = (VarTypeRef) TypeRef.buildTypeRef(typeId);
		if (dim == 1) {
			type = tmp;
			return;
		}
		for (int i = 1; i < dim; ++i) {
			tmp =  new ArrayTypeRef(tmp);
		}
		type = tmp;
		getSize = new FuncTypeRef(new IntTypeRef());
	}
	public ArrayTypeRef copy() {
		return new ArrayTypeRef(type.copy(), scale);
	}
	public SingleTypeRef getSimpleRef() {
		if (this.type instanceof SingleTypeRef) return (SingleTypeRef) this.type;
		return ((ArrayTypeRef) this.type).getSimpleRef();
	}
	public VarTypeRef getInnerType() {
		return type;
	}
	public boolean equals(TypeRef other) {
		if (!(other instanceof ArrayTypeRef)) return false;
		ArrayTypeRef tmp = (ArrayTypeRef) other;
		boolean a = (this.type instanceof SingleTypeRef), b = (tmp.type instanceof SingleTypeRef);
		if (a && b) return this.type.equals(tmp.type);
		if (a || b) return false;
		return this.type.equals(tmp.type);
	}
	public int getScale() {
		return scale;
	}
}

