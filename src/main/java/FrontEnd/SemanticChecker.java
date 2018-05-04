package FrontEnd;

import GeneralDataStructure.ScopeClass.GeneralScope;
import GeneralDataStructure.ScopeClass.Scope;
import GeneralDataStructure.TypeSystem.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class SemanticChecker extends AstVisitor {
	GeneralScope<TypeRef> genScope;
	int iterNum;
	List<String> classStack;
	TypeRef retType;
	public SemanticChecker(GeneralScope<TypeRef> g) {
		genScope = g;
		iterNum = 0;
		classStack = new ArrayList<String>();
		retType = null;
	}
	boolean checkTypeEntity(TypeRef type) {
		if (type instanceof ArrayTypeRef) type = ((ArrayTypeRef) type).getSimpleRef();
		if (type instanceof ClassTypeRef && genScope.findItem(((ClassTypeRef) type).typeId) == null) return false;
		return true;
	}
	boolean checkLeftValue(ExprNode nod) {
		if (nod instanceof VarExprNode) return true;
		if (nod instanceof ObjAccExprNode && checkLeftValue((ExprNode) nod.sons.get(1))) return true;
		if (nod instanceof ArrExprNode) return true;
		return false;
	}
	TypeRef arrCheckNode(ArrayTypeRef arr, ArrExprNode nod) throws Exception {
		Node son = nod.sons.get(0);
		VarTypeRef tp = arr.getInnerType();
		if (tp instanceof SingleTypeRef) {
			if (!(son instanceof VarExprNode)) throw new NoCastExpr(nod.loc);
			return tp;
		}
		else if (!(son instanceof ArrExprNode)) throw new NoCastExpr(nod.loc);
		else return arrCheckNode((ArrayTypeRef) tp, (ArrExprNode) son);
	}
	TypeRef classCheckObj(ClassDefTypeRef classTp, Node nod) throws Exception {
		if (!classTp.containsEntity(nod.id)) throw new NoDefinedVarError(nod.loc);
		TypeRef type = classTp.getEntity(nod.id);
		if (nod instanceof FuncExprNode) {
			if (!(type instanceof FuncTypeRef)) throw new NoDefinedVarError(nod.loc);
			if (!matchFuncForm((FuncTypeRef) type, (FuncExprNode) nod)) throw new NoDefinedVarError(nod.loc);
			return ((FuncTypeRef) type).getReturnType();
		} else if (nod instanceof ArrExprNode) {
			if (!(type instanceof ArrayTypeRef)) throw new NoCastExpr(nod.loc);
			return arrCheckNode(((ArrayTypeRef) type), (ArrExprNode) nod);
		} else {
			if (!(type instanceof VarTypeRef)) throw new NoDefinedVarError(nod.loc);
			return type;
		}
	}
	boolean matchFuncForm(FuncTypeRef fun, FuncExprNode nod) {
		int cnt = fun.getParamsCnt();
		if (nod.sons.size() != cnt) return false;
		for (int i = 0; i < cnt; ++i) {
			Node son = nod.sons.get(i);
			VarTypeRef tp = fun.getParam(i);
			if (!(son.type instanceof NullTypeRef && tp instanceof ClassTypeRef) && !son.type.equals(tp)) return false;
		}
		return true;
	}
	TypeRef arrCheckObj(ArrayTypeRef tp, Node nod) throws Exception {
		if (nod.id.equals("size") && nod.sons.size() == 1) return new IntTypeRef();
		throw new NoDefinedTypeError(nod.loc);
	}
	@Override void visit(VarDefNode nod) throws Exception {
		if (nod.type instanceof VoidTypeRef) throw new VoidDefVarError(nod.loc);
		if (!checkTypeEntity(nod.type)) throw new NoDefinedTypeError(nod.loc);
		visitChild(nod);
		if (!nod.sons.isEmpty()) {
			ExprNode init = (ExprNode) nod.sons.get(0);
			if (!nod.type.equals(init.type)) {
				OpType op = new AssignOpType("=");
				if (((AssignOpType) op).checkExpr(nod.type,  init.type));
				else throw new NoCastExpr(init.loc);
			}
		}
		Scope<TypeRef> curScope = nod.belongTo;
		if (!curScope.addItem(nod.id, nod.type)) throw new ReDefinedError(nod.loc);
	}
	@Override void visit(ClassDefNode nod) throws Exception {
		classStack.add(nod.id);
		for (int i = 0; i < nod.sons.size(); ++i) {
			Node son = nod.sons.get(i);
			if (!(son instanceof VarDefStatNode)) visit(son);
			else if (son.type instanceof ClassTypeRef) {
				if (genScope.findItem(((ClassTypeRef) son.type).typeId) == null) throw new NoDefinedTypeError(son.loc);
			}
		}
		classStack.remove(classStack.size() - 1);
	}
	@Override void visit(FuncDefNode nod) throws Exception {
		if (!checkTypeEntity(nod.type)) throw new NoDefinedTypeError(nod.loc);
		retType = nod.type;
		visitChild(nod);
		retType = null;
	}
	@Override void visit(ConsFuncDefNode nod) throws Exception {
		if (!classStack.get(classStack.size() - 1).equals(nod.id)) throw new WrongNameConsFunc(nod.loc);
		retType = nod.type;
		visitChild(nod);
		retType = null;
	}
	@Override void visit(IfStatNode nod) throws Exception {
		visitChild(nod);
		ExprNode ifCond = (ExprNode) nod.sons.get(0);
		if (!ifCond.type.equalsSingleType("bool")) throw new NotConditionExpr(ifCond.loc);
	}
	@Override void visit(ForStatNode nod) throws Exception {
		++iterNum;
		visitChild(nod);
		--iterNum;
		ExprNode condExpr = (ExprNode) nod.sons.get(1);
		if (!(condExpr instanceof EmptyExprNode) && !condExpr.type.equalsSingleType("bool")) throw new NotConditionExpr(condExpr.loc);
	}
	@Override void visit(WhileStatNode nod) throws Exception {
		++iterNum;
		visitChild(nod);
		--iterNum;
		ExprNode condExpr = (ExprNode) nod.sons.get(0);
		if (!condExpr.type.equalsSingleType("bool")) throw new NotConditionExpr (condExpr.loc);
	}
	@Override void visit(BrkStatNode nod) throws Exception {
		if (iterNum == 0) throw new BrkOutIterStat (nod.loc);
	}
	@Override void visit(CtnStatNode nod) throws Exception {
		if (iterNum == 0) throw new CntOutIterStat (nod.loc);
	}
	@Override void visit(RetStatNode nod) throws Exception {
		if (retType.equalsSingleType("void")) {
			if (nod.sons.size() > 0) throw new DisMatchedFuncReturn(nod.loc);
		} else {
			if (nod.sons.size() == 0) throw new DisMatchedFuncReturn(nod.loc);
			Node son = nod.sons.get(0);
			visitChild(nod);
			if (retType instanceof ArrayTypeRef || retType instanceof ClassTypeRef && !(retType instanceof StringTypeRef)) {
				if (!retType.equals(son.type) && !(son.type instanceof NullTypeRef)) throw new DisMatchedFuncReturn(nod.loc);
			} else {
				if (!retType.equals(son.type)) throw new DisMatchedFuncReturn(nod.loc);
			}
		}
	}
	@Override void visit(VarDefStatNode nod) throws Exception {
		visitChild(nod);
		if (!nod.sons.isEmpty()) nod.type = nod.sons.get(0).type;
	}
	@Override void visit(BinaryExprNode nod) throws Exception {
		visitChild(nod);
		ExprNode left = (ExprNode) nod.sons.get(0);
		ExprNode right = (ExprNode) nod.sons.get(1);
		OpType op = OpType.belongsTo(nod.id);
		if (!left.type.equals(right.type)) {
			if (op instanceof AssignOpType && ((AssignOpType) op).checkExpr(left.type, right.type));
			else if (op instanceof RelativeOpType && ((RelativeOpType) op).checkExpr(left.type, right.type));
			else throw new NoCastExpr(nod.loc);
		}
		if (op instanceof AssignOpType) {
			if (!checkLeftValue(left)) throw new NotLeftValue(left.loc);
			nod.type = TypeRef.buildTypeRef("void");
		} else if (op.containsType(left.type)) {
			if (op instanceof RelativeOpType) {
				nod.type = TypeRef.buildTypeRef("bool");
			} else {
				nod.type = left.type;
			}
		} else throw new NoDefinedOpError(nod.loc);
	}
	@Override void visit(LeftUnaryExprNode nod) throws Exception {
		visitChild(nod);
		ExprNode expr = (ExprNode) nod.sons.get(0);
		OpType op = OpType.belongsTo(nod.id);
		if (!checkLeftValue(expr) && op instanceof SelfPmOpType) throw new NotLeftValue(expr.loc);
		if (!op.containsType(expr.type)) throw new NoCastExpr(nod.loc);
		nod.type = expr.type;
	}
	@Override void visit(RightUnaryExprNode nod) throws Exception {
		visitChild(nod);
		ExprNode expr = (ExprNode) nod.sons.get(0);
		OpType op = OpType.belongsTo(nod.id);
		if (!checkLeftValue(expr) && op instanceof SelfPmOpType) throw new NotLeftValue(expr.loc);
		if (!op.containsType(expr.type)) throw new NoCastExpr(nod.loc);
		nod.type = expr.type;
	}
	@Override void visit(NewExprNode nod) throws Exception {
//		System.out.println(nod.type.typeId + " " + nod.type.dimension);
		visitChild(nod);
		nod.type = nod.sons.get(0).type;
	}
	@Override void visit(TypeExprNode nod) throws Exception {
		visitChild(nod);
		if (nod.sons.isEmpty()) {
			if (!checkTypeEntity(nod.type)) throw new NoDefinedTypeError(nod.loc);
		} else {
			nod.type = new ArrayTypeRef((VarTypeRef) nod.sons.get(0).type);
		}
	}
	@Override void visit(FuncExprNode nod) throws Exception {
//		System.out.println(nod.id);
		visitChild(nod);
		Pair<Scope<TypeRef>, TypeRef> ret = nod.belongTo.matchVarName(nod.id, genScope);
		if (ret == null) throw new NoDefinedTypeError(nod.loc);
		TypeRef tmp = ret.getValue();
		if (!(tmp instanceof FuncTypeRef)) throw new NoDefinedTypeError(nod.loc);
		if (!matchFuncForm((FuncTypeRef) tmp,nod)) throw new NoDefinedTypeError(nod.loc);
		nod.type = ((FuncTypeRef) tmp).getReturnType();
	}
	@Override void visit(ArrExprNode nod) throws Exception {
		visitChild(nod);
		Node pointer = nod.sons.get(0);
		if (!(pointer.type instanceof ArrayTypeRef)) throw new NullPointer(nod.loc);
		nod.type = ((ArrayTypeRef) pointer.type).getInnerType();
	}
	@Override void visit(ObjAccExprNode nod) throws Exception {
		try {
			visitChild(nod);
		} catch(Exception e) {}
		Node sonNode = nod.sons.get(0);
		Node objNode = nod.sons.get(1);
		if (sonNode.type instanceof ArrayTypeRef) {
			nod.type = arrCheckObj((ArrayTypeRef) sonNode.type, objNode);
			return;
		}
		if (!(sonNode.type instanceof ClassTypeRef)) throw new NoCastExpr(nod.loc);
		ClassDefTypeRef tmp = (ClassDefTypeRef) genScope.findItem(((ClassTypeRef) sonNode.type).typeId);
		nod.type = classCheckObj(tmp, objNode);
	}
	@Override void visit(VarExprNode nod) throws Exception {
		if (nod.id.equals("this") && classStack.size() > 0) {
			nod.type = new ClassTypeRef(classStack.get(classStack.size() - 1));
			return;
		}
		Pair<Scope<TypeRef>, TypeRef> ret = nod.belongTo.matchVarName(nod.id, genScope);
		if (ret == null) throw new NoDefinedVarError(nod.loc);
		nod.type = ret.getValue();
	}
}
