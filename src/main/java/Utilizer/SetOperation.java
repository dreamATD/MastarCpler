package Utilizer;

import java.util.*;

public class SetOperation {
	public static <T> HashSet<T> union(HashSet<T> a, HashSet<T> b) {
		HashSet<T> c = new HashSet<>();
		c.addAll(a);
		c.addAll(b);
		return c;
	}

	/* If no change, don't change the pointer. */
	public static <T> void selfUnion(HashSet<T> a, HashSet<T> b) {
		a.addAll(b);
	}

	public static <T> HashSet<T> intersect(HashSet<T> a, HashSet<T> b) {
		HashSet<T> c = new HashSet<>();
		if (a.size() < b.size()) {
			c = a;
			a = b;
			b = a;
		}
		for (T data: a) {
			if (b.contains(data)) c.add(data);
		}
		return c;
	}

	public static <T> HashSet<T> minus(HashSet<T> a, HashSet<T> b) {
		HashSet<T> c = new HashSet<>();
		for (T data: a) {
			if (!b.contains(data)) c.add(data);
		}
		return c;
	}

	public static <T> LinkedList<T> intersect(LinkedList<T> a, LinkedList<T> b) {
		LinkedList<T> res = new LinkedList<>(), tmp;
		if (a.size() > b.size()) {
			tmp = a;
			a = b;
			b = tmp;
		}
		for (T data: b) {
			if (a.contains(data)) res.add(data);
		}
		return res;
	}
}
