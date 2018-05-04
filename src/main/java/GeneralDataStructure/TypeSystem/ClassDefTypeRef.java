package GeneralDataStructure.TypeSystem;

import java.util.*;

public class ClassDefTypeRef extends TypeRef {
	Map<String, TypeRef> objs;
	public ClassDefTypeRef() {
		objs = new HashMap<String, TypeRef>();
		size = 0;
	}
	public ClassDefTypeRef(Map<String, TypeRef> objList) {
		objs = new HashMap<String, TypeRef>(objList);
		for (Map.Entry<String, TypeRef> entry: objs) {
			TypeRef tp = entry.getValue();
			size += tp.size == 0 ? 4 : tp.size; // for class and string
		}
	}
	public void insertObj(String key, TypeRef val) {
		objs.put(key, val);
		size += val.size == 0 ? 4 : val.size;
	}
	public boolean equals(TypeRef other) {
		if (!(other instanceof ClassDefTypeRef)) return false;
		if (this.objs.size() != ((ClassDefTypeRef) other).objs.size()) return false;
		for (String data: this.objs.keySet()) {
			if (!((ClassDefTypeRef) other).objs.containsKey(data) || !((ClassDefTypeRef) other).objs.get(data).equals(this.objs.get(data))) return false;
		}
		return true;
	}
	public boolean containsEntity(String str) {
		return objs.containsKey(str);
	}
	public TypeRef getEntity(String str) {
		return objs.get(str);
	}
}

