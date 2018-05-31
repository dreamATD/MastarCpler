package GeneralDataStructure.QuadClass;

import FrontEnd.LabelTable;
import GeneralDataStructure.OprandClass.Oprand;
import GeneralDataStructure.OprandClass.Register;

import java.util.ArrayList;

/*
* A2Quad:
* mov       rt r1
*
* A3Quad:
* add       rt r1 r2
* sub       rt r1 r2
* mul       rt r1 r2
* div       rt r1 r2
* mod       rt r1 r2
* sal       rt r1 r2
* sar       rt r1 r2
* and       rt r1 r2
* or        rt r1 r2
* xor       rt r1 r2
* equ       rt r1 r2
* neq       rt r1 r2
* les       rt r1 r2
* leq       rt r1 r2
* gre       rt r1 r2
* geq       rt r1 r2
* not       rt r1     ( xor r1 (1<<31)-1 )
* neg       rt r1     ( sub 0  neg       )
*
* CondQuad
* cmp          r1 r2
*
* CJumpQuad
* je           r1 r2
* jne          r1 r2
* jl           r1 r2
* jle          r1 r2
* jg           r1 r2
* jge          r1 r2
*
* PhiQuad
* phi       rt phiList
*
* nop
*
* JumpQuad
* jump      label
*
* RetQuad
* ret       rt
* ret
*
* ParamQuad
* param        r1
*
* CallQuad
* call      rt func
* call    null func
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
		if (label != null) System.err.printf("%-20s", label + ": ");
		else System.err.printf("%-20s", " ");
		System.err.print(op);
		if (rt != null) System.err.print(" " + rt.get());
		if (r1 != null) System.err.print(" " + r1.get());
		if (r2 != null) System.err.print(" " + r2.get());
		if (phiParams != null) {
			for (int i = 0; i < phiParams.size(); ++i) {
				System.err.print(" " + phiParams.get(i).get());
			}
		}
		System.err.println();
	}
	public void updateLabel(LabelTable labels) {
		switch (op) {
			case "jump":
				rt.set(labels.get(Integer.parseInt(rt.get())));
				break;
			case "je": case "jne":
			case "jl": case "jle":
			case "jg": case "jge":
				r1.set(labels.get(Integer.parseInt(r1.get())));
				r2.set(labels.get(Integer.parseInt(r2.get())));
				break;
		}
	}
	public void setOp(String op) {
		this.op = op;
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
	public void changeOp(String op) {
		this.op = op;
	}
	public void changeR1(Oprand nr1) {
		r1 = nr1;
	}
	public void changeR2(Oprand nr2) {
		r2 = nr2;
	}
	public void changeRt(Oprand nrt) {
		rt = nrt;
	}
}
