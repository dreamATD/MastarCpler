package FrontEnd;

import GeneralDataStructure.*;
import GeneralDataStructure.OprandClass.*;
import GeneralDataStructure.QuadClass.*;
import GeneralDataStructure.TypeSystem.*;
import Utilizer.ConstVar;
import Utilizer.Tool;
import javafx.util.Pair;

import java.nio.ByteBuffer;
import java.util.*;

import static GeneralDataStructure.OprandClass.Register.isTempReg;
import static Utilizer.ConstVar.addrLen;

enum VarDefStatus {GeneralVar, FuncParam, ClassObj, LovalVar}

public class IRBuilder extends AstVisitor {
	public LinearIR linearCode;

	private FuncFrame curFunc;
	private ArrayList<Quad> curCodeList;

	/*
	* related to the labels
	* */
	private int labelCnt;                   // the counter of temporary names;
	private List<Integer> nextStatLabel;    // the temporary names which need to jump to the next quad.
	private Stack<Integer> ifElseEndLabel;  // the end of if-else structure.
	private int quadLabelCnt;               // the counter of exist label names.
	private LabelTable uset;                // To match the temporary numbers with label name.
	private Stack<Integer> brkLabel;
	private Stack<Pair<Integer, Boolean>> ctnLabel;

	private VarDefStatus varState;

	/*
	* related to values of general variables
	* */
	private SymbolTable<String> generalVarStr;
	private SymbolTable<Long> generalVarInt;
	private SymbolTable<Long> generalVarBool;

	/*
	* related to the class
	* */
	private ClassDefTypeRef curClass; // for construction function
	private HashMap<String, Long> classObj;
	private HashSet<String> classObjStr;
	private long classObjSize;

	/*
	* related to the self-generated loops
	* */
	int selfGenLoopCnt;

	public IRBuilder() {
		linearCode = new LinearIR();
		labelCnt = 0;
		nextStatLabel = new ArrayList<>();
		brkLabel = new Stack<>();
		ctnLabel = new Stack<>();
		uset = new LabelTable();
		curCodeList = new ArrayList<>();
		ifElseEndLabel = new Stack<>();
		generalVarBool = new SymbolTable<>();
		generalVarInt = new SymbolTable<>();
		generalVarBool = new SymbolTable<>();
		selfGenLoopCnt = 0;
		classObjStr = new HashSet<>();
	}

	public LinearIR generateIR(Node root) throws Exception {
		visit(root);
		return linearCode;
	}

	/*
	 * private functions
	 * */

	private void insertFunc(FuncFrame fun) {
		updateLabel(uset, curCodeList);
		setMemPosition(curCodeList);
		curFunc.sortLocalVar();
		curFunc.buildCFG(curCodeList);
		curCodeList.clear();
		linearCode.insertTextFunc(fun);
	}

	private void insertInit(FuncFrame fun) {
		updateLabel(uset, curCodeList);
		setMemPosition(curCodeList);
		curFunc.sortLocalVar();
		curFunc.buildCFG(curCodeList);
		linearCode.insertInitFunc(curFunc);
		curCodeList.clear();
	}

	private Register changeOpr2Reg(Oprand r) {
		if (r instanceof Register) return (Register) r;
		Register reg = new Register(getTempName());
		insertQuad(new MovQuad("mov", reg, r.copy()));
		return reg.copy();
	}

	private ArrayList<Quad> paramsQuads = new ArrayList<>();
	private void insertQuad(Quad ins) {
		switch (ins.getOp()) {
			case "cmp":
				if (ins.getR1() instanceof MemAccess) ins.changeR1(changeOpr2Reg(ins.getR1()));
				if (ins.getR2() instanceof MemAccess) ins.changeR2(changeOpr2Reg(ins.getR2()));
		}
		if (nextStatLabel.size() > 0) {
			String label = nextLabel();
			ins.setLabel(label);
			for (int data: nextStatLabel) {
				uset.set(data, label);
			}
			nextStatLabel.clear();
		}
		if (ins instanceof ParamQuad) paramsQuads.add(ins);
		else if (ins instanceof CallQuad) {
			for (int i = 0; i < paramsQuads.size(); ++i) curCodeList.add(paramsQuads.get(i));
			paramsQuads.clear();
			curCodeList.add(ins);
		} else curCodeList.add(ins);
	}

	private void updateLabel(LabelTable labels, ArrayList<Quad> code) {
		for (int i = 0; i < code.size(); ++i) {
			Quad qua = code.get(i);
			qua.updateLabel(labels);
		}
	}

	private void setMemPosition(ArrayList<Quad> codes) {
		for (int i = 0; i < codes.size(); ++i) {
			Quad c = codes.get(i);
			Oprand r1 = c.getR1(), r2 = c.getR2(), rt = c.getRt();
			if (r1 instanceof Register) {
				String n1 = r1.get();
				if (!isTempReg(n1)) ((Register) r1).setMemPos(n1);
			}
			if (r2 instanceof Register) {
				String n2 = r2.get();
				if (!isTempReg(n2)) ((Register) r2).setMemPos(n2);
			}
			if (rt instanceof Register) {
				String nt = rt.get();
				if (!isTempReg(nt)) ((Register) rt).setMemPos(nt);
			}
		}
	}

	private void updateNextStatLabel(int t) {
		nextStatLabel.add(t);
		uset.add(t);
	}

