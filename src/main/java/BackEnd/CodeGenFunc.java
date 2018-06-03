package BackEnd;

import GeneralDataStructure.BasicBlock;
import GeneralDataStructure.Format;
import GeneralDataStructure.FuncFrame;
import GeneralDataStructure.MyListClass.MyList;
import GeneralDataStructure.OprandClass.*;
import GeneralDataStructure.QuadClass.*;
import Utilizer.ConstVar;
import Utilizer.Tool;
import Utilizer.UnionFind;

import java.util.*;

import static java.lang.Integer.min;

public class CodeGenFunc {
	private FuncFrame func;
	private HashMap<String, Long> values;
	private HashMap<String, Long> params;
	private HashMap<String, Long> localVars;
	private HashMap<String, Long> newLocalVars;
	private HashMap<String, Integer> varSize;
	private HashMap<String, HashSet<String> > entityExist;
	private HashMap<String, HashSet<String>> regStore;
	private HashSet<String> regLive;

	/*
	* related to blocks
	* */
	private BasicBlock firstBlock;
	private ArrayList<BasicBlock> blockList;
	private boolean [] visited;
	private int visitedCnt;

	private MyList<Format> result;
	private ArrayList<String> resCode;

	private String funcName;
	private String[] regList = {"rdi", "rsi", "rdx", "rcx", "r8", "r9", "rax", "rbx", "r12", "r13", "r14", "r15", "r11", "r10", "rbp", "rsp"};

	private boolean useRbp;

	private long localParamSize, rspVal;

	private ArrayList<Oprand> tmpParams;

	/*
	* related to label resolving
	* */
	private UnionFind<String> labelMerge;
	private String nextLabel;

	/*
	* related to register saving
	* */
	private HashSet<String> calleeSaveReg;

	/*
	* related to kick out
	* */
	private int outReg = 12;

	/*
	* related to moving parameters in stack into register
	* */
	private HashMap<String, String> paramsInStack;

	public CodeGenFunc(FuncFrame func, HashMap<String, Integer> globalSize) {
		this.func = func;
		ArrayList<BasicBlock> blocks = func.getBbList();
		values = new HashMap<>();
		entityExist = new HashMap<>();
		regStore = new HashMap<>();
		result = new MyList<>();

		blockList = func.getBbList();
		firstBlock = func.getFirst();
		visited = new boolean [func.getBbList().size()];
		for (int i = 0; i < visited.length; ++i) visited[i] = false;

		resCode = new ArrayList<>();
		useRbp = false;
		funcName = func.getName();
		params = func.getParams();
		localVars = func.getLocalVars();
		varSize = func.getVarSize();
		varSize.putAll(globalSize);
		tmpParams = new ArrayList<>();
		rspVal = 8;
		regLive = new HashSet<>();
		labelMerge = new UnionFind<>();
		calleeSaveReg = new HashSet<>();
		paramsInStack = new HashMap<>();
		for (int i = 0; i < regList.length; ++i) regStore.put(regList[i], new HashSet<>());
		newLocalVars = new HashMap<>();
	}

	private HashSet<String> getEntityExist(String e) {
		HashSet<String> set = entityExist.get(e);
		if (set == null) {
			set = new HashSet<>();
			entityExist.put(e, set);
		}
		return set;
	}

	private void addResult(Format code) {
		if (nextLabel != null) {
			code.setLabel(nextLabel);
			nextLabel = null;
		}
		result.add(code);
	}

	private void addFirstResult(Format code) {
//		if (nextLabel != null) code.setLabel(nextLabel);
		result.addFirst(code);
	}

//	private void updateLabel() {
//		for (int i = 1; i < result.size(); ++i) {
//			Format code = result.get(i);
//			String label = code.getLabel();
//			String op = code.getOp();
//			String r1 = code.getR1();
//			if (label != null) code.setLabel(labelMerge.find(label));
//			if (op == null) return;
//			switch (op) {
//				case "je": case "jne":
//				case "jl": case	"jle":
//				case "jg": case "jge":
//				case "jmp":
//					code.setR1(labelMerge.find(r1));
//			}
//		}
//	}

