package BackEnd;

import GeneralDataStructure.BasicBlock;
import GeneralDataStructure.FuncFrame;
import GeneralDataStructure.MyListClass.MyList;
import GeneralDataStructure.OprandClass.Oprand;
import GeneralDataStructure.OprandClass.Register;
import GeneralDataStructure.QuadClass.*;
import Optimizer.ActionAnalyzer;
import Utilizer.SimpleUnionFind;
import Utilizer.Tool;

import java.util.*;

public class RegDistributor {
	private ArrayList<BasicBlock> blocks;
	private SimpleUnionFind activeSet;
	private ArrayList<String> global;
	private HashMap<String, Integer> nameIdx;
	private ArrayList<HashSet<Integer>> edge;
	private ArrayList<HashSet<String>> liveOut;

	private String[] funcParams;
	private boolean [][] matrix;
	private boolean [] exist;
	private int[] value, col;
	private int nVar;

	private int colCnt, outCol;

	private String[] regList = {"rdi", "rsi", "rdx", "rcx", "r8", "r9", "rax", "r0", "r1", "r2", "r3", "r4", "r5", "r6", "r7", "r10", "r11", "r12", "r13", "r14", "r15", "rbx", "rbp", "rsp"};

	private boolean checkRegister(Oprand opr) {
		return opr instanceof Register && !(nameIdx.containsKey(opr.get()));
	}

	private void addRegister(Oprand opr) {
		nameIdx.put(opr.get(), nVar++);
		global.add(opr.get());
	}

