package GeneralDataStructure.QuadClass;

import GeneralDataStructure.OprandClass.Oprand;
import GeneralDataStructure.OprandClass.Register;

import java.util.ArrayList;

public class PhiQuad extends Quad {
	public PhiQuad(String op, Oprand rt, ArrayList<Register>phiParams) {
		super(op, rt, phiParams);
	}
	public ArrayList<Register> getParams() {
		return phiParams;
	}
}
