package Optimizer;

import GeneralDataStructure.BasicBlock;
import GeneralDataStructure.FuncFrame;
import GeneralDataStructure.MyListClass.MyList;
import GeneralDataStructure.OprandClass.MemAccess;
import GeneralDataStructure.OprandClass.Oprand;
import GeneralDataStructure.OprandClass.Register;
import GeneralDataStructure.QuadClass.*;
import Utilizer.SetOperation;

import java.util.ArrayList;
import java.util.HashSet;

public class ActionAnalyzer {
	private ArrayList<BasicBlock> blocks;
	private ArrayList<HashSet<String>> ueVar;
	private ArrayList<HashSet<String>> varKill;
	private ArrayList<HashSet<String>> liveOut;

	public ActionAnalyzer(ArrayList<BasicBlock> blockList) {
		blocks = blockList;
		ueVar = new ArrayList<>();
		varKill = new ArrayList<>();
		liveOut = new ArrayList<>();
	}

	private void checkOprand(Oprand r, HashSet<String> curUeVar, HashSet<String> curVarKill) {
		if (r == null) return;
		String n = r.get();
		if (r instanceof Register && !curVarKill.contains(n))
			curUeVar.add(n);
		else if (r instanceof MemAccess) {
			checkOprand(((MemAccess) r).getBase(), curUeVar, curVarKill);
			checkOprand(((MemAccess) r).getOffset(), curUeVar, curVarKill);
			checkOprand(((MemAccess) r).getOffsetCnt(), curUeVar, curVarKill);
			checkOprand(((MemAccess) r).getOffsetSize(), curUeVar, curVarKill);
		}
	}

	public ArrayList<HashSet<String>> buildLiveOut() {
		MyList<Quad> codes;
		for (int i = 0; i < blocks.size(); ++i) {
			BasicBlock block = blocks.get(i);
			ueVar.add(new HashSet<>());
			varKill.add(new HashSet<>());
			codes = block.getCodes();

			HashSet<String> curUeVar = ueVar.get(i);
			HashSet<String> curVarKill = varKill.get(i);
			for (int j = 0; j < codes.size(); ++j) {
				Quad c = codes.get(j);
//				if (c instanceof PhiQuad) {
//					ArrayList<Register> tmp = ((PhiQuad) c).getParams();
//					for (Register reg: tmp) checkOprand(reg, curUeVar, curVarKill);
//				}
				checkOprand(c.getR1(), curUeVar, curVarKill);
				checkOprand(c.getR2(), curUeVar, curVarKill);
				if (c.getRt() instanceof MemAccess)
					checkOprand(c.getRt(), curUeVar, curVarKill);
				if (c.getRt() instanceof Register && !(c instanceof PhiQuad)) {
					String x = c.getRtName();
					curVarKill.add(x);
				}
			}
		}

		for (int i = 0; i < blocks.size(); ++i) {
			liveOut.add(new HashSet<>());
		}

		boolean changed = true;
		while (changed) {
			changed = false;
			for (int i = 0; i < blocks.size(); ++i) {
				ArrayList<BasicBlock> succs = blocks.get(i).getSuccs();
				HashSet<String> l = liveOut.get(i);
				int oldSize = l.size();
				for (int j = 0; j < succs.size(); ++j) {
					int succIdx = succs.get(j).getIdx();
					SetOperation.selfUnion(l, ueVar.get(succIdx));
					SetOperation.selfUnion(l, SetOperation.minus(liveOut.get(succIdx), varKill.get(succIdx)));
					MyList<Quad> code = succs.get(j).getCodes();
					int idx = succs.get(j).getPreIdx(blocks.get(i));
					for (int k = 0; k < code.size(); ++k) {
						Quad c = code.get(k);
						if (!(c instanceof PhiQuad)) break;
						String nt = c.getRtName();
						if (l.contains(nt)) {
							l.remove(nt);
							l.add(c.getPhiParams(idx).get());
						}
					}
				}
				if (l.size() > oldSize) changed = true;
			}
		}
		return liveOut;
	}
}