	private void addCalleeSaveRegister(Oprand r) {
		if (r instanceof Register) {
			String reg = r.get();
			for (int i = 7; i < outReg; ++i)
				if (reg.equals(regList[i]))
					calleeSaveReg.add(reg);
		} /*else if (r instanceof MemAccess) {
			addCalleeSaveRegister(((MemAccess) r).getBase());
			addCalleeSaveRegister(((MemAccess) r).getOffset());
			addCalleeSaveRegister(((MemAccess) r).getOffsetCnt());
			addCalleeSaveRegister(((MemAccess) r).getOffsetSize());
		}*/
	}

	private void checkParamInStack(Oprand r) {
		if (r instanceof Register) {
			String n = r.get();
			String e = ((Register) r).getEntity();
			String m = ((Register) r).getMemPos();

			if (!e.equals(m)) return; // be redefined

			Long offset = params.get(e);
			if (offset == null || offset <= 0) return;
			paramsInStack.put(m, n);
			if (!n.equals("rax")) calleeSaveReg.add(n);
		} else if (r instanceof MemAccess) {
			checkParamInStack(((MemAccess) r).getBase());
			checkParamInStack(((MemAccess) r).getOffsetSize());
			checkParamInStack(((MemAccess) r).getOffset());
			checkParamInStack(((MemAccess) r).getOffsetCnt());
		}
	}

	public ArrayList<String> generateCode() {

		generateBlocks(firstBlock);

		/*
		* Mov the parameters in stack into registers.
		* */
		for (Map.Entry<String, String> entry: paramsInStack.entrySet()) {
			if (!entry.getValue().equals(regList[outReg]) && !entry.getValue().equals(regList[outReg + 1])) {
				addFirstResult(new Format("mov", entry.getValue(), getVarMem(entry.getKey(), entry.getKey())));
			}
		}

		/*
		* Add the enter codes.
		* */

		rspVal = 8 + (useRbp ? 1 : 0) * 8;
		if (!funcName.equals("main")) rspVal += calleeSaveReg.size() * 8;
		long delta;
		localParamSize = newLocalVars.size() * 8;
		if ((rspVal & 15) == 8) {
			delta = (localParamSize & 15) == 8 ? localParamSize : localParamSize + 8;
		} else {
			delta = (localParamSize & 15) == 0 ? localParamSize : localParamSize + 8;
		}
		subRspFirst(delta);

		/*
		* Add the leave codes.
		* */
		nextLabel = "end_" + funcName;

		addRsp(delta);
		if (useRbp) {
			addResult(new Format("pop", "rbp"));
			addFirstResult(new Format("mov", "rbp", "rsp"));
			addFirstResult(new Format("push", "rbp"));
		}

		String[] regs = new String[calleeSaveReg.size()];
		if (!funcName.equals("main")) {
			calleeSaveReg.toArray(regs);
			for (int i = 0; i < regs.length; ++i) {
				addFirstResult(new Format("push", regs[i]));
			}
		}


		if (!funcName.equals("main")) {
			for (int i = 0; i < regs.length; ++i) {
				addResult(new Format("pop", regs[i]));
			}
		}



		addResult(new Format("ret"));

		/*
		* Add the function name label.
		* */
		addFirstResult(new Format());
		result.getFirst().setLabel(funcName);

//		updateLabel();

		for (int i = 0; i < result.size(); ++i) {
			resCode.add(result.get(i).toString());
		}

		return resCode;
	}

