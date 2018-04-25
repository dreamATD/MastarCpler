package SemanticProc;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TypeDef {
	Map<String, DefinedEntity> obj;
	/*
	* If variable, typeName is its type
	* If function, typeName is its return type
	* If class, typeName is its class identifier
	* */
	TypeRef typeName;
	TypeDef(TypeDef other) {
		typeName = other.typeName;
		obj = new LinkedHashMap<String, DefinedEntity>();
		for (Map.Entry<String, DefinedEntity> entry: other.obj.entrySet()) {
			obj.put(entry.getKey(), entry.getValue().copy());
		}
	}
	TypeDef(TypeRef tr) {
		typeName = tr;
		obj = new LinkedHashMap<String, DefinedEntity>();
	}
	TypeDef(TypeRef tr, DefinedEntity... objList) {
		typeName = tr;
		obj = new LinkedHashMap<String, DefinedEntity>();
		for (int i = 0; i < objList.length; ++i) {
			obj.put(objList[i].id, objList[i]);
		}
	}
	TypeDef(TypeRef tr, List<DefinedEntity> objList) {
		typeName = tr;
		obj = new LinkedHashMap<String, DefinedEntity>();
		for (int i = 0; i < objList.size(); ++i) {
			obj.put(objList.get(i).id, objList.get(i));
		}
	}
	void insertObj(String str, DefinedEntity ent) {
		obj.put(str, ent);
	}
	DefinedEntity searchObj(String name, Location loc) throws SyntaxError {
		for (String data: obj.keySet()) {
			System.out.print(data + " ");
		}
		System.out.println();
		System.out.println("FindName: " + name + obj.size());
		if (!obj.containsKey(name)) throw new DisMatchedFormError(loc);
		return obj.get(name);
	}
	int getDim() {
		return obj.size();
	}
	TypeRef getTypeRef() {
		return typeName;
	}
	boolean matchParamType(List<Node> list) {
		if (list.size() != obj.size()) return false;
		int i = 0;
		for (Map.Entry<String, DefinedEntity> object : obj.entrySet()) {
			if (!list.get(i).type.equals(object.getValue().type.getTypeRef())) {
				return false;
			}
			++i;
		}
		return true;
	}
	void update(TypeDef type) {
		for (Map.Entry<String, DefinedEntity> entry: type.obj.entrySet()) {
			obj.put(entry.getKey(), entry.getValue().copy());
		}
	}
}