	private String nextLabel() {
		String str = "lb" + Integer.toString(quadLabelCnt++);
		return str;
	}

	private String classFuncLabel(String className, String funcName) {
		return "C_" + className + "_" + funcName;
	}

	private String funcLabel(String funcName) {
		return funcName.equals("main") ? funcName : "F_" + funcName;
	}

	private String initFuncLabel(String funcName) {
		return "F_init_" + funcName;
	}

	private String getTempName() {
		return "V_" + Integer.toString(labelCnt++);
	}

	public void visitChild(Node nod) throws Exception {
		if (nod == null) return;
		for (int i = 0; i < nod.sons.size(); ++i) {
			visit(nod.sons.get(i));
		}
	}

	private void generateCondition(Node nod, int labelTrue, int labelFalse) throws Exception {
		if (nod.id != null && nod.id.equals("true")) {
			insertQuad(new JumpQuad("jump", new LabelName(Integer.toString(labelTrue))));
			return;
		} else if (nod.id != null && nod.id.equals("false")) {
			insertQuad(new JumpQuad("jump", new LabelName(Integer.toString(labelFalse))));
			return;
		} else if (nod.reg != null || nod instanceof FuncExprNode || nod instanceof ArrExprNode || nod instanceof ObjAccExprNode) {
			visit(nod);
			Register tmp;
			if (nod.reg instanceof Register) tmp = (Register) nod.reg;
			else {
				tmp = new Register(getTempName());
				insertQuad(new MovQuad("mov", tmp, nod.reg));
			}
			insertQuad(new CondQuad("cmp", tmp, new ImmOprand(0)));
			insertQuad(new CJumpQuad("jne", new LabelName(Integer.toString(labelTrue)), new LabelName(Integer.toString(labelFalse))));
			return;
		}

		Node left = nod.sons.get(0);
		Node right = nod.sons.size() > 1 ? nod.sons.get(1) : null;

		int label = labelCnt++;
		LabelName lt = new LabelName(Integer.toString(labelTrue));
		LabelName lf = new LabelName(Integer.toString(labelFalse));

		switch (nod.id) {
			case "&&":
				generateCondition(left, label, labelFalse);
				updateNextStatLabel(label);
				generateCondition(right, labelTrue, labelFalse);
				break;
			case "||":
				generateCondition(left, labelTrue, label);
				updateNextStatLabel(label);
				generateCondition(right, labelTrue, labelFalse);
				labelCnt++;
				break;
			case "!":
				generateCondition(left, labelFalse, labelTrue);
				break;
			default:
				visit(left);
				visit(right);

				Oprand lr = left.reg.copy(), rr = right.reg.copy();
				String op;
				if (left.type instanceof StringTypeRef) {
					Register tmp = new Register(getTempName());
					generateStringFunc("strcmp", tmp, lr, rr);
					linearCode.insertExterns("strcmp");
					insertQuad(new CondQuad("cmp", tmp, new ImmOprand(0)));
				} else {
					insertQuad(new CondQuad("cmp", left.reg.copy(), right.reg.copy()));
				}
				switch (nod.id) {
					case "==": op = "je";  break;
					case "!=": op = "jne"; break;
					case "<" : op = "jl";  break;
					case "<=": op = "jle"; break;
					case ">":  op = "jg";  break;
					case ">=": op = "jge"; break;
					default: op = null;
				}
				insertQuad(new CJumpQuad(op, lt, lf));
		}
	}

	private void updateTempReg(String name, String newName, String mem) {
		for (int i = curCodeList.size() - 1; i >= 0; --i) {
			Quad code = curCodeList.get(i);
			if (code.getRt() instanceof Register && code.getRtName().equals(name)) {
				code.setRt(newName);
				((Register) code.getRt()).setMemPos(mem);
				break;
			}
		}
	}

	private boolean updateTempReg(Oprand r, Oprand newOpr) {
		String name = r.get();
		for (int i = curCodeList.size() - 1; i >= 0; --i) {
			Quad code = curCodeList.get(i);
			if (code.getRt() instanceof Register && code.getRtName().equals(name)) {
				if (code instanceof MovQuad && code.getR1() instanceof MemAccess && newOpr instanceof MemAccess) return false;
				if (code instanceof A3Quad && newOpr instanceof MemAccess) return false;
				code.changeRt(newOpr);
				break;
			}
		}
		return true;
	}

