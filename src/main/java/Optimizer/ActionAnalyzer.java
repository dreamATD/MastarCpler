package Optimizer;

import GeneralDataStructure.BasicBlock;
import GeneralDataStructure.FuncFrame;
import GeneralDataStructure.MyListClass.MyList;
import GeneralDataStructure.QuadClass.A3Quad;
import GeneralDataStructure.QuadClass.Quad;
import Utilizer.SetOperation;

import java.util.ArrayList;
import java.util.HashSet;

public class ActionAnalyzer {
	ArrayList<BasicBlock> blocks;
	ArrayList<HashSet<String>> ueVar;
	ArrayList<HashSet<String>> varKill;
	ArrayList<HashSet<String>> liveOut;

	public ActionAnalyzer(ArrayList<BasicBlock> blockList) {
		blocks = blockList;
		ueVar = new ArrayList<>();
		varKill = new ArrayList<>();
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
				Quad code = codes.get(j);
				if (!(code instanceof A3Quad)) continue;

				String x = code.getRtName(), y = code.getR1Name(), z = code.getR2Name();
				if (!curVarKill.contains(y)) curUeVar.add(y);
				if (!curVarKill.contains(z)) curUeVar.add(z);
				curVarKill.add(x);
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
					SetOperation.selfUnion(l, SetOperation.minus(liveOut.get(succIdx), varKill.get(succIdx)));
				}
				if (l.size() > oldSize) changed = true;
			}
		}
		return liveOut;
	}
}
