package GeneralDataStructure;

import javafx.util.Pair;

import java.util.HashMap;

public class SymbolTable<T> {
	HashTable<String, T> table = new HashTable<>();

	public void println() {
		table.println();
	}

	public T find(String name) {
		return table.find(name);
	}

	public boolean insert(Pair<String, T> item) {
		if (table.find(item.getKey()) != null) return false;
		table.insert(item);
		return true;
	}

	public boolean insert(String a, T b) {
		return insert(new Pair<>(a, b));
	}

	public HashMap<String, T> toHashMap() {
		return table.toHashMap();
	}

	public int getSize() {
		return table.getSize();
	}
}
