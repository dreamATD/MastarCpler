import java.lang.reflect.Array;

public abstract class TypeRef {
	String typeId;
	int dimension;
	abstract TypeRef copy();

	static TypeRef buildTypeRef(String typeId) {
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
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other instanceof TypeRef) {
			TypeRef other2 = (TypeRef) other;
			return typeId.equals(other2.typeId) && dimension == other2.dimension;
		}
		return false;
	}
	public boolean equalsSingleType(String typeId) {
		return (!(this instanceof ArrayTypeRef) && this.typeId.equals(typeId));
	}
}
class IntTypeRef extends TypeRef {
	IntTypeRef() {
		typeId = "int";
		dimension = 0;
	}
	IntTypeRef copy() {
		return new IntTypeRef();
	}
}
class BoolTypeRef extends TypeRef {
	BoolTypeRef() {
		typeId = "bool";
		dimension = 0;
	}
	BoolTypeRef copy() {
		return new BoolTypeRef();
	}
}
class StringTypeRef extends TypeRef {
	StringTypeRef() {
		typeId = "string";
		dimension = 0;
	}
	StringTypeRef copy() {
		return new StringTypeRef();
	}
}
class VoidTypeRef extends TypeRef {
	VoidTypeRef() {
		typeId = "void";
		dimension = 0;
	}
	VoidTypeRef copy() {
		return new VoidTypeRef();
	}
}
class ClassTypeRef extends TypeRef {
	ClassTypeRef(String className) {
		typeId = className;
		dimension = 0;
	}
	ClassTypeRef copy() {
		return new ClassTypeRef(typeId);
	}
}
class ArrayTypeRef extends TypeRef {
	ArrayTypeRef (String typeAndDim) {
		dimension = 0;
		for (int i = 0; i < typeAndDim.length(); ++i) {
			if (typeAndDim.charAt(i) == ' ' || typeAndDim.charAt(i) == '[') {
				typeId = typeAndDim.substring(0, i);
				break;
			}
		}
		for (int i = 0; i < typeAndDim.length(); ++i) {
			if (typeAndDim.charAt(i) == '[') {
				++dimension;
			}
		}
	}
	ArrayTypeRef(String type, int dim) {
		typeId = type;
		dimension = dim;
	}
	ArrayTypeRef copy() {
		return new ArrayTypeRef(typeId, dimension);
	}
	TypeRef getSimpleRef() {
		if (typeId.equals("int")) return new IntTypeRef();
		if (typeId.equals("bool")) return new BoolTypeRef();
		if (typeId.equals("string")) return new StringTypeRef();
		if (typeId.equals("void")) return new VoidTypeRef();
		return new ClassTypeRef(typeId);
	}
}
