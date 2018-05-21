package BackEnd;

import GeneralDataStructure.BasicBlock;
import GeneralDataStructure.FuncFrame;
import GeneralDataStructure.MyListClass.MyList;
import GeneralDataStructure.OprandClass.ImmOprand;
import GeneralDataStructure.OprandClass.Oprand;
import GeneralDataStructure.OprandClass.Register;
import GeneralDataStructure.QuadClass.*;
import Utilizer.ConstVar;
import Utilizer.Tool;

import java.util.*;

public class CodeGenFunc {
	private HashMap<String, Long> values;
	private HashMap<String, Long> params;
	private HashMap<String, Long> localVars;
	private HashMap<String, Integer> varSize;
	private HashMap<String, HashSet<String> > entityExist;
	private HashMap<String, HashSet<String>> regStore;
	private HashSet<String> regLive;
	private MyList<Quad> codes;

	private MyList<String> result;

	private String funcName;
	private String[] regList = {"rdi", "rsi", "rdx", "rcx", "r8", "r9", "rax", "r0", "r1", "r2", "r3", "r4", "r5", "r6", "r7", "r10", "r11", "r12", "r13", "r14", "r15", "rbx", "rbp", "rsp"};

	private boolean useRbp;

	private long localParamSize, rspVal;

	private ArrayList<String> tmpParams;

	HashSet<String> getEntityExist(String e) {
		HashSet<String> set = entityExist.get(e);
		if (set == null) {
			set = new HashSet<>();
			entityExist.put(e, set);
		}
		return set;
	}

