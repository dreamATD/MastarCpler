package BackEnd;

import GeneralDataStructure.BasicBlock;
import GeneralDataStructure.FuncFrame;
import GeneralDataStructure.MyListClass.MyList;
import GeneralDataStructure.OprandClass.MemAccess;
import GeneralDataStructure.OprandClass.Oprand;
import GeneralDataStructure.OprandClass.Register;
import GeneralDataStructure.QuadClass.*;
import Optimizer.ActionAnalyzer;
import Utilizer.SimpleUnionFind;
import Utilizer.Tool;

import java.util.*;

public class RegDistributor {
	private String funcName;
	private ArrayList<String> globalVars;
	private ArrayList<BasicBlock> blocks;
	private SimpleUnionFind activeSet;
	private ArrayList<String> global;
	private HashMap<String, Integer> nameIdx;
	private ArrayList<HashSet<Integer>> edge;
	private ArrayList<HashSet<String>> liveOut;
	private String[] lastParams;
	private String[] first6Params;
	private boolean [][] matrix;
	private int[] value;
	private ArrayList<HashSet<Integer>> col;
	private int nVar;

	private int colCnt, outCol;

	/*
	* related to the parameters fighting
	* need to be attach at the beginning
	* */
	MyList<Quad> movList;

	private String[] regList = {"rdi", "rsi", "rdx", "rcx", "r8", "r9", "rax", "r0", "r1", "r2", "r3", "r4", "r5", "r6", "r7", "r10", "r11", "r12", "r13", "r14", "r15", "rbx", "rbp", "rsp"};

	private void addRegister(Oprand opr) {
		if (opr instanceof Register && !(nameIdx.containsKey(opr.get()))) {
			nameIdx.put(opr.get(), nVar++);
			global.add(opr.get());
		} else if (opr instanceof MemAccess) {
			addRegister(((MemAccess) opr).getBase());
			addRegister(((MemAccess) opr).getOffset());
			addRegister(((MemAccess) opr).getOffsetCnt());
			addRegister(((MemAccess) opr).getOffsetSize());
		}
	}

	private void removeNode(int x) {
		nameIdx.remove(global.get(x));
	}

	private void link(int u, int v) {
		edge.get(u).add(v);
		edge.get(v).add(u);
		matrix[u][v] = matrix[v][u] = true;
	}

	private void cut(int u, int v) {
		edge.get(u).remove(v);
		edge.get(v).remove(u);
		matrix[u][v] = matrix[v][u] = false;
	}

	public RegDistributor(FuncFrame func, ArrayList<String> globalVars) {
		funcName = func.getName();
		blocks = func.getBbList();
		global = new ArrayList<>();
		nameIdx = new HashMap<>();
		edge = new ArrayList<>();
		this.globalVars = globalVars;
		first6Params = func.getFirst6Params();
		lastParams = func.getLastParams();
		movList = new MyList<>();
		colCnt = 19;
		outCol = 19;

		for (int i = 0; i < blocks.size(); ++i) {
			MyList<Quad> codes = blocks.get(i).getCodes();
			for (int j = 0; j < codes.size(); ++j) {
				Quad c = codes.get(j);
				addRegister(c.getRt());
				addRegister(c.getR1());
				addRegister(c.getR2());
			}
		}

		activeSet = new SimpleUnionFind(nVar);
		matrix = new boolean[nVar][nVar];
		value = new int[nVar];
		col = new ArrayList<>();

		for (int i = 0; i < nVar; ++i) {
			col.add(new HashSet<>());
		}
	}

	public void regDistribute() {
		buildActiveDomain();
		buildLiveOut();
		buildGraph();
		while (mergeActive());
		calcVal();
		dyeGraph();
		rebuildCodes();
	}

	private void buildActiveDomain() {
		for (int i = 0; i < blocks.size(); ++i) {
			MyList<Quad> codes = blocks.get(i).getCodes();
			for (int j = 0; j < codes.size(); ++j) {
				Quad c = codes.get(j);
				if (c instanceof PhiQuad) {
					String rt = c.getRtName();
					ArrayList<Register> params = ((PhiQuad) c).getParams();
					for (Register v: params) {
						int fu = activeSet.find(nameIdx.get(rt));
						int fv = activeSet.find(nameIdx.get(v.get()));
						activeSet.merge(fu, fv);
					}
				}
			}
		}
	}

