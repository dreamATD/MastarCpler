package Optimizer;

import GeneralDataStructure.BasicBlock;
import GeneralDataStructure.FuncFrame;
import GeneralDataStructure.QuadClass.Quad;
import Utilizer.SetOperation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class DomainEdgeBuilder {
	private FuncFrame curFunc;
	private ArrayList<BasicBlock> blockList;
	private ArrayList<ArrayList<BasicBlock>> preps;
	private ArrayList<ArrayList<BasicBlock>> rePreps;

	private ArrayList<HashSet<BasicBlock>> domainEdge;
	private ArrayList<HashSet<BasicBlock>> rDomainEdge;
	private ArrayList<LinkedList<BasicBlock> > dom;
	private ArrayList<LinkedList<BasicBlock>> rDom;
	private ArrayList<BasicBlock> immDom;
	private ArrayList<BasicBlock> rImmDom;
	private ArrayList<ArrayList<BasicBlock>> rmmDom;
	private ArrayList<ArrayList<BasicBlock>> rRmmDom;

	private ArrayList<BasicBlock> firstList;
	private ArrayList<BasicBlock> endList;


	public DomainEdgeBuilder(FuncFrame func) {
		curFunc = func;
		blockList = new ArrayList<>(func.getBbList());
		preps = new ArrayList<>();
		for (BasicBlock block: blockList) {
			preps.add(new ArrayList<>(block.getPreps()));
		}
		rePreps = new ArrayList<>();
		for (int i = 0; i < blockList.size(); ++i) rePreps.add(null);
		for (BasicBlock block: blockList) {
			rePreps.set(block.getIdx(), new ArrayList<>(block.getSuccs()));
		}
		domainEdge = new ArrayList<>();
		rDomainEdge = new ArrayList<>();
		dom = new ArrayList<>();
		rDom = new ArrayList<>();
		immDom = new ArrayList<>();
		rImmDom = new ArrayList<>();
		rmmDom = new ArrayList<>();
		rRmmDom = new ArrayList<>();

		firstList = new ArrayList<>(func.getFirst());
		endList = new ArrayList<>(func.getEnds());
	}

	public void work() {
//		System.err.println("in work");
		BuildImmDom(dom, immDom, rmmDom, preps, firstList);
		for (int i = 0; i < blockList.size(); ++i) {
			blockList.get(i).setRmmDom(rmmDom.get(i));
		}
		BuildDomainEdge(domainEdge, immDom, preps);
		for (int i = 0; i < blockList.size(); ++i) {
			blockList.get(i).setDomainEdge(domainEdge.get(i));
		}
//		System.err.println("out work");
	}

	public void rwork() {
		if (endList.size() > 1) {
			BasicBlock block = new BasicBlock("end_" + curFunc.getName());
			block.add(new Quad("nop"));
			block.setIdx(blockList.size());
			blockList.add(block);

			rePreps.add(new ArrayList<>());
			for (BasicBlock b: endList) {
				rePreps.get(b.getIdx()).add(block);
			}
			endList.clear();
			endList.add(block);
		}
		BuildImmDom(rDom, rImmDom, rRmmDom, rePreps, endList);
		for (int i = 0; i < blockList.size(); ++i) {
			blockList.get(i).setRImmDom(rImmDom.get(i));
		}
		BuildDomainEdge(rDomainEdge, rImmDom, rePreps);
		for (int i = 0; i < blockList.size(); ++i) {
			blockList.get(i).setRDomainEdge(rDomainEdge.get(i));
		}
	}

	private void BuildImmDom(
			ArrayList<LinkedList<BasicBlock> > dom,
			ArrayList<BasicBlock> immDom,
			ArrayList<ArrayList<BasicBlock>> rmmDom,
			ArrayList<ArrayList<BasicBlock>> preps,
			ArrayList<BasicBlock> firstList
	) {
		for (int i = 0; i < blockList.size(); ++i) {
			dom.add(new LinkedList<>());
		}
		for (BasicBlock block: firstList) {
			dom.get(block.getIdx()).add(block);
		}
		for (int i = 0; i < blockList.size(); ++i) {
			if (dom.get(i).isEmpty()) {
				for (int j = 0; j < blockList.size(); ++j) {
					dom.get(i).add(blockList.get(j));
				}
			}
		}
		boolean changed = true;
		LinkedList<BasicBlock> temp;
		if (blockList.size() > firstList.size()) {
			while (changed) {
//				System.err.println("??");
				changed = false;
				for (int i = 0; i < blockList.size(); ++i) {
					BasicBlock u = blockList.get(i);
					if (preps.get(u.getIdx()).isEmpty()) continue;

					temp = new LinkedList<>();
					ArrayList<BasicBlock> prep = preps.get(u.getIdx());
					temp.addAll(dom.get(prep.get(0).getIdx()));
					for (BasicBlock v: prep) {
						temp = SetOperation.intersect(temp, dom.get(v.getIdx()));
					}
					temp.add(u);
					if (!temp.equals(dom.get(u.getIdx()))) {
						dom.set(u.getIdx(), temp);
						changed = true;
					}
				}
			}
		}

		for (BasicBlock block: blockList) {
			immDom.add(null);
			rmmDom.add(new ArrayList<>());
		}

		for (BasicBlock block: firstList) {
			immDom.set(block.getIdx(), block);
		}
		for (int i = 0; i < blockList.size(); ++i) {
			if (preps.get(i).isEmpty()) continue;
			immDom.set(i, dom.get(i).get(dom.get(i).size() - 2));
		}
		for (int i = 0; i < blockList.size(); ++i) {
			if (preps.get(i).isEmpty()) continue;
			rmmDom.get(immDom.get(i).getIdx()).add(blockList.get(i));
		}

	}

	private void BuildDomainEdge(
			ArrayList<HashSet<BasicBlock>> domainEdge,
			ArrayList<BasicBlock> immDom,
			ArrayList<ArrayList<BasicBlock>> preps
	) {
		for (int i = 0; i < blockList.size(); ++i) {
			domainEdge.add(new HashSet<>());
		}
		for(int i = 0; i < blockList.size(); ++i) {
			BasicBlock u = blockList.get(i);
			ArrayList<BasicBlock> prep = preps.get(u.getIdx());
			if (prep.size() <= 1) continue;
			for (BasicBlock v: prep) {
				BasicBlock cur = v;
				while (cur != immDom.get(i)) {
					domainEdge.get(cur.getIdx()).add(u);
					cur = immDom.get(cur.getIdx());
				}
			}
		}
	}
}
