package BackEnd;

import GeneralDataStructure.BasicBlock;
import GeneralDataStructure.FuncFrame;
import GeneralDataStructure.MyListClass.MyList;
import GeneralDataStructure.OprandClass.Oprand;
import GeneralDataStructure.OprandClass.Register;
import GeneralDataStructure.QuadClass.A3Quad;
import GeneralDataStructure.QuadClass.MovQuad;
import GeneralDataStructure.QuadClass.PhiQuad;
import GeneralDataStructure.QuadClass.Quad;
import Optimizer.ActionAnalyzer;
import Utilizer.SimpleUnionFind;
import Utilizer.Tool;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.*;

public class RegDistributor {
	ArrayList<BasicBlock> blocks;
	SimpleUnionFind activeSet;
	ArrayList<String> global;
	HashMap<String, Integer> nameIdx;
	ArrayList<HashSet<Integer>> edge;
	ArrayList<HashSet<String>> liveOut;
	boolean [][] matrix;
	boolean [] exist;
	int[] value, col;
	int nVar;

	int colCnt, outCol;

	String[] regList;

	boolean checkRegister(Oprand opr) {
		return opr instanceof Register && !(nameIdx.containsKey(opr.get()));
	}

	void addRegister(Oprand opr) {
		nameIdx.put(opr.get(), nVar++);
		global.add(opr.get());
	}

	void removeNode(int x) {
		nameIdx.remove(global.get(x));
		exist[x] = false;
	}

	void link(int u, int v) {
		edge.get(u).add(v);
		edge.get(v).add(u);
		matrix[u][v] = matrix[v][u] = true;
	}

	void cut(int u, int v) {
		edge.get(u).remove(v);
		edge.get(v).remove(u);
		matrix[u][v] = matrix[v][u] = false;
	}

	public RegDistributor(FuncFrame func, int colors, String[] regs) {
		blocks = func.getBbList();
		global = new ArrayList<>();
		nameIdx = new HashMap<>();
		edge = new ArrayList<>();
		colCnt = colors - 1;
		outCol = colors - 1;
		regList = regs;

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

	void regDistribute() {
		buildActiveDomain();
		buildLiveOut();
		buildGraph();
		while (mergeActive());
		calcVal();
		dyeGraph();
		rebuildCodes();
	}

	void buildActiveDomain() {
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

	void buildLiveOut() {
		ActionAnalyzer analyzer = new ActionAnalyzer(blocks);
		liveOut = analyzer.buildLiveOut();
	}

	void buildGraph() {
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
				if (c instanceof A3Quad) {
					String rt = c.getRtName();
					String r1 = c.getR1Name();
					String r2 = c.getR2Name();
					int u = activeSet.find(nameIdx.get(rt));
					for (String data: liveNow) {
						if (data.equals(rt)) continue;
						int v = activeSet.find(nameIdx.get(data));
						if (matrix[u][v]) continue;
						matrix[u][v] = matrix[v][u] = true;
						edge.get(u).add(v);
						edge.get(v).add(u);
					}
					if (liveNow.contains(rt)) liveNow.remove(rt);
					liveNow.add(r1);
					liveNow.add(r2);
				}
			}
		}
	}

	boolean mergeActive() {
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

	void calcVal() {
		for (int i = 0; i < blocks.size(); ++i) {
			MyList<Quad> codes = blocks.get(i).getCodes();
			for (int j = 0; j < codes.size(); ++j) {
				Quad c = codes.get(j);
				if (c instanceof A3Quad) {
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

	void dyeGraph() {
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
				int j;
				for (j = 0; j < tmp.size(); ++j) {
					if (j != tmp.get(j)) {
						col[u] = j;
						break;
					}
				}
			}
		}
	}

	void rebuildCodes() {
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
