import java.util.ArrayList;
import java.util.List;

public abstract class DefinedEntity {
	String id;
	TypeDef type;
	Location loc;
	DefinedEntity() {}
	DefinedEntity(String str, Location l) {
		id = str;
		loc = l;
	}
	DefinedEntity(String str, TypeDef tp, Location l){
		id = str;
		type = tp;
		loc = l;
	}
	abstract DefinedEntity copy();
	/* for variable and object access and function */
	static void matchForm(Node node, DefinedEntity entity) throws SyntaxError {
		if (node instanceof VarExprNode) {
			if (entity instanceof DefinedVariable) {
				node.type = entity.type.typeName;
				return;
			}
			throw new DisMatchedFormError(node.loc);
		} else if (node instanceof ObjAccExprNode) {
			if (entity instanceof DefinedVariable) {
				Node tmp = node.sons.get(1);
				matchForm(tmp, entity.type.searchObj(tmp.id, tmp.loc));
				node.type = tmp.type;
			} else throw new DisMatchedFormError(node.loc);
		} else if (node instanceof FuncExprNode) {
			if ((entity instanceof DefinedFunc) && entity.type.matchParamType(node.sons));
			else throw new DisMatchedFormError(node.loc);
		}
	}
}
class DefinedFunc extends DefinedEntity {
	DefinedFunc(String str, TypeDef ret, Location l) {
		super(str, ret, l);
	}
	DefinedFunc(FuncDefNode node) {
		id = node.id;
		List<DefinedEntity> obj = new ArrayList<DefinedEntity>();
		if (node.sons.size() > 1) {
			for (int i = 0; i < node.sons.size() - 1; ++i) {
				obj.add(new DefinedVariable((VarDefNode) node.sons.get(i)));
			}
		}
		type = new TypeDef(node.type, obj);
		loc = node.loc;
	}
	@Override DefinedFunc copy() {
		return new DefinedFunc(this.id, new TypeDef(this.type), this.loc);
	}
}
class DefinedClass extends DefinedEntity {
	DefinedClass(String str, TypeDef tp, Location l) {
		id = str;
		type = tp;
		loc = l;
	}
	DefinedClass(ClassDefNode node) {
		id = node.id;
		List<DefinedEntity> obj = new ArrayList<DefinedEntity>();
		for (int i = 0; i < node.sons.size(); ++i) {
			if (node.sons.get(i) instanceof VarDefStatNode) {
				obj.addAll(DefinedVariable.splitAndConstr((VarDefStatNode) node.sons.get(i)));
			} else {
				obj.add(new DefinedFunc((FuncDefNode) node.sons.get(i)));
			}
		}
		type = new TypeDef(new ClassTypeRef(node.id), obj);
		loc = node.loc;
		System.out.println("buildClass: " + id + " " + type.getDim());
	}
	@Override DefinedClass copy() {
		return new DefinedClass(this.id, new TypeDef(this.type), this.loc);
	}
}
class DefinedVariable extends DefinedEntity {
	/* only if the type is int or string, the value has meanings */
	String value;
	int dimension;
	DefinedVariable(String str, Location l) {
		super(str, l);
	}
	DefinedVariable(String str, TypeDef ret, Location l) {
		super(str, ret, l);
	}
	DefinedVariable(VarDefNode node) {
		id = node.id;
		if (node.type instanceof ArrayTypeRef) {
			type = new TypeDef((((ArrayTypeRef) node.type).getSimpleRef()));
//			dimension = node.type.dimension;
		} else {
			type = new TypeDef(node.type);
			dimension = 0;
		}
		loc = node.loc;
	}
	static List<DefinedVariable> splitAndConstr(VarDefStatNode node) {
		List<DefinedVariable> obj = new ArrayList<DefinedVariable>();
		for (int i = 0; i < node.sons.size(); ++i) {
			obj.add(new DefinedVariable((VarDefNode) node.sons.get(i)));
		}
		return obj;
	}
	@Override DefinedVariable copy() {
		return new DefinedVariable(this.id, new TypeDef(this.type), this.loc);
	}
}
