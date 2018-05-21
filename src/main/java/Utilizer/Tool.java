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

	public static boolean isPow2(long t, Long ans) {
		ans = 0L;
		while (t > 1) {
			if ((t & 1) == 1) return false;
			++ans;
			t >>= 1;
		}
		return true;
	}
}
