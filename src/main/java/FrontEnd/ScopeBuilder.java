package FrontEnd;

import GeneralDataStructure.ScopeClass.*;
import GeneralDataStructure.TypeSystem.*;

public class ScopeBuilder {
	public GeneralScope<TypeRef> genScope = new GeneralScope<>(null, "");
	void initInternalType() throws SyntaxError {
		// build string class
		ClassDefTypeRef stringType = new ClassDefTypeRef();
		stringType.insertObj("length", new FuncTypeRef(new IntTypeRef()));
		stringType.insertObj("substring", new FuncTypeRef(new StringTypeRef(), new IntTypeRef(), new IntTypeRef()));
		stringType.insertObj("parseInt", new FuncTypeRef(new IntTypeRef()));
		stringType.insertObj("ord", new FuncTypeRef(new IntTypeRef(), new IntTypeRef()));
		genScope.addItem("string", stringType);
		// build void print(string str);
		FuncTypeRef printRef = new FuncTypeRef(new VoidTypeRef(), new StringTypeRef());
		genScope.addItem("print", printRef);
		// build void println(string str);
		FuncTypeRef printlnRef = new FuncTypeRef(new VoidTypeRef(), new StringTypeRef());
		genScope.addItem("println", printlnRef);
		// build string getString();
		FuncTypeRef getStringFunc = new FuncTypeRef(new StringTypeRef());
		genScope.addItem("getString", getStringFunc);
		// build int getInt();
		FuncTypeRef getIntFunc = new FuncTypeRef(new IntTypeRef());
		genScope.addItem("getInt", getIntFunc);
		// build string toString(int i)
		FuncTypeRef toStringFunc = new FuncTypeRef(new StringTypeRef(), new IntTypeRef());
		genScope.addItem("toString", toStringFunc);
	}
	public void codeResolver(Node astRoot) throws SyntaxError {
		initInternalType();
		specialScopeResolver(genScope, astRoot);
		TypeRef type = genScope.findItem("main");
		if (!(type instanceof FuncTypeRef) || !((FuncTypeRef) type).getReturnType().equalsSingleType("int")) throw new NoMainFuncError(new Location(0, 0));
	}
	/*
	* for the whole funcs or class
	* */
	void specialScopeResolver(SpecialScope<TypeRef> curScope, Node curNode) throws SyntaxError {
		curNode.belongTo = curScope;
		for (int i = 0; i < curNode.sons.size(); ++i) {
			Node node = curNode.sons.get(i);
			if (node instanceof ClassDefNode) {
				ClassDefNode tmpNode = (ClassDefNode) node;
				ClassScope<TypeRef> sonScope = Scope.newClassScope(curScope);
				specialScopeResolver(sonScope, tmpNode);
				ClassDefTypeRef classType = new ClassDefTypeRef(sonScope.table.toHashMap());
				if (!curScope.addItem(tmpNode.id, classType)) throw new ReDefinedError(tmpNode.loc);
			} else if (node instanceof FuncDefNode) {
				FuncDefNode tmpNode = (FuncDefNode) node;
				node.belongTo = curScope;
				FuncTypeRef func = new FuncTypeRef((VarTypeRef) tmpNode.type);
				LocalScope<TypeRef> sonScope = Scope.newLocalScope(curScope);
				// insert the parameters
				for (int j = 0; j < tmpNode.sons.size() - 1; ++j) {
					VarDefNode tmpSonNode = (VarDefNode) tmpNode.sons.get(j);
					tmpSonNode.belongTo = sonScope;
//					if (!sonScope.insert(tmpSonNode.id, (VarTypeRef) tmpSonNode.type)) throw new ReDefinedError(tmpSonNode.loc);
					func.insert((VarTypeRef) tmpSonNode.type);
				}
				if (!curScope.addItem(tmpNode.id, func)) throw new ReDefinedError(tmpNode.loc);
				// insert the function body
				localResolver(sonScope, tmpNode.sons.get(tmpNode.sons.size() - 1)); // function body
			} else if (node instanceof VarDefNode) {
				VarDefNode tmpNode = (VarDefNode) node;
				specialScopeResolver(curScope, tmpNode);
				if (curScope instanceof ClassScope && !curScope.addItem(tmpNode.id, tmpNode.type)) throw new ReDefinedError(tmpNode.loc);
			} else specialScopeResolver(curScope, node);
		}
	}
	/*
	* for the others
	* */
	void localResolver(LocalScope<TypeRef> curScope, Node curNode) throws SyntaxError {
		boolean forceNewScope = false;
		if (curNode instanceof ForStatNode
				|| curNode instanceof WhileStatNode
				|| curNode instanceof IfStatNode
				) forceNewScope = true;
		curNode.belongTo = curScope;
		for (int i = 0; i < curNode.sons.size(); ++i) {
			Node node = curNode.sons.get(i);
			if (node instanceof CompStatNode) {
				CompStatNode tmpNode = (CompStatNode) node;
				LocalScope<TypeRef> sonScope = Scope.newLocalScope(curScope);
				localResolver(sonScope, tmpNode);
			} else if (forceNewScope && (node instanceof StatNode)
					|| (curNode instanceof IfElseStatNode) && (i == curNode.sons.size() - 1)) {
				LocalScope<TypeRef> sonScope = Scope.newLocalScope(curScope);
				localResolver(sonScope, node);
			} else {
				localResolver(curScope, node);
			}
		}
	}

	/*
	* There is three things to do:
	* Matching the name of each var to their definition.
	* Matching the type of each var to their definition.
	* Check the validity of expressions
	* */
}
