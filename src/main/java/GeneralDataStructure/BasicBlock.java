package GeneralDataStructure;

import GeneralDataStructure.MyListClass.MyList;
import GeneralDataStructure.OprandClass.Register;
import GeneralDataStructure.QuadClass.PhiQuad;
import GeneralDataStructure.QuadClass.Quad;

import java.util.*;

public class BasicBlock {
	public MyList<Quad> codes;
	public ArrayList<BasicBlock> preps, succs;
	public String name;
	public int idx;

	BasicBlock(String label) {
		name = label;
		preps = new ArrayList<>();
		succs = new ArrayList<>();
		codes = new MyList<>();
	}

	public void setIdx(int cnt) {
		idx = cnt;
	}

	public int getIdx() {
		return idx;
	}

	public MyList<Quad> getCodes() {
		return codes;
	}

	public ArrayList<BasicBlock> getSuccs() {
		return succs;
	}

	public ArrayList<BasicBlock> getPreps() {
		return preps;
	}

	/* for SSA */

	public boolean containsPhi(String x) {
		for (int i = 0; i < codes.size(); ++i) {
			Quad quad = codes.get(i);
			if (!quad.op.equals("phi")) break;
			if (quad.getRtName().equals(x)) return true;
		}
		return false;
	}

	public void addPhi(String x) {
		codes.addFirst(new PhiQuad("phi", new Register(x, x), new ArrayList<>()));
		codes.get(0).setLabel(name);
	}

	public void addPhiParams(HashSet<String> nameList, HashMap<String, Stack<String>> nameStack) {
		for (int i = 0; i < codes.size(); ++i) {
			Quad c = codes.get(i);
			if (! (c instanceof PhiQuad)) break;
			String rt = ((Register) c.getRt()).getEntity();
			if (nameList.contains(rt)) c.addPhiParams(nameStack.get(rt).peek());
		}
	}

	public String getName() {
		return name;
	}

	public void add(Quad data) {
		codes.add(data);
	}

	public Quad get(int idx) {
		return codes.get(idx);
	}

	public void addSuccs(BasicBlock... succList) {
		for (int i = 0; i < succList.length; ++i) {
			succs.add(succList[i]);
		}
	}
	public void addPreps(BasicBlock... prepList) {
		for (int i = 0; i < prepList.length; ++i) {
			preps.add(prepList[i]);
		}
	}

	public void print() {
		System.err.printf("BasicBlock %d %s: \n", idx, name);
		for (int i = 0; i < codes.size(); ++i) {
			codes.get(i).print();
		}

		System.err.println();
		System.err.print("(From:");
		for (int i = 0; i < preps.size(); ++i) {
			System.err.print(" " + preps.get(i).getName());
		}
		System.err.println(")");
		System.err.print("(To:");
		for (int i = 0; i < succs.size(); ++i) {
			System.err.print(" " + succs.get(i).getName());
		}
		System.err.println(")");
		System.err.println();
	}
}
