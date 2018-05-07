package FrontEnd;

import java.util.ArrayList;

public class LabelTable {
	ArrayList<String> table;

	public LabelTable() {
		table = new ArrayList<>();
	}

	public void set(int t, String a) {
		table.set(t, a);
	}

	public void add(int t) {
		while (t >= table.size()) table.add(null);
	}

	public String get(int t) {
		return table.get(t);
	}
}
