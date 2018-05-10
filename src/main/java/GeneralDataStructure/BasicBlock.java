package GeneralDataStructure;

import GeneralDataStructure.MyListClass.MyList;

import java.util.ArrayList;

public class BasicBlock {
	MyList<Quad> code;
	ArrayList<BasicBlock> preps, succs;
	String name;

	BasicBlock(String label) {
		name = label;
		preps = new ArrayList<>();
		succs = new ArrayList<>();
		code = new MyList<>();
	}

	String getName() {
		return name;
	}

	public void add(Quad data) {
		code.add(data);
	}

	public Quad get(int idx) {
		return code.get(idx);
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
		for (int i = 0; i < code.size(); ++i) {
			code.get(i).print();
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
