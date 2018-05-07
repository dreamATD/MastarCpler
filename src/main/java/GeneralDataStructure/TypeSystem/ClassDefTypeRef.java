package GeneralDataStructure.TypeSystem;

import javafx.util.Pair;

import java.util.*;

public class ClassDefTypeRef extends TypeRef {
	Map<String, Pair<TypeRef, Integer>> objs;
	public ClassDefTypeRef() {
		objs = new HashMap<>();
		size = 0;
	}
	public ClassDefTypeRef(Map<String, TypeRef> objList) {
		objs = new HashMap<>();
		for (Map.Entry<String, TypeRef> entry: objList.entrySet()) {
			TypeRef tp = entry.getValue();
			objs.put(entry.getKey(), new Pair<>(tp, size));
			if (tp instanceof VarTypeRef) size += tp instanceof SimpleTypeRef ? tp.size : 4; // for class and string
		}
	}
	public void insertObj(String key, TypeRef tp) {
		objs.put(key, new Pair<>(tp, size));
		if (tp instanceof VarTypeRef) size += tp instanceof SimpleTypeRef ? tp.size : 4; // for class and string
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
		return objs.get(str).getKey();
	}
	public int getOffset(String str) {
		return objs.get(str).getValue();
	}
}

