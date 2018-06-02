package BackEnd;

import GeneralDataStructure.BasicBlock;
import GeneralDataStructure.FuncFrame;
import GeneralDataStructure.MyListClass.MyList;
import GeneralDataStructure.OprandClass.ImmOprand;
import GeneralDataStructure.OprandClass.MemAccess;
import GeneralDataStructure.OprandClass.Oprand;
import GeneralDataStructure.OprandClass.Register;
import GeneralDataStructure.QuadClass.*;
import Optimizer.ActionAnalyzer;
import Utilizer.ConstVar;
import Utilizer.SimpleUnionFind;
import javafx.util.Pair;

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
	private String[] params;
	private String[] first6Params;
	private boolean [][] matrix;
	private int[] value;
	private ArrayList<HashSet<Integer>> col;
	private ArrayList<HashSet<Integer>> deCol;
	private int nVar;

	private int colCnt, outCol;

	/*
	* related to the parameters fighting
	* need to be attach at the beginning
	* */
	HashMap<Pair<String, String>, Quad> movList;

	private String[] regList = {"rdi", "rsi", "rdx", "rcx", "r8", "r9", "rax", "rbx", "r12", "r13", "r14", "r15", "r11", "r10", "rbp", "rsp"};

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

		params = func.getParamList();

		movList = new HashMap<>();
		colCnt = 12;
		outCol = 12;

		for (int i = 0; i < blocks.size(); ++i) {
			MyList<Quad> codes = blocks.get(i).getCodes();
			for (int j = 0; j < codes.size(); ++j) {
				Quad c = codes.get(j);
				if (c instanceof PhiQuad) {
					ArrayList<Register> p = ((PhiQuad) c).getParams();
					for (Register reg: p) addRegister(reg);
				}
				addRegister(c.getRt());
				addRegister(c.getR1());
				addRegister(c.getR2());
			}
		}

		boolean [] isTemp = new boolean [nVar];
		for (int i = 0; i < nVar; ++i) isTemp[i] = Register.isTempReg(global.get(i));
		activeSet = new SimpleUnionFind(nVar, isTemp);

		matrix = new boolean[nVar][nVar];
		value = new int[nVar];
		col = new ArrayList<>();
		deCol = new ArrayList<>();
		for (int i = 0; i < nVar; ++i) {
			col.add(new HashSet<>());
			deCol.add(new HashSet<>());
		}
	}

	public void regDistribute() {
		buildActiveDomain();
		buildLiveOut();
		buildGraph();
//		while (mergeActive());
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
						if (v == null) continue;
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
				addLiveNow(c.getR2(), liveNow);
				int rax = 6, rdx = 2;
				if (!(c instanceof PhiQuad)) {
					if (c.getOp().equals("mul") || c.getOp().equals("div") || c.getOp().equals("mod")) {
						if (c.getR1() instanceof Register) {
							int tmp = activeSet.find(nameIdx.get(c.getR1Name()));
							deCol.get(tmp).add(rax);
							deCol.get(tmp).add(rdx);
						}
						if (c.getR2() instanceof Register) {
							int tmp = activeSet.find(nameIdx.get(c.getR2Name()));
							deCol.get(tmp).add(rax);
							deCol.get(tmp).add(rdx);
						}
					}
					if (c.getRt() instanceof Register || c instanceof ParamQuad && c.getR1() instanceof Register) {
						String nt = c instanceof ParamQuad ? c.getR1Name() : c.getRtName();
						int u = activeSet.find(nameIdx.get(nt));

						for (String data : liveNow) {
							if (data.equals(nt)) continue;
							int v = activeSet.find(nameIdx.get(data));

							/*
							* Can't use the parameters' register.
							* */
							HashSet<Integer> tmpDeCol = deCol.get(v);
							if (c instanceof CallQuad) {
								for (int k = 0; k < 7; ++k) tmpDeCol.add(k);
							} else if (c instanceof A3Quad && (
									c.getOp().equals("mul") ||
									c.getOp().equals("div") ||
									c.getOp().equals("mod"))) {
									tmpDeCol.add(rax);
									tmpDeCol.add(rdx);
							}

							if (u == v || matrix[u][v] || (c instanceof MovQuad && c.getRt() instanceof Register && data.equals(c.getR1Name())))
								continue;
							matrix[u][v] = matrix[v][u] = true;
							edge.get(u).add(v);
							edge.get(v).add(u);
//						System.err.println("Edge: " + global.get(u) + " " + global.get(v));
						}
					}
				}
				addLiveNow(c.getR1(), liveNow);
				if (c.getRt() instanceof MemAccess)
					addLiveNow(c.getRt(), liveNow);
				else if (c.getRt() instanceof Register) liveNow.remove(c.getRtName());
			}
			if (i == 0 && params != null) {
				for (String su: params) if (nameIdx.containsKey(su)) {
					int u = activeSet.find(nameIdx.get(su));
					for (String sv: liveNow) {
						int v = activeSet.find(nameIdx.get(sv));
						if (matrix[u][v] || u == v)
							continue;
						matrix[u][v] = matrix[v][u] = true;
						edge.get(u).add(v);
						edge.get(v).add(u);
//						System.err.println("Edge: " + global.get(u) + " " + global.get(v));
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
					int f;
					if (!matrix[fu][fv] && fu != fv) {
						HashSet<Integer> tmp1 = edge.get(fu), tmp2 = edge.get(fv);
						activeSet.merge(fu, fv);
						f = activeSet.find(fu);

						/* Rebuild graph. */
						HashSet<Integer> edgeList = edge.get(f);
						for (int t: tmp1) {
							int tt = activeSet.find(t);
							edgeList.add(tt);
							matrix[tt][f] = matrix[f][tt] = true;
						}
						for (int t: tmp2) {
							int tt = activeSet.find(t);
							edgeList.add(tt);
							matrix[tt][f] = matrix[f][tt] = true;
						}
						changed = true;
					}
				}
			}
		}
		return changed;
	}

	private void updateRegVal(Oprand r, int loop) {
		if (r instanceof Register) {
			int n = nameIdx.get(r.get());
			if (activeSet.getVal(n)) value[activeSet.find(n)] = ConstVar.INF;
			else value[activeSet.find(n)] += loop;
		} else if (r instanceof MemAccess) {
			updateRegVal(((MemAccess) r).getBase(), loop);
			updateRegVal(((MemAccess) r).getOffset(), loop);
			updateRegVal(((MemAccess) r).getOffsetCnt(), loop);
			updateRegVal(((MemAccess) r).getOffsetSize(), loop);
		}
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
				Oprand r1 = c.getR1(), r2 = c.getR2(), rt = c.getRt();
				updateRegVal(r1, loop);
				updateRegVal(r2, loop);
				if (rt instanceof MemAccess)
					updateRegVal(rt, loop);
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

	private void dyePoint(int u) {
		HashSet<Integer> tmp = new HashSet<>();
		tmp.clear();
		for (int v: edge.get(u)) {
			tmp.addAll(col.get(activeSet.find(v)));
		}
		int j;
		HashSet<Integer> set = col.get(u), tmpDeCol = deCol.get(u);
		boolean isTemp = activeSet.getVal(u);
		for (int i = 0; i < colCnt; ++i) {
			if (!tmp.contains(i) && !tmpDeCol.contains(i)) {
				set.add(i);
				return;
			}
		}
	}

	private void dyeGraph() {

		boolean[] dyed = new boolean[6];

		/* force dye */
		if (first6Params != null) {
			for (int i = 0; i < first6Params.length; ++i) {
				if (nameIdx.containsKey(first6Params[i])) {
					int u = activeSet.find(nameIdx.get(first6Params[i]));
					col.get(u).add(i);
				}
			}
		}

		int paramCnt = 0;
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
							if (!nameIdx.containsKey(myParam)) continue;
							int v = activeSet.find(nameIdx.get(myParam));
							HashSet<Integer> setv = col.get(v);
							if (matrix[u][v] && setv.iterator().next() == paramCnt) {
								int nReg = 7 + paramCnt;
								setv.remove(paramCnt);
								setv.add(nReg);
								movList.put(new Pair<>(regList[nReg], regList[paramCnt]),
											new MovQuad("mov", new Register(regList[nReg], myParam, myParam),
												new Register(regList[paramCnt], myParam, myParam)
											)
								);
							}
						}
						paramCnt++;
					}
				} else if (c instanceof CallQuad) {
					paramCnt = 0;
					if (c.getRt() instanceof Register) {
						col.get(activeSet.find(nameIdx.get(c.getRtName()))).add(6);
					}
				} else if (c.getOp().equals("sal") || c.getOp().equals("sar")) {
					if (c.getRt() instanceof Register) {
						col.get(activeSet.find(nameIdx.get(c.getRtName()))).add(3);
					}
				}
			}
		}

		if (first6Params != null) {
			for (int i = 0; i < first6Params.length; ++i) {
				if (nameIdx.containsKey(first6Params[i])) {
					int u = activeSet.find(nameIdx.get(first6Params[i]));
					HashSet<Integer> set = col.get(u);
					if (deCol.get(u).contains(i) && set.contains(i)) {
						int nReg = 7 + i;
						set.remove(i);
						set.add(nReg);
						movList.put(new Pair<>(regList[nReg], regList[i]),
								new MovQuad("mov", new Register(regList[nReg], first6Params[i], first6Params[i]),
										new Register(regList[i], first6Params[i], first6Params[i])
								)
						);
					}
				}
			}
		}

		ArrayList<Integer> sortList = new ArrayList<>();
		for (HashMap.Entry<String, Integer> entry: nameIdx.entrySet()) {
			int u = entry.getValue();
			int fu = activeSet.find(u);
			if (u == fu && col.get(u).isEmpty()) {
				sortList.add(u);
			}
		}
		sortList.sort((x, y) -> {
			if (value[x] > value[y]) return 1;
			else if (value[x] < value[y]) return -1;
			return 0;
		});

		for (int i = 0; i < sortList.size(); ++i) {
			int u = sortList.get(i);
			dyePoint(u);
		}

		for (HashMap.Entry<String, Integer> entry: nameIdx.entrySet()) {
			int u = entry.getValue();
			HashSet<Integer> set = col.get(u);
			if (set.isEmpty()) { // whose degree is less than colCnt.
				dyePoint(u);
			}
		}
	}

	private void rebuildOprand(Oprand r, int t) {
		if (r instanceof Register) {
			Integer id = nameIdx.get(r.get());
			if (id == null) return;
			HashSet<Integer> nt = col.get(activeSet.find(id));
			if (nt.isEmpty()) r.set(regList[outCol + t]);
			else r.set(regList[nt.iterator().next()]);
		} else if (r instanceof MemAccess) {
			rebuildOprand(((MemAccess) r).getBase(), 1);
			rebuildOprand(((MemAccess) r).getOffset(), 0);
			rebuildOprand(((MemAccess) r).getOffsetCnt(), 0);
			rebuildOprand(((MemAccess) r).getOffsetSize(), 1);
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
				if (c instanceof ParamQuad && c.getR1() instanceof Register && cnt < 6) {
					int id = activeSet.find(nameIdx.get(c.getR1Name()));
					HashSet set = col.get(id);
					if (set.size() > 1) {
						set.remove(cnt++);
						set.size();
					}
				} else if (c instanceof CallQuad) cnt = 0;
			}

		}

		for (int i = 0; i < blocks.size(); ++i) {
			codes = blocks.get(i).getCodes();
			for (int j = 0; j < codes.size(); ++j) {
				Quad c = codes.get(j);
				Oprand rt = c.getRt(), r1 = c.getR1(), r2 = c.getR2();
				rebuildOprand(rt, 0);
				if (c instanceof PhiQuad) continue;
				rebuildOprand(r1, 0);
				rebuildOprand(r2, 1);
			}


			if (i == 0) for (Map.Entry<Pair<String, String>, Quad> entry: movList.entrySet()) {
				Quad c = codes.getFirst();
				if (c.getLabel() != null) {
					Quad d = entry.getValue();
					d.setLabel(c.getLabel());
					c.setLabel(null);
					codes.addFirst(d);
				}
			}
		}

	}

}