	private Oprand calcBinary(String op, Oprand left, Oprand right) {
		if (left instanceof ImmOprand) {
			long lv = ((ImmOprand) left).getVal(), rv = ((ImmOprand) right).getVal();
			long ans;
			switch (op) {
				case "+" : ans = lv +  rv; break;
				case "-" : ans = lv -  rv; break;
				case "*" : ans = lv *  rv; break;
				case "/" : ans = lv /  rv; break;
				case "%" : ans = lv %  rv; break;
				case "<<": ans = lv << rv; break;
				case ">>": ans = lv >> rv; break;
				case "&" : ans = lv &  rv; break;
				case "|" : ans = lv |  rv; break;
				case "^" : ans = lv ^  rv; break;
				default: switch (op) {
					case "&&": ans = lv &  rv; break;
					case "||": ans = lv |  rv; break;
					case "<" : ans = lv <  rv ? 1 : 0; break;
					case ">" : ans = lv >  rv ? 1 : 0; break;
					case "<=": ans = lv <= rv ? 1 : 0; break;
					case ">=": ans = lv >= rv ? 1 : 0; break;
					case "==": ans = lv == rv ? 1 : 0; break;
					case "!=": ans = lv != rv ? 1 : 0; break;
					default: ans = 0;
				}
					return new BoolImmOprand(ans);
			}
			return new ImmOprand(ans);
		} else {
			String lv = left.get(), rv = right.get();
			String str;
			switch (op) {
				case "+" : str = lv +  rv; break;
				default:
					long ans;
					int tmp = lv.compareTo(rv);
					switch (op) {
						case "<" : ans = tmp <  0 ? 1 : 0; break;
						case ">" : ans = tmp >  0 ? 1 : 0; break;
						case "<=": ans = tmp <= 0 ? 1 : 0; break;
						case ">=": ans = tmp >= 0 ? 1 : 0; break;
						case "==": ans = tmp == 0 ? 1 : 0; break;
						case "!=": ans = tmp != 0 ? 1 : 0; break;
						default: ans = 0;
					}
					return new BoolImmOprand(ans);
			}
			return new StringLiteral(str);
		}
	}

	private Oprand calcUnary(String op, Oprand son) {
		long val = ((ImmOprand) son).getVal(), ans;
		switch (op) {
			case "~": ans = ~val; break;
			case "-": ans = -val; break;
			case "+": ans =  val; break;
			default :
				ans = (~val) & 1;
				return new BoolImmOprand(ans);
		}
		return new ImmOprand(ans);
	}

	private void generateStringFunc(String funcName, Oprand ans, Oprand left, Oprand right) {
		getParam(left, right);
		insertQuad(new CallQuad("call", ans, new FuncName(funcName), new ImmOprand(2)));
	}

	private Oprand generateMemAccess(Oprand... opr) {
		if (opr.length == 1) return new MemAccess(opr[0]);
		Register tmp = new Register(getTempName());
		if (opr.length == 2) insertQuad(new LeaQuad("lea", tmp, new MemAccess(opr[0], opr[1])));
		if (opr.length == 3) insertQuad(new LeaQuad("lea", tmp, new MemAccess(opr[0], opr[1], opr[2])));
		if (opr.length == 4) insertQuad(new LeaQuad("lea", tmp, new MemAccess(opr[0], opr[1], opr[2], opr[3])));
		return new MemAccess(tmp);
	}

	private void generateStrAdd(Node nod, Oprand reg) throws Exception {
		if (nod instanceof BinaryExprNode) {
			generateStrAdd(nod.sons.get(0), reg);
			generateStrAdd(nod.sons.get(1), reg);
		} else {
			visit(nod);
			generateStringFunc("S_strcat", new Register("_"), reg.copy(), nod.reg.copy());
		}
	}

	private void addClassObj(String var, int sz) {
		if (classObjSize % sz != 0) classObjSize = (classObjSize + sz - 1) / sz * sz;
		classObj.put(var, classObjSize);
		classObjSize += sz;
	}

	private void generateObjFunc(Node nod, String classId, Oprand base) throws Exception {
		visitChild(nod);

		int n = nod.sons.size();
		String func = classFuncLabel(classId, nod.id);
		ArrayList<Oprand> tmp = new ArrayList<>();
		tmp.add(base);
		for (int i = 0; i < n; ++i) tmp.add(nod.sons.get(i).reg);
		getParam(tmp);
		nod.reg = nod.type instanceof VoidTypeRef ? null : new Register(getTempName());
		if (nod.type instanceof VoidTypeRef) {
			insertQuad(new CallQuad("call", nod.reg, new FuncName(func), new ImmOprand(n + 1)));
		} else {
			Register reg = new Register(getTempName());
			insertQuad(new CallQuad("call", reg, new FuncName(func), new ImmOprand(n + 1)));
			insertQuad(new MovQuad("mov", nod.reg, reg));
		}
	}

	private void generateNewFunc(Oprand ret, Oprand len) {
		getParam(len);
		Register tmp = new Register(getTempName());
		insertQuad(new CallQuad("call", tmp, new FuncName("malloc"), new ImmOprand(1)));
		insertQuad(new MovQuad("mov", ret, tmp.copy()));
	}

	private String getLoopRegisterName() {
		return "V_Loop._" + Integer.toString(selfGenLoopCnt++);
	}

	private void generateLoop(Oprand low, Oprand hig, Oprand ret, int size, Node son) throws Exception {
		String ni = getLoopRegisterName();
		String no = getLoopRegisterName();
		Register i = new Register(ni, ni);
		Register offset = new Register(no, no);
		insertQuad(new MovQuad("mov", i, low));
		insertQuad(new CondQuad("cmp", i.copy(), hig));
		int label1 = labelCnt++, label2 = labelCnt++;
		insertQuad(new CJumpQuad("jl", new LabelName(Integer.toString(label1)), new LabelName(Integer.toString(label2))));
		updateNextStatLabel(label1);

		visit(son);
		insertQuad(new A3Quad("add", offset.copy(), offset, new ImmOprand(size)));
		insertQuad(new MovQuad("mov", generateMemAccess(ret.copy(), offset.copy()), son.reg.copy()));

		insertQuad(new A3Quad("add", i.copy(), i.copy(), new ImmOprand(1)));
		insertQuad(new CondQuad("cmp", i.copy(), hig.copy()));
		insertQuad(new CJumpQuad("jl", new LabelName(Integer.toString(label1)), new LabelName(Integer.toString(label2))));
		updateNextStatLabel(label2);
	}
	private void getParam(ArrayList<Oprand> r) {
		Oprand[] tmp = new Oprand[r.size()];
		for (int i = 0; i < r.size(); ++i) {
			Oprand rr = r.get(i);
//			if (!(rr instanceof Register && isTempReg(rr.get()))) {
			if (i < 6) {
				tmp[i] = new Register(getTempName());
				insertQuad(new MovQuad("mov", tmp[i].copy(), rr.copy()));
			} else tmp[i] = r.get(i);
//			} else tmp[i] = ((Register) rr).copy();
		}
		for (int i = 0; i < tmp.length; ++i)
			insertQuad(new ParamQuad("param", tmp[i]));
	}