	private void buildLiveOut() {
		ActionAnalyzer analyzer = new ActionAnalyzer(blocks);
		liveOut = analyzer.buildLiveOut();
	}

	private void updateRegLive(Oprand r, HashSet<String> liveNow) {
		if (r == null) return;

		String n = r.get();
		if (r instanceof Register) {
			((Register) r).setEntity(n);
			if (liveNow.contains(r.get())) ((Register) r).setWillUse(true);
			else ((Register) r).setWillUse(false);
		} else if (r instanceof MemAccess) {
			updateRegLive(((MemAccess) r).getBase(), liveNow);
			updateRegLive(((MemAccess) r).getOffset(), liveNow);
			updateRegLive(((MemAccess) r).getOffsetCnt(), liveNow);
			updateRegLive(((MemAccess) r).getOffsetSize(), liveNow);
		}
	}

	private void addLiveNow(Oprand r, HashSet<String> liveNow) {
		if (r instanceof Register) liveNow.add(r.get());
		else if (r instanceof MemAccess) {
			addLiveNow(((MemAccess) r).getBase(), liveNow);
			addLiveNow(((MemAccess) r).getOffset(), liveNow);
			addLiveNow(((MemAccess) r).getOffsetCnt(), liveNow);
			addLiveNow(((MemAccess) r).getOffsetSize(), liveNow);
		}
	}

	private void buildGraph() {
		for (int i = 0; i < nVar; ++i) {
			for (int j = 0; j < nVar; ++j) {
				matrix[i][j] = false;
			}
			edge.add(new HashSet<>());
		}

		for (int i = 0; i < blocks.size(); ++i) {
			MyList<Quad> codes = blocks.get(i).getCodes();
			HashSet<String> liveNow = liveOut.get(i);
			for (int j = codes.size() - 1; j >= 0; --j) {
				Quad c = codes.get(j);
				updateRegLive(c.getRt(), liveNow);
				updateRegLive(c.getR1(), liveNow);
				updateRegLive(c.getR2(), liveNow);
				if (!(c instanceof PhiQuad) && c.getRt() instanceof Register) {
					String nt = c.getRtName();
					int u = activeSet.find(nameIdx.get(nt));
					for (String data: liveNow) {
						if (data.equals(nt)) continue;
						int v = activeSet.find(nameIdx.get(data));
						if (matrix[u][v] || (c instanceof MovQuad && c.getRt() instanceof Register && data.equals(c.getR1Name())))
							continue;
						matrix[u][v] = matrix[v][u] = true;
						edge.get(u).add(v);
						edge.get(v).add(u);
						System.out.println("Edge: " + global.get(u) + " " + global.get(v));
					}
					liveNow.remove(nt);
				}
				addLiveNow(c.getR1(), liveNow);
				addLiveNow(c.getR2(), liveNow);
				if (c.getRt() instanceof MemAccess)
					addLiveNow(c.getRt(), liveNow);
			}
			if (i == 0 && lastParams != null) {
				for (String su: lastParams) if (nameIdx.containsKey(su)) {
					int u = activeSet.find(nameIdx.get(su));
					for (String sv: liveNow) {
						int v = activeSet.find(nameIdx.get(sv));
						if (matrix[u][v] || u == v)
							continue;
						matrix[u][v] = matrix[v][u] = true;
						edge.get(u).add(v);
						edge.get(v).add(u);
						System.out.println("Edge: " + global.get(u) + " " + global.get(v));
					}
				}
			}
		}
	}

	private boolean mergeActive() {
		boolean changed = false;
		MyList<Quad> codes;
		for (int i = 0; i < blocks.size(); ++i) {
			codes = blocks.get(i).getCodes();
			for (int j = 0; j < codes.size(); ++j) {
				Quad c = codes.get(j);
				if (c instanceof MovQuad && c.getR1() instanceof Register && c.getRt() instanceof Register) {
					String nu = c.getRtName(), nv = c.getR1Name();
					int u = nameIdx.get(nu), v = nameIdx.get(nv);
					int fu = activeSet.find(u);
					int fv = activeSet.find(v);
					if (!matrix[fu][fv] && fu != fv) {
						activeSet.merge(fu, fv);
						if (activeSet.find(fv) != fv) Tool.<Integer>swap(fu, fv);
						changed = true;

						/* Rebuild graph. */
						HashSet<Integer> edgeList = edge.get(fu);
						while (edgeList.size() > 0) {
							for (int p: edgeList) {
								cut(p, fu);
								link(p, fv);
								break;
							}
						}
					}
				}
			}
		}
		return changed;
	}

