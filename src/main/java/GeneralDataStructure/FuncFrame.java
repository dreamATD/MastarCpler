package GeneralDataStructure;

import GeneralDataStructure.QuadClass.CJumpQuad;
import GeneralDataStructure.QuadClass.JumpQuad;
import GeneralDataStructure.QuadClass.Quad;
import GeneralDataStructure.QuadClass.RetQuad;
import GeneralDataStructure.ScopeClass.LocalScope;
import GeneralDataStructure.ScopeClass.SpecialScope;
import javafx.util.Pair;

import java.util.*;

public class FuncFrame {
	String name;
	/*
	* To matching variables with their offset.
	* */
	HashMap<String, Long> localVars;
	HashMap<String, Long> params; // < 0 indicates it's in reg.
	HashMap<String, Long> classObj; // useful only when it is a class function.
	ArrayList<String> paramList;

	HashMap<String, Integer> varSize;
	ArrayList<BasicBlock> ends;
	BasicBlock first;
	ArrayList<BasicBlock> bbList;

	ArrayList<HashSet<String>> liveOut;

	long paramSize, localVarSize;

	int retSize;

	public FuncFrame(String funcName) {
		name = funcName;
		localVars = new HashMap<>();
		params = new HashMap<>();
		classObj = new HashMap<>();
		ends = new ArrayList<>();
		bbList = new ArrayList<>();
		paramSize = 0;
		varSize = new HashMap<>();
		paramList = new ArrayList<>();
	}
	public String getName() {
		return name;
	}
	public String[] getParamList() {
		String[] ret = new String[paramList.size()];
		paramList.toArray(ret);
		return ret;
	}
	public HashMap<String, Long> getParams() {
		return params;
	}
	public HashMap<String, Long> getLocalVars() {
		return localVars;
	}
	public BasicBlock getFirst() {
		return first;
	}

	public ArrayList<BasicBlock> getBbList() {
		return bbList;
	}
	public void setRetSize(int sz) {
		retSize = sz;
	}
	public int getRetSize() {
		return retSize;
	}

	public ArrayList<HashSet<String>> getLiveOut() {
		return liveOut;
	}

	public void print() {
		System.err.printf("\nFunc_def %s (", name);
		for (Map.Entry<String, Long> entry: params.entrySet()) {
			System.err.print(entry.getKey() + " ");
		}
		System.err.println("):");
		if (first  != null) {
			LinkedList<BasicBlock> q = new LinkedList<>();
			Set<String> inQueue = new HashSet<>();
			q.push(first);
			while (q.size() > 0) {
				BasicBlock u = q.pop();
				u.print();
				for (int i = 0; i < u.succs.size(); ++i) {
					BasicBlock v = u.succs.get(i);
					String name = v.getName();
					if (!inQueue.contains(name)) {
						inQueue.add(name);
						q.push(v);
					}
				}
			}
		}
		System.err.println("Func_def_done");
	}

	public void addParam(String name, int sz) {
		paramSize += sz;
		params.put(name, params.size() < 6 ? -(long)params.size() : paramSize - 48);
		varSize.put(name, sz);
		paramList.add(name);
	}

	public void addLocalVar(String name, int sz) {
		localVars.put(name, (long) sz);
		varSize.put(name, sz);
	}

	public void sortLocalVar() {
		ArrayList<Pair<String, Long>> vars = new ArrayList<>();
		for (Map.Entry<String, Long> entry: localVars.entrySet()) {
			vars.add(new Pair<>(entry.getKey(), entry.getValue()));
		}

		Collections.sort(vars, (x, y) -> x.getValue() < y.getValue() ? 1 : 0);
		localVars.clear();

		localVarSize = 8;
		for (int i = 0; i < vars.size(); ++i) {
			String key = vars.get(i).getKey();
			long sz = vars.get(i).getValue();
			if (localVarSize % sz != 0) localVarSize = sz * ((localVarSize + sz - 1) / sz);
			localVars.put(key, -localVarSize);
			localVarSize += sz;
		}
	}

