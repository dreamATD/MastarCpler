package Optimizer;

import GeneralDataStructure.*;
import GeneralDataStructure.MyListClass.MyList;
import GeneralDataStructure.OprandClass.MemAccess;
import GeneralDataStructure.OprandClass.Oprand;
import GeneralDataStructure.OprandClass.Register;
import GeneralDataStructure.QuadClass.*;
import Utilizer.ConstVar;
import Utilizer.SetOperation;

import java.util.*;

public class FuncSSABuilder {
	private FuncFrame curFunc;
	private HashSet<String> global;

	private HashMap<String, HashSet<BasicBlock>> varDomain;
	private ArrayList<HashSet<BasicBlock> > domainEdge;
	private ArrayList<LinkedList<BasicBlock> > dom;
	private ArrayList<BasicBlock> immDom;
	private ArrayList<ArrayList<BasicBlock>> rmmDom;
	private HashMap<String, Stack<String> > nameStack;

	private ArrayList<BasicBlock> blockList;

	private int varCnt;

	public FuncSSABuilder(FuncFrame func) {
		blockList = func.getBbList();
		global = new HashSet<>();
		varDomain = new HashMap<>();
		domainEdge = new ArrayList<>();
		dom = new ArrayList<>();
		immDom = new ArrayList<>();
		rmmDom = new ArrayList<>();
		nameStack = new HashMap<>();
		curFunc = func;
	}

	public void buildSSAFunc() {
		BuildImmDom();
		BuildDomainEdge();
		addPhi();
		renameVar(blockList.get(0));
	}

	private void BuildImmDom() {
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
		if (n > 0) {
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
		}
		immDom.add(blockList.get(0));
		rmmDom.add(new ArrayList<>());
		for (int i = 1; i < blockList.size(); ++i) {
			immDom.add(dom.get(i).get(dom.get(i).size() - 2));
			rmmDom.add(new ArrayList<>());
		}
		for (int i = 1; i < blockList.size(); ++i) {
			rmmDom.get(immDom.get(i).getIdx()).add(blockList.get(i));
		}
	}

	private void BuildDomainEdge() {
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

	private void addPhiCheckOprand(Oprand r, HashSet<String> varKill) {
		if (r == null) return;

		String n = r.get();
		if (r instanceof Register && !varKill.contains(n)) global.add(n);
		else if (r instanceof MemAccess) {
			addPhiCheckOprand(((MemAccess) r).getBase(), varKill);
			addPhiCheckOprand(((MemAccess) r).getOffset(), varKill);
			addPhiCheckOprand(((MemAccess) r).getOffsetCnt(), varKill);
			addPhiCheckOprand(((MemAccess) r).getOffsetSize(), varKill);
		}
	}

	private void addPhi() {
		for (BasicBlock u: blockList) {
			HashSet<String> varKill = new HashSet<>();
			MyList<Quad> codes = u.getCodes();
			for (int i = 0; i < codes.size(); ++i) {
				Quad c = codes.get(i);
				if (c instanceof PhiQuad) continue;
				addPhiCheckOprand(c.getR1(), varKill);
				addPhiCheckOprand(c.getR2(), varKill);
				if (c.getRt() instanceof MemAccess)
					addPhiCheckOprand(c.getRt(), varKill);
				if (c.getRt() instanceof Register) {
					String x = c.getRtName();
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
			if (workList == null) continue;
			while (!workList.isEmpty()) {
				BasicBlock block = workList.iterator().next();
				workList.remove(block);
				for (BasicBlock sucBlock: domainEdge.get(block.getIdx())) {
					if (!sucBlock.containsPhi(data)) {
						sucBlock.addPhi(data);
						workList.add(sucBlock);
					}
				}
			}
		}
	}


	private String newName(String name) {
		String res = name + '$' + Integer.toString(varCnt++);
		if (!nameStack.containsKey(name)) {
			nameStack.put(name, new Stack<>());
		}
		nameStack.get(name).push(res);
		return res;
	}

	private void renameVarCheckOprand(Oprand r) {
		if (r == null) return;

		String n = r.get();
		if (r instanceof Register && nameStack.containsKey(n))
			r.set(nameStack.get(n).peek());
		else if (r instanceof MemAccess) {
			renameVarCheckOprand(((MemAccess) r).getBase());
			renameVarCheckOprand(((MemAccess) r).getOffset());
			renameVarCheckOprand(((MemAccess) r).getOffsetCnt());
			renameVarCheckOprand(((MemAccess) r).getOffsetSize());
		}
	}

	private void renameVar(BasicBlock u) {
		HashSet<String> tmp = new HashSet<>();
		MyList<Quad> codes = u.getCodes();
		for (int i = 0; i < codes.size(); ++i) {
			Quad c = codes.get(i);
			if (c instanceof PhiQuad) {
				String rt = c.getRtName();
				c.setRt(newName(rt));
				tmp.add(rt);
			} else {
				renameVarCheckOprand(c.getR1());
				renameVarCheckOprand(c.getR2());
				if (c.getRt() instanceof MemAccess)
					renameVarCheckOprand(c.getRt());
				if (c.getRt() instanceof Register) {
					String rt = c.getRtName();
					c.setRt(newName(rt));

					/*
					 * the modifiable parameters equals to local variables
					 * */
					if (curFunc.containsParam(((Register) c.getRt()).getMemPos())) {
						curFunc.addLocalVar(((Register) c.getRt()).getMemPos(), ConstVar.addrLen);
					}

					tmp.add(rt);
				}
			}
		}

		for (int i = 0; i < u.succs.size(); ++i) {
			u.succs.get(i).addPhiParams(tmp, nameStack);
		}

		ArrayList<BasicBlock> domSucc = rmmDom.get(u.getIdx());
		for (int i = 0; i < domSucc.size(); ++i) {
			renameVar(domSucc.get(i));
		}

		for (int i = 0; i < codes.size(); ++i) {
			Quad c = codes.get(i);
			if (c instanceof PhiQuad || c instanceof A3Quad || c instanceof MovQuad) {
				if (c.getRt() instanceof Register) {
					String name = c.getRtName();
					int t = name.lastIndexOf("$");
					String nn = name.substring(0, t);
					nameStack.get(name.substring(0, t)).pop();
				}
			}
		}
	}
}
