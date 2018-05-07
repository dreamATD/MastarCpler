package GeneralDataStructure;

import FrontEnd.LabelTable;

import java.util.LinkedList;
import java.util.List;

public class LinearIR {
	LinkedList<FuncFrame> code;
	public LinearIR() {
		code = new LinkedList<>();
	}
	public void insertFunc(FuncFrame func) {
		code.add(func);
	}
	public void print() {
		for (int i = 0; i < code.size(); ++i) {
			code.get(i).print();
		}
	}
	public void updateAllLabel(LabelTable labels) {
		for (int i = 0; i < code.size(); ++i) {
			code.get(i).updateLabel(labels);
		}
	}
}
