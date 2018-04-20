import java.util.ArrayList;
import java.util.List;

public class SemanticChecker extends AstVisitor {
	GeneralScope genScope;
	int iterNum;
	List<String> classStack;
	SemanticChecker(GeneralScope g) {
		genScope = g;
		iterNum = 0;
		classStack = new ArrayList<String>();
	}
	void checkFuncRet(Node nod, TypeRef type) throws SyntaxError {
		if (nod instanceof RetStatNode) {
			if (type.equalsSingleType("void") && nod.sons.size() > 0) throw new DisMatchedFuncReturn(nod.loc);
			if (!type.equalsSingleType("void")) {
				if (nod.sons.size() == 0) throw new DisMatchedFuncReturn(nod.loc);
				if (!type.equals(nod.sons.get(0).type)) throw new DisMatchedFuncReturn(nod.loc);
			}
		} else {
			for (int i = 0; i < nod.sons.size(); ++i) {
				Node son = nod.sons.get(i);
				if (son instanceof StatNode) checkFuncRet(son, type);
			}
		}
	}
	boolean checkTypeEntity(TypeRef type) {
		if (type instanceof ArrayTypeRef) type = ((ArrayTypeRef) type).getSimpleRef();
		if (type instanceof ClassTypeRef && !genScope.entities.containsKey(((ClassTypeRef) type).typeId)) return false;
		return true;
	}
	boolean checkLeftValue(ExprNode nod) {
		if (nod instanceof VarExprNode) return true;
		if (nod instanceof ObjAccExprNode && checkLeftValue((ExprNode) nod.sons.get(1))) return true;
		if (nod instanceof ArrExprNode && nod.sons.get(0) instanceof VarExprNode) return true;
		return false;
	}
	@Override void visit(VarDefNode nod) throws SyntaxError {
		visitChild(nod);
		if (!nod.sons.isEmpty()) {
			ExprNode init = (ExprNode) nod.sons.get(0);
			if (!nod.type.equals(init.type)) throw new NoCastExpr(init.loc);
		}
	}
	@Override void visit(ClassDefNode nod) throws SyntaxError {
		classStack.add(nod.id);
		visitChild(nod);
		classStack.remove(classStack.size() - 1);
	}
	@Override void visit(FuncDefNode nod) throws SyntaxError {
		visitChild(nod);
		checkFuncRet(nod.sons.get(nod.sons.size() - 1), nod.type);
	}
	@Override void visit(ConsFuncDefNode nod) throws SyntaxError {
		if (!classStack.get(classStack.size() - 1).equals(nod.id)) throw new WrongNameConsFunc(nod.loc);
		visitChild(nod);
	}
	@Override void visit(IfStatNode nod) throws SyntaxError {
		visitChild(nod);
		ExprNode ifCond = (ExprNode) nod.sons.get(0);
		if (!ifCond.type.equalsSingleType("bool")) throw new NotConditionExpr(ifCond.loc);
	}
	@Override void visit(ForStatNode nod) throws SyntaxError {
		++iterNum;
		visitChild(nod);
		--iterNum;
		ExprNode condExpr = (ExprNode) nod.sons.get(1);
		if (!(condExpr instanceof EmptyExprNode) && !condExpr.type.equalsSingleType("bool")) throw new NotConditionExpr(condExpr.loc);
	}
	@Override void visit(WhileStatNode nod) throws SyntaxError {
		++iterNum;
		visitChild(nod);
		--iterNum;
		ExprNode condExpr = (ExprNode) nod.sons.get(0);
		if (!condExpr.type.equalsSingleType("bool")) throw new NotConditionExpr (condExpr.loc);
	}
	@Override void visit(BrkStatNode nod) throws SyntaxError {
		if (iterNum == 0) throw new BrkOutIterStat (nod.loc);
	}
	@Override void visit(CtnStatNode nod) throws SyntaxError {
		if (iterNum == 0) throw new CntOutIterStat (nod.loc);
	}
	@Override void visit(VarDefStatNode nod) throws SyntaxError {
		if (!checkTypeEntity(nod.type)) throw new NoDefinedTypeError(nod.loc);
		visitChild(nod);
		if (!nod.sons.isEmpty()) nod.type = nod.sons.get(0).type;
	}
	@Override void visit(BinaryExprNode nod) throws SyntaxError {
		visitChild(nod);
		ExprNode left = (ExprNode) nod.sons.get(0);
		ExprNode right = (ExprNode) nod.sons.get(1);
		if (!left.type.equals(right.type)) throw new NoCastExpr(nod.loc);
		OpType op = OpType.belongsTo(nod.id);
		if (op instanceof AssignOpType) {
			if (!checkLeftValue(left)) throw new NotLeftValue(left.loc);
			nod.type = TypeRef.buildTypeRef("void");
		} if (op instanceof RelativeOpType) {
			nod.type = TypeRef.buildTypeRef("bool");
		} else {
			nod.type = left.type;
		}
	}
	@Override void visit(LeftUnaryExprNode nod) throws SyntaxError {
		visitChild(nod);
		ExprNode expr = (ExprNode) nod.sons.get(0);
		if (!checkLeftValue(expr)) throw new NotLeftValue(expr.loc);
		if (!(expr.type instanceof IntTypeRef) && !(expr.type instanceof BoolTypeRef)) throw new NoCastExpr(nod.loc);
	}
	@Override void visit(RightUnaryExprNode nod) throws SyntaxError {
		visitChild(nod);
		ExprNode expr = (ExprNode) nod.sons.get(0);
		if (!checkLeftValue(expr)) throw new NotLeftValue(expr.loc);
		if (!(expr.type instanceof IntTypeRef) && !(expr.type instanceof BoolTypeRef)) throw new NoCastExpr(nod.loc);
	}
	@Override void visit(NewExprNode nod) throws SyntaxError {
//		System.out.println(nod.type.typeId + " " + nod.type.dimension);
		if (!checkTypeEntity(nod.type)) throw new NoDefinedTypeError(nod.loc);
		visitChild(nod);
	}
	@Override void visit(FuncExprNode nod) throws SyntaxError {
//		System.out.println(nod.id);
		visitChild(nod);
		TypeRef tmp = genScope.matchVarName(nod.id, nod.loc, genScope);
		if (!(tmp instanceof FuncTypeRef)) throw new NoDefinedTypeError(nod.loc);
		if (!((FuncTypeRef) tmp).matchForm(nod)) throw new NoDefinedTypeError(nod.loc);
		nod.type = ((FuncTypeRef) tmp).retType;
	}
	@Override void visit(ArrExprNode nod) throws SyntaxError {
		visitChild(nod);
		Node pointer = nod.sons.get(0);
		if (!(pointer.type instanceof ArrayTypeRef)) throw new NoCastExpr(nod.loc);
		int dim1 = ((ArrayTypeRef) pointer.type).dimension;
		int dim2 = nod.sons.size() - 1;
		if (dim1 < dim2) throw new NullPointer (nod.loc);
		int dim = dim1 - dim2;
		if (dim > 0) nod.type = new ArrayTypeRef(((ArrayTypeRef) pointer.type).getSimpleRef().typeId, dim);
		else nod.type = ((ArrayTypeRef) pointer.type).getSimpleRef();
	}
	@Override void visit(ObjAccExprNode nod) throws SyntaxError {
		try {
			visitChild(nod);
		} catch(SyntaxError e) {}
		Node sonNode = nod.sons.get(0);
		Node objNode = nod.sons.get(1);
		if (!(sonNode.type instanceof ClassTypeRef)) throw new NoCastExpr(nod.loc);
		ClassDefTypeRef tmp = (ClassDefTypeRef) genScope.entities.get(((ClassTypeRef) sonNode.type).typeId);
		nod.type = tmp.checkObj(objNode);
	}
	@Override void visit(VarExprNode nod) throws SyntaxError {
		nod.type = nod.belongTo.matchVarName(nod.id, nod.loc, genScope);
	}
}
