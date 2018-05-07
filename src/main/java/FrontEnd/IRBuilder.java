package FrontEnd;

import GeneralDataStructure.*;
import GeneralDataStructure.Quad;
import GeneralDataStructure.ScopeClass.*;
import GeneralDataStructure.TypeSystem.*;
import javafx.util.Pair;

import javax.sound.sampled.Line;
import java.util.*;

public class IRBuilder extends AstVisitor {
	public LinearIR linearCode;
	HashTable<String, ClassScope<Info> > classTable;
	public int labelCnt;
	String nextStatLabel;
	Scope<Info> curScope;
	SpecialScope<Info> genScope;
	FuncFrame curFunc;

	Stack<String> brkLabel;
	Stack<Pair<String, Boolean>> ctnLabel;

	public IRBuilder() {
		linearCode = new LinearIR();
		labelCnt = 0;
		nextStatLabel = null;
		brkLabel = new Stack<>();
		ctnLabel = new Stack<>();
		classTable = new HashTable<>();
	}

	public LinearIR generateIR(Node root) throws Exception {
		visit(root);
		return linearCode;
	}

	private void insertQuad(Quad ins) {
		if (nextStatLabel != null) {
			ins.setLabel(nextStatLabel);
			nextStatLabel = null;
		}
		curFunc.insertCode(ins);
	}

	private void insertFunc(FuncFrame fun) {
		linearCode.insertFunc(fun);
	}

	private String nextLabel() {
		return "label" + Integer.toString(labelCnt++);
	}

	private String classFuncLabel(String className, String funcName) {
		return "%" + className + "_" + funcName;
	}

	private String funcLabel(String funcName) {
		return "@" + funcName;
	}

	private String getGlobalName(String name) {
		return "@" + name;
	}

	private String getLocalName(String name) {
		return "%" + name + Integer.toString(labelCnt++);
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
		if (nod.type instanceof SingleTypeRef) return Integer.toString(p ? 4 : nod.type.getSize());
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
		curScope = genScope = new GeneralScope<>(null);
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
		if (curScope instanceof GeneralScope) var = new Info(getGlobalName(nod.id), nod.type);
		else if (curScope instanceof ClassScope) var = new Info(getLocalName("this." + nod.id), nod.type);
		else var = new Info(getLocalName(nod.id), nod.type);
		curScope.addItem(nod.id, var);
		if (nod.sons.size() == 0) return;
		Node son = nod.sons.get(0);
		if (son instanceof NewExprNode) {
			insertQuad(new Quad("new", var.getRegName(), generateNewCode(son.sons.get(0), false)));
		}
	}

	@Override public void visit(ClassDefNode nod) throws Exception {
		curScope = genScope = new ClassScope<>(curScope);
		classTable.insert(nod.id, (ClassScope<Info>) curScope);
		for (int i = 0; i < nod.sons.size(); ++i) {
			Node son = nod.sons.get(i);
			if (son instanceof FuncDefNode) {
				nextStatLabel = classFuncLabel(nod.id, son.id);
				visit(son);
			} else visit(son);
		}
		curScope = genScope = (SpecialScope<Info>) genScope.parent;
	}

	@Override public void visit(FuncDefNode nod) throws Exception {
		curScope = new LocalScope<>(curScope);
		curFunc = new FuncFrame((LocalScope<Info>) curScope, genScope);
		nextStatLabel = nod.inClass == null ? funcLabel(nod.id) : classFuncLabel(nod.inClass, nod.id);
		int size = nod.sons.size();
		for (int i = 0; i < size; ++i) {
			Node son = nod.sons.get(i);
			visit(son);
			if (i < size - 1) insertQuad(new Quad("init", curScope.findItem(son.id).getRegName()));
		}
		curScope = curScope.parent;
		insertFunc(curFunc);
	}

	@Override public void visit(ConsFuncDefNode nod) throws Exception {
		curScope = new LocalScope<>(curScope);
		curFunc = new FuncFrame((LocalScope<Info>) curScope, genScope);
		nextStatLabel = funcLabel(nod.id);
		int size = nod.sons.size();
		for (int i = 0; i < size; ++i) {
			Node son = nod.sons.get(i);
			visit(son);
			if (i < size - 1) insertQuad(new Quad("init", curScope.findItem(son.id).getRegName()));
		}
		curScope = curScope.parent;
		insertFunc(curFunc);
	}

