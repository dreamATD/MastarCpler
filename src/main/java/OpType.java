public abstract class OpType {
	static OpType belongsTo(String op) {
		if (op.equals("="))
			return new AssignOpType();
		if (op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/") || op.equals("%"))
			return new ArithmeticOpType();
		if (op.equals("<") || op.equals(">") || op.equals("==") || op.equals("!=") || op.equals("<=") || op.equals(">="))
			return new RelativeOpType();
		if (op.equals("&&") || op.equals("||") || op.equals("!"))
			return new LogicalOpType();
		if (op.equals("<<") || op.equals(">>") || op.equals("&") || op.equals("|") || op.equals("^") || op.equals("~"))
			return new BitOpType();
		if (op.equals("++") || op.equals("--"))
			return new SelfPmOpType();
		assert (false);
		return new FakeOpType();
	}
}
class ArithmeticOpType extends OpType  {

}
class RelativeOpType extends OpType {

}
class LogicalOpType extends OpType {

}
class BitOpType extends OpType {

}
class AssignOpType extends OpType {

}
class SelfPmOpType extends OpType {

}
class FakeOpType extends OpType {

}
