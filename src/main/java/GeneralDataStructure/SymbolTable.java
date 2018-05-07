package GeneralDataStructure;

import javafx.util.Pair;

import java.util.HashMap;

public class SymbolTable<T> {
	HashTable<String, T> table = new HashTable<>();

	public void println() {
		table.println();
	}

	public T find(String name) {
		Pair<String, T> res = table.find(name);
		if (res == null) return null;
		return res.getValue();
	}

	public boolean insert(Pair<String, T> item) {
		if (table.find(item.getKey()) != null) return false;
		table.insert(item);
		return true;
	}

	public HashMap<String, T> toHashMap() {
		return table.toHashMap();
	}
}