	public void generateBlocks(BasicBlock block) {
		++visitedCnt;
		visited[block.getIdx()] = true;
		MyList<Quad> codes = block.getCodes();
		ArrayList<BasicBlock> succBlocks = block.getSuccs();

		for (int i = 0; i < codes.size(); ++i) {
			Quad c = codes.get(i);
			Oprand rt = c.getRt();
			Oprand r1 = c.getR1();
			Oprand r2 = c.getR2();

			if (rt instanceof MemAccess) checkParamInStack(rt);
			checkParamInStack(r1);
			checkParamInStack(r2);

			addCalleeSaveRegister(rt);
		}

		for (String reg: regStore.keySet()) {
			regStore.get(reg).clear();
		}


//		for (Map.Entry<String, Long> entry: params.entrySet()) {
//			String n = entry.getKey();
//			long offset = entry.getValue();
//			if (offset <= 0) {
//				HashSet<String> tmp = getEntityExist(n);
//				tmp.add(regList[-(int)offset]);
//			}
//		}

		if (!block.getName().equals(funcName)) nextLabel = block.getName();
		for (int i = 0; i < codes.size(); ++i) {
			Quad c = codes.get(i);
			Oprand rt = c.getRt(), r1 = c.getR1(), r2 = c.getR2();

			if (c instanceof PhiQuad) {
//				modifySize(((Register) rt).getEntity(), ConstVar.addrLen);
				regStore.get(c.getRtName()).add(((Register) rt).getEntity());
//				getEntityExist(((Register) rt).getEntity()).add(c.getRtName());
				continue;
			}

			loadRegister(r1);
			loadRegister(r2);
			if (rt instanceof MemAccess) loadRegister(rt);
			storeRegister(rt);
			if (c instanceof ParamQuad) storeRegister(r1);

			if (c instanceof A3Quad) {
				updateA3Quad(c);
			} else if (c instanceof MovQuad) {
				updateMov(c);
			} else if (c instanceof ParamQuad) {
				tmpParams.add(c.getR1());
			} else if (c instanceof CallQuad) {
				updateCall(c);
			} else if (c instanceof RetQuad) {
				if (visitedCnt < blockList.size())
					updateRet(c, "end_" + funcName);
				else updateRet(c, null);
			} else if (c instanceof JumpQuad) {
				BasicBlock succ = block.succs.get(0);
				if (!visited[succ.getIdx()]) {
					updateJump(c, succ.getName());
					generateBlocks(succ);
				} else updateJump(c, null);
			} else if (c instanceof CJumpQuad) {
				BasicBlock succ1 = succBlocks.get(0);
				BasicBlock succ2 = succBlocks.get(1);
				if (!visited[succ1.getIdx()]) {
					updateCondJump(c, succ1.getName());
					generateBlocks(succ1);
					if (!visited[succ2.getIdx()]) generateBlocks(succ2);
				} else if (!visited[succ2.getIdx()]) {
					updateCondJump(c, succ2.getName());
					generateBlocks(succ2);
				} else {
					updateCondJump(c, succ1.getName());
					updateJump(new JumpQuad("jump", new LabelName(succ1.getName())), null);
				}
			} else if (c instanceof CondQuad) {
				updateCond(c);
			} else {
				translate(c);
			}
			updateRt(rt);
			modifyQuadRegLive(c);
		}

		if (succBlocks.isEmpty() && visitedCnt < blockList.size()) {
			updateJump(new JumpQuad("jump", new LabelName("end_" + funcName)), null);
		}
	}

	private  void subRspFirst(long offset) {
		if (offset == 0) return;
		addFirstResult(new Format("sub", "rsp", Long.toString(offset)));
	}

	private  void subRsp(long offset) {
		if (offset == 0) return;
		addResult(new Format("sub", "rsp", Long.toString(offset)));
	}

	private void addRsp(long offset) {
		if (offset == 0) return;
		addResult(new Format("add", "rsp", Long.toString(offset)));
	}

	private String translateSize(int sz) {
		switch (sz) {
			case 1: return "byte";
			case 8: return "qword";
			default: return "dword";
		}
	}

