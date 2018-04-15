import java.util.ArrayList;
import java.util.List;

public class DefinedEntity {
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
		type = new TypeDef(node.type, obj);
		loc = node.loc;
	}
}
class DefinedVariable extends DefinedEntity {
	DefinedVariable(String str, Location l) {
		super(str, l);
	}
	DefinedVariable(String str, TypeDef ret, Location l) {
		super(str, ret, l);
	}
	DefinedVariable(VarDefNode node) {
		id = node.id;
		type = new TypeDef(node.type);
		loc = node.loc;
	}
	static List<DefinedVariable> splitAndConstr(VarDefStatNode node) {
		List<DefinedVariable> obj = new ArrayList<DefinedVariable>();
		for (int i = 0; i < node.sons.size(); ++i) {
			obj.add(new DefinedVariable((VarDefNode) node.sons.get(i)));
		}
		return obj;
	}
}
