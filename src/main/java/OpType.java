import com.sun.org.apache.xpath.internal.operations.Bool;

public abstract class OpType {
	String op;
	OpType(String o) {
		op = o;
	}
	static OpType belongsTo(String o) {
		if (o.equals("="))
			return new AssignOpType(o);
		if (o.equals("+") || o.equals("-") || o.equals("*") || o.equals("/") || o.equals("%"))
			return new ArithmeticOpType(o);
		if (o.equals("<") || o.equals(">") || o.equals("==") || o.equals("!=") || o.equals("<=") || o.equals(">="))
			return new RelativeOpType(o);
		if (o.equals("&&") || o.equals("||") || o.equals("!"))
			return new LogicalOpType(o);
		if (o.equals("<<") || o.equals(">>") || o.equals("&") || o.equals("|") || o.equals("^") || o.equals("~"))
			return new BitOpType(o);
		if (o.equals("++") || o.equals("--"))
			return new SelfPmOpType(o);
		assert (false);
		return new FakeOpType(o);
	}
	abstract boolean containsType(TypeRef type);
}
class ArithmeticOpType extends OpType  {
	ArithmeticOpType(String o) {
		super(o);
	}
	@Override boolean containsType(TypeRef type) {
		return (type instanceof IntTypeRef) || (op.equals("+") && type instanceof StringTypeRef);
	}
}
class RelativeOpType extends OpType {
	RelativeOpType(String o) {
		super(o);
	}
	@Override boolean containsType(TypeRef type) {
		return (type instanceof IntTypeRef) || (type instanceof StringTypeRef);
	}
}
class LogicalOpType extends OpType {
	LogicalOpType(String o) {
		super(o);
	}
	@Override boolean containsType(TypeRef type) {
		return (type instanceof BoolTypeRef);
	}
}
class BitOpType extends OpType {
	BitOpType(String o) {
		super(o);
	}
	@Override boolean containsType(TypeRef type) {
		return (type instanceof IntTypeRef);
	}
}
class AssignOpType extends OpType {
	AssignOpType(String o) {
		super(o);
	}
	@Override boolean containsType(TypeRef type) {
		return (type instanceof VarTypeRef);
	}
}
class SelfPmOpType extends OpType {
	SelfPmOpType(String o) {
		super(o);
	}
	@Override boolean containsType(TypeRef type) {
		return (type instanceof IntTypeRef);
	}
}
class FakeOpType extends OpType {
	FakeOpType(String o) {
		super(o);
	}
	@Override boolean containsType(TypeRef type) {
		return false;
	}
}
