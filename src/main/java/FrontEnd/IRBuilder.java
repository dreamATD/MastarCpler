package FrontEnd;

import GeneralDataStructure.*;
import GeneralDataStructure.OprandClass.Register;
import GeneralDataStructure.Quad;
import GeneralDataStructure.ScopeClass.ClassScope;
import GeneralDataStructure.ScopeClass.GeneralScope;
import GeneralDataStructure.ScopeClass.LocalScope;
import GeneralDataStructure.ScopeClass.Scope;
import GeneralDataStructure.TypeSystem.*;
import javafx.util.Pair;

import java.util.*;

public class IRBuilder extends AstVisitor {
	public List<FuncFrame> linearCode;
	HashTable<String, ClassScope<Info> > classTable;
	public int labelCnt;
	String nextStatLabel;
	Scope<Info> curScope;
	FuncFrame curFunc;

	Stack<String> brkLabel;
	Stack<Pair<String, Boolean>> ctnLabel;

	public IRBuilder() {
		linearCode = new LinkedList<>();
		labelCnt = 0;
		nextStatLabel = null;
	}

	public List<FuncFrame> generateIR(Node root) throws Exception {
		visit(root);
		return linearCode;
	}

	void insertQuad(Quad ins) {
		if (nextStatLabel != null) {
			ins.setLabel(nextStatLabel);
			nextStatLabel = null;
		}
		curFunc.insertCode(ins);
	}

	void insertFunc(FuncFrame fun) {
		linearCode.add(fun);
	}

	String nextLabel() {
		return "label" + Integer.toString(labelCnt++);
	}

	String classFuncLabel(String className, String funcName) {
		return "def_" + className + "_" + funcName;
	}

	String FuncLabel(String funcName) {
		return "def_" + funcName;
	}

	String getGlobalName(String name) {
		return "@" + name;
	}

	String getLocalName(String name) {
		return "%" + name + Integer.toString(labelCnt++);
	}


	public void visitChild(Node nod) throws Exception {
		if (nod == null) return;
		for (int i = 0; i < nod.sons.size(); ++i) {
			visit(nod.sons.get(i));
		}
	}

	String generateNewCode(Node nod) throws Exception {
		if (nod.type instanceof SingleTypeRef) return Integer.toString(nod.type.getSize());
		Node scale = nod.sons.get(1);
		if (scale instanceof EmptyExprNode) {
			return Integer.toString(4);
		}
		visit(scale);
		return scale.reg + "(" + generateNewCode(nod.sons.get(0)) + ")";
	}

	void initAll() {

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
		else var = new Info(getLocalName(nod.id), nod.type);
		curScope.addItem(nod.id, var);
		if (nod.sons.size() == 0) return;
		Node son = nod.sons.get(0);
		if (son instanceof NewExprNode) {
			curFunc.insertCode(new Quad("new", var.getRegName(), generateNewCode(son.sons.get(0))));
		}
	}

	@Override public void visit(ClassDefNode nod) throws Exception {
		curScope = new ClassScope<>(curScope);
		classTable.insert(nod.id, (ClassScope<Info>) curScope);
		for (int i = 0; i < nod.sons.size(); ++i) {
			Node son = nod.sons.get(i);
			if (son instanceof FuncDefNode) {
				nextStatLabel = classFuncLabel(nod.id, son.id);
				visit(son);
			} else visit(son);
		}
		curScope = curScope.parent;
	}

	@Override public void visit(FuncDefNode nod) throws Exception {
		curScope = new LocalScope<>(curScope);
		curFunc = new FuncFrame((LocalScope<Info>) curScope);
		nextStatLabel = FuncLabel(nod.id);
		int size = nod.sons.size();
		for (int i = 0; i < size; ++i) {
			Node son = nod.sons.get(i);
			visit(son);
			if (i < size - 1) curFunc.insertCode(new Quad("init", son.id, curScope.findItem(son.id).getRegName()));
		}
		curScope = curScope.parent;

	}