	private void calcVal() {
		for (int i = 0; i < blocks.size(); ++i) {
			BasicBlock block = blocks.get(i);
			ArrayList<BasicBlock> succs = block.getSuccs();
			int loop = 1;
			for (BasicBlock v: succs) {
				if (v.getIdx() == i) {
					loop = 100;
					break;
				}
			}
			MyList<Quad> codes = block.getCodes();
			for (int j = 0; j < codes.size(); ++j) {
				Quad c = codes.get(j);
				Oprand r1 = c.getR1(), r2 = c.getR2();
				if (r1 instanceof Register) {
					value[activeSet.find(nameIdx.get(r1.get()))] += loop;
				}
				if (r2 instanceof Register) {
					value[activeSet.find(nameIdx.get(r2.get()))] += loop;
				}
			}
		}
	}

	private void forceDyeOprand(Oprand r, boolean[] dyed, int k) {
		if (r == null) return;

		if (!dyed[k] && r instanceof Register && first6Params[k].equals(((Register) r).getMemPos())) {
			dyed[k] = true;
			col.get(activeSet.find(nameIdx.get(r.get()))).add(k);
		} else if (r instanceof MemAccess) {
			forceDyeOprand(((MemAccess) r).getBase(), dyed, k);
			forceDyeOprand(((MemAccess) r).getOffset(), dyed, k);
			forceDyeOprand(((MemAccess) r).getOffsetCnt(), dyed, k);
			forceDyeOprand(((MemAccess) r).getOffsetSize(), dyed, k);
		}
	}

