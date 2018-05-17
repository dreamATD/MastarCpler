package FrontEnd;

import GeneralDataStructure.*;
import GeneralDataStructure.OprandClass.*;
import GeneralDataStructure.QuadClass.A2Quad;
import GeneralDataStructure.QuadClass.A3Quad;
import GeneralDataStructure.QuadClass.MovQuad;
import GeneralDataStructure.QuadClass.Quad;
import GeneralDataStructure.ScopeClass.*;
import GeneralDataStructure.TypeSystem.*;
import javafx.util.Pair;

import java.util.*;

public class IRBuilder extends AstVisitor {
	public LinearIR linearCode;
	public int labelCnt;
	List<Integer> nextStatLabel;
	int ifElseEndLabel;
	int quadLabelCnt;
	Scope<Info> curScope;
	SpecialScope<Info> genScope;

	FuncFrame curFunc;
	ArrayList<Quad> curCodeList;

	Stack<Integer> brkLabel;
	Stack<Pair<Integer, Boolean>> ctnLabel;

	LabelTable uset;

	ClassDefTypeRef classType;
	public IRBuilder() {
		linearCode = new LinearIR();
		labelCnt = 0;
		nextStatLabel = new ArrayList<>();
		brkLabel = new Stack<>();
		ctnLabel = new Stack<>();
		uset = new LabelTable();
		curCodeList = new ArrayList<>();
	}


	void updateNextStatLabel(int t) {
		nextStatLabel.add(t);
		uset.add(t);
	}

	public LinearIR generateIR(Node root) throws Exception {
		visit(root);
		linearCode.setGeneralSymbols(genScope.getTable());
		return linearCode;
	}


	private void insertQuad(Quad ins) {
		if (nextStatLabel.size() > 0) {
			String label = nextLabel();
			ins.setLabel(label);
			for (int data: nextStatLabel) {
				uset.set(data, label);
			}
			quadLabelCnt++;
			nextStatLabel.clear();
		}
		curCodeList.add(ins);
	}

	public void updateLabel(LabelTable labels, ArrayList<Quad> code) {
		for (int i = 0; i < code.size(); ++i) {
			Quad qua = code.get(i);
			qua.updateLabel(labels);
		}
	}

	private void insertFunc(FuncFrame fun) {
		updateLabel(uset, curCodeList);
		curFunc.buildCFG(curCodeList);
		curCodeList.clear();
		linearCode.insertFunc(fun);
	}

	private String nextLabel() {
		String str = "label" + Integer.toString(quadLabelCnt++);
		return str;
	}

	private String classFuncLabel(String className, String funcName) {
		return "%" + className + "$" + funcName;
	}

	private String funcLabel(String funcName) {
		return "@" + funcName;
	}

	private String getGlobalName(String name) {
		return "@" + name;
	}

