package Optimizer;

import GeneralDataStructure.BasicBlock;
import GeneralDataStructure.FuncFrame;
import GeneralDataStructure.MyListClass.MyList;
import GeneralDataStructure.QuadClass.Quad;

import java.util.ArrayList;
import java.util.HashSet;

public class CodeRebuilder {
	private FuncFrame curFunc;
	private ArrayList<BasicBlock> blocks;
	private BasicBlock first;
	private HashSet<BasicBlock> blockSet;
	public CodeRebuilder(FuncFrame func) {
		curFunc = func;
		blocks = new ArrayList<>();
		first = func.getFirst0();
		blockSet = new HashSet<>();
	}

	public void rebuildFunc() {
		rebuildBasicBlock(first);
		for (int i = 0; i < curFunc.getBbList().size(); ++i) {
			curFunc.getBbList().get(i).getPreps().clear();
		}
		blockSet.clear();
		rebuildCFG(first);
		curFunc.setBbList(blocks);
	}

	private void rebuildBasicBlock(BasicBlock cur) {
		if (blockSet.contains(cur)) return;
		blockSet.add(cur);
		MyList<Quad> newList = new MyList<>(), codes = cur.getCodes();
		for (int i = 0; i < codes.size(); ++i) {
			Quad c = codes.get(i);
			if (c.getUseful()) newList.add(c);
		}
		ArrayList<BasicBlock> succs = cur.getSuccs();
		for (int i = 0; i < succs.size(); ++i) {
			rebuildBasicBlock(succs.get(i));
		}
	}

	private void rebuildCFG(BasicBlock u) {
		if (blockSet.contains(u)) return;
		blockSet.add(u);
		u.setIdx(blocks.size());
		blocks.add(u);
		ArrayList<BasicBlock> succs = u.getSuccs();
		for (int i = 0; i < succs.size(); ++i) {
			BasicBlock v = succs.get(i);
			v.getPreps().add(u);
			rebuildCFG(v);
		}
	}
}