	private void getParam(Oprand... r) {
		Oprand[] tmp = new Oprand[r.length];
		for (int i = 0; i < r.length; ++i) {
//			if (!(r[i] instanceof Register && isTempReg(r[i].get()))) {
			if (i < 6) {
				tmp[i] = new Register(getTempName());
				insertQuad(new MovQuad("mov", tmp[i].copy(), r[i].copy()));
			} else tmp[i] = r[i];
//			} else tmp[i] = ((Register) r[i]).copy();
		}
		for (int i = 0; i < tmp.length; ++i)
			insertQuad(new ParamQuad("param", tmp[i]));
	}

	@Override public void visit(CodeNode nod) throws Exception {
		varState = VarDefStatus.GeneralVar;
		curFunc = new FuncFrame("__init");
		curFunc.setClassObj(new HashMap<>());

		for (int i = 0; i < nod.sons.size(); ++i) {
			Node son = nod.sons.get(i);
			if (son instanceof VarDefStatNode) visit(son);
		}
		if (!curCodeList.isEmpty()) insertInit(curFunc);

		for (int i = 0; i < nod.sons.size(); ++i) {
			Node son = nod.sons.get(i);
			if (!(son instanceof VarDefStatNode)) visit(son);
		}
	}

	@Override public void visit(VarDefNode nod) throws Exception {
		Node son;
		int sz = nod.type.getSize();
		String name = nod.reg.get();

		switch (varState) {
			case ClassObj   :
				addClassObj(name, sz);
				if (nod.type instanceof StringTypeRef) classObjStr.add(name);
				break;
			case FuncParam  : curFunc.addParam(name, sz); break;
			case LovalVar   :
				curFunc.addLocalVar(name, sz);
				if (nod.type instanceof StringTypeRef) {
					generateNewFunc(nod.reg, new ImmOprand(256));
					if (!nod.sons.isEmpty()) {
						visit(nod.sons.get(0));
						generateStringFunc("S_strcpy", new Register("_"), nod.reg.copy(), nod.sons.get(0).reg.copy());
					}
					break;
				}

				if (nod.sons.isEmpty()) break;
				son = nod.sons.get(0);
				if (son.reg == null && son.type instanceof BoolTypeRef) {
					int label1 = labelCnt++, label2 = labelCnt++, label3 = labelCnt++;
					generateCondition(son, label1, label2);
					updateNextStatLabel(label1);
					insertQuad(new MovQuad("mov", nod.reg.copy(), new ImmOprand(1)));
					insertQuad(new JumpQuad("jump", new LabelName(Integer.toString(label3))));
					updateNextStatLabel(label2);
					insertQuad(new MovQuad("mov", nod.reg.copy(), new ImmOprand(0)));
					insertQuad(new JumpQuad("jump", new LabelName(Integer.toString(label3))));
					updateNextStatLabel(label3);
					break;
				}
				visit(son);
				if (isTempReg(son.reg.get()) && !(son instanceof NewExprNode)) {
					updateTempReg(son.reg.get(), name, name);
				} else {
					insertQuad(new MovQuad("mov", nod.reg.copy(), son.reg.copy()));
				}
				break;
			case GeneralVar :
				name = ((GeneralMemAccess) nod.reg).getName();

				linearCode.insertVar(name, sz);

				TypeRef type = nod.type;
				if (type instanceof StringTypeRef) {
					generateNewFunc(nod.reg, new ImmOprand(256));
					linearCode.addUninitMem(name, 256);
				} else linearCode.addUninitMem(name, nod.type.getSize() / addrLen);
				if (nod.sons.isEmpty()) break;
				son = nod.sons.get(0);
				visit(son);
				insertQuad(new MovQuad("mov", nod.reg, changeOpr2Reg(son.reg)));
				break;
		}
	}

	@Override public void visit(ClassDefNode nod) throws Exception {
		VarDefStatus tmp = varState;
		varState = VarDefStatus.ClassObj;

		classObj = new HashMap<>();
		for (int i = 0; i < nod.sons.size(); ++i) {
			Node son = nod.sons.get(i);
			if (son instanceof VarDefStatNode) visit(son);
		}

		for (int i = 0; i < nod.sons.size(); ++i) {
			Node son = nod.sons.get(i);
			if (son instanceof FuncDefNode) visit(son);
		}

		varState = tmp;
	}

