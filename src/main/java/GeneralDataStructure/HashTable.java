package GeneralDataStructure;

import javafx.util.Pair;
import java.util.*;

public class HashTable<K, T> {
	ArrayList< LinkedList< Pair<K, T> > > arr;
	static int p = 1447;

	public HashTable() {
		arr = new ArrayList<>(p);
		for (int i = 0; i < p; ++i) arr.add(null);
	}

	public boolean containsKey(K other) {
		return find(other) == null;
	}

	public T find(K other) {
		int t = other.hashCode() % p;
		t = (t + p) % p;
//		System.out.println("hash_code for " + other.toString() + ": " + t);
		if (arr.get(t) == null) return null;
		LinkedList<Pair<K, T>> list = arr.get(t);
		for (Iterator< Pair<K, T> > iter = list.iterator(); iter.hasNext(); ) {
			Pair<K, T> pir = iter.next();
			if (pir.getKey().equals(other)) return pir.getValue();
		}
		return null;
	}

	public void insert(K key, T value) {
		insert(new Pair<>(key, value));
	}

	public void insert(Pair<K, T> other) {
		int t = other.getKey().hashCode() % p;
		t = (t + p) % p;
//		System.out.println("hash_code for " + other.toString() + ": " + t);
		if (arr.get(t) == null) arr.set(t, new LinkedList<>());
		arr.get(t).add(other);
	}

	public void clear() {
		for (int i = 0; i < arr.size(); ++i) {
			if (arr.get(i) != null) arr.set(i, null);
		}
	}

	public void remove(K other) {
		int t = other.hashCode() % p;
		if (arr.get(t) == null) return;
		LinkedList< Pair<K, T> > list = arr.get(t);
		Iterator< Pair<K, T> > iter = list.iterator();
		while (iter.hasNext()) {
			Pair<K, T> a = iter.next();
			if (a.getKey().equals(other)) {
				iter.remove();
				return;
			}
		}
	}

	public void println() {
		for (int i = 0; i < arr.size(); ++i) {
			LinkedList< Pair<K, T> > list = arr.get(i);
			if (list == null) continue;
			for (Pair<K, T> a: list) {
				System.out.print("<" + a.getKey().toString() + " " + a.getValue().toString() + "> ");
			}
		}
		System.out.println();
	}

	public HashMap<K, T> toHashMap() {
		HashMap<K, T> ret = new HashMap<>();
		for (int i = 0; i < arr.size(); ++i) {
			LinkedList< Pair<K, T> > list = arr.get(i);
			if (list == null) continue;
			for (Pair<K, T> a: list) {
				ret.put(a.getKey(), a.getValue());
			}
		}
		return ret;
	}

	public void set(K key, T value) {
		int t = key.hashCode() % p;
		t = (t + p) % p;
//		System.out.println("hash_code for " + other.toString() + ": " + t);
		if (arr.get(t) == null) arr.set(t, new LinkedList<>());
		LinkedList< Pair<K, T> > list = arr.get(t);
		for (ListIterator< Pair<K, T> > iter = list.listIterator(); iter.hasNext(); ) {
			Pair<K, T> ret = iter.next();
			if (ret.getKey().equals(key)) {
				iter.set(new Pair<>(key, value));
			}
		}
	}
}
