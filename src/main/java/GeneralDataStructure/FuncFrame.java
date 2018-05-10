package GeneralDataStructure;

import GeneralDataStructure.ScopeClass.LocalScope;
import GeneralDataStructure.ScopeClass.SpecialScope;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class FuncFrame {
	LocalScope<Info> funcScope;
	SpecialScope<Info> envScope;
	ArrayList<String> params;
	BasicBlock first;
	String name;
	public FuncFrame(String funcName, LocalScope<Info> scope, SpecialScope<Info> env) {
		name = funcName;
		funcScope = scope;
		envScope = env;
		params = new ArrayList<>();
	}
	public void print() {
		System.out.printf("\nFunc_def %s (", name);
		for (int i = 0; i < params.size(); ++i) {
			System.out.print(params.get(i));
			if (i < params.size() - 1) System.out.print(", ");
		}
		System.out.println("):");
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
		System.out.println("Func_def_done");
	}
	public void addParam(String reg) {
		params.add(reg);
	}
	public void buildCFG(ArrayList<Quad> codes) {
		System.out.println(name + ": ");

		for (int i = 0; i < codes.size(); ++i) {
			codes.get(i).print();
		}

		System.out.println();
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
			if (label != null) {
				map.insert(label, new BasicBlock(label));
			}
		}
		for (int i = 0; i < size; ++i) {
			Quad code = codes.get(i);
			String label = code.getLabel();
			if (label != null) {
				BasicBlock block = map.find(label);
				if (i == 0) first = block;
				block.add(code);

				INBLOCK:
				for (int j = i + 1; j < size; ++j) {
					Quad code2 = codes.get(j);
					if (code2.getLabel() != null) {
						i = j - 1;
						break;
					}
					block.add(code2);
					BasicBlock block1, block2;
					switch (code2.getOp()) {
						case "cbr": case "tbr":
							block1 = map.find(code2.getR1());
							block2 = map.find(code2.getR2());
							block.addSuccs(block1, block2);
							block1.addPreps(block);
							block2.addPreps(block);
							break INBLOCK;
						case "jump":
							block1 = map.find(code2.getRt());
							block.addSuccs(block1);
							block1.addPreps(block);
							break INBLOCK;
						case "ret":
							break INBLOCK;
					}
				}
			}
		}
	}
}
