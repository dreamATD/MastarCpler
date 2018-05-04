package FrontEnd;

public abstract class AstVisitor {
	void visit(Node nod) throws Exception {
		if (nod instanceof CodeNode) visit((CodeNode) nod);
		else if (nod instanceof VarDefNode) visit((VarDefNode) nod);
		else if (nod instanceof ClassDefNode) visit((ClassDefNode) nod);
		else if (nod instanceof ConsFuncDefNode) visit((ConsFuncDefNode) nod);
		else if (nod instanceof FuncDefNode) visit((FuncDefNode) nod);
		else if (nod instanceof CompStatNode) visit((CompStatNode) nod);
		else if (nod instanceof ExprStatNode) visit((ExprStatNode) nod);
		else if (nod instanceof IfElseStatNode) visit((IfElseStatNode) nod);
		else if (nod instanceof IfStatNode) visit((IfStatNode) nod);
		else if (nod instanceof ForStatNode) visit((ForStatNode) nod);
		else if (nod instanceof WhileStatNode) visit((WhileStatNode) nod);
		else if (nod instanceof RetStatNode) visit((RetStatNode) nod);
		else if (nod instanceof BrkStatNode) visit((BrkStatNode) nod);
		else if (nod instanceof CtnStatNode) visit((CtnStatNode) nod);
		else if (nod instanceof NullStatNode) visit((NullStatNode) nod);
		else if (nod instanceof VarDefStatNode) visit((VarDefStatNode) nod);
		else if (nod instanceof EmptyExprNode) visit((EmptyExprNode) nod);
		else if (nod instanceof BinaryExprNode) visit((BinaryExprNode) nod);
		else if (nod instanceof LeftUnaryExprNode) visit((LeftUnaryExprNode) nod);
		else if (nod instanceof RightUnaryExprNode) visit((RightUnaryExprNode) nod);
		else if (nod instanceof NewExprNode) visit((NewExprNode) nod);
		else if (nod instanceof TypeExprNode) visit((TypeExprNode) nod);
		else if (nod instanceof FuncExprNode) visit((FuncExprNode) nod);
		else if (nod instanceof ArrExprNode) visit((ArrExprNode) nod);
		else if (nod instanceof ObjAccExprNode) visit((ObjAccExprNode) nod);
		else if (nod instanceof VarExprNode) visit((VarExprNode) nod);
		else if (nod instanceof IntLiteralNode) visit((IntLiteralNode) nod);
		else if (nod instanceof LogicalLiteralNode) visit((LogicalLiteralNode) nod);
		else if (nod instanceof NullLiteralNode) visit((NullLiteralNode) nod);
		else if (nod instanceof StringLiteralNode) visit((StringLiteralNode) nod);
	}
	void visitChild(Node nod) throws Exception {
		if (nod == null) return;
		for (int i = 0; i < nod.sons.size(); ++i) {
			visit(nod.sons.get(i));
		}
	}
	void visit(CodeNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(VarDefNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(ClassDefNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(FuncDefNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(ConsFuncDefNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(CompStatNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(ExprStatNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(IfElseStatNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(IfStatNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(ForStatNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(WhileStatNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(RetStatNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(BrkStatNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(CtnStatNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(NullStatNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(VarDefStatNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(EmptyExprNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(BinaryExprNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(LeftUnaryExprNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(RightUnaryExprNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(NewExprNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(TypeExprNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(FuncExprNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(ArrExprNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(ObjAccExprNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(VarExprNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(IntLiteralNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(LogicalLiteralNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(NullLiteralNode nod) throws Exception {
		visitChild(nod);
	}
	void visit(StringLiteralNode nod) throws Exception {
		visitChild(nod);
	}
}
