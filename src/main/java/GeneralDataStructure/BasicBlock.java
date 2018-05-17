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
		codes.addFirst(new PhiQuad("phi", new Register(x), new ArrayList<>()));
	}

	public void addPhiParams(HashSet<String> nameList, HashMap<String, Stack<String>> nameStack) {
		for (int i = 0; i < codes.size(); ++i) {
			Quad c = codes.get(i);
			if (! (c instanceof PhiQuad)) break;
			String rt = c.getRtName();
			if (nameList.contains(rt)) c.addPhiParams(nameStack.get(rt).peek());
		}
	}

	String getName() {
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
		System.out.printf("BasicBlock %s: \n", name);
		for (int i = 0; i < codes.size(); ++i) {
			codes.get(i).print();
		}

		System.out.println();
		System.out.print("(From:");
		for (int i = 0; i < preps.size(); ++i) {
			System.out.print(" " + preps.get(i).getName());
		}
		System.out.println(")");
		System.out.print("(To:");
		for (int i = 0; i < succs.size(); ++i) {
			System.out.print(" " + succs.get(i).getName());
		}
		System.out.println(")");
		System.out.println();
	}
}
