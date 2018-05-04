package GeneralDataStructure.TypeSystem;

import java.util.ArrayList;
import java.util.List;

public class FuncTypeRef extends TypeRef {
	VarTypeRef retType;
	List<VarTypeRef> params;
	public FuncTypeRef(VarTypeRef ret) {
		retType = ret;
		params = new ArrayList<VarTypeRef>();
		size = 0;
	}
	public FuncTypeRef(VarTypeRef ret, VarTypeRef... paramList) {
		retType = ret;
		params = new ArrayList<VarTypeRef>();
		for (int i = 0; i < paramList.length; ++i) {
			params.add(paramList[i]);
		}
		size = 0;
	}
	public int getParamsCnt() {
		return params.size();
	}
	public VarTypeRef getParam(int ind) {
		return params.get(ind);
	}
	public VarTypeRef getReturnType() {
		return retType;
	}
	public void insert(VarTypeRef p) {
		params.add(p);
	}
	public boolean equals(TypeRef other) {
		if (!(other instanceof FuncTypeRef)) return false;
		if (!this.retType.equals(((FuncTypeRef) other).retType)) return false;
		if (this.params.size() != ((FuncTypeRef) other).params.size()) return false;
		for (int i = 0; i < this.params.size(); ++i) {
			if (!this.params.get(i).equals(((FuncTypeRef) other).params.get(i))) return false;
		}
		return true;
	}
}