	private String getVarMem(String re, String rm) {
		Long offset1 = localVars.get(rm);
		Long offset2 = params.get(rm);
		if (offset1 == null && offset2 == null)
			return String.format("qword" + " [rel %s]", rm);
		/*
		* in case of modifying the parameters
		* */
		if (offset2 != null && offset2 <= 0 && re.equals(rm)) return regList[-offset2.intValue()];
		useRbp = true;
		if (offset2 == null && !newLocalVars.containsKey(rm)) newLocalVars.put(rm, -(long) newLocalVars.size() * 8 - 8);
		offset1 = newLocalVars.get(rm);
		Long offset = offset1 == null ? offset2 : offset1;
		useRbp = true;
		String tmp = offset > 0 ? '+' + Long.toString(offset + (calleeSaveReg.size() + 1) * 8) : offset == 0 ? "" : "-" + Long.toString(-offset);
		return "qword" + " [" + "rbp" + tmp + ']';
	}

	private void modifySize(String re, int sz) {
		if (!varSize.containsKey(re)) varSize.put(re, sz);
		else varSize.replace(re, sz);

	}

	void modifyRegLive(Register reg) {
		String rn = reg.get();
		String re = reg.getEntity();
		if (reg.getWillUse() && !regLive.contains(re)) regLive.add(rn);
		if (!reg.getWillUse() && regLive.contains(re)) regLive.remove(rn);
	}

	void modifyQuadRegLive(Quad c) {
		if (c.getRt() instanceof Register) modifyRegLive((Register) c.getRt());
		if (c.getR1() instanceof Register) modifyRegLive((Register) c.getR1());
		if (c.getR2() instanceof Register) modifyRegLive((Register) c.getR2());
	}

	private void updateA3Quad(Quad c) {
		Oprand rt = c.getRt();
		Oprand r1 = c.getR1(), r2 = c.getR2();
		String n1 = r1.get(), n2 = r2.get();
		String nt = rt.get();
		boolean certain1 = r1 instanceof ImmOprand;
		boolean certain2 = r2 instanceof ImmOprand;

		if (certain1 && certain2) {
			long val = calc(c.getOp(), getValue(r1), getValue(r2));
			translate(new MovQuad("mov", rt, new ImmOprand(val)));
			return;
		} else {
			String op = c.getOp();
			if ((op.equals("div") || op.equals("mod") || op.equals("mul")) &&
					!(c.getR1() instanceof ImmOprand) && !(c.getR2() instanceof ImmOprand)) {
				boolean raxUse = !regStore.get("rax").isEmpty();
				boolean rdxUse = !regStore.get("rdx").isEmpty();
				if (raxUse && !n2.equals("rax")) translate(new MovQuad("mov", new Register(regList[outReg]), new Register("rax")));
				if (rdxUse && !n2.equals("rdx")) translate(new MovQuad("mov", new Register(regList[outReg + 1]), new Register("rdx")));
				translate(new MovQuad("mov", new Register("rax"), r1));
				translate(c);
				if (op.equals("mod")) translate(new MovQuad("mov", rt, new Register("rdx")));
				else translate(new MovQuad("mov", rt, new Register("rax")));
				if (rdxUse && !n2.equals("rdx")) translate(new MovQuad("mov", new Register("rdx"), new Register(regList[outReg + 1])));
				if (raxUse && !n2.equals("rax")) translate(new MovQuad("mov", new Register("rax"), new Register(regList[outReg])));
			} else translate(c);
		}
//		modifySize((rt).getEntity(), ConstVar.addrLen);
	}

	private void updateMov(Quad c) {
		if (c.rt.get().equals(c.r1.get())) return;

		if (c.getRt() instanceof MemAccess || !(c.getR1() instanceof Register)) {
			translate(c);
			return;
		}

		Register rt = (Register) c.getRt();
		Register r1 = (Register) c.getR1();
		String nt = rt.get();
		HashSet<String> reg1 = regStore.get(nt);
		if (reg1.contains(r1.getEntity())) return;
		reg1.add(r1.getEntity());
		translate(c);
	}

	private void push(String reg, int sz) {
		rspVal += sz;
		if (Character.isDigit(reg.charAt(0))) addResult(new Format("pushl", reg));
		else if (reg.charAt(0) == 't') addResult(new Format("pushl", "1"));
		else if (reg.charAt(0) == 'f') addResult(new Format("pushl", "0"));
		else addResult(new Format("push", reg));
	}

