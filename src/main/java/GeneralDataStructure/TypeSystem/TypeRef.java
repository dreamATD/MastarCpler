package GeneralDataStructure.TypeSystem;

public abstract class TypeRef {
	int size;
	public static TypeRef buildTypeRef(String typeId) {
		if (typeId.contains("[")) {
			int dim = 0;
			boolean flag = true;
			String type = "";
			for (int i = 0; i < typeId.length(); ++i) {
				if (typeId.charAt(i) == '[') ++dim;
				if (!Character.isLetter(typeId.charAt(i)) && typeId.charAt(i) != '_' && flag) {
					type = typeId.substring(0, i);
					flag = false;
				}
			}
			return new ArrayTypeRef(type, dim);
		} else {
			if (typeId.equals("int")) return new IntTypeRef();
			else if (typeId.equals("bool")) return new BoolTypeRef();
			else if (typeId.equals("string")) return new StringTypeRef();
			else if (typeId.equals("void")) return new VoidTypeRef();
			else return new ClassTypeRef(typeId);
		}
	}
	public boolean equalsSingleType(String type) {
		return (this instanceof SingleTypeRef) && (((SingleTypeRef) this).typeId.equals(type));
	}
	public abstract boolean equals(TypeRef other);
	public int getSize() {
		return size;
	}
}


