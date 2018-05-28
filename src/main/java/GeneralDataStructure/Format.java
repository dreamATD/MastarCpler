package GeneralDataStructure;

public class Format {
	String label;
	String op;
	String r1;
	String r2;
	String r3;

	public Format() {
		label = op = r1 = r2 = r3 = null;
	}

	public Format(String o) {
		op = o;
	}

	public Format(String o, String op1) {
		op = o;
		r1 = op1;
	}

	public Format(String o, String op1, String op2) {
		op = o;
		r1 = op1;
		r2 = op2;
	}

	public Format(String o, String op1, String op2, String op3) {
		op = o;
		r1 = op1;
		r2 = op2;
		r3 = op3;
	}

	public String toString() {
		String res = String.format("%-8s", label == null ? " " : label + ": ");
		if (op != null) res += String.format("%-8s", op);
		if (r1 != null) res += r1 + ", ";
		if (r2 != null) res += r2 + ", ";
		if (r3 != null) res += r3 + ", ";
		if (op != null)res = res.substring(0, res.length() - 2);
		return res;
	}

	public String getLabel() {
		return label;
	}

	public String getOp() {
		return op;
	}

	public String getR1() {
		return r1;
	}

	public String getR2() {
		return r2;
	}

	public String getR3() {
		return r3;
	}

	public void setLabel(String l) {
		label = l;
	}

	public void setR1(String r) {
		r1 = r;
	}

	public void setR2(String r) {
		r2 = r;
	}
}
