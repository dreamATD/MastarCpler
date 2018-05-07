package FrontEnd;

import java.util.ArrayList;
import java.util.List;

import GeneralDataStructure.ScopeClass.Scope;
import GeneralDataStructure.TypeSystem.TypeRef;

public abstract class Node {
	String id;
	TypeRef type;
	Location loc;
	List<Node> sons;
	Scope<TypeRef> belongTo;
	String reg, inClass;
	Node() {
		sons = new ArrayList<Node>();
		type = TypeRef.buildTypeRef("void");
		reg = inClass = null;
	}
	void print(String indentation) {}
	public void accept(AstVisitor visitor) throws Exception {
		visitor.visit(this);
	}
}
class CodeNode extends Node {
//	List<ClassDefNode> classDefList;
//	List<FuncDefNode> funcDefList;
//	List<VarDefStatNode> varDefList;
	CodeNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<CodeNode> id: " + id + " type: " + type.toString());
	}
}
class VarDefNode extends Node {
//	ExprNode initVal;
	VarDefNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<VarDefNode> id: " + id + " type: " + type.toString());
	}
}
class ClassDefNode extends Node {
//	List<FuncDefNode> funcObjList;
//	List<VarDefNode> varObjList;
//	ConsFuncDefNode consFunc;
	ClassDefNode () {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<ClassDefNode> id: " + id + " type: " + type.toString());
	}
}
class FuncDefNode extends Node {
//	List<VarDefNode> paramList;
//	CompStatNode funcBody;
	FuncDefNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<FuncDefNode> id: " + id + " type: " + type.toString());
	}
}
class ConsFuncDefNode extends FuncDefNode {
	ConsFuncDefNode(){
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<ConsFuncDefNode> id: " + id + " type: " + type.toString());
	}
}

abstract class StatNode extends Node {
	StatNode(){
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<StatNode> id: " + id + " type: " + type.toString());
	}
}
class CompStatNode extends StatNode {
//	List<StatNode> statList;
	CompStatNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<CompStatNode> id: " + id + " type: " + type.toString());
	}
}
class ExprStatNode extends StatNode {
//	ExprNode expr;
	ExprStatNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<ExprStatNode> id: " + id + " type: " + type.toString());
	}
}
abstract class CondStatNode extends StatNode {
	CondStatNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<CondStatNode> id: " + id + " type: " + type.toString());
	}
}
class IfStatNode extends CondStatNode {
//	ExprNode ifCond;
//	StatNode ifStat;
	IfStatNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<IfStatNode> id: " + id + " type: " + type.toString());
	}
}
class IfElseStatNode extends CondStatNode {
//	IfStatNode ifStat;
//	List<IfStatNode> ifElseStat;
//	StatNode elseStat;
	IfElseStatNode(){
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<IfElseStatNode> id: " + id + " type: " + type.toString());
	}
}
abstract class IterStatNode extends StatNode {
	IterStatNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<IterStatNode> id: " + id + " type: " + type.toString());
	}
}
class ForStatNode extends IterStatNode {
//	ExprNode initExpr;
//	ExprNode condExpr;
//	ExprNode loopExpr;
//	StatNode forBody;
	ForStatNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<ForStatNode> id: " + id + " type: " + type.toString());
	}
}
class WhileStatNode extends IterStatNode {
//	ExprNode condExpr;
//	CompStatNode whileBody;
	WhileStatNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<WhileStatNode> id: " + id + " type: " + type.toString());
	}
}
abstract class JumpStatNode extends StatNode {
	JumpStatNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<JumpStatNode> id: " + id + " type: " + type.toString());
	}
}
class RetStatNode extends JumpStatNode {
//	ExprNode retExpr;
	RetStatNode(){
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<RetStatNode> id: " + id + " type: " + type.toString());
	}
}
class BrkStatNode extends JumpStatNode {
	BrkStatNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<BrkStatNode> id: " + id + " type: " + type.toString());
	}
}
class CtnStatNode extends JumpStatNode {
	CtnStatNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<CtnStatNode> id: " + id + " type: " + type.toString());
	}
}
class NullStatNode extends StatNode {
	NullStatNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<NullStatNode> id: " + id + " type: " + type.toString());
	}
}
class VarDefStatNode extends StatNode {
//	List<VarDefNode> varDefList;
	VarDefStatNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<VarDefStatNode> id: " + id + " type: " + type.toString());
	}
}
abstract class ExprNode extends Node {
	ExprNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<IfStatNode> id: " + id + " type: " + type.toString());
	}
}
class EmptyExprNode extends ExprNode {
	EmptyExprNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<EmptyExprNode> id: " + id + " type: " + type.toString());
	}
}
class BinaryExprNode extends ExprNode {
//	ExprNode left;
//	ExprNode right;
	BinaryExprNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<BinaryExprNode> id: " + id + " type: " + type.toString());
	}
}
class UnaryExprNode extends ExprNode {
	UnaryExprNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<UnaryExprNode> id: " + id + " type: " + type.toString());
	}
}
class LeftUnaryExprNode extends UnaryExprNode {
//	ExprNode expr;
	LeftUnaryExprNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<LeftUnaryExprNode> id: " + id + " type: " + type.toString());
	}
}
class RightUnaryExprNode extends UnaryExprNode {
//	ExprNode expr;
	RightUnaryExprNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<RightUnaryExprNode> id: " + id + " type: " + type.toString());
	}
}
class NewExprNode extends ExprNode {
//	ArrayTypeRef type;
//	List<ExprNode> DefinedExprList;
	NewExprNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<NewExprNode> id: " + id + " type: " + type.toString());
	}
}
class TypeExprNode extends ExprNode {
	TypeExprNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<TypeExprNode> id: " + id + " type: " + type.toString());
	}
}
abstract class PrimExprNode extends ExprNode {
	PrimExprNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<PrimExprNode> id: " + id + " type: " + type.toString());
	}
}
class FuncExprNode extends PrimExprNode {
//	List<ExprNode> paramList;
	FuncExprNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<FuncExprNode> id: " + id + " type: " + type.toString());
	}
}
class ArrExprNode extends PrimExprNode {
//	ExprNode arrPointer;
//	ExprNode index;
	ArrExprNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<ArrExprNode> id: " + id + " type: " + type.toString());
	}
}
class ObjAccExprNode extends PrimExprNode {
//	ExprNode classExpr;
//	ExprNode objectExpr;
	ObjAccExprNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<ObjAccExprNode> id: " + id + " type: " + type.toString());
	}
}
class VarExprNode extends PrimExprNode {
	VarExprNode(){
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<VarExprNode> id: " + id + " type: " + type.toString());
	}
}
abstract class LiteralNode extends ExprNode {
	LiteralNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<LiteralNode> id: " + id + " type: " + type.toString());
	}
}
abstract class ArithmeticLiteralNode extends LiteralNode {
	ArithmeticLiteralNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<ArithmeticLiteralNode> id: " + id + " type: " + type.toString());
	}
}
class IntLiteralNode extends ArithmeticLiteralNode {
	IntLiteralNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<IntLiteralNode> id: " + id + " type: " + type.toString());
	}
}
class LogicalLiteralNode extends ArithmeticLiteralNode {
	LogicalLiteralNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<LogicalLiteralNode> id: " + id + " type: " + type.toString());
	}
}
class NullLiteralNode extends LiteralNode {
	NullLiteralNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<NullLiteralNode> id: " + id + " type: " + type.toString());
	}
}
class StringLiteralNode extends LiteralNode {
	StringLiteralNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<StringLiteralNode> id: " + id + " type: " + type.toString());
	}
}
