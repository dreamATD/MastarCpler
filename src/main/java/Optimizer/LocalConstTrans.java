package Optimizer;

import GeneralDataStructure.BasicBlock;
import GeneralDataStructure.FuncFrame;
import GeneralDataStructure.MyListClass.MyList;
import GeneralDataStructure.OprandClass.GeneralMemAccess;
import GeneralDataStructure.OprandClass.Register;
import GeneralDataStructure.QuadClass.A3Quad;
import GeneralDataStructure.QuadClass.LeaQuad;
import GeneralDataStructure.QuadClass.MovQuad;
import GeneralDataStructure.QuadClass.Quad;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LocalConstTrans {
	FuncFrame curFunc;
	HashMap<String, Register> leaValue;
	HashMap<Pair<String, Pair<String, String>>, Register> a3Value;
	HashMap<String, Register> movValue;
	public LocalConstTrans(FuncFrame func) {
		curFunc = func;
	}
	public void transConst() {
		for (BasicBlock b: curFunc.getBbList()) calc(b);
	}

	private boolean isExchangeable(String op) {
		switch (op) {
			case "sub": case "div": case "mod":
			case "sal": case "sar":
			case "not": case "neg":
				return false;
			default: return true;
		}
	}

	private void calc(BasicBlock block) {
		leaValue = new HashMap<>();
		a3Value = new HashMap<>();
		movValue = new HashMap<>();
		MyList<Quad> codes = block.getCodes();
		for (int i = 0; i < codes.size(); ++i) {
			Quad c = codes.get(i);
			if (c instanceof LeaQuad) {
				if (leaValue.containsKey(c.getR1Name())) {
					codes.set(i, new MovQuad("mov", c.getRt(), leaValue.get(c.getR1Name())));
				} else if (c.getRt() instanceof Register) {
					leaValue.put(c.getR1Name(), (Register) c.getRt());
				}
			} else if (c instanceof A3Quad && c.getR1() instanceof Register && c.getR2() instanceof Register) {
				boolean flag = isExchangeable(c.getOp());
				Pair<String, Pair<String, String>> key1 = new Pair<>(c.getOp(), new Pair<>(c.getR1Name(), c.getR2Name()));
				Pair<String, Pair<String, String>> key2 = new Pair<>(c.getOp(), new Pair<>(c.getR2Name(), c.getR1Name()));
				if (a3Value.containsKey(key1) || flag && a3Value.containsKey(key2)) {
					if (a3Value.containsKey(key1)) codes.set(i, new MovQuad("mov", c.getRt(), a3Value.get(key1)));
					else codes.set(i, new MovQuad("mov", c.getRt(), a3Value.get(key2)));
				} else if (c.getRt() instanceof Register) {
					a3Value.put(key1, (Register) c.getRt());
				}
			}
		}
	}
}
