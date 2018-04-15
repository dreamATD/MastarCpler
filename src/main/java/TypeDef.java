import java.util.HashMap;
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
	TypeDef(TypeRef tr) {
		typeName = tr;
		obj = new HashMap<String, DefinedEntity>();
	}
	TypeDef(TypeRef tr, DefinedEntity... objList) {
		typeName = tr;
		obj = new HashMap<String, DefinedEntity>();
		for (int i = 0; i < objList.length; ++i) {
			obj.put(objList[i].id, objList[i]);
		}
	}
	TypeDef(TypeRef tr, List<DefinedEntity> objList) {
		typeName = tr;
		obj = new HashMap<String, DefinedEntity>();
		for (int i = 0; i < objList.size(); ++i) {
			obj.put(objList.get(i).id, objList.get(i));
		}
	}
	void insertObj(String str, DefinedEntity ent) {
		obj.put(str, ent);
	}
}
