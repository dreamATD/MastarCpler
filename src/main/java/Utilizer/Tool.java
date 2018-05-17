package Utilizer;

public class Tool {
	public static <T> void swap(T a, T b) {
		T c;
		c = a;
		a = b;
		b = c;
	}
}
