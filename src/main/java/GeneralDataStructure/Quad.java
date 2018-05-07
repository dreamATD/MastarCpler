package GeneralDataStructure;

import FrontEnd.LabelTable;

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
	void print() {
		if (label != null) System.out.printf("%-20s", label + ": ");
		else System.out.printf("%-20s", " ");
		System.out.print(op);
		if (rt != null) System.out.print(" " + rt);
		if (r1 != null) System.out.print(" " + r1);
		if (r2 != null) System.out.print(" " + r2);
		System.out.println();
	}
	public boolean isJump() {
		switch(op) {
			case "tbr": case "cbr": case "jump" : return true;
			default: return false;
		}
	}
	public void updateLabel(LabelTable labels) {
		switch (op) {
			case "jump":
				rt = labels.get(Integer.parseInt(rt));
				break;
			case "tbr":
			case "cbr":
				r1 = labels.get(Integer.parseInt(r1));
				r2 = labels.get(Integer.parseInt(r2));
				break;
		}
	}
}
