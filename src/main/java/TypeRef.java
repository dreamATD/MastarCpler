import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TypeRef {
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
	boolean equalsSingleType(String type) {
		return (this instanceof SingleTypeRef) && (((SingleTypeRef) this).typeId.equals(type));
	}
	abstract boolean equals(TypeRef other);
}
abstract class VarTypeRef extends TypeRef {
	abstract VarTypeRef copy();
}
abstract class SingleTypeRef extends VarTypeRef {
	String typeId;
}
class IntTypeRef extends SingleTypeRef {
	IntTypeRef() {
		typeId = "int";
	}
	IntTypeRef copy() {
		return new IntTypeRef();
	}
	@Override boolean equals (TypeRef other) {
		return (other instanceof IntTypeRef);
	}
}
class BoolTypeRef extends SingleTypeRef {
	BoolTypeRef() {
		typeId = "bool";
	}
	BoolTypeRef copy() {
		return new BoolTypeRef();
	}
	@Override boolean equals (TypeRef other) {
		return (other instanceof BoolTypeRef);
	}
}
class StringTypeRef extends ClassTypeRef {
	StringTypeRef() {
		typeId = "string";
	}
	StringTypeRef copy() {
		return new StringTypeRef();
	}
	@Override boolean equals (TypeRef other) {
		return (other instanceof StringTypeRef);
	}
}
class VoidTypeRef extends SingleTypeRef {
	VoidTypeRef() {
		typeId = "void";
	}
	VoidTypeRef copy() {
		return new VoidTypeRef();
	}
	@Override boolean equals (TypeRef other) {
		return (other instanceof VoidTypeRef);
	}
}
class ClassTypeRef extends SingleTypeRef {
	ClassTypeRef(){}
	ClassTypeRef(String className) {
		typeId = className;
	}
	ClassTypeRef copy() {
		return new ClassTypeRef(typeId);
	}
	@Override boolean equals (TypeRef other) {
		return (other instanceof ClassTypeRef) && ((ClassTypeRef) other).typeId.equals(this.typeId);
	}
}
class ArrayTypeRef extends VarTypeRef {
	SingleTypeRef type;
	int dimension;
	ArrayTypeRef (String typeAndDim) {
		dimension = 0;
		for (int i = 0; i < typeAndDim.length(); ++i) {
			if (typeAndDim.charAt(i) == ' ' || typeAndDim.charAt(i) == '[') {
				type = (SingleTypeRef) TypeRef.buildTypeRef(typeAndDim.substring(0, i));
				break;
			}
		}
		for (int i = 0; i < typeAndDim.length(); ++i) {
			if (typeAndDim.charAt(i) == '[') {
				++dimension;
			}
		}
	}
	ArrayTypeRef(String typeId, int dim) {
		type = (SingleTypeRef) TypeRef.buildTypeRef(typeId);
		dimension = dim;
	}
	ArrayTypeRef copy() {
		return new ArrayTypeRef(type.typeId, dimension);
	}
	SingleTypeRef getSimpleRef() {
		return type;
	}
	boolean equals(TypeRef other) {
		if (!(other instanceof ArrayTypeRef)) return false;
		return this.type.equals(((ArrayTypeRef) other).type) && this.dimension == ((ArrayTypeRef) other).dimension;
	}
}
class FuncTypeRef extends TypeRef {
	VarTypeRef retType;
	List<VarTypeRef> params;
	FuncTypeRef(VarTypeRef ret) {
		retType = ret;
		params = new ArrayList<VarTypeRef>();
	}
	FuncTypeRef(VarTypeRef ret, VarTypeRef... paramList) {
		retType = ret;
		params = new ArrayList<VarTypeRef>();
		for (int i = 0; i < paramList.length; ++i) {
			params.add(paramList[i]);
		}
	}
	void insert(VarTypeRef p) {
		params.add(p);
	}
	boolean equals(TypeRef other) {
		if (!(other instanceof FuncTypeRef)) return false;
		if (!this.retType.equals(((FuncTypeRef) other).retType)) return false;
		if (this.params.size() != ((FuncTypeRef) other).params.size()) return false;
		for (int i = 0; i < this.params.size(); ++i) {
			if (!this.params.get(i).equals(((FuncTypeRef) other).params.get(i))) return false;
		}
		return true;
	}
	boolean matchForm(FuncExprNode nod) {
		if (nod.sons.size() != params.size()) return false;
		for (int i = 0; i < params.size(); ++i) {
			Node son = nod.sons.get(i);
			if (!son.type.equalsSingleType("void") && !son.type.equals(params.get(i))) return false;
		}
		return true;
	}
}
class ClassDefTypeRef extends TypeRef {
	Map<String, TypeRef> objs;
	ClassDefTypeRef() {
		objs = new HashMap<String, TypeRef>();
	}
	ClassDefTypeRef(Map<String, TypeRef> objList) {
		objs = new HashMap<String, TypeRef>(objList);
	}
	void insertObj(String key, TypeRef val) {
		objs.put(key, val);
	}
	boolean equals(TypeRef other) {
		if (!(other instanceof ClassDefTypeRef)) return false;
		if (this.objs.size() != ((ClassDefTypeRef) other).objs.size()) return false;
		for (String data: this.objs.keySet()) {
			if (!((ClassDefTypeRef) other).objs.containsKey(data) || !((ClassDefTypeRef) other).objs.get(data).equals(this.objs.get(data))) return false;
		}
		return true;
	}
	TypeRef checkObj(Node nod) throws SyntaxError {
		if (!objs.containsKey(nod.id)) throw new NoDefinedVarError(nod.loc);
		TypeRef type = objs.get(nod.id);
		if (nod instanceof FuncExprNode) {
			if (!(type instanceof FuncTypeRef)) throw new NoDefinedVarError(nod.loc);
			if (!((FuncTypeRef) type).matchForm((FuncExprNode) nod)) throw new NoDefinedVarError(nod.loc);
			return ((FuncTypeRef) type).retType;
		} else if (nod instanceof ArrExprNode) {
			if (!(type instanceof ArrayTypeRef)) throw new NoCastExpr(nod.loc);
			int dim = ((ArrayTypeRef) type).dimension - nod.sons.size() + 1;
			if (dim < 0) throw new NullPointer(nod.loc);
			if (dim == 0) return ((ArrayTypeRef) type).getSimpleRef();
			return new ArrayTypeRef(((ArrayTypeRef) type).getSimpleRef().typeId, dim);
		} else {
			if (!(type instanceof VarTypeRef)) throw new NoDefinedVarError(nod.loc);
			return type;
		}
	}
}