	@Override public void visit(IfElseStatNode nod) throws Exception {
		String endLabel = nextLabel();
		visit(nod.sons.get(0));
		for (int i = 1; i < nod.sons.size(); ++i) {
			insertQuad(new Quad("jump", endLabel));
			visit(nod.sons.get(i));
		}
		nextStatLabel = endLabel;
	}

	void generateCondition(Node nod, String labelTrue, String labelFalse) throws Exception {
		Node left, right;
		String label;
		switch (nod.id) {
			case "&&":
				left = nod.sons.get(0);
				right = nod.sons.get(1);
				label = nextLabel();
				generateCondition(left, label, labelFalse);
				nextStatLabel = label;
				generateCondition(right, labelTrue, labelFalse);
				break;
			case "||":
				left = nod.sons.get(0);
				right = nod.sons.get(1);
				label = nextLabel();
				generateCondition(left, labelTrue, label);
				nextStatLabel = label;
				generateCondition(right, labelTrue, labelFalse);
				break;
			case "!" :
				left = nod.sons.get(0);
				visit(left);
				insertQuad(new Quad("tbr", left.reg, labelFalse, labelTrue));
				break;
			default:
				visit(nod);
				insertQuad(new Quad("cbr", nod.reg, labelTrue, labelFalse));
				break;

		}
	}

	@Override public void visit(IfStatNode nod) throws Exception {
		Node ifCond = nod.sons.get(0);
		Node ifStat = nod.sons.get(1);
		String label1 = nextLabel(), label2 = nextLabel();
		generateCondition(ifCond, label1, label2);
		nextStatLabel = label1;
		visit(ifStat);
		nextStatLabel = label2;
	}

	@Override public void visit(ForStatNode nod) throws Exception {
		Node initExpr = nod.sons.get(0);
		Node condExpr = nod.sons.get(1);
		Node loopExpr = nod.sons.get(2);
		Node forBody = nod.sons.get(3);
		visit(initExpr);

		String label1 = nextLabel();
		String label2 = nextLabel();
		String label3 = nextLabel();
		if (!(condExpr instanceof EmptyExprNode)) {
			generateCondition(condExpr, label1, label2);
		} else {
			insertQuad(new Quad("jump", label1));
		}

		nextStatLabel = label1;
		ctnLabel.push(new Pair<>(label3, false));
		brkLabel.push(label2);
		visit(forBody);
		brkLabel.pop();

		if (ctnLabel.pop().getValue().equals(true)) nextStatLabel = label3;
		visit(loopExpr);
		if (!(condExpr instanceof EmptyExprNode)) {
			generateCondition(condExpr, label1, label2);
		} else {
			insertQuad(new Quad("jump", label1));
		}

		nextStatLabel = label2;
	}

	@Override public void visit(WhileStatNode nod) throws Exception {
		Node condExpr = nod.sons.get(0);
		Node whileBody = nod.sons.get(1);

		String label1, label2, label3;
		label1 = nextLabel();
		label2 = nextLabel();
		label3 = nextLabel();
		generateCondition(condExpr, label1, label2);

		nextStatLabel = label1;
		ctnLabel.push(new Pair<>(label3, false));
		brkLabel.push(label2);
		visit(whileBody);
		brkLabel.pop();

		if (ctnLabel.pop().getValue().equals(true)) nextStatLabel = label3;
		visit(condExpr);
		generateCondition(condExpr, label1, label2);

		nextStatLabel = label2;
	}

	@Override public void visit(RetStatNode nod) throws Exception {
		visitChild(nod);
		if (nod.sons.size() > 0) insertQuad(new Quad("ret", nod.sons.get(0).reg));
		else insertQuad(new Quad("ret"));
	}

	@Override public void visit(BrkStatNode nod) throws Exception {
		insertQuad(new Quad("jump", brkLabel.peek()));
	}

