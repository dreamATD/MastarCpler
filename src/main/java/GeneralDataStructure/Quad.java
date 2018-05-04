package GeneralDataStructure;

public class Quad {
	String op;
	String r1, r2, rt;
	String label;

	public Quad(String op, String rt, String r1) {
		this.op = op;
		this.rt = rt;
		this.r1 = r1;
	}
	public Quad(String op, String rt, String r1, String r2) {
		this.op = op;
		this.rt = rt;
		this.r1 = r1;
		this.r2 = r2;
	}
	public Quad(String op, String rt) {
		this.op = op;
		this.rt = rt;
	}
	public Quad(String op) {
		this.op = op;
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
	public String getRt() {
		return rt;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String l) {
		label = l;
	}
}
