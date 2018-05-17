package Utilizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class UnionFind<T> {
	HashMap<T, T> pnt;
	HashMap<T, Integer> size;

	public void buildGlobal(ArrayList<T> global, HashMap<T, Integer> nameIdx) {
		for (Map.Entry<T, T> entry: pnt.entrySet()) {
			T data = entry.getValue();
			if (!global.contains(data)) {
				nameIdx.put(data, global.size());
				global.add(data);
			}
		}
	}

	public T find(T a) {
		if (!pnt.containsKey(a)) pnt.put(a, a);
		T fa = pnt.get(a);
		if (fa.equals(a)) return a;
		else{
			T res = find(fa);
			pnt.replace(a, fa, res);
			return res;
		}
	}

	public void merge(T a, T b) {
		T fa = find(a);
		T fb = find(b);
		Integer sizeA = size.get(fa);
		Integer sizeB = size.get(fb);
		if (fa.equals(fb)) return;
		if (sizeA < sizeB) {
			pnt.replace(fa, fa, fb);
			sizeB += sizeA;
		} else {
			pnt.replace(fb, fb, fa);
			sizeA += sizeB;
		}
		assert(size.get(fa).equals(sizeA));
		assert(size.get(fb).equals(sizeB));
	}
}
