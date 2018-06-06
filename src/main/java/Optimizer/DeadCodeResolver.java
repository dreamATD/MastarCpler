package Optimizer;

import GeneralDataStructure.BasicBlock;
import GeneralDataStructure.FuncFrame;
import GeneralDataStructure.MyListClass.MyList;
import GeneralDataStructure.OprandClass.*;
import GeneralDataStructure.QuadClass.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class DeadCodeResolver {
	private FuncFrame curFunc;
	private ArrayList<BasicBlock> blocks;

	private HashMap<String, ArrayList<Quad>> modify;
	private HashMap<Quad, BasicBlock> belong;

	/*
	* special for function call
	* */
	HashMap<Quad, ArrayList<Quad>> callParams;
	HashMap<Quad, Quad> paramBelong;

	public DeadCodeResolver(FuncFrame func) {
		curFunc = func;
		blocks = func.getBbList();
		modify = new HashMap<>();
		belong = new HashMap<>();
		callParams = new HashMap<>();
		paramBelong = new HashMap<>();
	}

	public void updateUseful() {
		mark();
		sweep();
	}

	private boolean isCritical(Quad c) {
		if (c instanceof RetQuad) return true;
		Oprand r1 = c.getR1(), r2 = c.getR2(), rt = c.getRt();
		if (c instanceof CallQuad /*&&
				(r1.get().equals("F_print") || r1.get().equals("F_println") ||
				 r1.get().equals("F_getInt")|| r1.get().equals("F_getString()"))*/)
			return true;
		if (rt instanceof MemAccess) return true;
		return false;
	}

	private void updateDef(Oprand r, HashSet<Quad> workList) {
		if (r instanceof Register) {
			ArrayList<Quad> list = modify.get(r.get());
			if (list == null) return;
			for (Quad dc: list) {
				if (!dc.getUseful()) {
					dc.setUseful(true);
					workList.add(dc);
				}
			}
		} else if (r instanceof MemAccess) {
			updateDef(((MemAccess) r).getBase(), workList);
			updateDef(((MemAccess) r).getOffset(), workList);
			updateDef(((MemAccess) r).getOffsetCnt(), workList);
			updateDef(((MemAccess) r).getOffsetSize(), workList);
		}
	}

	private void addModify(Oprand r, Quad c) {
		if (r instanceof Register || r instanceof GeneralMemAccess) {
			ArrayList<Quad> tmp = modify.get(r.get());
			if (tmp == null) {
				tmp = new ArrayList<>();
				modify.put(r.get(), tmp);
			}
			tmp.add(c);
		} else if (r instanceof MemAccess) addModify(((MemAccess) r).getBase(), c);
	}

	private void mark() {
		HashSet<Quad> workList = new HashSet<>();
		Stack<Quad> params = new Stack<>();
		for (BasicBlock block: blocks) {
			MyList<Quad> codes = block.getCodes();
			for (int i = 0; i < codes.size(); ++i) {
				Quad c = codes.get(i);
				c.setUseful(false);
				belong.put(c, block);
				if (isCritical(c)) {
					c.setUseful(true);
					workList.add(c);
				}
				addModify(c.getRt(), c);
				if (c.getR1() instanceof Register && Register.isAddrBase(c.getR1Name()))
					addModify(c.getR1(), c);
				if (c.getR2() instanceof Register && Register.isAddrBase(c.getR2Name()))
					addModify(c.getR2(), c);
				if (c instanceof ParamQuad) {
					params.push(c);
				}
				if (c instanceof CallQuad) {
					ArrayList<Quad> tmp = new ArrayList<>();
					for (int j = 0; j < ((ImmOprand) c.getR2()).getVal(); ++j) {
						Quad cp = params.peek();
						String n = cp.getR1Name();
						if (Register.isAddrBase(n)) addModify(cp.getR1(), c);
						if (!paramBelong.containsKey(cp)) paramBelong.put(cp, c);
						tmp.add(params.pop());
					}
					callParams.put(c, tmp);
				}
			}
		}

		while (!workList.isEmpty()) {
			Quad c = workList.iterator().next();
			workList.remove(c);

			Oprand rt = c.getRt(), r1 = c.getR1(), r2 = c.getR2();
			updateDef(r1, workList);
			updateDef(r2, workList);
			if (rt instanceof MemAccess) updateDef(rt, workList);
			if (rt instanceof Register && Register.isAddrBase(rt.get())) updateDef(rt, workList);
			if (c instanceof PhiQuad) {
				for (Register r : ((PhiQuad) c).getParams()) updateDef(r, workList);
			}
			if (c instanceof CallQuad) {
				ArrayList<Quad> param = callParams.get(c);
				for (Quad p: param) {
					if (!p.getUseful()) {
						p.setUseful(true);
						workList.add(p);
					}
				}
			}

			HashSet<BasicBlock> rdf = belong.get(c).getRDomainEdge();
			for (BasicBlock block : rdf) {
				int e = block.getCodes().size() - 1;
				Quad jmp = block.getCodes().get(e);
				if (jmp instanceof JumpQuad && !jmp.getUseful()) {
					jmp.setUseful(true);
					workList.add(jmp);
				} else if (jmp instanceof CJumpQuad && !jmp.getUseful()) {
					jmp.setUseful(true);
					workList.add(jmp);
					Quad cmp = block.getCodes().get(e - 1);
					if (!cmp.getUseful()) {
						cmp.setUseful(true);
						workList.add(cmp);
					}
				}
			}
		}
	}

	private void sweep() {
		for (BasicBlock block: blocks) {
			MyList<Quad> newCodes = new MyList<>(), codes = block.getCodes();
			for (int i = 0; i < codes.size(); ++i) {
				Quad c = codes.get(i);
				if (!c.getUseful()) {
					if (c.getOp().equals("nop")) newCodes.add(c);
					if ((c instanceof JumpQuad || c instanceof CJumpQuad) && block.getRImmDom().getIdx() < blocks.size()) {
						newCodes.add(new JumpQuad("jump", new LabelName(block.getRImmDom().getName())));
					}
				} else {
					newCodes.add(c);
				}
			}
			block.setCodes(newCodes);
		}
	}
}