	@Override public void visit(ConsFuncDefNode nod) throws Exception {

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

	@Override public void visit(IfStatNode nod) throws Exception {
		Node ifCond = nod.sons.get(0);
		Node ifStat = nod.sons.get(1);
		String label1, label2;
		label1 = ifStat.label = nextLabel();
		label2 = nextStatLabel = nextLabel();
		visit(ifCond);
		insertQuad(new Quad("cbr", ifCond.result, label1, label2));
		visit(ifStat);
		nextStatLabel = label2;
	}

	@Override public void visit(ForStatNode nod) throws Exception {
		Node initExpr = nod.sons.get(0);
		Node condExpr = nod.sons.get(1);
		Node loopExpr = nod.sons.get(2);
		Node forBody = nod.sons.get(3);
		visit(initExpr);
		visit(condExpr);

		String label1 = nextLabel();
		String label2 = nextLabel();
		String label3 = nextLabel();
		if (!(condExpr instanceof EmptyExprNode)) {
			insertQuad(new Quad("cbr", condExpr.result, label1, label2));
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
		visit(condExpr);
		if (!(condExpr instanceof EmptyExprNode)) {
			insertQuad(new Quad("cbr", condExpr.result, label1, label2));
		} else {
			insertQuad(new Quad("jump", label1));
		}

		nextStatLabel = label2;
	}

	@Override public void visit(WhileStatNode nod) throws Exception {
		Node condExpr = nod.sons.get(0);
		Node whileBody = nod.sons.get(1);
		visit(condExpr);

		String label1, label2, label3;
		label1 = nextLabel();
		label2 = nextLabel();
		label3 = nextLabel();
		insertQuad(new Quad("cbr", condExpr.result, label1, label2));

		nextStatLabel = label1;
		ctnLabel.push(new Pair<>(label3, false));
		brkLabel.push(label2);
		visit(whileBody);
		brkLabel.pop();

		if (ctnLabel.pop().getValue().equals(true)) nextStatLabel = label3;
		visit(condExpr);
		insertQuad(new Quad("cbr", condExpr.result, label1, label2));

		nextStatLabel = label2;
	}

	@Override public void visit(RetStatNode nod) throws Exception {
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

	@Override public void visit(VarDefStatNode nod) throws Exception {
		visitChild(nod);
	}

	@Override public void visit(EmptyExprNode nod) throws Exception {
	}

	void visitLeft(Node nod) {
		if (nod instanceof VarExprNode) visitLeft((VarExprNode) nod);
		else if (nod instanceof )
	}

	void visitLeft(VarExprNode nod) {
		String mem = (nod.defineBelongTo instanceof GeneralScope && !(nod.defineBelongTo instanceof ClassScope)) ? getGlobalName(nod.id) : getLocalName(nod.id);
		insertQuad(new Quad("LoadI",  ))
	}

	@Override public void visit(BinaryExprNode nod) throws Exception {
		Node left = nod.sons.get(0);
		Node right = nod.sons.get(1);
		String res = nod.result;

		visitLeft(left);
		visit(right);

		switch (nod.id) {
			case "=":
				opc = "xchg";

				break;
			case "+":
				opc = "add";
				break;
			case "-":
				opc = "sub";
				break;
			case "*":
				opc = "mult";
				break;
			case "/":
				opc = "div";
				break;
			case "%":
				opc = "mod";
				break;
			case "<<":
				opc = "lshift";
				break;
			case ">>":
				opc = "rshift";
				break;
			case "&":
				opc = "and";
				break;
			case "|":
				opc = "or";
				break;
			case "^":
				opc = "xor";
				break;
			case "==":
				opc = "cmpEQ";
				break;
			case "!=":
				opc = "cmpNE";
				break;
			case "<":
				opc = "cmpLT";
				break;
			case "<=":
				opc = "cmpLE";
				break;
			case ">":
				opc = "cmpGT";
				break;
			case ">=":
				opc = "cmpGE";
				break;
		}

		Quad result;
		if (opc.equals("xchg")) {
			insertQuad(Quad.assignExpr(opc, left.result.name, right.result.name));
			result = new Quad("store", ??,left.result.name);
		}
		else {
			nod.result = Register.getNextReg();
			result = Quad.binaryExpr(opc, nod.result.name, left.result.name, right.result.name);
		}
		insertQuad(result);
		update(ret, result);
		return ret;
	}



	public Quad visit(FuncExprNode nod) throws Exception {
		List<String> lst;
		lst = new ArrayList<>();
		Quad ret = null;
		for (int i = 0; i < nod.sons.size(); ++i) {
			Node son = nod.sons.get(i);
			update(ret, visit(son));
			lst.add(son.result.name);
		}
		Quad func;
		if (nod.type instanceof VoidTypeRef)
			func = Quad.funcCall("call", getGlobalName(nod.id), lst);
		else {
			nod.result = Register.getNextReg();
			func = Quad.funcCall("call", nod.result.name, getGlobalName(nod.id), lst);
		}
		insertQuad(func);
		update(ret, func);
		return ret;
	}

	public void visit(VarExprNode nod) throws Exception {
		nod.result = new Register(nod.defineBelongTo.toString() + "." + nod.id);
		Quad ret = null;
		if (...) {
			update(ret, insertQuad(new Quad("load", nod.result, ??)));
		}
		return ret;
	}

}