	private void pop(String reg, int sz) {
		addResult(new Format("pop", reg));
		rspVal -= sz;
	}

	private void updateCall(Quad c) {
		int cnt = (int)((ImmOprand) c.getR2()).getVal();
		int up = tmpParams.size();
		int down = tmpParams.size() - cnt;
		long oldRsp;

//		Stack<Pair<String, Integer> > pushReg = new Stack<>();

		for (int j = 0; j < min(6, up - down); ++j) {
			String tr = tmpParams.get(down + j).get();
//			if (j < 6) {
				String reg = regList[j];
//				HashSet<String> set = regStore.get(reg);
//				Long val = values.get(tr);
				if (tr != reg) {
//					if (regLive.contains(reg)) {
//						push(reg, ConstVar.intLen);
//						pushReg.add(new Pair<>(reg, ConstVar.intLen));
//					}
//					if (val != null) {
//						if (val == 0) addResult(new Format("xor", reg, reg));
//						addResult(new Format("mov", reg, Long.toString(val)));
//					} else {
						updateMov(new MovQuad("mov", new Register(reg), new Register(tr)));
//					}
				}
//			}
		}

		long size = up - down > 6 ? 8 * (up - down - 6) : (6 - (up - down)) * 8;
		if ((size & 15) == 8) subRsp(8);

		for (int j = up - down - 1; j >= 6; --j) {
			Oprand r = tmpParams.get(down + j);
			if (! (r instanceof Register)) {
				translate(new MovQuad("mov", new Register(regList[outReg]), r));
				push(regList[outReg], ConstVar.intLen);
			} else push(r.get(), ConstVar.addrLen);
		}
		for (int i = up - down; i < 6; ++i) {
			push(regList[i], ConstVar.addrLen);
		}
		for (int i = 0; i < cnt; ++i) tmpParams.remove(tmpParams.size() - 1);

//		if (c.getRt() != null) {
//			Register rt = (Register) c.getRt();
//			String nt = rt.get();
//			modifySize(rt.getEntity(), ConstVar.addrLen);
//		}

//		boolean raxUse = regLive.contains("rax");
//		if (raxUse) push("rax", varSize.get(regStore.get("rax").iterator().next()));
		translate(c);
		for (int i = 5; i >= up - down; --i) {
			pop(regList[i], ConstVar.addrLen);
		}
		if (up - down > 6) {
			if ((size & 15) == 8) {
				addRsp(size + 8);
			} else addRsp(size);
		} else if ((size & 15) == 8) addRsp(8);
//		if (raxUse) pop("rax", ConstVar.addrLen);
//		while (!pushReg.isEmpty()) {
//			Pair<String, Integer> u = pushReg.pop();
//			pop(u.getKey(), u.getValue());
//		}
	}

	private void updateRet(Quad c, String label) {
		translate(c);
		if (label != null) addResult(new Format("jmp", label));
	}

	private void updateJump(Quad c, String nextLabel) {
		if (nextLabel == null || !c.getRtName().equals(nextLabel)) {
//			if (nextLabel != null) labelMerge.merge(nextLabel, c.getRtName());
			translate(c);
		} else translate(null);
	}

	private void updateCond(Quad c) {
		translate(c);
	}

	private void updateCondJump(Quad c, String nextLabel) {
		if (nextLabel != null && nextLabel.equals(c.getR1Name())) {
			switch (c.op) {
				case "je": c.changeOp("jne"); break;
				case "jne": c.changeOp("je"); break;
				case "jl": c.changeOp("jge"); break;
				case "jg": c.changeOp("jle"); break;
				case "jle": c.changeOp("jg"); break;
				case "jge": c.changeOp("jl"); break;
			}
			c.changeRt(c.getR2());
		}
		else c.changeRt(c.getR1());
		translate(c);
	}

