public class TempTestAst {
	private String delta = "    ";
	void dfs(Node u, String indentation) {
		u.print(indentation + delta);
		for (int i = 0; i < u.sons.size(); ++i)
			dfs(u.sons.get(i), indentation + delta);
	}
}
