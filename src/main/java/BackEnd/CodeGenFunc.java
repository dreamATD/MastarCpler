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
import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.*;

import static java.lang.Integer.min;

public class CodeGenFunc {
	private FuncFrame func;
	private HashMap<String, Long> values;
	private HashMap<String, Long> params;
	private HashMap<String, Long> localVars;
	private HashMap<String, Integer> varSize;
	private HashMap<String, HashSet<String> > entityExist;
	private HashMap<String, HashSet<String>> regStore;
	private HashSet<String> regLive;
	private MyList<Quad> codes;

	private MyList<Format> result;
	private ArrayList<String> resCode;

	private String funcName;
	private String[] regList = {"rdi", "rsi", "rdx", "rcx", "r8", "r9", "rax", "rbx", "r12", "r13", "r14", "r10", "r11", "r15", "rbp", "rsp"};

	private boolean useRbp;

	private long localParamSize, rspVal;

	private ArrayList<String> tmpParams;

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

	private Format retFormat;

	public CodeGenFunc(FuncFrame func, HashMap<String, Integer> globalSize) {
		this.func = func;
		ArrayList<BasicBlock> blocks = func.getBbList();
		values = new HashMap<>();
		entityExist = new HashMap<>();
		regStore = new HashMap<>();
		codes = new MyList<>();
		result = new MyList<>();
		resCode = new ArrayList<>();
		useRbp = false;
		localParamSize = func.getLocalVarSize();
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

		for (int i = 0; i < blocks.size(); ++i) {
			codes.addAll(blocks.get(i).getCodes());
		}

		for (int i = 0; i < codes.size(); ++i) {
			Quad c = codes.get(i);
			Oprand rt = c.getRt();
			Oprand r1 = c.getR1();
			Oprand r2 = c.getR2();

			addCalleeSaveRegister(rt);
			addCalleeSaveRegister(r1);
			addCalleeSaveRegister(r2);
		}

		for (int i = 0; i < regList.length; ++i) regStore.put(regList[i], new HashSet<>());

		for (Map.Entry<String, Long> entry: params.entrySet()) {
			String n = entry.getKey();
			long offset = entry.getValue();
			if (offset <= 0) {
				HashSet<String> tmp = getEntityExist(n);
				tmp.add(regList[-(int)offset]);
			}
		}

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
			for (int i = 0; i < 7; ++i) if (reg.equals(regList[i])) return;
			calleeSaveReg.add(reg);
		} else if (r instanceof MemAccess) {
			addCalleeSaveRegister(((MemAccess) r).getBase());
			addCalleeSaveRegister(((MemAccess) r).getOffset());
			addCalleeSaveRegister(((MemAccess) r).getOffsetCnt());
			addCalleeSaveRegister(((MemAccess) r).getOffsetSize());
		}
	}

	private void checkParamInStack(Oprand r) {
		if (!(r instanceof Register)) return;
		String n = r.get();
		String e = ((Register) r).getEntity();
		String m = ((Register) r).getMemPos();

		if (!e.equals(m)) return; // be redefined

		Long offset = params.get(e);
		if (offset == null || offset <= 0) return;
		paramsInStack.put(m, n);
	}

	public ArrayList<String> generateCode() {
		rspVal += calleeSaveReg.size() * 8;
		long old = rspVal;
		if ((rspVal & 15) == 8) {
			subRspFirst((localParamSize & 15) == 8 ? localParamSize : localParamSize + 8);
		}
		else {
			subRspFirst((localParamSize & 15) == 0 ? localParamSize : localParamSize + 8);
		}
		retFormat = new Format("add", "rsp", Long.toString(rspVal - old));

		for (int i = 0; i < codes.size(); ++i) {
			Quad c = codes.get(i);
			Oprand rt = c.getRt(), r1 = c.getR1(), r2 = c.getR2();

			if (c instanceof PhiQuad) {
				modifySize(((Register) rt).getEntity(), ConstVar.addrLen);
				regStore.get(c.getRtName()).add(((Register) rt).getEntity());
				getEntityExist(((Register) rt).getEntity()).add(c.getRtName());
				continue;
			}
			if (c.getLabel() != null && i > 0) {
//				if (!result.isEmpty() && result.getLast().getOp() == null) {
//					String lastCode = result.getLast().getLabel();
//					if (lastCode != null) {
//						labelMerge.merge(c.getLabel(), lastCode);
//						result.removeLast();
//					}
//				}
				nextLabel = c.getLabel();
			}

			loadRegister(r1);
			loadRegister(r2);
			if (rt instanceof MemAccess) loadRegister(rt);
			storeRegister(rt);

			if (c instanceof A3Quad) {
				updateA3Quad(c);
			} else if (c instanceof MovQuad) {
				updateMov(c);
			} else if (c instanceof ParamQuad) {
				tmpParams.add(c.getR1Name());
			} else if (c instanceof CallQuad) {
				updateCall(c);
			} else if (c instanceof RetQuad) {
				updateRet(c);
			} else if (c instanceof JumpQuad) {
				if (i + 1 < codes.size()) updateJump(c, codes.get(i + 1));
				else updateJump(c, null);
			} else if (c instanceof CJumpQuad) {
				updateCondJump(c, codes.get(i + 1));
			} else if (c instanceof CondQuad) {
				updateCond(c);
			}
			updateRt(rt);
			modifyQuadRegLive(c);
		}

		for (Map.Entry<String, String> entry: paramsInStack.entrySet()) {
			addResult(new Format("mov", entry.getValue(), entry.getKey()));
		}

		if (useRbp) {
			addResult(new Format("pop"));
			addFirstResult(new Format("mov", "rbp", "rsp"));
			addFirstResult(new Format("push", "rbp"));
		}

		if (!funcName.equals("main")) {
			String[] regs = new String[calleeSaveReg.size()];
			calleeSaveReg.toArray(regs);
			for (int i = 0; i < regs.length; ++i) {
				addFirstResult(new Format("push", regs[i]));
			}
			if (func.getRetSize() == 0) {
				addResult(retFormat);
				for (int i = regs.length - 1; i >= 0; --i) {
					addResult(new Format("pop", regs[i]));
				}
			}
		}

		addFirstResult(new Format());
		result.getFirst().setLabel(funcName);

//		updateLabel();

		for (int i = 0; i < result.size(); ++i) {
			resCode.add(result.get(i).toString());
		}

		return resCode;
	}

	private  void subRspFirst(long offset) {
		if (offset == 0) return;
		addFirstResult(new Format("sub", "rsp", Long.toString(offset)));
		rspVal += offset;
	}

	private  void subRsp(long offset) {
		if (offset == 0) return;
		addResult(new Format("sub", "rsp", Long.toString(offset)));
		rspVal += offset;
	}

	private void addRsp(long offset) {
		if (offset == 0) return;
		addResult(new Format("add", "rsp", Long.toString(offset)));
		rspVal -= offset;
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
		if (offset2 != null && offset2 <= 0 && !re.equals(rm)) return regList[-offset2.intValue()];
		Long offset = offset1 == null ? offset2 : offset1;

		useRbp = true;
		String tmp = offset > 0 ? '+' + Long.toHexString(offset) : offset == 0 ? "" : "-" + Long.toHexString(-offset);
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
			if (certain1) c.changeR1(new ImmOprand(getValue(r1)));
			if (certain2) c.changeR2(new ImmOprand(getValue(r2)));
			String op = c.getOp();
			if (op.equals("div")) {
				boolean raxUse = regLive.contains("rax") && !nt.equals("rax");
				if (raxUse) translate(new MovQuad("mov", new Register(regList[outReg]), new Register("rax")));
				updateMov(new MovQuad("mov", new Register("rax"), r1));
				translate(c);
				updateMov(new MovQuad("mov", rt, new Register("rax")));
				if (raxUse) translate(new MovQuad("mov", new Register("rax"), new Register(regList[outReg])));
			} else if (op.equals("mod")) {
				boolean raxUse = regLive.contains("rax");
				boolean rdxUse = regLive.contains("rdx") && !nt.equals("rdx");
				if (raxUse) updateMov(new MovQuad("mov", new Register(regList[outReg]), new Register("rax")));
				if (rdxUse) updateMov(new MovQuad("mov", new Register(regList[outReg + 1]), new Register("rdx")));
				updateMov(new MovQuad("mov", new Register("rax"), r1));
				translate(c);
				updateMov(new MovQuad("mov", rt, new Register("rdx")));
				if (rdxUse) updateMov(new MovQuad("mov", new Register("rdx"), new Register(regList[outReg + 1])));
				if (raxUse) updateMov(new MovQuad("mov", new Register("rax"), new Register(regList[outReg])));
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
		Oprand r1 = c.getR1();
		String nt = rt.get();
		Long tmp;

		if (r1 instanceof ImmOprand) {
//			values.remove(nt);
//			tmp = ((ImmOprand) r1).getVal();
//			values.put(nt, tmp);
			translate(c);
//			modifySize(rt.getEntity(), ConstVar.addrLen);
			return;
		} else if (r1 instanceof Register) {
			String n1 = r1.get();
//			tmp = values.get(r1);
//			if (tmp == null) {
			HashSet<String> setR1 = regStore.get(n1);
			HashSet<String> setRt = regStore.get(nt);
			for (String data: setR1) {
				update(getEntityExist(data), nt, setRt);
			}
			translate(c);
//			} else {
//				values.put(nt, tmp);
//				translate(null);
//			}
//			modifySize(rt.getEntity(), ConstVar.addrLen);
		}
	}

	private void push(String reg, int sz) {
		if (rspVal % sz != 0) {
			long newRsp = (rspVal + sz - 1) / sz * sz;
			subRsp(newRsp - rspVal);
			rspVal = newRsp;
		}
		rspVal += sz;
		if (Character.isDigit(reg.charAt(0))) addResult(new Format("pushl", reg));
		else if (reg.charAt(0) == 't') addResult(new Format("pushl", "1"));
		else if (reg.charAt(0) == 'f') addResult(new Format("pushl", "0"));
		else addResult(new Format("push", reg));
	}

	private void pop(String reg, int sz) {
		addResult(new Format("pop", reg));
		rspVal -= 8;
	}

	private void updateCall(Quad c) {
		int cnt = (int)((ImmOprand) c.getR2()).getVal();
		int up = tmpParams.size();
		int down = tmpParams.size() - cnt;
		long oldRsp;

//		Stack<Pair<String, Integer> > pushReg = new Stack<>();

		for (int j = 0; j < min(6, up - down); ++j) {
			String tr = tmpParams.get(down + j);
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

		oldRsp = rspVal;

		for (int j = up - down - 1; j >= 6; --j) {
			push(tmpParams.get(down + j), ConstVar.intLen);
		}
		for (int i = 0; i < cnt; ++i) tmpParams.remove(tmpParams.size() - 1);

//		if (c.getRt() != null) {
//			Register rt = (Register) c.getRt();
//			String nt = rt.get();
//			modifySize(rt.getEntity(), ConstVar.addrLen);
//		}

//		boolean raxUse = regLive.contains("rax");
//		if (raxUse) push("rax", varSize.get(regStore.get("rax").iterator().next()));
		if ((rspVal & 15) != 0) subRsp(8);
		translate(c);
//		if (raxUse) pop("rax", ConstVar.addrLen);
		addRsp(rspVal - oldRsp);
		rspVal = oldRsp;
//		while (!pushReg.isEmpty()) {
//			Pair<String, Integer> u = pushReg.pop();
//			pop(u.getKey(), u.getValue());
//		}
	}

	private void updateRet(Quad c) {
		translate(c);
	}

	private void updateJump(Quad c, Quad cNext) {
		if (cNext == null || !c.getRtName().equals(cNext.getLabel())) {
//			if (nextLabel != null) labelMerge.merge(nextLabel, c.getRtName());
			translate(c);
		} else translate(null);
	}

	private void updateCond(Quad c) {
		translate(c);
	}

	private void updateCondJump(Quad c, Quad cNext) {
		if (cNext.getLabel().equals(c.getR1Name())) {
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
		if (e != null) {
			HashSet<String> set = getEntityExist(e);
			HashSet<String> regSet = regStore.get(n);
			regSet.clear();
			regSet.add(e);
//			for (String reg : set) {
//				update(entityExist.get(reg), n, regSet);
//			}
			set.add(n);
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
		HashSet<String> set = getEntityExist(re);

		if (rn.equals(regList[outReg]) || rn.equals(regList[outReg + 1])) {
			if (!set.isEmpty()) {
				if (set.contains(rn)) return;
				String tmp = set.iterator().next();
				addResult(new Format("mov", rn, tmp));
				update(regStore.get(tmp), rn, regStore.get(rn));
			} else {
				String pos = getVarMem(re, rm);
				addResult(new Format("mov", rn, pos));
				getEntityExist(re).add(rn);
				regStore.get(rn).add(re);
			}
		}
//		modifySize(re, ConstVar.addrLen);
	}

	private void storeRegister(Oprand r) {
		if (r instanceof Register) storeRegister((Register) r);
	}

	private void storeRegister(Register r) {
		String rn = r.get();
		if (rn.equals(regList[outReg]) || rn.equals(regList[outReg + 1])) {
			if (!regStore.get(rn).isEmpty()) {
				for (String e : regStore.get(rn)) {
					HashSet<String> exist = getEntityExist(e);
					exist.remove(rn);
					if (exist.isEmpty()) {
						if (regLive.contains(r.getEntity())) {
							addResult(new Format("mov", getVarMem(e, e.split("_", 2)[0]), rn));
						}
					}
				}
			}
		}
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
			case "add":
				if (c.getR1() instanceof Register && c.getR2() instanceof Register && c.getRt() instanceof Register)
					addResult(new Format("lea", nt, String.format("[%s]", n1 + "+" + n2)));
				else {
					addResult(new Format("mov", nt, n1));
					addResult(new Format("add", nt, n2));
				}
				break;
			case "sub":
				if (c.getR1() instanceof ImmOprand && ((ImmOprand) c.getR1()).getVal() == 0) {
					if (!nt.equals(n2)) addResult(new Format("mov", nt, n2));
					addResult(new Format("neg", nt));
				} else if (c.getR2() instanceof ImmOprand && ((ImmOprand) c.getR2()).getVal() == 1) {
					if (!nt.equals(n1)) addResult(new Format("mov", nt, n1));
					addResult(new Format("dec", nt));
				} else {
					if (!nt.equals(n1)) addResult(new Format("mov", nt, n1));
					addResult(new Format("lea", nt, String.format("%s", n1 + "-" + n2)));
				}
				break;
			case "mul":
				if (c.getR1() instanceof ImmOprand) {
					long tmp = ((ImmOprand) c.getR1()).getVal();
					Long log = 0L;
					if (Tool.isPow2(tmp, log)) {
						addResult(new Format("lea", nt, String.format("[%s*%d]", tmp, n2)));
						break;
					}
				}
				if (c.getR2() instanceof ImmOprand) {
					long tmp = ((ImmOprand) c.getR2()).getVal();
					Long log = 0L;
					if (Tool.isPow2(tmp, log)) {
						addResult(new Format("lea", nt, String.format("[%s*%d]", n1, tmp)));
						break;
					}
				}
				addResult(new Format("imul", nt, n1, n2));
				break;
			case "div": ;
				addResult(new Format("cqo"));
				addResult(new Format("idiv", n2));
				break;
			case "mod":
				addResult(new Format("cqo"));
				addResult(new Format("idiv", n2));
				break;
			case "sal":	case "sar":
			case "and":	case "or" :  case "xor":
				if (!nt.equals(n1)) addResult(new Format("mov", nt, n1));
				addResult(new Format(c.getOp(), nt, n2));
				break;
			case "not":
				if (!nt.equals(n1)) addResult(new Format("mov", nt, n1));
				addResult(new Format("not", nt));
				break;
			case "mov":
				if (!nt.equals(n1)) addResult(new Format("mov", nt, n1));
				break;
			case "call":
				addResult(new Format("call", c.getR1Name()));
				if (c.getRt() != null)
					if (!c.getRtName().equals("rax")) addResult(new Format("mov", c.getRtName(), "rax"));
				break;
			case "equ": if (oop == null) oop = "sete";
			case "neq": if (oop == null) oop = "setne";
			case "les": if (oop == null) oop = "setl";
			case "leq": if (oop == null) oop = "setle";
			case "gre": if (oop == null) oop = "setg";
			case "geq": if (oop == null) oop = "setge";
				addResult(new Format("cmp", c.getR1Name(), c.getR2Name()));
				addResult(new Format(oop, c.getRtName()));
				break;
			case "cmp":
				addResult(new Format("cmp", c.getR1Name(), c.getR2Name()));
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
				String[] regs = new String[calleeSaveReg.size()];
				calleeSaveReg.toArray(regs);
				for (int i = regs.length - 1; i >= 0; --i) {
					addResult(new Format("pop", regs[i]));
				}
				addResult(retFormat);
				addResult(new Format("ret"));
				break;
		}
	}

	private long getValue(Oprand r) {
		return ((ImmOprand) r).getVal();
	}
}
