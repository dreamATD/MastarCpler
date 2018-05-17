package Optimizer;

import GeneralDataStructure.*;
import GeneralDataStructure.MyListClass.MyList;
import GeneralDataStructure.QuadClass.A3Quad;
import GeneralDataStructure.QuadClass.Quad;
import Utilizer.SetOperation;

import java.util.*;

public class FuncSSABuilder {
	HashSet<String> global;

	HashMap<String, HashSet<BasicBlock>> varDomain;
	ArrayList<HashSet<BasicBlock> > domainEdge;
	ArrayList<LinkedList<BasicBlock> > dom;
	ArrayList<BasicBlock> immDom;
	ArrayList<ArrayList<BasicBlock>> rmmDom;
	HashMap<String, Stack<String> > nameStack;

	ArrayList<BasicBlock> blockList;

	int varCnt;

	public FuncSSABuilder(FuncFrame func) {
		blockList = func.getBbList();
		global = new HashSet<>();
		varDomain = new HashMap<>();
		domainEdge = new ArrayList<>();
		dom = new ArrayList<>();
		immDom = new ArrayList<>();
		rmmDom = new ArrayList<>();
		nameStack = new HashMap<>();

	}

	public void buildSSAFunc() {
		BuildImmDom();
		BuildDomainEdge();
		addPhi();
		renameVar(blockList.get(0));
	}

	void addPhi() {
		HashSet<String> varKill = new HashSet<>();

		for (BasicBlock u: blockList) {
			MyList<Quad> codes = u.getCodes();
			for (int i = 0; i < codes.size(); ++i) {
				Quad c = codes.get(i);
				if (c instanceof A3Quad) {
					String x = c.getRtName(), y = c.getR1Name(), z = c.getR2Name();
					if (!varKill.contains(y)) global.add(y);
					if (!varKill.contains(z)) global.add(z);
					varKill.add(x);
					if (!varDomain.containsKey(x)) {
						varDomain.put(x, new HashSet<>());
					}
					varDomain.get(x).add(u);
				}
			}
		}

		HashSet<BasicBlock> workList;
		for (String data: global) {
			workList = varDomain.get(data);
			for (BasicBlock block: workList) {
				for (BasicBlock sucBlock: domainEdge.get(block.getIdx())) {
					if (!sucBlock.containsPhi(data)) {
						sucBlock.addPhi(data);
						workList.add(block);
					}
				}
			}
		}
	}

	void BuildDomainEdge() {
		for (int i = 0; i < blockList.size(); ++i) {
			domainEdge.add(new HashSet<>());
		}
		for(int i = 0; i < blockList.size(); ++i) {
			BasicBlock u = blockList.get(i);
			if (u.getPreps().size() <= 1) continue;
			ArrayList<BasicBlock> preps = u.getPreps();
			for (BasicBlock v: preps) {
				BasicBlock cur = v;
				while (cur != immDom.get(i)) {
					domainEdge.get(cur.getIdx()).add(u);
					cur = immDom.get(cur.getIdx());
				}
			}
		}
	}


	void BuildImmDom() {
		int n = blockList.size() - 1;
		dom.add(new LinkedList<>());
		dom.get(0).add(blockList.get(0));
		for (int i = 1; i < blockList.size(); ++i) {
			dom.add(new LinkedList<>());
			for (int j = 0; j < blockList.size(); ++j) {
				dom.get(i).add(blockList.get(j));
			}
		}
		boolean changed = true;
		LinkedList<BasicBlock> temp;
		while (changed) {
			changed = false;
			for (int i = 1; i <= n; ++i) {
				BasicBlock u = blockList.get(i);
				temp = new LinkedList<>();
				temp.addAll(dom.get(u.preps.get(0).getIdx()));
				for (int j = 1; j < u.preps.size(); ++j) {
					BasicBlock v = u.preps.get(j);
					temp = SetOperation.intersect(temp, dom.get(v.getIdx()));
				}
				temp.add(u);
				if (!temp.equals(dom.get(i))) {
					dom.set(i, temp);
					changed = true;
				}
			}
		}
		immDom.add(blockList.get(0));
		for (int i = 1; i < blockList.size(); ++i) {
			immDom.add(dom.get(i).get(dom.get(i).size() - 2));
			rmmDom.add(new ArrayList<>());
		}
		for (int i = 0; i < blockList.size(); ++i) {
			rmmDom.get(immDom.get(i).getIdx()).add(blockList.get(i));
		}
	}

	String newName(String name) {
		String res = name + '_' + Integer.toString(varCnt++);
		if (!nameStack.containsKey(name)) {
			nameStack.put(name, new Stack<>());
		}
		nameStack.get(name).push(res);
		return res;
	}

	void renameVar(BasicBlock u) {
		HashSet<String> tmp = new HashSet<>();
		MyList<Quad> codes = u.getCodes();
		for (int i = 0; i < codes.size(); ++i) {
			Quad c = codes.get(i);
			if (c.getOp().equals("phi")) {
				String rt = c.getRtName();
				c.setRt(newName(rt));
				tmp.add(rt);
			} else if (c instanceof A3Quad) {
				String rt = c.getRtName();
				c.setR1(nameStack.get(c.getR1Name()).peek());
				c.setR2(nameStack.get(c.getR2Name()).peek());
				c.setRt(newName(rt));
				tmp.add(rt);
			}
		}

		for (int i = 0; i < u.succs.size(); ++i) {
			u.addPhiParams(tmp, nameStack);
		}

		ArrayList<BasicBlock> domSucc = rmmDom.get(u.getIdx());
		for (int i = 0; i < domSucc.size(); ++i) {
			renameVar(domSucc.get(i));
		}

		for (int i = 0; i < codes.size(); ++i) {
			Quad c = codes.get(i);
			if (c.getOp().equals("phi") || c instanceof A3Quad) {
				nameStack.get(c.getRtName()).pop();
			}
		}
	}
}