	public CodeGenFunc(FuncFrame func, HashMap<String, Integer> globalSize) {
		ArrayList<BasicBlock> blocks = func.getBbList();
		values = new HashMap<>();
		entityExist = new HashMap<>();
		regStore = new HashMap<>();
		codes = new MyList<>();
		result = new MyList<>();
		useRbp = false;
		localParamSize = func.getLocalVarSize();
		funcName = func.getName();
		params = func.getParams();
		localVars = func.getLocalVars();
		varSize = func.getVarSize();
		varSize.putAll(globalSize);
		tmpParams = new ArrayList<>();
		rspVal = 0;
		regLive = new HashSet<>();

		for (int i = 0; i < blocks.size(); ++i) {
			codes.addAll(blocks.get(i).getCodes());
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

	public MyList<String> generateCode() {
		subRsp(localParamSize + 15 >> 4 << 4);
		for (int i = 0; i < codes.size(); ++i) {
			Quad c = codes.get(i);
			if (c.getLabel() != null && i > 0) result.add(c.getLabel() + ": ");
			if (c instanceof A3Quad) {
				updateA3Quad(c);
			} else if (c instanceof MovQuad) {
				updateMov(c);
			} else if (c instanceof ParamQuad) {
				tmpParams.add(c.getR1Name());
			} else if (c instanceof CallQuad) {
				updateCall(c);
			}
			modifyQuadRegLive(c);
		}

		if (useRbp) {
			result.add(format("pop", "rbp"));
			result.addFirst(format("mov", "rbp", "rsp"));
			result.addFirst(format("push", "rbp"));
		}
		result.addFirst(codes.get(0).getLabel() + ":");

		return result;
	}

	private  void subRsp(long offset) {
		if (offset == 0) return;
		result.add(format("sub", "rsp", Long.toString(offset)));
	}

	private void addRsp(long offset) {
		if (offset == 0) return;
		result.add(format("add", "rsp", Long.toString(offset)));
	}

	private String translateSize(int sz) {
		switch (sz) {
			case 1: return "byte";
			case 8: return "qword";
			default: return "dword";
		}
	}

	private String format(String op) {
		return String.format("%-10s", op);
	}

	private String format(String op, String op1) {
		return String.format("%-10s%s", op, op1);
	}

	private String format(String op, String op1, String op2) {
		return String.format("%-10s%s", op, op1 + ", " + op2);
	}

	private String format(String op, String op1, String op2, String op3) {
		return String.format("%-10s%s", op, op1 + ", " + op2 + ", " + op3);
	}

	private String getVarMem(String re, String rm) {
		Long offset1 = localVars.get(rm);
		Long offset2 = params.get(rm);
		if (offset1 == null && offset2 == null)
			return String.format(translateSize(varSize.get(re)) + " [rel ]");
		if (offset2 != null && offset2 <= 0) return regList[-offset2.intValue()];
		Long offset = offset1 == null ? offset2 : offset1;

		useRbp = true;
		String tmp = offset > 0 ? '+' + Long.toHexString(offset) : offset == 0 ? "" : "-" + Long.toHexString(-offset);
		return translateSize(varSize.get(re)) + " [" + "rbp" + tmp + ']';
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
		Register rt = (Register) c.getRt();
		Oprand r1 = c.getR1(), r2 = c.getR2();
		String n1 = r1.get(), n2 = r2.get(), nt = rt.get();
		boolean certain1 = r1 instanceof ImmOprand || r1 instanceof Register && values.containsKey(n1);
		boolean certain2 = r2 instanceof ImmOprand || r2 instanceof Register && values.containsKey(n2);

		if (!certain1 && r1 instanceof Register) loadRegister((Register) r1);
		if (!certain2 && r2 instanceof Register) loadRegister((Register) r2);

		storeRegister(rt);
		getEntityExist(rt.getEntity()).add(nt);
		regStore.get(nt).add(rt.getEntity());
		if (certain1 && certain2) {
			values.put(nt, calc(c.getOp(), getValue(r1), getValue(r2)));
			return;
		} else {
			if (certain1) c.changeR1(new ImmOprand(values.get(n1)));
			if (certain2) c.changeR2(new ImmOprand(values.get(n2)));
			translate(c);
		}
		modifySize((rt).getEntity(), varSize.get(((Register) r1).getEntity()));
	}

	private void updateMov(Quad c) {
		Register rt = (Register) c.getRt();
		Oprand r1 = c.getR1();
		String nt = rt.get();
		Long tmp;

		storeRegister(rt);

		if (r1 instanceof ImmOprand) {
			values.remove(nt);
			tmp = ((ImmOprand) r1).getVal();
			values.put(nt, tmp);
			modifySize(rt.getEntity(), ((ImmOprand) r1).getSize());
			return;
		}
		if (r1 instanceof Register) {
			String n1 = r1.get();
			tmp = values.get(r1);
			if (tmp == null) {
				HashSet<String> setR1 = regStore.get(n1);
				HashSet<String> setRt = regStore.get(nt);
				for (String data: setR1) {
					update(getEntityExist(data), nt, setRt);
				}
				translate(c);
			} else {
				values.put(nt, tmp);
			}
			modifySize(rt.getEntity(), varSize.get(((Register) r1).getEntity()));
		}
	}

	private void push(String reg, int sz) {
		if (rspVal % sz != 0) {
			long newRsp = (rspVal + sz - 1) / sz * sz;
			subRsp(newRsp - rspVal);
			rspVal = newRsp + sz;
		}
		result.add(format("push", reg));
	}

	private void updateCall(Quad c) {
		int sz = tmpParams.size();
		long oldRsp = rspVal;
		for (int j = 0; j < sz; ++j) {
			String tr = tmpParams.get(j);
			if (j < 6) {
				String reg = regList[j];
				HashSet<String> set = regStore.get(reg);
				Long val = values.get(tr);
				if (tr != reg) {
					if (!regStore.get(reg).isEmpty()) {
						push(reg, ConstVar.intLen);
					}
					if (val != null) {
						result.add(format("mov", reg, Long.toString(val)));
					} else {
						result.add(format("mov", reg, tr));
					}
				}
			} else {
				push(tr, ConstVar.intLen);
			}
		}

		addRsp(rspVal - oldRsp);
		rspVal = oldRsp;
		if (c.getRt() != null) {
			Register rt = (Register) c.getRt();
			String nt = rt.get();
			storeRegister(rt);
			regStore.get(nt).add(rt.getEntity());
			getEntityExist(rt.getEntity()).add(nt);
			modifySize(rt.getEntity(), varSize.get(c.getR1().get()));
		}

		boolean raxUse = regLive.contains("rax");
		if (raxUse) push("rax", varSize.get(regStore.get("rax").iterator().next()));
		translate(c);
		if (raxUse) result.add(format("pop"));
	}

	private void update(HashSet<String> entities, String reg, HashSet<String> regSt) {
		for (String data: entities) getEntityExist(data).add(reg);
		regSt.addAll(entities);
	}

	private void loadRegister(Register r) {
		String rn = r.get();
		String rm = r.getMemPos();
		String re = r.getEntity();
		HashSet<String> set = getEntityExist(re);
		if (!set.isEmpty()) {
			if (set.contains(rn)) return;
			String tmp = set.iterator().next();
			result.add(format("mov", rn, tmp));
			update(regStore.get(tmp), rn, regStore.get(rn));
		} else {
			String pos;
			if (params.containsKey(re)) {
				long tmp = params.get(re);
				if (tmp < 0) pos = regList[-(int)tmp];
				else pos = getVarMem(re, rm);
			}
			else pos = getVarMem(re, rm);
			result.add(format( "mov", rn, pos));
			getEntityExist(re).add(rn);
			regStore.get(rn).add(re);
		}
		modifySize(re, varSize.get(re));
	}

	private void storeRegister(Register r) {
		String rn = r.get();
		if (!regStore.get(rn).isEmpty()) {
			for (String e: regStore.get(rn)) {
				HashSet<String> exist = getEntityExist(e);
				exist.remove(rn);
				if (exist.isEmpty()) {
					if (regLive.contains(r.getEntity())) {
						result.add(format("mov", getVarMem(e, e.split("_", 2)[0]), rn));
					}
				}
			}
		}
		regStore.get(rn).clear();
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
		String nt = c.getRtName(), n1 = c.getR1Name(), n2 = c.getR2Name();
		switch (c.getOp()) {
			case "add":
				if (c.getR1() instanceof ImmOprand && ((ImmOprand) c.getR1()).getVal() == 1) {
					result.add(format("mov", nt, n2));
					result.add(format("inc", nt));
				} else if (c.getR2() instanceof ImmOprand && ((ImmOprand) c.getR2()).getVal() == 1) {
					result.add(format("mov", nt, n1));
					result.add(format("inc", nt));
				} else {
					result.add(format("lea", nt, String.format("[%s]", n1 + "+" + n2)));
				}
				break;
			case "sub":
				if (c.getR1() instanceof ImmOprand && ((ImmOprand) c.getR1()).getVal() == 0) {
					result.add(format("mov", nt, n2));
					result.add(format("neg", nt));
				} else if (c.getR2() instanceof ImmOprand && ((ImmOprand) c.getR2()).getVal() == 1) {
					result.add(format("mov", nt, n1));
					result.add(format("dec", nt));
				} else {
					result.add(format("mov", nt, n1));
					result.add(format("lea", nt, String.format("%s", n1 + "-" + n2)));
				}
				break;
			case "mul":
				if (c.getR1() instanceof ImmOprand) {
					long tmp = ((ImmOprand) c.getR1()).getVal();
					Long log = 0L;
					if (Tool.isPow2(tmp, log)) {

						break;
					}
				}
				if (c.getR2() instanceof ImmOprand) {
					long tmp = ((ImmOprand) c.getR2()).getVal();
					Long log = 0L;
					if (Tool.isPow2(tmp, log)) {

						break;
					}
				}
				result.add(format("imul", nt, n1, n2));
				break;
			case "div":
				result.add(format("xchg", n1, "eax"));
				result.add(format("xchg", nt, "edx"));
				result.add(format("div", n2));
				result.add(format("xchg", n1, "eax"));
				result.add(format("xchg", nt, "edx"));
				break;
			case "shl":
				result.add(format("mov", nt, n1));
				result.add(format("sal", nt, n2));
				break;
			case "shr":
				result.add(format("mov", nt, n1));
				result.add(format("sar", nt, n2));
				break;
			case "and":
				result.add(format("mov", nt, n1));
				result.add(format("and", nt, n2));
				break;
			case "or":
				result.add(format("mov", nt, n1));
				result.add(format("or", nt, n2));
				break;
			case "xor":
				result.add(format("mov", nt, n1));
				result.add(format("xor", nt, n2));
				break;
			case "not":
				result.add(format("mov", nt, n1));
				result.add(format("not", nt));
				break;
			case "mov":
				if (nt != n1) result.add(format("mov", nt, n1));
				break;
			case "call":
				result.add(format("call", c.getR1Name()));
				if (c.getRt() != null)
					result.add(format("mov", c.getRtName(), "rax"));
		}
	}

	private long getValue(Oprand r) {
		return r instanceof ImmOprand ? ((ImmOprand) r).getVal() : values.get(r.get());
	}
}