	@Override public void visit(FuncDefNode nod) throws Exception {
		VarDefStatus tmp = varState;

		if (nod.inClass == null) {
			curFunc = new FuncFrame(funcLabel(nod.id));
			curFunc.setClassObj(new HashMap<>());
		} else {
			curFunc = new FuncFrame(classFuncLabel(nod.inClass, nod.id));
			curFunc.setClassObj(classObj);
			curFunc.addParam("V_this", addrLen);
		}
		int size = nod.sons.size();
		for (int i = 0; i < size; ++i) {
			Node son = nod.sons.get(i);
			if (i < size - 1) varState = VarDefStatus.FuncParam;
			else varState = VarDefStatus.LovalVar;
			visit(son);
		}
		Quad last = curCodeList.get(curCodeList.size() - 1);
		if (curFunc.getName().equals("main") && !(last instanceof RetQuad)) insertQuad(new RetQuad("ret", new ImmOprand(0)));

		if (!nextStatLabel.isEmpty()) insertQuad(new Quad("nop"));
		if (!(nod.type instanceof VoidTypeRef))
			curFunc.setRetSize(nod.type.getSize());
		else curFunc.setRetSize(0);

		insertFunc(curFunc);

		varState = tmp;
	}

	@Override public void visit(ConsFuncDefNode nod) throws Exception {
		VarDefStatus tmp = varState;
		curFunc = new FuncFrame(classFuncLabel(nod.id, nod.id));
		curFunc.setClassObj(classObj);
		curFunc.addParam("V_this", addrLen);

		for (String var: classObjStr) {
			generateNewFunc(generateMemAccess(new Register("V_this", "V_this"), new ImmOprand(classObj.get(var))),
							new ImmOprand(256));
		}

		int size = nod.sons.size();
		for (int i = 0; i < size; ++i) {
			if (i < size - 1) varState = VarDefStatus.FuncParam;
			else varState = VarDefStatus.LovalVar;
			Node son = nod.sons.get(i);
			if (!(son instanceof NullStatNode)) {
				visit(son);
//				for (Map.Entry<String, Long> entry: classObj.entrySet()) {
//					if (classObjStr.contains(entry.getKey())) continue;
//					insertQuad(new MovQuad("mov",
//							generateMemAccess(new Register("V_this", "V_this"), new ImmOprand(entry.getValue())),
//							new Register(entry.getKey(), entry.getKey()))
//					);
//				}
			} else {
				insertQuad(new Quad("nop"));
			}
		}
		insertFunc(curFunc);
		varState = tmp;
	}

	@Override public void visit(IfElseStatNode nod) throws Exception {
		ifElseEndLabel.push(labelCnt++);
		visitChild(nod);
		insertQuad(new JumpQuad("jump", new LabelName(Integer.toString(ifElseEndLabel.peek()))));
		updateNextStatLabel(ifElseEndLabel.pop());
	}


	@Override public void visit(IfStatNode nod) throws Exception {
		Node ifCond = nod.sons.get(0);
		Node ifStat = nod.sons.get(1);
		int label1 = labelCnt++, label2 = labelCnt++;
		generateCondition(ifCond, label1, label2);
		updateNextStatLabel(label1);
		visit(ifStat);
		if (nod.markForIf)
			insertQuad(new JumpQuad("jump", new LabelName(Integer.toString(ifElseEndLabel.peek()))));
		else insertQuad(new JumpQuad("jump", new LabelName(Integer.toString(label2))));
		updateNextStatLabel(label2);
	}

	@Override public void visit(ForStatNode nod) throws Exception {
		Node initExpr = nod.sons.get(0);
		Node condExpr = nod.sons.get(1);
		Node loopExpr = nod.sons.get(2);
		Node forBody = nod.sons.get(3);
		visit(initExpr);

		int label1 = labelCnt++;
		int label2 = labelCnt++;
		int label3 = labelCnt++;
		if (!(condExpr instanceof EmptyExprNode)) {
			generateCondition(condExpr, label1, label2);
		} else {
			insertQuad(new JumpQuad("jump", new LabelName(Integer.toString(label1))));
		}

		updateNextStatLabel(label1);
		ctnLabel.push(new Pair<>(label3, false));
		brkLabel.push(label2);
		visit(forBody);
		brkLabel.pop();

		if (ctnLabel.pop().getValue().equals(true)) updateNextStatLabel(label3);
		visit(loopExpr);
		if (!(condExpr instanceof EmptyExprNode)) {
			generateCondition(condExpr, label1, label2);
		} else {
			insertQuad(new JumpQuad("jump", new LabelName(Integer.toString(label1))));
		}
		updateNextStatLabel(label2);
	}

	@Override public void visit(WhileStatNode nod) throws Exception {
		Node condExpr = nod.sons.get(0);
		Node whileBody = nod.sons.get(1);

		int label1 = labelCnt++;
		int label2 = labelCnt++;
		int label3 = labelCnt++;
		generateCondition(condExpr, label1, label2);

		updateNextStatLabel(label1);
		ctnLabel.push(new Pair<>(label3, false));
		brkLabel.push(label2);
		visit(whileBody);
		brkLabel.pop();

		if (ctnLabel.pop().getValue().equals(true)) updateNextStatLabel(label3);
		generateCondition(condExpr, label1, label2);

		updateNextStatLabel(label2);

	}

