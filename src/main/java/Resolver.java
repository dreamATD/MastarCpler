import jdk.nashorn.internal.runtime.regexp.joni.ast.StateNode;

import java.lang.instrument.ClassDefinition;
import java.util.List;

public class Resolver {
	GeneralScope genScope = new GeneralScope(null);
	void initInternalType() throws SyntaxError {
		// build string class
		TypeDef stringType = new TypeDef(new StringTypeRef());
		stringType.insertObj("length", new DefinedFunc("length", new TypeDef(new IntTypeRef()), new Location(0, 0)));
		{
			DefinedVariable varLeft = new DefinedVariable("left", new TypeDef(new IntTypeRef()), new Location(0, 0));
			DefinedVariable varRight = new DefinedVariable("right", new TypeDef(new IntTypeRef()), new Location(0, 0));
			stringType.insertObj("substring", new DefinedFunc("substring", new TypeDef(new StringTypeRef(), varLeft, varRight), new Location(0, 0)));
		}
		stringType.insertObj("parseInt", new DefinedFunc("parseInt", new TypeDef( new IntTypeRef() ), new Location(0, 0)));
		{
			DefinedVariable varPos = new DefinedVariable("pos", new TypeDef(new IntTypeRef()), new Location(0, 0));
			stringType.insertObj("ord", new DefinedFunc("ord", new TypeDef(new IntTypeRef(), varPos), new Location(0, 0)));
		}
		genScope.insert("string", new DefinedClass("string", stringType, new Location(0, 0)));
		// build void print(string str);
		TypeDef printFunc = new TypeDef(new VoidTypeRef());
		{
			DefinedVariable str = new DefinedVariable("str", new TypeDef(new StringTypeRef()), new Location(0, 0));
			printFunc.insertObj("str", str);
		}
		genScope.insert("print", new DefinedFunc("print", printFunc, new Location(0, 0)));
		// build void println(string str);
		TypeDef printlnFunc = new TypeDef(new VoidTypeRef());
		{
			DefinedVariable str = new DefinedVariable("str", new TypeDef(new StringTypeRef()), new Location(0, 0));
			printlnFunc.insertObj("str", str);
		}
		genScope.insert("println", new DefinedFunc("println", printlnFunc, new Location(0, 0)));
		// build string getString();
		TypeDef getStringFunc = new TypeDef(new StringTypeRef());
		genScope.insert("getString", new DefinedFunc("getString", getStringFunc, new Location(0, 0)));
		// build int getInt();
		TypeDef getIntFunc = new TypeDef(new IntTypeRef());
		genScope.insert("getInt", new DefinedFunc("getInt", getIntFunc, new Location(0, 0)));
		// build string toString(int i)
		TypeDef toStringFunc = new TypeDef(new StringTypeRef());
		{
			DefinedVariable i = new DefinedVariable("i", new TypeDef(new IntTypeRef()), new Location(0, 0));
			toStringFunc.insertObj("i", i);
		}
		genScope.insert("toString", new DefinedFunc("toString", toStringFunc, new Location(0, 0)));
	}
	void codeResolver(Node astRoot) throws SyntaxError {
		initInternalType();
		generalResolver(genScope, astRoot);
	}
	/*
	* for the whole code or class
	* */
	public void generalResolver(GeneralScope curScope, Node curNode) throws SyntaxError {
		curNode.belongTo = curScope;
		for (int i = 0; i < curNode.sons.size(); ++i) {
			Node node = curNode.sons.get(i);
			if (node instanceof ClassDefNode) {
				ClassDefNode tmpNode = (ClassDefNode) node;
				curScope.insert(node.id, new DefinedClass(tmpNode));
				ClassScope sonScope = Scope.newClassScope(curScope);
				generalResolver(sonScope, tmpNode);
			} else if (node instanceof FuncDefNode) {
				FuncDefNode tmpNode = (FuncDefNode) node;
				curScope.insert(tmpNode.id, new DefinedFunc(tmpNode));

				LocalScope sonScope = Scope.newLocalScope(curScope);
				// insert the parameters
				for (int j = 0; j < tmpNode.sons.size() - 1; ++j) {
					VarDefNode tmpSonNode = (VarDefNode) tmpNode.sons.get(j);
					tmpSonNode.belongTo = sonScope;
					sonScope.insert(tmpSonNode.id, new DefinedVariable(tmpSonNode));
				}
				// insert the function body
				localResolver(sonScope, tmpNode.sons.get(node.sons.size() - 1)); // function body
			} else {
				VarDefStatNode tmpNode = (VarDefStatNode) node;
				tmpNode.belongTo = curScope;
				List<DefinedVariable> obj = DefinedVariable.splitAndConstr(tmpNode);
				for (int j = 0; j < obj.size(); ++j) {
					DefinedVariable var = obj.get(j);
					curScope.insert(var.id, var);
					tmpNode.sons.get(j).belongTo = curScope;
				}
			}
		}
	}
	public void localResolver(LocalScope curScope, Node curNode) throws SyntaxError {
		boolean forceNewScope = false;
		if (curNode instanceof ForStatNode
				|| curNode instanceof WhileStatNode
				|| curNode instanceof IfStatNode
				) forceNewScope = true;
		for (int i = 0; i < curNode.sons.size(); ++i) {
			Node node = curNode.sons.get(i);
			if (node instanceof CompStatNode) {
				CompStatNode tmpNode = (CompStatNode) node;
				LocalScope sonScope = Scope.newLocalScope(curScope);
				localResolver(sonScope, tmpNode);
			} else if (node instanceof VarDefStatNode) {
				VarDefStatNode tmpNode = (VarDefStatNode) node;
				List<DefinedVariable> obj = DefinedVariable.splitAndConstr(tmpNode);
				for (int j = 0; j < obj.size(); ++j) {
					DefinedVariable var = obj.get(j);
					curScope.insert(var.id, var);
				}
			} else if (forceNewScope && (node instanceof StatNode)
					|| (curNode instanceof IfElseStatNode) && (i == curNode.sons.size() - 1)) {
				LocalScope sonScope = Scope.newLocalScope(curScope);
				localResolver(sonScope, node);
			} else {
				localResolver(curScope, node);
			}
		}
	}
}