	private void removeNode(int x) {
		nameIdx.remove(global.get(x));
		exist[x] = false;
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

	public RegDistributor(FuncFrame func) {
		blocks = func.getBbList();
		global = new ArrayList<>();
		nameIdx = new HashMap<>();
		edge = new ArrayList<>();
		funcParams = func.getFirst6Params();
		colCnt = 22;
		outCol = 22;

		for (int i = 0; i < blocks.size(); ++i) {
			MyList<Quad> codes = blocks.get(i).getCodes();
			for (int j = 0; j < codes.size(); ++j) {
				Quad c = codes.get(j);
				if (checkRegister(c.getRt())) addRegister(c.getRt());
				if (checkRegister(c.getR1())) addRegister(c.getR1());
				if (checkRegister(c.getR2())) addRegister(c.getR2());
			}
		}

		activeSet = new SimpleUnionFind(nVar);
		matrix = new boolean[nVar][nVar];
		exist = new boolean[nVar];
		value = new int[nVar];
		col = new int[nVar];

		for (int i = 0; i < nVar; ++i) {
			exist[i] = true;
			col[i] = -1;
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
					for (int k = 0; k < params.size(); ++k) {
						int fu = activeSet.find(nameIdx.get(rt));
						int fv = activeSet.find(nameIdx.get(params.get(k).get()));
						activeSet.merge(nameIdx.get(rt), nameIdx.get(params.get(k).get()));
						if (fv != activeSet.find(fv)) Tool.swap(fu, fv);
						removeNode(fu);
					}
				}
			}
		}
	}

	private void buildLiveOut() {
		ActionAnalyzer analyzer = new ActionAnalyzer(blocks);
		liveOut = analyzer.buildLiveOut();
	}

	void updateRegLive(Register r, HashSet<String> liveNow) {
		r.setEntity(r.get());
		if (liveNow.contains(r.get())) r.setWillUse(true);
		else r.setWillUse(false);
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
				if (c.getRt() instanceof Register) {
					updateRegLive((Register) c.getRt(), liveNow);
				}
				if (c.getR1() instanceof Register) {
					updateRegLive((Register) c.getR1(), liveNow);
				}
				if (c.getR2() instanceof Register) {
					updateRegLive((Register) c.getR2(), liveNow);
				}
				if (c instanceof A3Quad || c instanceof MovQuad || c instanceof CallQuad) {
					if (c.getRt() == null) continue;
					String nt = c.getRtName();
					int u = activeSet.find(nameIdx.get(nt));
					for (String data: liveNow) {
						if (data.equals(nt)) continue;
						int v = activeSet.find(nameIdx.get(data));
						if (matrix[u][v] || (c instanceof MovQuad && data.equals(c.getR1Name()))) continue;
						matrix[u][v] = matrix[v][u] = true;
						edge.get(u).add(v);
						edge.get(v).add(u);
						System.out.println("Edge: " + global.get(u) + " " + global.get(v));
					}
					if (liveNow.contains(nt)) {
						liveNow.remove(nt);
						c.setLive(true);
					} else {
						c.setLive(false);
					}
					if (c.getR1() instanceof Register) liveNow.add(c.getR1Name());
					if (c.getR2() instanceof Register) liveNow.add(c.getR2Name());
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
				if (c instanceof MovQuad && c.getR1() instanceof Register) {
					String nu = c.getRtName(), nv = c.getR1Name();
					int u = nameIdx.get(nu), v = nameIdx.get(nv);
					if (!matrix[u][v]) {
						int fu = activeSet.find(u);
						int fv = activeSet.find(v);
						activeSet.merge(u, v);
						if (activeSet.find(v) != fv) Tool.<Integer>swap(u, v);
						removeNode(fu);
						changed = true;

						/* Rebuild graph. */
						HashSet<Integer> edgeList = edge.get(fu);
						for (int  p: edgeList) {
							cut(p, fu);
							link(p, fv);
						}
					}
				}
			}
		}
		return changed;
	}

	private void calcVal() {
		for (int i = 0; i < blocks.size(); ++i) {
			MyList<Quad> codes = blocks.get(i).getCodes();
			for (int j = 0; j < codes.size(); ++j) {
				Quad c = codes.get(j);
				if (c instanceof A3Quad || c instanceof MovQuad || c instanceof ParamQuad) {
					Oprand r1 = c.getR1(), r2 = c.getR2();
					if (r1 instanceof Register) {
						++value[activeSet.find(nameIdx.get(r1.get()))];
					}
					if (r2 instanceof Register) {
						++value[activeSet.find(nameIdx.get(r2.get()))];
					}
				}
			}
		}
	}

	private void dyeGraph() {

		boolean[] dyed = new boolean[6];

		/* force dye */
		for (int i = 0; i < blocks.size(); ++i) {
			MyList<Quad> codes = blocks.get(i).getCodes();
			for (int j = 0; j < codes.size(); ++j) {
				Quad c = codes.get(j);
				Oprand r1 = c.getR1();
				Oprand r2 = c.getR2();
				for (int k = 0; k < funcParams.length; ++k) {
					if (!dyed[k] && r1 instanceof Register && funcParams[k].equals(((Register) r1).getMemPos())) {
						dyed[k] = true;
						col[activeSet.find(nameIdx.get(r1.get()))] = k;
					}
					if (!dyed[k] && r2 instanceof Register && funcParams[k].equals(((Register) r2).getMemPos())) {
						dyed[k] = true;
						col[activeSet.find(nameIdx.get(r2.get()))] = k;
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
				if (col[v] >= 0) tmp.add(col[v]);
			}
			Collections.sort(tmp);
			Tool.unique(tmp);
			int j;
			for (j = 0; j < tmp.size(); ++j) {
				if (j != tmp.get(j)) {
					col[u] = j;
					break;
				}
			}
			if (col[u] == -1) {
				if (j < colCnt) col[u] = j;
				else {
					col[u] = outCol; // Can't be dyed.
				}
			}
		}

		for (HashMap.Entry<String, Integer> entry: nameIdx.entrySet()) {
			int u = entry.getValue();
			if (col[u] == -1) { // whose degree is less than colCnt.
				tmp.clear();
				for (int v : edge.get(u)) {
					if (col[v] >= 0) tmp.add(col[v]);
				}
				Collections.sort(tmp);
				Tool.unique(tmp);
				int j;
				for (j = 0; j < tmp.size(); ++j) {
					if (j != tmp.get(j)) {
						col[u] = j;
						break;
					}
				}
				if (col[u] == -1) {
					if (j < colCnt) col[u] = j;
					else {
						col[u] = outCol; // Can't be dyed.
					}
				}
			}
		}
	}

	private void rebuildCodes() {
		MyList<Quad> codes;
		for (int i = 0; i < blocks.size(); ++i) {
			codes = blocks.get(i).getCodes();

			/* Remove phi. */
			for (int j = 0; j < codes.size(); ++j) {
				Quad c = codes.get(j);
				if (!(c instanceof PhiQuad)) {
					codes.removeBefore(j);
					break;
				}
			}

			for (int j = 0; j < codes.size(); ++j) {
				Quad c = codes.get(j);
				Oprand rt = c.getRt(), r1 = c.getR1(), r2 = c.getR2();
				if (rt instanceof Register) {
					int t = activeSet.find(nameIdx.get(rt.get()));
					int nrt = col[activeSet.find(nameIdx.get(rt.get()))];
					rt.set(regList[nrt]);
				}
				if (r1 instanceof Register) {
					int nr1 = col[activeSet.find(nameIdx.get(r1.get()))];
					r1.set(regList[nr1]);
				}
				if (r2 instanceof Register) {
					int nr2 = col[activeSet.find(nameIdx.get(r2.get()))];
					r2.set(regList[nr2]);
				}
			}
		}

	}

}