	@Override public void visit(RetStatNode nod) throws Exception {
		if (nod.sons.size() > 0) {
			Node son = nod.sons.get(0);
			if (son.type instanceof BoolTypeRef && son.reg == null) {
				int label1 = labelCnt++, label2 = labelCnt++, label3 = labelCnt++;
				generateCondition(son, label1, label2);
				updateNextStatLabel(label1);
				insertQuad(new RetQuad("ret", new ImmOprand(1)));
				insertQuad(new JumpQuad("jump", new LabelName(Integer.toString(label3))));
				updateNextStatLabel(label2);
				insertQuad(new RetQuad("ret", new ImmOprand(0)));
				insertQuad(new JumpQuad("jump", new LabelName(Integer.toString(label3))));
				updateNextStatLabel(label3);
				return;
			}
			visitChild(nod);
			insertQuad(new RetQuad("ret", nod.sons.get(0).reg.copy()));
		}
		else {
			visitChild(nod);
			insertQuad(new RetQuad("ret"));
		}
	}

	@Override public void visit(BrkStatNode nod) throws Exception {
		insertQuad(new JumpQuad("jump", new LabelName(Integer.toString(brkLabel.peek()))));
	}

	@Override public void visit(CtnStatNode nod) throws Exception {
		int label = ctnLabel.pop().getKey();
		insertQuad(new JumpQuad("jump", new LabelName(Integer.toString(label))));
		ctnLabel.push(new Pair<>(label, true));
	}

	@Override public void visit(NullStatNode nod) throws Exception {
		insertQuad(new Quad("nop"));
	}

	@Override public void visit(BinaryExprNode nod) throws Exception {
		Node left = nod.sons.get(0);
		Node right = nod.sons.get(1);
		boolean isStringType = left.type instanceof StringTypeRef;
		boolean isIntType = left.type instanceof IntTypeRef;
		boolean isBoolType = left.type instanceof BoolTypeRef;
		boolean isPointer = left.type instanceof ArrayTypeRef || left.type instanceof ClassTypeRef;

		/*
		 * Special for String add
		 * */
		if (isStringType && nod.id.equals("+")) {
			nod.reg = new Register(getTempName());
			generateNewFunc(nod.reg, new ImmOprand(256));
			generateStrAdd(nod, nod.reg.copy());
			return;
		}


		if (nod.id.equals("=") && right.type instanceof BoolTypeRef && right.reg == null) {
			int label1 = labelCnt++, label2 = labelCnt++, label3 = labelCnt++;
			visit(left);
			generateCondition(right, label1, label2);
			updateNextStatLabel(label1);
			insertQuad(new MovQuad("mov", left.reg.copy(), new ImmOprand(1)));
			insertQuad(new JumpQuad("jump", new LabelName(Integer.toString(label3))));
			updateNextStatLabel(label2);
			insertQuad(new MovQuad("mov", left.reg.copy(), new ImmOprand(0)));
			insertQuad(new JumpQuad("jump", new LabelName(Integer.toString(label3))));
			updateNextStatLabel(label3);
			return;
		}

		visit(left);
		visit(right);

		Oprand lr = left.reg.copy();
		Oprand rr = right.reg.copy();
		boolean certain = left.isCertain() && right.isCertain();

		if (certain) {
			nod.beCertain();
			nod.reg = calcBinary(nod.id, left.reg, right.reg);
			return;
		}

		nod.reg = new Register(getTempName());

		/* Special for useless assignment statement. */
		if (nod.id.equals("=") && isTempReg(rr.get()) && !(right instanceof NewExprNode)
				&& !(right.type instanceof StringTypeRef)) {
			if (updateTempReg(rr.copy(), lr.copy()))	return;
		}


		String op;
		if (isIntType || isPointer) {
			switch (nod.id) {
				case "=" : op = "mov"; break;
				case "+" : op = "add"; break;
				case "-" : op = "sub"; break;
				case "*" : op = "mul"; break;
				case "/" : op = "div"; break;
				case "%" : op = "mod"; break;
				case "<<": op = "sal"; break;
				case ">>": op = "sar"; break;
				case "&" : op = "and"; break;
				case "|" : op = "or" ; break;
				case "^" : op = "xor"; break;
//				case "==": op = "equ"; break;
//				case "!=": op = "neq"; break;
//				case "<" : op = "les"; break;
//				case "<=": op = "leq"; break;
//				case ">" : op = "gre"; break;
//				case ">=": op = "geq"; break;
				default: op = null;
			}
			if (op.equals("mov")) {
				if (rr instanceof MemAccess && lr instanceof MemAccess) {
					Register tmp = new Register(getTempName());
					insertQuad(new MovQuad("mov", tmp, rr));
					rr = tmp.copy();
				}
				insertQuad(new MovQuad(op, lr, rr));
			} else {
				if ((op.equals("div") || op.equals("mod")) && !(rr instanceof ImmOprand && Tool.isPow2(((ImmOprand) rr).getVal())))
					rr = changeOpr2Reg(rr).copy();
				else if (op.equals("sal") || op.equals("sar")) {
					if (!isTempReg(rr.get()) && !(rr instanceof ImmOprand)) {
						Register tmp = new Register(getTempName());
						insertQuad(new MovQuad("mov", tmp, rr));
						rr = tmp.copy();
					}
				}
				insertQuad(new A3Quad(op, nod.reg, lr, rr));
			}
		} else if (isBoolType) {
			switch (nod.id) {
				case "=" : op = "mov"; break;
//				case "&&":
				case "&" : op = "and"; break;
//				case "||":
				case "|" : op = "or" ; break;
				case "^" : op = "xor"; break;
//				case "==": op = "equ"; break;
//				case "!=": op = "neq"; break;
				default: op = null;
			}
			if (op.equals("mov")) {
				if (rr instanceof MemAccess && lr instanceof MemAccess) {
					Register tmp = new Register(getTempName());
					insertQuad(new MovQuad("mov", tmp, rr));
					rr = tmp.copy();
				}
				insertQuad(new MovQuad(op, lr, rr));
			} else insertQuad(new A3Quad(op, nod.reg, lr, rr));
		} else if (isStringType) {
			if (nod.id.equals("=")) {
				generateStringFunc("S_strcpy", new Register("_"), lr, rr);
				return;
			}
		} else {
			assert(nod.id.equals("="));
			insertQuad(new MovQuad("mov", lr, rr));
		}
	}