	private void updateRt(Oprand r) {
		if (!(r instanceof Register)) return;
		String n = r.get();
		String e = ((Register) r).getEntity();
		String m = ((Register) r).getMemPos();
		if (e != null) {
//			HashSet<String> set = getEntityExist(e);
			if (n.equals(regList[outReg]) || n.equals(regList[outReg + 1])) {
				addResult(new Format("mov", getVarMem(e, m), n));
				HashSet<String> regSet = regStore.get(n);
				regSet.add(e);
			}
//			for (String reg : set) {
//				update(entityExist.get(reg), n, regSet);
//			}
//			set.add(n);
		}
	}

	private void update(HashSet<String> entities, String reg, HashSet<String> regSt) {
		for (String data: entities) getEntityExist(data).add(reg);
		regSt.addAll(entities);
	}

	private void loadRegister(Oprand r) {
		if (r instanceof Register) loadRegister((Register) r);
		else if (r instanceof MemAccess) {
			loadRegister(((MemAccess) r).getBase());
			loadRegister(((MemAccess) r).getOffset());
			loadRegister(((MemAccess) r).getOffsetCnt());
			loadRegister(((MemAccess) r).getOffsetSize());
		}
	}

	private void loadRegister(Register r) {
		String rn = r.get();
		String rm = r.getMemPos();
		String re = r.getEntity();
		if (re == null) return;
//		HashSet<String> set = getEntityExist(re);

		if (rn.equals(regList[outReg]) || rn.equals(regList[outReg + 1])) {
//			if (!set.isEmpty()) {
//				if (set.contains(rn)) return;
//				String tmp = set.iterator().next();
//				addResult(new Format("mov", rn, tmp));
//				update(regStore.get(tmp), rn, regStore.get(rn));
//			} else {
			assert(rm != null);
			String pos = getVarMem(re, rm);
			addResult(new Format("mov", rn, pos));
//				regStore.get(rn).add(re);
//			}
		} else regStore.get(rn).add(re);
//		modifySize(re, ConstVar.addrLen);
	}

	private void storeRegister(Oprand r) {
		if (r instanceof Register) storeRegister((Register) r);
	}

	private void storeRegister(Register r) {
		String rn = r.get();
		String re = r.getEntity();
		String rm = r.getMemPos();
//		if (rn.equals(regList[outReg]) || rn.equals(regList[outReg + 1])) {
//			if (!regStore.get(rn).isEmpty()) {
//				for (String e : regStore.get(rn)) {
//					HashSet<String> exist = getEntityExist(e);
//					exist.remove(rn);
//					if (exist.isEmpty()) {
//						if (regLive.contains(r.getEntity())) {
//							addResult(new Format("mov", getVarMem(re, rm), rn));
//						}
//					}
//				}
//			}
//		} else {
			regStore.get(rn).clear();
//		}
	}

	private long calc(String op, long a, long b) {
		switch (op) {
			case "add": return a + b;
			case "sub": return a - b;
			case "mul": return a * b;
			case "div": return a / b;
			case "mod": return a % b;
			case "lsh": return a << b;
			case "rsh": return a >> b;
			case "and": return a & b;
			case "or" : return a | b;
			case "xor": return a ^ b;
			case "equ": return a == b ? 1 : 0;
			case "neq": return a != b ? 1 : 0;
			case "les": return a <  b ? 1 : 0;
			case "leq": return a <= b ? 1 : 0;
			case "gre": return a >  b ? 1 : 0;
			case "geq": return a >= b ? 1 : 0;
			case "not": return ~a;
			case "mov": return a;
		}
		assert (false);
		return 0;
	}

