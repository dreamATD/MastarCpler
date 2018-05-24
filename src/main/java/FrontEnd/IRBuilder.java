package FrontEnd;

import GeneralDataStructure.*;
import GeneralDataStructure.OprandClass.*;
import GeneralDataStructure.QuadClass.*;
import GeneralDataStructure.ScopeClass.*;
import GeneralDataStructure.TypeSystem.*;
import Utilizer.ConstVar;
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

	VarDefStatus varState;

	/*
	* related to values of general variables
	* */
	SymbolTable<String> generalVarStr;
	SymbolTable<Long> generalVarInt;
	SymbolTable<Long> generalVarBool;

	/*
	* related to the class
	* */
	HashMap<String, Long> classObj;
	long classObjSize;

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
		linearCode.insertFunc(fun);
	}

	private void insertQuad(Quad ins) {
		if (nextStatLabel.size() > 0) {
			String label = nextLabel();
			ins.setLabel(label);
			for (int data: nextStatLabel) {
				uset.set(data, label);
			}
			nextStatLabel.clear();
		}
		curCodeList.add(ins);
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
		String str = "label" + Integer.toString(quadLabelCnt++);
		return str;
	}

	private String classFuncLabel(String className, String funcName) {
		return className + "$" + funcName;
	}

	private String funcLabel(String funcName) {
		return funcName;
	}

	private String getTempName() {
		return "%" + Integer.toString(labelCnt++);
	}

	public void visitChild(Node nod) throws Exception {
		if (nod == null) return;
		for (int i = 0; i < nod.sons.size(); ++i) {
			visit(nod.sons.get(i));
		}
	}

	private void generateCondition(Node nod, int labelTrue, int labelFalse) throws Exception {
		Node left = nod.sons.get(0);
		Node right = nod.sons.size() > 1 ? nod.sons.get(1) : null;

		int label = labelCnt;
		LabelName lt = new LabelName(Integer.toString(labelTrue));
		LabelName lf = new LabelName(Integer.toString(labelFalse));

		switch (nod.id) {
			case "&&":
				generateCondition(left, label, labelFalse);
				updateNextStatLabel(label);
				generateCondition(right, labelTrue, labelFalse);
				labelCnt++;
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
				Oprand lr = left.reg.copy(), rr = right.reg.copy();
				String op;

				visit(left);
				visit(right);
				if (left.type instanceof StringTypeRef) {
					Register tmp = new Register(getTempName());
					generateStringFunc("strcmp", tmp, lr, rr);
					insertQuad(new CondQuad("cmp", tmp, new ImmOprand(0)));
				} else insertQuad(new CondQuad("cmp", left.reg.copy(), right.reg.copy()));
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
		insertQuad(new ParamQuad("param", left));
		insertQuad(new ParamQuad("param", right));
		insertQuad(new CallQuad("call", ans, new FuncName(funcLabel(funcName)), new ImmOprand(2)));
	}

	private void addClassObj(String var, int sz) {
		if (classObjSize % sz != 0) classObjSize = (classObjSize + sz - 1) / sz * sz;
		classObj.put(var, classObjSize);
		classObjSize += sz;
	}

	private void generateObjFunc(Node nod, String classId, Oprand base) throws Exception {
		visitChild(nod);

		int n = nod.sons.size() - 1;
		String func = classId + '$' + nod.id;
		insertQuad(new ParamQuad("param", base));
		for (int i = 0; i < n; ++i) insertQuad(new ParamQuad("param", nod.sons.get(i).reg.copy()));
		nod.reg = nod.type instanceof VoidTypeRef ? null : new Register(getTempName());
		insertQuad(new CallQuad("call", nod.reg, new FuncName(func), new ImmOprand(n + 1)));
	}

	@Override public void visit(CodeNode nod) throws Exception {
		varState = VarDefStatus.GeneralVar;
		for (int i = 0; i < nod.sons.size(); ++i) {
			Node son = nod.sons.get(i);
			visit(son);
		}
	}

	@Override public void visit(VarDefNode nod) throws Exception {
		Node son;
		int sz = nod.type.getSize();
		String name = nod.reg.get();

		switch (varState) {
			case ClassObj   : addClassObj(name, sz); break;
			case FuncParam  : curFunc.addParam(name, sz);
			case LovalVar   :
				curFunc.addLocalVar(name, sz);

				if (nod.sons.isEmpty()) break;
				son = nod.sons.get(0);
				visit(son);
				if (isTempReg(son.reg.get())) {
					updateTempReg(son.reg.get(), name, name);
				} else insertQuad(new MovQuad("mov", nod.reg, son.reg.copy()));
				break;
			case GeneralVar :
				linearCode.insertVar(name, sz);

				if (nod.sons.isEmpty()) {
					linearCode.addUninitMem(name, nod.type.getSize());
					break;
				}
				son = nod.sons.get(0);
				visit(son);
				TypeRef type = nod.type;
				if (type instanceof StringTypeRef) {
					StringLiteral sonReg = (StringLiteral) son.reg;
					linearCode.addInitMem(name, sonReg.get().getBytes());
				} else if (type instanceof IntTypeRef || type instanceof BoolTypeRef) {
					ImmOprand sonReg = (ImmOprand) son.reg;
					linearCode.addInitMem(name, ByteBuffer.allocate(ConstVar.intLen).putLong(sonReg.getVal()).array());
				} else if (type instanceof ArrayTypeRef) {

				} else if (type instanceof ClassTypeRef) {

				}
				break;
		}
	}

	@Override public void visit(ClassDefNode nod) throws Exception {
		VarDefStatus tmp = varState;
		varState = VarDefStatus.ClassObj;

		classObj = new HashMap<>();
		for (int i = 0; i < nod.sons.size(); ++i) {
			Node son = nod.sons.get(i);
			if (son instanceof VarDefNode) visit(son);
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
			curFunc.addParam("%this", addrLen);
		}
		int size = nod.sons.size();
		for (int i = 0; i < size; ++i) {
			Node son = nod.sons.get(i);
			if (i < size - 1) varState = VarDefStatus.FuncParam;
			else varState = VarDefStatus.LovalVar;
			visit(son);
		}
		if (!nextStatLabel.isEmpty()) insertQuad(new Quad("nop"));
		if (!(nod.type instanceof VoidTypeRef))
			curFunc.setRetSize(nod.type.getSize());

		insertFunc(curFunc);

		varState = tmp;
	}

	@Override public void visit(ConsFuncDefNode nod) throws Exception {
		VarDefStatus tmp = varState;
		curFunc = new FuncFrame(classFuncLabel(nod.id, nod.id));
		curFunc.setClassObj(classObj);
		curFunc.addParam("%this", addrLen);

		int size = nod.sons.size();
		for (int i = 0; i < size; ++i) {
			if (i < size - 1) varState = VarDefStatus.FuncParam;
			else varState = VarDefStatus.LovalVar;
			Node son = nod.sons.get(i);
			visit(son);

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
		if (!ifElseEndLabel.isEmpty())
			insertQuad(new JumpQuad("jump", new LabelName(Integer.toString(ifElseEndLabel.peek()))));
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
		visit(condExpr);
		generateCondition(condExpr, label1, label2);

		updateNextStatLabel(label2);
	}

	@Override public void visit(RetStatNode nod) throws Exception {
		visitChild(nod);
		if (nod.sons.size() > 0) insertQuad(new RetQuad("ret", nod.sons.get(0).reg.copy()));
		else insertQuad(new RetQuad("ret"));
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

		visit(left);
		visit(right);

		Oprand lr = left.reg.copy();
		Oprand rr = right.reg.copy();
		boolean isStringType = left.type instanceof StringTypeRef;
		boolean isIntType = left.type instanceof IntTypeRef;
		boolean isBoolType = left.type instanceof BoolTypeRef;
		boolean certain = left.isCertain() && right.isCertain();

		if (certain) {
			nod.beCertain();
			nod.reg = calcBinary(nod.id, left.reg, right.reg);
			return;
		}

		if (nod.reg == null) nod.reg = new Register(getTempName());

		/* Special for useless assignment statement. */
		if (nod.id.equals("=") && isTempReg(rr.get())) {
			updateTempReg(rr.get(), lr.get(), ((Register) lr).getMemPos());
			return;
		}

		String op;
		if (isIntType) {
			switch (nod.id) {
				case "=" : op = "mov"; break;
				case "+" : op = "add"; break;
				case "-" : op = "sub"; break;
				case "*" : op = "mul"; break;
				case "/" : op = "div"; break;
				case "%" : op = "mod"; break;
				case "<<": op = "lsh"; break;
				case ">>": op = "rsh"; break;
				case "&" : op = "and"; break;
				case "|" : op = "or" ; break;
				case "^" : op = "xor"; break;
				case "==": op = "equ"; break;
				case "!=": op = "neq"; break;
				case "<" : op = "les"; break;
				case "<=": op = "leq"; break;
				case ">" : op = "gre"; break;
				case ">=": op = "geq"; break;
				default: op = null;
			}
			if (op.equals("mov")) insertQuad(new MovQuad(op, lr, rr));
			insertQuad(new A3Quad(op, nod.reg, lr, rr));
		} else if (isBoolType) {
			switch (nod.id) {
				case "=" : op = "mov"; break;
				case "&&":
				case "&" : op = "and"; break;
				case "||":
				case "|" : op = "or" ; break;
				case "^" : op = "xor"; break;
				case "==": op = "equ"; break;
				case "!=": op = "neq"; break;
				default: op = null;
			}
			if (op.equals("mov")) insertQuad(new MovQuad(op, lr, rr));
			insertQuad(new A3Quad(op, nod.reg, lr, rr));
		} else if (isStringType) {
			if (nod.id.equals("=")) {
				insertQuad(new MovQuad("mov", lr, rr));
				return;
			} else if (nod.id.equals("+")) {
				generateStringFunc("strcat", nod.reg, lr, rr);
				return;
			}
			Register tmp = new Register(getTempName());
			generateStringFunc("strcmp", tmp, lr, rr);
			switch (nod.id) {
				case "==": op = "equ"; break;
				case "!=": op = "neq"; break;
				case "<" : op = "les"; break;
				case "<=": op = "leq"; break;
				case ">" : op = "gre"; break;
				case ">=": op = "geq"; break;
				default: op = null;
			}
			insertQuad(new A3Quad(op, nod.reg, tmp, new ImmOprand(0)));
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
		nod.reg = new Register(getTempName());
		switch(nod.id) {
			case "++": op = "add"; break;
			case "--": op = "sub"; break;
			default:
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
		insertQuad(new A3Quad(op, sonReg.copy(), sonReg.copy(), new ImmOprand(1)));
		insertQuad(new MovQuad("mov", nod.reg, sonReg.copy()));
	}

	@Override void visit(RightUnaryExprNode nod) throws Exception {
		visitChild(nod);
		Oprand sonReg = nod.sons.get(0).reg;
		nod.reg = new Register(getTempName());
		insertQuad(new MovQuad("mov", nod.reg, sonReg.copy()));

		String op;
		switch (nod.id) {
			case "++": op = "add";
			case "--": op = "sub";
			default: op = null;
		}
		insertQuad(new A3Quad(op, sonReg.copy(), sonReg.copy(), new ImmOprand(1)));
	}

	@Override void visit(NewExprNode nod) throws Exception {
		Oprand var = new Register(getTempName());
		Node son = nod.sons.get(0);

		if (son.type instanceof ClassTypeRef) son.reg = new ImmOprand(((ClassTypeRef) son.type).getBelongClass().getSize());
		else visit(son);
		nod.reg = new Register(getTempName());
		insertQuad(new ParamQuad("param", son.reg.copy()));
		insertQuad(new CallQuad("call", nod.reg, new FuncName("_Znam"), new ImmOprand(1)));
	}

	@Override public void visit(TypeExprNode nod) throws Exception {
		if (nod.sons.isEmpty()) {
			nod.reg = new ImmOprand(nod.type.getSize());
			nod.beCertain();
		} else {
			Node len = nod.sons.get(0);
			Node typ = nod.sons.get(1);
			visit(len);
			visit(typ);
			if (len.isCertain() && typ.isCertain()) {
				nod.reg = new ImmOprand(((ImmOprand) len.reg).getVal() * ((ImmOprand) typ.reg).getVal());
			} else {
				nod.reg = new Register(getTempName());
				insertQuad(new A3Quad("mul", nod.reg, len.reg.copy(), typ.reg.copy()));
			}

		}
	}

	@Override public void visit(FuncExprNode nod) throws Exception {
		visitChild(nod);

		int n = nod.sons.size();
		String fun = funcLabel(nod.id);
		for (int i = 0; i < n; ++i) {
			insertQuad(new ParamQuad("param", nod.sons.get(i).reg.copy()));
		}

		nod.reg = nod.type instanceof VoidTypeRef ? null : new Register(getTempName());
		insertQuad(new CallQuad("call", nod.reg, new FuncName(fun), new ImmOprand(n)));
	}

	@Override void visit(ArrExprNode nod) throws Exception {
		visitChild(nod);
		Node son = nod.sons.get(0);
		Node idx = nod.sons.get(1);
		if (nod.type instanceof SingleTypeRef)
			nod.reg = new MemAccess(son.reg.copy(), idx.reg.copy(), new ImmOprand(nod.type.getSize()));
		else
			nod.reg = new MemAccess(son.reg.copy(), idx.reg.copy(), new ImmOprand(addrLen));
	}

	@Override void visit(ObjAccExprNode nod) throws Exception {
		Node son = nod.sons.get(0);
		Node mem = nod.sons.get(1);
		visit(son);
		if (mem instanceof VarExprNode) return;
		if (son.type instanceof ClassTypeRef) {
			generateObjFunc(mem, ((ClassTypeRef) son.type).getTypeId(), son.reg.copy());
			nod.reg = mem.reg;
		} else if (son.type instanceof StringTypeRef) {
			nod.reg = new Register(getTempName());
			switch (mem.id) {
				case "length":
					insertQuad(new ParamQuad("param", son.reg.copy()));
					insertQuad(new CallQuad("call", nod.reg, new FuncName("str$strlen"), new ImmOprand(1)));
					break;
				case "subString":
					Node left = mem.sons.get(0), right = mem.sons.get(1);
					visit(left);
					visit(right);
					insertQuad(new ParamQuad("param", left.reg.copy()));
					insertQuad(new ParamQuad("param", right.reg.copy()));
					insertQuad(new CallQuad("call", nod.reg, new FuncName("str$substring"), new ImmOprand(2)));
					break;
				case "parseInt":
					insertQuad(new CallQuad("call", nod.reg, new FuncName("str$parseInt"), new ImmOprand(0)));
					break;
				case "ord":
					Node memSon = mem.sons.get(0);
					visit(memSon);
					insertQuad(new ParamQuad("param", memSon.reg.copy()));
					insertQuad(new CallQuad("call", nod.reg, new FuncName("str$ord"), new ImmOprand(1)));
			}
		} else {
			nod.reg = new Register(getTempName());
			insertQuad(new LoadQuad("load", nod.reg, son.reg.copy(), new ImmOprand(addrLen)));
		}
	}

	@Override public void visit(VarExprNode nod) throws Exception {
		if (nod.id.equals("this")) return;

		/*
		* to initialize general variables
		* */
		if (varState == VarDefStatus.GeneralVar) {
			if (nod.type instanceof StringTypeRef) {
				nod.reg = new StringLiteral(generalVarStr.find(nod.reg.get()));
			} else if (nod.type instanceof IntTypeRef) {
				nod.reg = new ImmOprand(generalVarInt.find(nod.reg.get()));
			} else if (nod.type instanceof BoolTypeRef) {
				nod.reg = new BoolImmOprand(generalVarBool.find(nod.reg.get()));
			}
			nod.beCertain();
		}
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
		nod.reg = new StringLiteral(nod.id);
		nod.beCertain();
	}
}
