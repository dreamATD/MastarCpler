package SemanticProc;

import org.antlr.v4.runtime.Token;

public class Location {
	public int line, column;
	public Location(int l, int c) {
		line = l;
		column = c;
	}
	public Location(Location other) {
		line = other.line;
		column = other.column;
	}
	public Location(Token t) {
		line = t.getLine();
		column = t.getCharPositionInLine();
	}
	public String toString() {
		return "Line " + line + ", Column " + column;
	}
}