	private void translate(Quad c) {
		if (c == null) {
			if (nextLabel != null) addResult(new Format());
			return;
		}
		String nt = c.getRt() == null ? null : c.getRtName();
		String n1 = c.getR1() == null ? null : c.getR1Name();
		String n2 = c.getR2() == null ? null : c.getR2Name();
		String oop = null;
		switch (c.getOp()) {
			case "lea":
				addResult(new Format("lea", nt, n1.substring(6, n1.length())));
				break;
			case "add":
				if (c.getR1() instanceof Register && c.getR2() instanceof Register && c.getRt() instanceof Register) {
					addResult(new Format("lea", nt, String.format("[%s]", n1 + "+" + n2)));
					break;
				}
			case "sub":
			case "and":	case "or" :  case "xor":
				if(!nt.equals(n1)) addResult(new Format("mov", nt, n1));
				addResult(new Format(c.getOp(), nt, n2));
				break;
			case "sal":	case "sar":
				if (!nt.equals(n1)) addResult(new Format("mov", nt, n1));
				if (c.getR2() instanceof ImmOprand) addResult(new Format(c.getOp(), nt, n2));
				else addResult(new Format(c.getOp(), nt, "cl"));
				break;
			case "mul":
				Oprand tmp1, tmp2;
				if (c.getR1() instanceof ImmOprand) {
					tmp1 = c.getR2();
					tmp2 = c.getR1();
				} else {
					tmp1 = c.getR1();
					tmp2 = c.getR2();
				}
				if (tmp2 instanceof ImmOprand) {
					long tmp = ((ImmOprand) tmp2).getVal();
					if (Tool.isPow2(tmp)) {
						if (tmp <= 8 && tmp1 instanceof Register)
							addResult(new Format("lea", nt, String.format("[%s*%d]", n1, tmp)));
						else {
							long log = Tool.log2(tmp);
							if (log >= 0) {
								addResult(new Format("mov", nt, tmp1.get()));
							} else {
								addResult(new Format("mov", nt, tmp1.get()));
								addResult(new Format("neg", nt));
								log = -log;
							}
							if (log > 0) addResult(new Format("sal", nt, Long.toString(log)));
						}
						break;
					} else addResult(new Format("imul", nt, tmp1.get(), tmp2.get()));
				} else addResult(new Format("imul", n2));
				break;
			case "div":
				if (c.getR2() instanceof ImmOprand && Tool.isPow2(((ImmOprand) c.getR2()).getVal())) {
					long log = Tool.log2(((ImmOprand) c.getR2()).getVal());
					translate(new A3Quad("sar", c.getRt(), c.getR1(), new ImmOprand(log)));
					break;
				}
				addResult(new Format("cqo"));
				addResult(new Format("idiv", n2));
				break;
			case "mod":
				if (c.getR2() instanceof ImmOprand && Tool.isPow2(((ImmOprand) c.getR2()).getVal())) {
					translate(new A3Quad("and", c.getRt(), c.getR1(), new ImmOprand(((ImmOprand) c.getR2()).getVal() - 1)));
					break;
				}
				addResult(new Format("cqo"));
				addResult(new Format("idiv", n2));
				break;
			case "not":
				if (!nt.equals(n1)) addResult(new Format("mov", nt, n1));
				addResult(new Format("not", nt));
				break;
			case "neg":
				if (!nt.equals(n1)) addResult(new Format("mov", nt, n1));
				addResult(new Format("neg", nt));
				break;
			case "mov":
				if (!nt.equals(n1)) {
					addResult(new Format("mov", nt, n1));
				}
				break;
			case "call":
				addResult(new Format("call", c.getR1Name()));
				break;

			case "cmp":
				if (c.getR1() instanceof ImmOprand) addResult(new Format("cmp", c.getR2Name(), c.getR1Name()));
				else addResult(new Format("cmp", c.getR1Name(), c.getR2Name()));
				break;
			case "jump": c.setOp("jmp");
			case "je":
			case "jne":
			case "jl":
			case "jle":
			case "jg":
			case "jge":
				addResult(new Format(c.getOp(), labelMerge.find(c.getRtName())));
				break;
			case "ret":
				Oprand r1 = c.getR1();
				if (r1 != null) {
//			Long val = values.get(c.getR1Name());
					if (r1 instanceof ImmOprand && ((ImmOprand) r1).getVal() == 0) addResult(new Format("xor", "rax", "rax"));
					else if (!n1.equals("rax")) addResult(new Format("mov", "rax", n1));
				}
				break;
			default:
				addResult(new Format("nop"));
		}
	}

	private long getValue(Oprand r) {
		return ((ImmOprand) r).getVal();
	}
}
