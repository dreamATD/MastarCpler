package Utilizer;

import java.util.ArrayList;

public class Tool {
	public static <T> void swap(T a, T b) {
		T c;
		c = a;
		a = b;
		b = c;
	}
	public static <T> void unique(ArrayList<T> list) {
		int i, j;
		for (i = 1, j = 0; i < list.size(); ++i) {
			if (!list.get(i).equals(list.get(j))) list.set(++j, list.get(i));
		}
		++j;
		while (j < list.size()) list.remove(list.size() - 1);
	}

	public static boolean isPow2(long t) {
		while (t != 1) {
			if ((t & 1) == 1) return false;
			t >>= 1;
		}
		return true;
	}

	public static long log2(long x) {
		long len = 0;
		while (x != 1) {
			++len;
			x >>= 1;
		}
		return len;
	}
}