	private String getLocalName(String name) {
		return "%" + name + '.' + Integer.toString(labelCnt++);
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

	private String generateNewCode(Node nod, boolean isPointer) throws Exception {
		boolean p = isPointer;
		if (nod.type instanceof SingleTypeRef) {
			return Integer.toString(p ? 4 + 4 : nod.type.getSize()); // with size in record
		}
		Node scale = nod.sons.get(1);
		if (scale instanceof EmptyExprNode) {
			p = true;
			return generateNewCode(nod.sons.get(0), p);
		} else {
			visit(scale);
			return scale.reg + "x" + generateNewCode(nod.sons.get(0), p);
		}
	}

	void initAll() {
		curScope = genScope = new GeneralScope<>(null, null);
	}

	@Override public void visit(CodeNode nod) throws Exception {
		initAll();
		for (int i = 0; i < nod.sons.size(); ++i) {
			Node son = nod.sons.get(i);
			visit(son);
		}
	}

	@Override public void visit(VarDefNode nod) throws Exception {
		Info var;
		if (curScope instanceof GeneralScope) var = new Info(nod.reg.get(), nod.type);
		else if (curScope instanceof ClassScope) var = new Info("%this$" + classType.getOffset(nod.id), nod.type);
		else var = new Info(nod.reg.get(), nod.type);
		curScope.addItem(nod.id, var);
		if (nod.sons.size() == 0) return;
		Node son = nod.sons.get(0);
		if (son instanceof NewExprNode) {
			insertQuad(new Quad("new", new Register(var.getRegName()), new MemSpace(generateNewCode(son.sons.get(0), false))));
		} else {
			visit(son);
			if (isTempReg(son.reg.get())) {
				updateTempReg(son.reg.get(), nod.reg.get());
				son.reg = nod.reg;
			}
			else insertQuad(new MovQuad("mov", new Register(var.getRegName()), son.reg));
		}
	}

	@Override public void visit(ClassDefNode nod) throws Exception {
		curScope = genScope = new ClassScope<>(curScope, null);
		classType = (ClassDefTypeRef) nod.type;
		for (int i = 0; i < nod.sons.size(); ++i) {
			Node son = nod.sons.get(i);
			if (son instanceof FuncDefNode) {
				visit(son);
			} else visit(son);
		}
		curScope = genScope = (SpecialScope<Info>) genScope.parent;
	}

	@Override public void visit(FuncDefNode nod) throws Exception {
		curScope = new LocalScope<>(curScope, null);
		curFunc = new FuncFrame(nod.inClass == null ? funcLabel(nod.id) : classFuncLabel(nod.inClass, nod.id), (LocalScope<Info>) curScope, genScope);

		int size = nod.sons.size();
		for (int i = 0; i < size; ++i) {
			Node son = nod.sons.get(i);
			visit(son);
			if (i < size - 1) curFunc.addParam(curScope.findItem(son.id).getRegName());
		}
		if (!nextStatLabel.isEmpty()) insertQuad(new Quad("nop"));
		curScope = curScope.parent;
		insertFunc(curFunc);
	}

	@Override public void visit(ConsFuncDefNode nod) throws Exception {
		curScope = new LocalScope<>(curScope, null);
		curFunc = new FuncFrame(funcLabel(nod.id), (LocalScope<Info>) curScope, genScope);
		int size = nod.sons.size();
		for (int i = 0; i < size; ++i) {
			Node son = nod.sons.get(i);
			visit(son);
			if (i < size - 1) curFunc.addParam(curScope.findItem(son.id).getRegName());
		}
		curScope = curScope.parent;
		insertFunc(curFunc);
	}

	@Override public void visit(IfElseStatNode nod) throws Exception {
		ifElseEndLabel = labelCnt++;
		visit(nod.sons.get(0));
		for (int i = 1; i < nod.sons.size(); ++i) {
			visit(nod.sons.get(i));
		}
		updateNextStatLabel(ifElseEndLabel);
		ifElseEndLabel = -1;
	}

	void generateCondition(Node nod, int labelTrue, int labelFalse) throws Exception {
		Node left, right;
		int label;
		switch (nod.id) {
			case "&&":
				left = nod.sons.get(0);
				right = nod.sons.get(1);
				label = labelCnt++;
				generateCondition(left, label, labelFalse);
				updateNextStatLabel(label);
				generateCondition(right, labelTrue, labelFalse);
				break;
			case "||":
				left = nod.sons.get(0);
				right = nod.sons.get(1);
				label = labelCnt++;
				generateCondition(left, labelTrue, label);
				updateNextStatLabel(label);
				generateCondition(right, labelTrue, labelFalse);
				break;
			case "!" :
				left = nod.sons.get(0);
				visit(left);
				insertQuad(new Quad("tbr", left.reg, new LabelName(Integer.toString(labelFalse)), new LabelName(Integer.toString(labelTrue))));
				break;
			default:
				visit(nod);
				insertQuad(new Quad("cbr", nod.reg, new LabelName(Integer.toString(labelTrue)), new LabelName(Integer.toString(labelFalse))));
				break;

		}
	}

	@Override public void visit(IfStatNode nod) throws Exception {
		Node ifCond = nod.sons.get(0);
		Node ifStat = nod.sons.get(1);
		int label1 = labelCnt++, label2 = labelCnt++;
		generateCondition(ifCond, label1, label2);
		updateNextStatLabel(label1);
		visit(ifStat);
		if (ifElseEndLabel > 0)	insertQuad(new Quad("jump", new LabelName(Integer.toString(ifElseEndLabel))));
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
			insertQuad(new Quad("jump", new LabelName(Integer.toString(label1))));
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
			insertQuad(new Quad("jump", new LabelName(Integer.toString(label1))));
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
		if (nod.sons.size() > 0) insertQuad(new Quad("ret", nod.sons.get(0).reg));
		else insertQuad(new Quad("ret"));
	}

	@Override public void visit(BrkStatNode nod) throws Exception {
		insertQuad(new Quad("jump", new LabelName(Integer.toString(brkLabel.peek()))));
	}

	@Override public void visit(CtnStatNode nod) throws Exception {
		int label = ctnLabel.pop().getKey();
		insertQuad(new Quad("jump", new LabelName(Integer.toString(label))));
		ctnLabel.push(new Pair<>(label, true));
	}

	@Override public void visit(NullStatNode nod) throws Exception {
		insertQuad(new Quad("nop"));
	}

	boolean isTempReg(String name) {
		char c = name.charAt(1);
		return '0' <= c && c <= '9';
	}

	void updateTempReg(String name, String newName) {
		for (int i = curCodeList.size() - 1; i >= 0; --i) {
			Quad code = curCodeList.get(i);
			if (code.getRtName().equals(name)) {
				code.setRt(newName);
				break;
			}
		}
	}

	@Override public void visit(BinaryExprNode nod) throws Exception {
		Node left = nod.sons.get(0);
		Node right = nod.sons.get(1);

		visit(left);
		visit(right);
		boolean certain = left.isCertain() && right.isCertain();
		if (certain) nod.beCertain();
		else {
			if (nod.reg == null) nod.reg = new Register(getTempName());
		}

		switch (nod.id) {
			case "=":
				if (isTempReg(right.reg.get())) {
					updateTempReg(right.reg.get(), left.reg.get());
				} else {
					insertQuad(new MovQuad("mov", left.reg, right.reg));
				}
				break;
			case "+":
				if (!certain) {
					if (left.type instanceof IntTypeRef)
						insertQuad(new A3Quad("add", nod.reg, left.reg, right.reg));
					else if (left.type instanceof StringTypeRef) {
						insertQuad(new Quad("param", left.reg));
						insertQuad(new Quad("param", right.reg));
						insertQuad(new Quad("call", nod.reg, new FuncName(funcLabel("stringCat")), new ImmOprand(2)));
					}
				}
				else {
					if (left.type instanceof IntTypeRef)
						nod.reg = new ImmOprand(((ImmOprand) left.reg).getVal() + ((ImmOprand)right.reg).getVal());
					else
						nod.reg = new StringLiteral(left.reg.get().substring(0, left.reg.get().length() - 1) + right.reg.get().substring(1, right.reg.get().length()));
				}
				break;
			case "-":
				if (!certain) insertQuad(new A3Quad("sub", nod.reg, left.reg, right.reg));
				else nod.reg = new ImmOprand(((ImmOprand) left.reg).getVal() - ((ImmOprand)right.reg).getVal());
				break;
			case "*":
				if (!certain) insertQuad(new A3Quad("mul", nod.reg, left.reg, right.reg));
				else nod.reg = new ImmOprand(((ImmOprand) left.reg).getVal() * ((ImmOprand)right.reg).getVal());
				break;
			case "/":
				if (!certain) insertQuad(new A3Quad("div", nod.reg, left.reg, right.reg));
				else nod.reg = new ImmOprand(((ImmOprand) left.reg).getVal() / ((ImmOprand)right.reg).getVal());
				break;
			case "%":
				if (!certain) insertQuad(new A3Quad("mod", nod.reg, left.reg, right.reg));
				else nod.reg = new ImmOprand(((ImmOprand) left.reg).getVal() % ((ImmOprand)right.reg).getVal());
				break;
			case "<<":
				if (!certain) insertQuad(new A3Quad("lsh", nod.reg, left.reg, right.reg));
				else nod.reg = new ImmOprand(((ImmOprand) left.reg).getVal() << ((ImmOprand)right.reg).getVal());
				break;
			case ">>":
				if (!certain) insertQuad(new A3Quad("rsh", nod.reg, left.reg, right.reg));
				else nod.reg = new ImmOprand(((ImmOprand) left.reg).getVal() >> ((ImmOprand)right.reg).getVal());
				break;
			case "&":
				if (!certain) insertQuad(new A3Quad("and", nod.reg, left.reg, right.reg));
				else nod.reg = new ImmOprand(((ImmOprand) left.reg).getVal() & ((ImmOprand)right.reg).getVal());
				break;
			case "|":
				if (!certain) insertQuad(new A3Quad("or", nod.reg, left.reg, right.reg));
				else nod.reg = new ImmOprand(((ImmOprand) left.reg).getVal() | ((ImmOprand)right.reg).getVal());
				break;
			case "^":
				if (!certain) insertQuad(new A3Quad("xor", nod.reg, left.reg, right.reg));
				else nod.reg = new ImmOprand(((ImmOprand) left.reg).getVal() ^ ((ImmOprand)right.reg).getVal());
				break;
			case "==":
				if (!certain) insertQuad(new A3Quad("equ", nod.reg, left.reg, right.reg));
				else
					nod.reg = new BoolImmOprand(left.reg.get().equals(right.reg.get()) ? 1 : 0);
				break;
			case "!=":
				if (!certain) insertQuad(new A3Quad("neq", nod.reg, left.reg, right.reg));
				else nod.reg = new BoolImmOprand(!left.reg.get().equals(right.reg.get()) ? 1 : 0);
				break;
			case "<":
				if (!certain) {
					if (left.type instanceof IntTypeRef) insertQuad(new A3Quad("les", nod.reg, left.reg, right.reg));
					else if (left.type instanceof StringTypeRef) {
						insertQuad(new Quad("param", left.reg));
						insertQuad(new Quad("param", right.reg));
						insertQuad(new Quad("call", nod.reg, new FuncName(funcLabel("stringLes")), new ImmOprand(2)));
					}
				}
				else if (left.type instanceof IntTypeRef) {
					nod.reg = new BoolImmOprand(((ImmOprand) left.reg).getVal() < ((ImmOprand) right.reg).getVal() ? 1 : 0);
				} else nod.reg = new BoolImmOprand(left.reg.get().compareTo(right.reg.get()) < 0 ? 1 : 0);
				break;
			case "<=":
				if (!certain) {
					if (left.type instanceof IntTypeRef) insertQuad(new A3Quad("leq", nod.reg, left.reg, right.reg));
					else if (left.type instanceof StringTypeRef) {
						insertQuad(new Quad("param", left.reg));
						insertQuad(new Quad("param", right.reg));
						insertQuad(new Quad("call", nod.reg, new FuncName(funcLabel("stringLeq")), new ImmOprand(2)));
					}
				}
				else if (left.type instanceof IntTypeRef) {
					nod.reg = new BoolImmOprand(((ImmOprand) left.reg).getVal() <= ((ImmOprand) right.reg).getVal() ? 1 : 0);
				} else nod.reg = new BoolImmOprand(left.reg.get().compareTo(right.reg.get()) <= 0 ? 1 : 0);
				break;
			case ">":
				if (!certain) {
					if (left.type instanceof IntTypeRef) insertQuad(new A3Quad("gre", nod.reg, left.reg, right.reg));
					else if (left.type instanceof StringTypeRef) {
						insertQuad(new Quad("param", right.reg));
						insertQuad(new Quad("param", left.reg));
						insertQuad(new Quad("call", nod.reg, new FuncName(funcLabel("stringLes")), new ImmOprand(2)));
					}
				}
				else if (left.type instanceof IntTypeRef) {
					nod.reg = new BoolImmOprand(((ImmOprand) left.reg).getVal() > ((ImmOprand) right.reg).getVal() ? 1 : 0);
				} else nod.reg = new BoolImmOprand(left.reg.get().compareTo(right.reg.get()) > 0 ? 1 : 0);
				break;
			case ">=":
				if (certain) {
					if (left.type instanceof IntTypeRef) insertQuad(new A3Quad("geq", nod.reg, left.reg, right.reg));
					else if (left.type instanceof StringTypeRef) {
						insertQuad(new Quad("param", right.reg));
						insertQuad(new Quad("param", left.reg));
						insertQuad(new Quad("call", nod.reg, new FuncName(funcLabel("stringLeq")), new ImmOprand(2)));
					}
				}
				else if (left.type instanceof IntTypeRef) {
					nod.reg = new BoolImmOprand(((ImmOprand) left.reg).getVal() >= ((ImmOprand) right.reg).getVal() ? 1 : 0);
				} else nod.reg = new BoolImmOprand(left.reg.get().compareTo(right.reg.get()) >= 0 ? 1 : 0);
				break;
		}
	}

	@Override void visit(LeftUnaryExprNode nod) throws Exception {
		visitChild(nod);
		Node son = nod.sons.get(0);
		Oprand sonReg = son.reg;
		Oprand tmp;
		switch(nod.id) {
			case "++":
				nod.reg = sonReg;
				insertQuad(new A3Quad("add", sonReg, sonReg, new ImmOprand(1)));
				break;
			case "--":
				nod.reg = sonReg;
				insertQuad(new A3Quad("sub", sonReg, sonReg, new ImmOprand(1)));
				break;
			case "~": case "!":
				if (!son.isCertain()) {
					nod.reg = tmp = new Register(getTempName());
					long val = (1L << 31) - 1;
					insertQuad(new A3Quad("xor", tmp, sonReg, new ImmOprand((int) val)));
				} else {
					nod.beCertain();
					if (son.type instanceof IntTypeRef) {
						nod.reg = new ImmOprand(~((ImmOprand) son.reg).getVal());
					} else {
						nod.reg = sonReg.equals("true") ? new BoolImmOprand(0) : new BoolImmOprand(1);
					}
				}
				break;
			case "-":
				if (!son.isCertain()) {
					nod.reg = tmp = new Register(getTempName());
					insertQuad(new A3Quad("sub", tmp, new ImmOprand(0), sonReg));
				} else {
					nod.beCertain();
					nod.reg = new ImmOprand(-((ImmOprand) son.reg).getVal());
				}
				break;
			case "+":
				nod.reg = sonReg;
				nod.setCertain(son.isCertain());
				break;
		}
	}

	@Override void visit(RightUnaryExprNode nod) throws Exception {
		visitChild(nod);
		Oprand tmp = new Register(getTempName()), sonReg = nod.sons.get(0).reg;
		insertQuad(new MovQuad("mov", tmp, sonReg));
		switch (nod.id) {
			case "++":
				insertQuad(new A3Quad("add", sonReg, sonReg, new ImmOprand(1)));
				break;
			case "--":
				insertQuad(new A3Quad("sub", sonReg, sonReg, new ImmOprand(1)));
				break;
		}
		nod.reg = tmp;
	}

	@Override void visit(NewExprNode nod) throws Exception {
		Oprand var = new Register(getTempName());
		Node son = nod.sons.get(0);
		insertQuad(new Quad("new", var, new MemSpace(generateNewCode(son, false))));
		nod.reg = var;
	}

	@Override public void visit(FuncExprNode nod) throws Exception {
		visitChild(nod);
		int n = nod.sons.size();
		for (int i = 0; i < n; ++i) {
			insertQuad(new Quad("param", nod.sons.get(i).reg));
		}
		String fun = nod.inClass == null ? funcLabel(nod.id): classFuncLabel(nod.inClass, nod.id);
		if (!(nod.type instanceof VoidTypeRef)) {
			nod.reg = new Register(getTempName());
			insertQuad(new Quad("call", nod.reg, new FuncName(fun), new ImmOprand(n)));
		} else {
			insertQuad(new Quad("call", null, new FuncName(fun), new ImmOprand(n)));
		}
	}

	@Override void visit(ArrExprNode nod) throws Exception {
		visitChild(nod);
		Node son = nod.sons.get(0);
		Node idx = nod.sons.get(1);
		if (nod.type instanceof SingleTypeRef) {
			nod.reg = new MemAccess(son.reg.get() + '$' + idx.reg.get() + 'x' + nod.type.getSize());
		} else {
			nod.reg = new MemAccess(son.reg.get() + '$' + idx.reg.get() + 'x' + 8);
		}
	}

	@Override void visit(ObjAccExprNode nod) throws Exception {
		Node son = nod.sons.get(0);
		Node mem = nod.sons.get(1);
		visit(son);
		if (mem instanceof VarExprNode) {
			nod.reg = new MemAccess(son.reg.get() + '$' + ((ClassTypeRef) son.type).getBelongClass().getOffset(mem.id));
		} else {
			if (son.type instanceof ClassTypeRef) {
				visit(mem);
				nod.reg = mem.reg;
			}
			else {
				nod.reg = new Register(getTempName());
				insertQuad(new Quad("loadAI", nod.reg, son.reg, new ImmOprand(4)));
			}
		}
	}

	@Override public void visit(VarExprNode nod) throws Exception {
		if (nod.id.equals("this")) {
			nod.reg = new Register("%this");
			return;
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