	public long getParamSize() {
		return paramSize;
	}

	public long getLocalVarSize() {
		return localVarSize;
	}

	public HashMap<String, Integer> getVarSize() {
		return varSize;
	}

	public HashMap<String, Long> getClassObj() {
		return classObj;
	}

	public void setClassObj(HashMap<String, Long> map) {
		classObj = map;
	}

	public void buildCFG(ArrayList<Quad> codes) {
		int cnt = 0;
//		System.out.println(name + ": ");
//
//		for (int i = 0; i < codes.size(); ++i) {
//			codes.get(i).print();
//		}
//
//		System.out.println();
		if (codes.size() == 0) {
			first = null;
			return;
		} else {
			codes.get(0).label = name;
		}

		int size = codes.size();
		HashTable<String, BasicBlock> map = new HashTable<>();
		for (int i = 0; i < size; ++i) {
			Quad code = codes.get(i);
			String label = code.getLabel();
//			code.print();
			if (label != null) {
				map.insert(label, new BasicBlock(label));
			}
		}

		BasicBlock lastBB = null;
		Quad last = null;
		for (int i = 0; i < size; ++i) {
			Quad code = codes.get(i);
			String label = code.getLabel();
			if (label != null) {
				BasicBlock block = map.find(label);
				if (i == 0) first = block;
				if (last != null && !(last instanceof JumpQuad) && !(last instanceof CJumpQuad) && !(last instanceof RetQuad))
					lastBB.addSuccs(block);

				Quad code2 = code;
				for (int j = i; j < size; ++j) {
					last = code2;
					code2 = codes.get(j);
					if (i != j && code2.getLabel() != null) {
						i = j - 1;
						break;
					}
					block.add(code2);
					BasicBlock block1, block2;
					if (code2 instanceof CJumpQuad) {
						block1 = map.find(code2.getR1Name());
						block2 = map.find(code2.getR2Name());
						block.addSuccs(block1, block2);
						last = code2;
						break;
					} else if (code2 instanceof JumpQuad) {
						block1 = map.find(code2.getRtName());
						block.addSuccs(block1);
						last = code2;
						break;
					} else if (code2 instanceof RetQuad) {
						last = code2;
						break;
					}
				}

				if (block.succs.isEmpty()) ends.add(block);
				lastBB = block;
			}
		}
		buildBbList();
	}

	/* In DFS order. */
	public void buildBbList() {
		Stack<BasicBlock> st = new Stack<>();
		Set<String> vis = new HashSet<>();

		st.push(first);
		while (st.size() > 0) {
			BasicBlock u = st.pop();
			u.setIdx(bbList.size());
			bbList.add(u);
			for (int i = u.succs.size() - 1; i >= 0; --i) {
				BasicBlock v = u.succs.get(i);
				v.addPreps(u);
				String name = v.name;
				if (!vis.contains(name)) {
					vis.add(name);
					st.push(v);
				}

			}
		}
	}

	public void setLiveOut(ArrayList<HashSet<String>> liveOut) {
		this.liveOut = liveOut;
	}

	public String[] getFirst6Params() {
		if (params.size() == 0) return null;
		String[] ret = new String[paramList.size() >= 6 ? 6 : params.size()];
		for (int i = 0; i < ret.length; ++i) ret[i] = paramList.get(i);
		return ret;
	}

	public String[] getLastParams() {
		if (params.size() <= 6) return null;
		String[] ret = new String[params.size() - 6];
		for (Map.Entry<String, Long> entry: params.entrySet()) {
			if (entry.getValue() > 0) {
				ret[entry.getValue().intValue() - 8 >> 3] = entry.getKey();
			}
		}
		return ret;
	}

	public void addInit(ArrayList<FuncFrame> initFuncs) {
		for (FuncFrame func: initFuncs) {
			bbList.get(0).addInitFunc(func.getName());
		}
	}

	public boolean containsParam(String str) {
		return params.containsKey(str);
	}

	public boolean containsLocalVar(String str) {
		return localVars.containsKey(str);
	}
}