	private void dyeGraph() {

		boolean[] dyed = new boolean[6];

		/* force dye */
//		for (int i = 0; i < blocks.size(); ++i) {
//			MyList<Quad> codes = blocks.get(i).getCodes();
//			for (int j = 0; j < codes.size(); ++j) {
//				Quad c = codes.get(j);
//				Oprand r1 = c.getR1();
//				Oprand r2 = c.getR2();
//				Oprand rt = c.getRt();
//				for (int k = 0; k < first6Params.length; ++k) {
//					forceDyeOprand(r1, dyed, k);
//					forceDyeOprand(r2, dyed, k);
//					if (rt instanceof MemAccess)
//						forceDyeOprand(rt, dyed, k);
//				}
//				if (c instanceof ParamQuad) {
//					if (paramCnt < 6) {
//						int tmp = activeSet.find(nameIdx.get(c.getR1Name()));
//						col.get(tmp).add(paramCnt++);
//					}
//				}
//				if (c instanceof CallQuad) {
//					paramCnt = 0;
//					if (c.getRt() instanceof Register) {
//						col.get(activeSet.find(nameIdx.get(c.getRtName()))).add(6);
//					}
//				}
//			}
//		}
		if (first6Params != null) {
			for (int i = 0; i < first6Params.length; ++i) {
				col.get(activeSet.find(nameIdx.get(first6Params[i]))).add(i);
			}
		}

		int paramCnt = 0;
		int fightCnt = 0; // The number of the parameters of the function I call fight with my parameters.
		for (int i = 0; i < blocks.size(); ++i) {
			MyList<Quad> codes = blocks.get(i).getCodes();
			for (int j = 0; j < codes.size(); ++j) {
				Quad c = codes.get(j);
				if (c instanceof ParamQuad) {
					if (paramCnt < 6) {
						int u = activeSet.find(nameIdx.get(c.getR1Name()));
						col.get(u).add(paramCnt);
						if (first6Params != null && paramCnt < first6Params.length) {
							String myParam = first6Params[paramCnt];
							int v = activeSet.find(nameIdx.get(myParam));
							if (matrix[u][v]) {
								int nReg = 7 + fightCnt++;
								HashSet<Integer> setv = col.get(v);
								setv.remove(paramCnt);
								setv.add(nReg);
								movList.add(new MovQuad("mov", new Register(regList[nReg], myParam, myParam),
												new Register(regList[paramCnt], myParam, myParam)
										)
								);

							}
						}
						paramCnt++;
					}
				}
				if (c instanceof CallQuad) {
					paramCnt = 0;
					if (c.getRt() instanceof Register) {
						col.get(activeSet.find(nameIdx.get(c.getRtName()))).add(6);
					}
				}
			}
		}

		ArrayList<Integer> sortList = new ArrayList<>();
		for (HashMap.Entry<String, Integer> entry: nameIdx.entrySet()) {
			int u = entry.getValue();
			if (edge.get(u).size() >= colCnt) {
				sortList.add(u);
			}
		}
		Collections.sort(sortList, (x, y) -> value[x] >= value[y] ? 1 : 0);

		ArrayList<Integer> tmp = new ArrayList<>();
		for (int i = 0; i < sortList.size(); ++i) {
			int u = sortList.get(i);
			tmp.clear();
			for (int v: edge.get(u)) {
				tmp.addAll(col.get(v));
			}
			Collections.sort(tmp);
			Tool.unique(tmp);
			int j;
			HashSet<Integer> set = col.get(u);
			for (j = 0; j < tmp.size(); ++j) {
				if (j != tmp.get(j)) {
					set.add(j);
					break;
				}
			}
			if (set.isEmpty()) {
				if (j < colCnt) set.add(j);
			}
		}

		for (HashMap.Entry<String, Integer> entry: nameIdx.entrySet()) {
			int u = entry.getValue();
			HashSet<Integer> set = col.get(u);
			if (set.isEmpty()) { // whose degree is less than colCnt.
				tmp.clear();
				for (int v : edge.get(u)) {
					tmp.addAll(col.get(v));
				}
				Collections.sort(tmp);
				Tool.unique(tmp);
				int j;
				for (j = 0; j < tmp.size(); ++j) {
					if (j != tmp.get(j)) {
						set.add(j);
						break;
					}
				}
				if (set.isEmpty()) {
					if (j < colCnt) set.add(j);
				}
			}
		}
	}

	private void rebuildOprand(Oprand r, int t) {
		if (r instanceof Register) {
			Integer id = nameIdx.get(r.get());
			int nr = col.get(activeSet.find(id)).iterator().next();
			if (nr == -1) r.set(regList[nr + t]);
			else r.set(regList[nr]);
		} else if (r instanceof MemAccess) {
			rebuildOprand(((MemAccess) r).getBase(), 0);
			rebuildOprand(((MemAccess) r).getOffset(), 1);
			rebuildOprand(((MemAccess) r).getOffsetCnt(), 1);
			rebuildOprand(((MemAccess) r).getOffsetSize(), 0);
		}
	}

	private void rebuildCodes() {
		MyList<Quad> codes;
		for (int i = 0; i < blocks.size(); ++i) {
			codes = blocks.get(i).getCodes();

			/* update parameters' color */
			int cnt = 0;
			for (int j = 0; j < codes.size(); ++j) {
				Quad c = codes.get(j);
				if (c instanceof ParamQuad) {
					int id = activeSet.find(nameIdx.get(c.getR1Name()));
					HashSet set = col.get(id);
					if (set.size() > 1) {
						set.remove(cnt++);
					}
				} else if (c instanceof CallQuad) cnt = 0;
			}

			for (int j = 0; j < codes.size(); ++j) {
				Quad c = codes.get(j);
				Oprand rt = c.getRt(), r1 = c.getR1(), r2 = c.getR2();
				rebuildOprand(rt, 0);
				if (c instanceof PhiQuad) continue;
				rebuildOprand(r1, 0);
				rebuildOprand(r2, 1);
			}

			if (i == 0) for (int j = movList.size() - 1; j >= 0; --j) {
				Quad c = codes.getFirst();
				if (c.getLabel() != null) {
					Quad d = movList.get(j);
					d.setLabel(c.getLabel());
					c.setLabel(null);
					codes.addFirst(d);
				}
			}
		}

	}

}
