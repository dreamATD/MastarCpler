import java.lang.reflect.Array;

public class TypeRef {
	String typeId;
	int dimension;
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
	boolean equals(TypeRef other) {
		return typeId.equals(other.typeId) && dimension == other.dimension;
	}
}
class IntTypeRef extends TypeRef {
	IntTypeRef() {
		typeId = "int";
		dimension = 1;
	}
}
class BoolTypeRef extends TypeRef {
	BoolTypeRef() {
		typeId = "bool";
		dimension = 1;
	}
}
class StringTypeRef extends TypeRef {
	StringTypeRef() {
		typeId = "string";
		dimension = 1;
	}
}
class VoidTypeRef extends TypeRef {
	VoidTypeRef() {
		typeId = "void";
		dimension = 1;
	}
}
class ClassTypeRef extends TypeRef {
	ClassTypeRef(String className) {
		typeId = className;
		dimension = 1;
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
}
