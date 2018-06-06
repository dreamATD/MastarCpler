package Optimizer;

import GeneralDataStructure.BasicBlock;
import GeneralDataStructure.FuncFrame;
import GeneralDataStructure.MyListClass.MyList;
import GeneralDataStructure.OprandClass.LabelName;
import GeneralDataStructure.QuadClass.*;
import Utilizer.UnionFind;

import java.util.ArrayList;
import java.util.HashMap;

public class LabelResolver {
	private FuncFrame curFunc;
	private ArrayList<BasicBlock> blocks;
	public LabelResolver(FuncFrame func) {
		curFunc = func;
		blocks = func.getBbList();
	}
	public void labelResolve() {
		clean();
	}

	private void clean() {
		do {
			curFunc.buildBbList();
		} while (onePass());
	}

	private void updatePhi(BasicBlock block, BasicBlock oldB, BasicBlock newB) {
		int oldI = oldB.getIdx(), newI = newB.getIdx();
		MyList<Quad> codes = block.getCodes();
		for (int i = 0; i < codes.size(); ++i) {
			Quad c = codes.get(i);
			if (!(c instanceof PhiQuad)) return;
			c.setPhiParams(oldI, newI);
		}
	}

	private void updatePreps(BasicBlock block, BasicBlock oldB, BasicBlock newB) {
		block.getPreps().remove(oldB);
		block.getPreps().add(newB);
	}

	private void modifyTrans(BasicBlock block, String oldName, BasicBlock oldBlock, String newName, BasicBlock newBlock) {
		MyList<Quad> codes = block.getCodes();
		int e = codes.size() - 1;
		Quad c = codes.get(e);
		if (c instanceof JumpQuad && c.getRtName().equals(oldName))
			c.changeRt(new LabelName(newName));
		if (c instanceof CJumpQuad) {
			if (c.getR1Name().equals(oldName)) c.changeR1(new LabelName(newName));
			if (c.getR2Name().equals(oldName)) c.changeR2(new LabelName(newName));
			for (int i = 0; i < block.getSuccs().size(); ++i) {
				if (block.getSuccs().get(i) == oldBlock) {
					block.getSuccs().set(i, newBlock);
					updatePreps(newBlock, oldBlock, block);
					updatePhi(newBlock, oldBlock, block);
				}
			}
		}
	}

	private boolean onePass() {
		boolean changed = false;
		for (int i = blocks.size() - 1; i >= 0; --i) {
			BasicBlock block = blocks.get(i);
			MyList<Quad> codes = block.getCodes();
			int e = codes.size() - 1;
			Quad c = codes.get(e);
			if (c instanceof CJumpQuad) {
				if (c.getR1Name().equals(c.getR2Name())) {
					BasicBlock suc = block.getSuccs().get(0);
					codes.set(e - 1, new JumpQuad("jump", c.getR1()));
					codes.removeLast();
					block.getSuccs().clear();
					block.getSuccs().add(suc);
				}
			}

			e = codes.size() - 1;
			c = codes.get(e);
			if (c instanceof JumpQuad) {
				BasicBlock suc = block.getSuccs().get(0);
				if (codes.size() == 1) {
					for (BasicBlock prep: block.getPreps())
						modifyTrans(prep, block.getName(), block, c.getRtName(), suc);
					changed = true;
				}
				if (suc.getPreps().size() == 1) {
					codes.removeLast();
					block.getSuccs().clear();
					codes.addAll(suc.getCodes());
					block.getSuccs().addAll(suc.getSuccs());
					for (BasicBlock ssuc: suc.getSuccs()) {
						updatePreps(ssuc, suc, block);
						updatePhi(ssuc, suc, block);
					}
					changed = true;
				}
				if (suc.getCodes().get(0) instanceof CondQuad || suc.getCodes().get(0) instanceof JumpQuad) {
					codes.removeLast();
					block.getSuccs().clear();
					for (int j = 0; j < suc.getCodes().size(); ++j) {
						codes.add(suc.getCodes().get(j));
					}
					block.getSuccs().addAll(suc.getSuccs());
					for (BasicBlock ssuc: suc.getSuccs()) {
						updatePreps(ssuc, suc, block);
						updatePhi(ssuc, suc, block);
					}
					changed = true;
				}
			}
		}
		return changed;
	}
}
