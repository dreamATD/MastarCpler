import java.util.ArrayList;
import java.util.List;

public class Node {
	String id;
	TypeRef type;
	Location loc;
	List<Node> sons;
	Scope belongTo;
	Node() {
		sons = new ArrayList<Node>();
		type = TypeRef.buildTypeRef("void");
	}
	Node (String id2, TypeRef type2) {
		id = id2;
		type = type2;
		sons = new ArrayList<Node>();
	}
	void print(String indentation) {}
}
class CodeNode extends Node {
//	List<ClassDefNode> classDefList;
//	List<FuncDefNode> funcDefList;
//	List<VarDefStatNode> varDefList;
	CodeNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<CodeNode> id: " + id + " type: " + type.typeId);
	}
}
class VarDefNode extends Node{
//	ExprNode initVal;
	VarDefNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<VarDefNode> id: " + id + " type: " + type.typeId);
	}
}
class ClassDefNode extends Node{
//	List<FuncDefNode> funcObjList;
//	List<VarDefNode> varObjList;
//	ConsFuncDefNode consFunc;
	ClassDefNode () {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<ClassDefNode> id: " + id + " type: " + type.typeId);
	}
}
class FuncDefNode extends Node{
//	List<VarDefNode> paramList;
//	CompStatNode funcBody;
	FuncDefNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<FuncDefNode> id: " + id + " type: " + type.typeId);
	}
}
class ConsFuncDefNode extends FuncDefNode {
	ConsFuncDefNode(){
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<ConsFuncDefNode> id: " + id + " type: " + type.typeId);
	}
}

class StatNode extends Node {
	StatNode(){
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<StatNode> id: " + id + " type: " + type.typeId);
	}
}
class CompStatNode extends StatNode {
//	List<StatNode> statList;
	CompStatNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<CompStatNode> id: " + id + " type: " + type.typeId);
	}
}
class ExprStatNode extends StatNode {
//	ExprNode expr;
	ExprStatNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<ExprStatNode> id: " + id + " type: " + type.typeId);
	}
}
class CondStatNode extends StatNode {
	CondStatNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<CondStatNode> id: " + id + " type: " + type.typeId);
	}
}
class IfStatNode extends CondStatNode {
//	ExprNode ifCond;
//	StatNode ifStat;
	IfStatNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<IfStatNode> id: " + id + " type: " + type.typeId);
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
		System.out.println(indentation + "<IfElseStatNode> id: " + id + " type: " + type.typeId);
	}
}
class IterStatNode extends StatNode {
	IterStatNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<IterStatNode> id: " + id + " type: " + type.typeId);
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
		System.out.println(indentation + "<ForStatNode> id: " + id + " type: " + type.typeId);
	}
}
class WhileStatNode extends IterStatNode {
//	ExprNode condExpr;
//	CompStatNode whileBody;
	WhileStatNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<WhileStatNode> id: " + id + " type: " + type.typeId);
	}
}
class JumpStatNode extends StatNode {
	JumpStatNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<JumpStatNode> id: " + id + " type: " + type.typeId);
	}
}
class RetStatNode extends JumpStatNode {
//	ExprNode retExpr;
	RetStatNode(){
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<RetStatNode> id: " + id + " type: " + type.typeId);
	}
}
class BrkStatNode extends JumpStatNode {
	BrkStatNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<BrkStatNode> id: " + id + " type: " + type.typeId);
	}
}
class CtnStatNode extends JumpStatNode {
	CtnStatNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<CtnStatNode> id: " + id + " type: " + type.typeId);
	}
}
class NullStatNode extends StatNode {
	NullStatNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<NullStatNode> id: " + id + " type: " + type.typeId);
	}
}
class VarDefStatNode extends StatNode {
//	List<VarDefNode> varDefList;
	VarDefStatNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<VarDefStatNode> id: " + id + " type: " + type.typeId);
	}
}
class ExprNode extends Node {
	ExprNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<IfStatNode> id: " + id + " type: " + type.typeId);
	}
}
class EmptyExprNode extends ExprNode{
	EmptyExprNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<EmptyExprNode> id: " + id + " type: " + type.typeId);
	}
}
class BinaryExprNode extends ExprNode {
//	ExprNode left;
//	ExprNode right;
	BinaryExprNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<BinaryExprNode> id: " + id + " type: " + type.typeId);
	}
}
class UnaryExprNode extends ExprNode {
	UnaryExprNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<UnaryExprNode> id: " + id + " type: " + type.typeId);
	}
}
class LeftUnaryExprNode extends UnaryExprNode {
//	ExprNode expr;
	LeftUnaryExprNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<LeftUnaryExprNode> id: " + id + " type: " + type.typeId);
	}
}
class RightUnaryExprNode extends UnaryExprNode {
//	ExprNode expr;
	RightUnaryExprNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<RightUnaryExprNode> id: " + id + " type: " + type.typeId);
	}
}
class NewExprNode extends ExprNode {
//	ArrayTypeRef type;
//	List<ExprNode> DefinedExprList;
	NewExprNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<NewExprNode> id: " + id + " type: " + type.typeId);
	}
}
class PrimExprNode extends ExprNode {
	PrimExprNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<PrimExprNode> id: " + id + " type: " + type.typeId);
	}
}
class FuncExprNode extends PrimExprNode {
//	List<ExprNode> paramList;
	FuncExprNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<FuncExprNode> id: " + id + " type: " + type.typeId);
	}
}
class ArrExprNode extends PrimExprNode {
//	ExprNode arrPointer;
//	List<ExprNode> dimList;
	ArrExprNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<ArrExprNode> id: " + id + " type: " + type.typeId);
	}
}
class ObjAccExprNode extends PrimExprNode {
//	ExprNode classExpr;
//	ExprNode objectExpr;
	ObjAccExprNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<ObjAccExprNode> id: " + id + " type: " + type.typeId);
	}
}
class VarExprNode extends PrimExprNode {
	VarExprNode(){
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<VarExprNode> id: " + id + " type: " + type.typeId);
	}
}
class LiteralNode extends ExprNode {
	LiteralNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<LiteralNode> id: " + id + " type: " + type.typeId);
	}
}
abstract class ArithmeticLiteralNode extends LiteralNode {
	ArithmeticLiteralNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<ArithmeticLiteralNode> id: " + id + " type: " + type.typeId);
	}
}
class IntLiteralNode extends ArithmeticLiteralNode {
	IntLiteralNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<IntLiteralNode> id: " + id + " type: " + type.typeId);
	}
}
class LogicalLiteralNode extends ArithmeticLiteralNode {
	LogicalLiteralNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<LogicalLiteralNode> id: " + id + " type: " + type.typeId);
	}
}
class NullLiteralNode extends LiteralNode {
	NullLiteralNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<NullLiteralNode> id: " + id + " type: " + type.typeId);
	}
}
class StringLiteralNode extends LiteralNode {
	StringLiteralNode() {
		super();
	}
	void print(String indentation) {
		System.out.println(indentation + "<StringLiteralNode> id: " + id + " type: " + type.typeId);
	}
}