	@Override void visit(LeftUnaryExprNode nod) throws Exception {
		visitChild(nod);
		Node son = nod.sons.get(0);
		Oprand sonReg = son.reg;

		if (son.isCertain()) {
			nod.reg = calcUnary(nod.id, sonReg);
			nod.beCertain();
			return;
		}

		Oprand tmp;
		String op;
		switch(nod.id) {
			case "++": op = "add"; break;
			case "--": op = "sub"; break;
			default:
				nod.reg = new Register(getTempName());
				switch (nod.id) {
					case "!":
					case "~": op = "not"; break;
					case "-": op = "neg"; break;
					case "+": op = "mov"; break;
					default: op = null;
				}
				if (op.equals("mov")) insertQuad(new MovQuad(op, nod.reg, sonReg.copy()));
				insertQuad(new A3Quad(op, nod.reg, sonReg.copy(), new ImmOprand(0)));
				return;
		}
		if (!(sonReg instanceof MemAccess))
			insertQuad(new A3Quad(op, sonReg.copy(), sonReg.copy(), new ImmOprand(1)));
		else {
			tmp = new Register(getTempName());
			insertQuad(new A3Quad(op, tmp, sonReg.copy(), new ImmOprand(1)));
			insertQuad(new MovQuad("mov", sonReg.copy(), tmp.copy()));
		}
		nod.reg = sonReg.copy();
	}

	@Override void visit(RightUnaryExprNode nod) throws Exception {
		visitChild(nod);
		Oprand sonReg = nod.sons.get(0).reg;
		nod.reg = new Register(getTempName());
		insertQuad(new MovQuad("mov", nod.reg, sonReg.copy()));

		String op;
		switch (nod.id) {
			case "++": op = "add"; break;
			case "--": op = "sub"; break;
			default: op = null;
		}
		insertQuad(new A3Quad(op, sonReg.copy(), sonReg.copy(), new ImmOprand(1)));
	}

	@Override void visit(NewExprNode nod) throws Exception {
		Node son = nod.sons.get(0);

		if (son.type instanceof ClassTypeRef) {
			nod.reg = new Register(getTempName());
			son.reg = new ImmOprand(((ClassTypeRef) son.type).getBelongClass().getSize());
			generateNewFunc(nod.reg, son.reg);

			getParam(nod.reg);
			String className = ((ClassTypeRef) son.type).getTypeId();
			String funcName = classFuncLabel(className, className);
			insertQuad(new CallQuad("call", new Register("_"), new FuncName(funcName), new ImmOprand(1)));

			if (varState == VarDefStatus.GeneralVar) {
				insertQuad(new RetQuad("ret", nod.reg.copy()));
			}
		} else {
			visit(son);
			nod.reg = son.reg;
		}
	}

	@Override public void visit(TypeExprNode nod) throws Exception {
		if (nod.sons.isEmpty()) {
			if (nod.type instanceof StringTypeRef) {
				nod.reg = new Register(getTempName());
				generateNewFunc(nod.reg, new ImmOprand(256));
			} else nod.reg = null;
			return;
		}
		Node len = nod.sons.get(0);
		Node typ = nod.sons.get(1);
		int size = addrLen;

		/*
		* int[2][3]
		* 2 * int[3]
		* 2 * 8
		* 3 * 8
		* */
		if (len instanceof EmptyExprNode) {
			nod.reg = null;
			return;
		}
		visit(len);
		nod.reg = new Register(getTempName());
		if (len.isCertain()) {
			int val = ((Long) ((ImmOprand) len.reg).getVal()).intValue();
			generateNewFunc(nod.reg, new ImmOprand((val + 1) * size));
			insertQuad(new MovQuad("mov", generateMemAccess(nod.reg.copy()), new ImmOprand(val)));

			int offset = 0;
			for (int i = 0; i < val; ++i) {
				visit(typ);
				offset += size;
				if (typ.reg != null)
					insertQuad(new MovQuad("mov", generateMemAccess(nod.reg.copy(), new ImmOprand(offset)), typ.reg.copy()));
			}
		} else {
			Register tmp = new Register(getTempName());
			insertQuad(new A3Quad("mul", tmp, len.reg.copy(), new ImmOprand(size)));
			insertQuad(new A3Quad("add", tmp.copy(), tmp.copy(), new ImmOprand(size)));
			generateNewFunc(nod.reg, tmp.copy());
			insertQuad(new MovQuad("mov", generateMemAccess(nod.reg.copy()), changeOpr2Reg(len.reg.copy())));
			if (!(typ.type instanceof SingleTypeRef && !(typ.type instanceof StringTypeRef)) && !(typ.sons.get(0) instanceof EmptyExprNode))
				generateLoop(new ImmOprand(0), len.reg.copy(), nod.reg, size, typ);
		}
	}