	@Override public void visit(CtnStatNode nod) throws Exception {
		String label = ctnLabel.pop().getKey();
		insertQuad(new Quad("jump", label));
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
		nod.reg = getTempName();

		switch (nod.id) {
			case "=":
				insertQuad(new Quad("mov", left.reg, right.reg));
				break;
			case "+":
				insertQuad(new Quad("add", nod.reg, left.reg, right.reg));
				break;
			case "-":
				insertQuad(new Quad("sub", nod.reg, left.reg, right.reg));
				break;
			case "*":
				insertQuad(new Quad("mul", nod.reg, left.reg, right.reg));
				break;
			case "/":
				insertQuad(new Quad("div", nod.reg, left.reg, right.reg));
				break;
			case "%":
				insertQuad(new Quad("mod", nod.reg, left.reg, right.reg));
				break;
			case "<<":
				insertQuad(new Quad("lsh", nod.reg, left.reg, right.reg));
				break;
			case ">>":
				insertQuad(new Quad("rsh", nod.reg, left.reg, right.reg));
				break;
			case "&":
				insertQuad(new Quad("rsh", nod.reg, left.reg, right.reg));
				break;
			case "|":
				insertQuad(new Quad("or", nod.reg, left.reg, right.reg));
				break;
			case "^":
				insertQuad(new Quad("xor", nod.reg, left.reg, right.reg));
				break;
			case "==":
				insertQuad(new Quad("equ", nod.reg, left.reg, right.reg));
				break;
			case "!=":
				insertQuad(new Quad("neq", nod.reg, left.reg, right.reg));
				break;
			case "<":
				insertQuad(new Quad("les", nod.reg, left.reg, right.reg));
				break;
			case "<=":
				insertQuad(new Quad("leq", nod.reg, left.reg, right.reg));
				break;
			case ">":
				insertQuad(new Quad("gre", nod.reg, left.reg, right.reg));
				break;
			case ">=":
				insertQuad(new Quad("geq", nod.reg, left.reg, right.reg));
				break;
		}
	}

	@Override void visit(LeftUnaryExprNode nod) throws Exception {
		visitChild(nod);
		String sonReg = nod.sons.get(0).reg;
		String tmp;
		switch(nod.id) {
			case "++":
				nod.reg = sonReg;
				insertQuad(new Quad("add", sonReg, sonReg, Integer.toString(1)));
				break;
			case "--":
				nod.reg = sonReg;
				insertQuad(new Quad("sub", sonReg, sonReg, Integer.toString(1)));
				break;
			case "~": case "!":
				nod.reg = tmp = getTempName();
				insertQuad(new Quad("not", tmp, sonReg));
				break;
			case "-":
				nod.reg = tmp = getTempName();
				insertQuad(new Quad("neg", tmp, sonReg));
				break;
			case "+":
				nod.reg = sonReg;
				break;
		}
	}

	@Override void visit(RightUnaryExprNode nod) throws Exception {
		visitChild(nod);
		String tmp = getTempName(), sonReg = nod.sons.get(0).reg;
		insertQuad(new Quad("mov", tmp, sonReg));
		switch (nod.id) {
			case "++":
				insertQuad(new Quad("add", sonReg, sonReg, Integer.toString(1)));
				break;
			case "--":
				insertQuad(new Quad("sub", sonReg, sonReg, Integer.toString(1)));
				break;
		}
		nod.reg = tmp;
	}

	@Override void visit(NewExprNode nod) throws Exception {
		visitChild(nod);
		String var = getTempName();
		Node son = nod.sons.get(0);
		insertQuad(new Quad("new", var, generateNewCode(son, false)));
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
			nod.reg = getTempName();
			insertQuad(new Quad("call", nod.reg, fun, Integer.toString(n)));
		} else {
			insertQuad(new Quad("call", null, fun, Integer.toString(n)));
		}
	}

	@Override void visit(ArrExprNode nod) throws Exception {
		visitChild(nod);
		Node son = nod.sons.get(0);
		Node idx = nod.sons.get(1);
		if (nod.type instanceof SingleTypeRef) {
			nod.reg = son.reg + '.' + idx.reg + 'x' + nod.type.getSize();
		} else {
			nod.reg = son.reg + '.' + idx.reg + 'x' + 4;
		}
	}

	@Override void visit(ObjAccExprNode nod) throws Exception {
		Node son = nod.sons.get(0);
		Node mem = nod.sons.get(1);
		visit(son);
		if (mem instanceof VarExprNode) {
			nod.reg = son.reg + '.' + ((ClassTypeRef) son.type).getBelongClass().getOffset(mem.id);
		} else {
			if (son.type instanceof ClassTypeRef) visit(mem);
			else {

			}
		}
	}

	@Override public void visit(VarExprNode nod) throws Exception {
		if (nod.id.equals("this")) {
			nod.id = "this";
			return;
		}
		Info var = curScope.matchVarName(nod.id).getValue();
		nod.reg = var.getRegName();
	}

	@Override void visit(IntLiteralNode nod) throws Exception {
		nod.reg = nod.id;
	}

	@Override void visit(LogicalLiteralNode nod) throws Exception {
		nod.reg = nod.id;
	}

	@Override void visit(NullLiteralNode nod) throws Exception {
		nod.reg = "null";
	}

	@Override void visit(StringLiteralNode nod) throws Exception {
		nod.reg = '"' + nod.id + '"';
	}
}
