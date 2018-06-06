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
	private HashSet<String> params;

	private HashMap<String, HashSet<BasicBlock>> varDomain;
	private HashMap<String, Stack<String> > nameStack;

	private ArrayList<BasicBlock> blockList;

	private int varCnt;

	public FuncSSABuilder(FuncFrame func) {
		blockList = func.getBbList();
		global = new HashSet<>();
		params = new HashSet<>();

		String [] tmp = func.getParamList();
		for (String data: tmp) params.add(data);
		varDomain = new HashMap<>();
		nameStack = new HashMap<>();
		curFunc = func;
	}

	public void buildSSAFunc() {
		addPhi();

		HashSet<String> tmp = new HashSet<>();
		tmp.addAll(params);
		renameVar(blockList.get(0), tmp);
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
				if (u.getIdx() == 0) {
					for (String x: params) {
						varKill.add(x);
						if (!varDomain.containsKey(x)) {
							varDomain.put(x, new HashSet<>());
						}
						varDomain.get(x).add(u);
					}
				}
			}
		}

		HashSet<BasicBlock> workList;
		Stack<BasicBlock> st = new Stack<>();
		for (String data: global) {
			workList = varDomain.get(data);
			if (workList == null) continue;
			for (BasicBlock block: workList) st.push(block);
			while (!st.isEmpty()) {
				BasicBlock block = st.pop();
				for (BasicBlock sucBlock: block.getDomainEdge()) {
					if (!sucBlock.containsPhi(data)) {
						sucBlock.addPhi(data);
						if (!workList.contains(sucBlock)) {
							st.push(sucBlock);
							workList.add(sucBlock);
						}
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

	private void renameVar(BasicBlock u, HashSet<String> pre) {
		HashSet<String> tmp = new HashSet<>();
		tmp.addAll(pre);
		MyList<Quad> codes = u.getCodes();
		if (u.getIdx() == 0) {
			for (String x: params) {
				if (!nameStack.containsKey(x)) nameStack.put(x, new Stack<>());
				nameStack.get(x).add(x);
			}
		}
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

		for (int i = 0; i < u.getSuccs().size(); ++i) {
			u.getSuccs().get(i).addPhiParams(tmp, nameStack, u);
		}

		ArrayList<BasicBlock> domSucc = u.getRmmDom();
		for (int i = 0; i < domSucc.size(); ++i) {
			renameVar(domSucc.get(i), tmp);
		}

		for (int i = 0; i < codes.size(); ++i) {
			Quad c = codes.get(i);
//			if (c instanceof PhiQuad || c instanceof A3Quad || c instanceof MovQuad || c instanceof LeaQuad) {
			if (c.getRt() instanceof Register) {
				String name = c.getRtName();
				int t = name.lastIndexOf("$");
				String nn = name.substring(0, t);
				nameStack.get(name.substring(0, t)).pop();
			}
//			}
		}
	}
}