	@Override public void visit(FuncExprNode nod) throws Exception {
		visitChild(nod);
		int n = nod.sons.size();
		if (nod.inClass != null) {
			generateObjFunc(nod, nod.inClass, new Register("V_this", "V_this"));
			return;
		}
		String fun = funcLabel(nod.id);
		ArrayList<Oprand> tmp = new ArrayList<>();
		for (int i = 0; i < n; ++i) {
			tmp.add(nod.sons.get(i).reg);
		}
		getParam(tmp);

		nod.reg = nod.type instanceof VoidTypeRef ? new Register("_") : new Register(getTempName());
		if (nod.type instanceof VoidTypeRef) {
			insertQuad(new CallQuad("call", nod.reg, new FuncName(fun), new ImmOprand(n)));
		} else {
			Register register = new Register(getTempName());
			insertQuad(new CallQuad("call", register, new FuncName(fun), new ImmOprand(n)));
			insertQuad(new MovQuad("mov", nod.reg, register));
		}
	}

	@Override void visit(ArrExprNode nod) throws Exception {
		visitChild(nod);
		Node son = nod.sons.get(0);
		Node idx = nod.sons.get(1);

		Oprand tmp1 = son.reg instanceof MemAccess ? changeOpr2Reg(son.reg) : son.reg;
		Oprand tmp2 = idx.reg instanceof MemAccess ? changeOpr2Reg(idx.reg) : idx.reg;

		nod.reg = generateMemAccess(tmp1.copy(), tmp2.copy(), new ImmOprand(addrLen), new ImmOprand(addrLen));
	}

	@Override void visit(ObjAccExprNode nod) throws Exception {
		Node son = nod.sons.get(0);
		Node mem = nod.sons.get(1);
		visit(son);
		if (mem instanceof VarExprNode) {
			Register tmp = changeOpr2Reg(son.reg);
			nod.reg = generateMemAccess(tmp.copy(), new ImmOprand(((ClassTypeRef) son.type).getBelongClass().getOffset(mem.id)));
			return;
		}
		if (son.type instanceof ClassTypeRef) {
			generateObjFunc(mem, ((ClassTypeRef) son.type).getTypeId(), son.reg.copy());
			nod.reg = mem.reg;
		} else if (son.type instanceof StringTypeRef) {
			Register tmp = new Register(getTempName());
			nod.reg = new Register(getTempName());
			switch (mem.id) {
				case "length":
					getParam(son.reg);
					insertQuad(new CallQuad("call", tmp, new FuncName("S_strlen"), new ImmOprand(1)));
					break;
				case "substring":
					Node left = mem.sons.get(0), right = mem.sons.get(1);
					visit(left);
					visit(right);
					getParam(son.reg, left.reg, right.reg);
					insertQuad(new CallQuad("call", tmp, new FuncName("S_substring"), new ImmOprand(3)));
					break;
				case "parseInt":
					getParam(son.reg);
					insertQuad(new CallQuad("call", tmp, new FuncName("S_parseInt"), new ImmOprand(1)));
					break;
				case "ord":
					Node memSon = mem.sons.get(0);
					visit(memSon);
					getParam(son.reg, memSon.reg);
					insertQuad(new CallQuad("call", tmp, new FuncName("S_ord"), new ImmOprand(2)));
			}
			insertQuad(new MovQuad("mov", nod.reg.copy(), tmp));
		} else {
			Register tmp;
			if (son.reg instanceof Register) tmp = ((Register) son.reg);
			else {
				tmp = new Register(getTempName());
				insertQuad(new MovQuad("mov", tmp, son.reg.copy()));
			}
			nod.reg = new Register(getTempName());
			insertQuad(new MovQuad("mov", nod.reg, generateMemAccess(tmp)));
		}
	}

	@Override public void visit(VarExprNode nod) throws Exception {
		if (nod.id.equals("this")) return;

//		/*
//		* to initialize general variables
//		* */
//		if (varState == VarDefStatus.GeneralVar) {
//			if (nod.type instanceof StringTypeRef) {
//				nod.reg = new StringLiteral(generalVarStr.find(nod.reg.get()));
//			} else if (nod.type instanceof IntTypeRef) {
//				nod.reg = new ImmOprand(generalVarInt.find(nod.reg.get()));
//			} else if (nod.type instanceof BoolTypeRef) {
//				nod.reg = new BoolImmOprand(generalVarBool.find(nod.reg.get()));
//			}
//			nod.beCertain();
//		}
	}

	@Override void visit(IntLiteralNode nod) throws Exception {
		nod.reg = new ImmOprand(Integer.parseInt(nod.id));
		nod.beCertain();
	}

	@Override void visit(LogicalLiteralNode nod) throws Exception {
		nod.reg = new BoolImmOprand(nod.id.equals("true") ? 1 : 0);
		nod.beCertain();
	}

	@Override void visit(NullLiteralNode nod) throws Exception {
		nod.reg = new NullImmOprand(0);
		nod.beCertain();
	}

	@Override void visit(StringLiteralNode nod) throws Exception {
		String str = nod.id;
		nod.reg = new StringLiteral(linearCode.insertStrConst(str));
		nod.beCertain();
	}
}
