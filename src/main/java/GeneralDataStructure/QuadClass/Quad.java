package GeneralDataStructure.QuadClass;

import FrontEnd.LabelTable;
import GeneralDataStructure.OprandClass.Oprand;
import GeneralDataStructure.OprandClass.Register;

import java.util.ArrayList;

/*
* new       rt [mem]
* mov       rt r1
* add       rt r1 r2
* sub       rt r1 r2
* mul       rt r1 r2
* div       rt r1 r2
* mod       rt r1 r2
* lsh       rt r1 r2
* rsh       rt r1 r2
* and       rt r1 r2
* or        rt r1 r2
* xor       rt r1 r2
* equ       rt r1 r2
* neq       rt r1 r2
* les       rt r1 r2
* leq       rt r1 r2
* gre       rt r1 r2
* geq       rt r1 r2
* not       rt r1
* neg       rt r1
*
* phi       rt phiList
*
* loadAI    rt r1 r2
*
* nop
*
* tbr       rt labelTrue labelFalse
* cbr       rt labelTrue labelFalse
* jump      label
* ret       rt
* ret
*
* param rt
* call  rt
*
* */

public class Quad {
	public String op;
	public Oprand r1, r2, rt;
	public String label;

	public ArrayList<Register> phiParams;

	public Quad(String op, Oprand rt, ArrayList<Register> phiParams) {
		this.op = op;
		this.rt = rt;
		this.phiParams = phiParams;
	}
	public Quad(String op, Oprand rt, Oprand r1) {
		this.op = op;
		this.rt = rt;
		this.r1 = r1;
	}
	public Quad(String op, Oprand rt, Oprand r1, Oprand r2) {
		this.op = op;
		this.rt = rt;
		this.r1 = r1;
		this.r2 = r2;
	}
	public Quad(String op, Oprand rt) {
		this.op = op;
		this.rt = rt;
	}
	public Quad(String op) {
		this.op = op;
	}
	public String getOp() {
		return op;
	}
	public String getR1Name() {
		return r1.get();
	}
	public String getR2Name() {
		return r2.get();
	}
	public String getRtName() {
		return rt.get();
	}
	public Oprand getR1() {
		return r1;
	}
	public Oprand getR2() {
		return r2;
	}
	public Oprand getRt() {
		return rt;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String l) {
		label = l;
	}
	public void print() {
		if (label != null) System.out.printf("%-20s", label + ": ");
		else System.out.printf("%-20s", " ");
		System.out.print(op);
		if (rt != null) System.out.print(" " + rt.get());
		if (r1 != null) System.out.print(" " + r1.get());
		if (r2 != null) System.out.print(" " + r2.get());
		if (phiParams != null) {
			for (int i = 0; i < phiParams.size(); ++i) {
				System.out.print(" " + phiParams.get(i).get());
			}
		}
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
				rt.set(labels.get(Integer.parseInt(rt.get())));
				break;
			case "tbr":
			case "cbr":
				r1.set(labels.get(Integer.parseInt(r1.get())));
				r2.set(labels.get(Integer.parseInt(r2.get())));
				break;
		}
	}
	public void setR1(String r1) {
		this.r1.set(r1);
	}
	public void setR2(String r2) {
		this.r2.set(r2);
	}
	public void setRt(String rt) {
		this.rt.set(rt);
	}
	public void addPhiParams(String x) {
		phiParams.add(new Register(x));
	}
}
