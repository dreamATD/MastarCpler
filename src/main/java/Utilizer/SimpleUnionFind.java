package Utilizer;

public class SimpleUnionFind {
	int[] f, s;
	boolean[] val;
	int size;

	public SimpleUnionFind(int size, boolean[] v) {
		f = new int[size];
		s = new int[size];
		val = v;
		this.size = size;
		for (int i = 0; i < size; ++i) {
			f[i] = i;
			s[i] = 1;
		}
	}

	public int find(int x) {
		if (f[x] == x) return x;
		f[x] = find(f[x]);
		return f[x];
	}

	public boolean getVal(int x) {
		return val[find(x)];
	}

	public void merge(int x, int y) {
		int fx = find(x);
		int fy = find(y);
		if (s[fx] > s[fy]) {
			f[fy] = fx;
			s[fx] += s[fy];
			val[fx] = val[fx] && val[fy];
		} else {
			f[fx] = fy;
			s[fy] += s[fx];
			val[fy] = val[fx] && val[fy];
		}
	}
}
