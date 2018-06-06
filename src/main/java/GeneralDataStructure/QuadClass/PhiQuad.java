package GeneralDataStructure.QuadClass;

import GeneralDataStructure.OprandClass.Oprand;
import GeneralDataStructure.OprandClass.Register;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhiQuad extends Quad {
	public PhiQuad(String op, Oprand rt, HashMap<Integer, Register> phiParams) {
		super(op, rt, phiParams);
	}
	public ArrayList<Register> getParams() {
		ArrayList<Register> ret = new ArrayList<>();
		for (Map.Entry<Integer, Register> entry: phiParams.entrySet()) {
			ret.add(entry.getValue());
		}
		return ret;
	}
}
