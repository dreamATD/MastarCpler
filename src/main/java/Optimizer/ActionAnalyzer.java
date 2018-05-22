package Optimizer;

import GeneralDataStructure.BasicBlock;
import GeneralDataStructure.FuncFrame;
import GeneralDataStructure.MyListClass.MyList;
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
				if (c instanceof A3Quad || c instanceof MovQuad || c instanceof CallQuad
						|| c instanceof CondQuad || c instanceof ParamQuad) {
					if (c.getR1() instanceof Register && !curVarKill.contains(c.getR1Name()))
						curUeVar.add(c.getR1Name());
					if (c.getR2() instanceof Register && !curVarKill.contains(c.getR2Name()))
						curUeVar.add(c.getR2Name());
					if (c instanceof CondQuad || c instanceof ParamQuad) continue;
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
				}
				if (l.size() > oldSize) changed = true;
			}
		}
		return liveOut;
	}
}
