import java.util.*;

public class Scope {
	List<Scope> sonScopes;
	Scope parent;
	void pushScope(Scope ls) {
		sonScopes.add(ls);
	}
	static GeneralScope newGeneralScope(Scope pnt) {
		GeneralScope sonScope = new GeneralScope(pnt);
		pnt.pushScope(sonScope);
		return sonScope;
	}
	static ClassScope newClassScope(Scope pnt) {
		ClassScope sonScope = new ClassScope(pnt);
		pnt.pushScope(sonScope);
		return sonScope;
	}
	static LocalScope newLocalScope(Scope pnt) {
		LocalScope sonScope = new LocalScope(pnt);
		pnt.pushScope(sonScope);
		return sonScope;
	}

	static void dfs(Scope curScope, String indentation) {
		System.out.print(indentation);
		if (curScope instanceof ClassScope) {
			ClassScope cur = (ClassScope) curScope;
			System.out.print("ClassScope: ");
			for (DefinedEntity ent: cur.entities.values()) {
				if (ent instanceof DefinedFunc) {
					System.out.print("<func " + ent.id + "> ");
				} else if (ent instanceof DefinedVariable) {
					System.out.print("<var " + ent.id + "> ");
				}
			}
			System.out.println();
		} else if (curScope instanceof GeneralScope) {
			GeneralScope cur = (GeneralScope) curScope;
			System.out.print("GeneralScope: ");
			for (DefinedEntity ent: cur.entities.values()) {
				if (ent instanceof DefinedFunc) {
					System.out.print("<func " + ent.id + "> ");
				} else if (ent instanceof DefinedVariable) {
					System.out.print("<var " + ent.id + "> ");
				} else if (ent instanceof DefinedClass) {
					System.out.print("<class " + ent.id + "> ");
				} else System.out.print("exception!");
			}
			System.out.println();
		} else {
			LocalScope cur = (LocalScope) curScope;
			System.out.print("LocalScope: ");
			for (DefinedVariable ent: cur.variables.values()) {
				System.out.print("<var " + ent.id + "> ");
			}
			System.out.println();
		}
		for (int i = 0; i < curScope.sonScopes.size(); ++i) {
			Scope sonScope = curScope.sonScopes.get(i);
			dfs(sonScope, indentation + "    ");
		}
	}
}
class GeneralScope extends Scope {
	Map<String, DefinedEntity> entities;
	GeneralScope(Scope pnt) {
		entities = new HashMap<String, DefinedEntity>();
		sonScopes = new LinkedList<Scope>();
		parent = pnt;
	}
	void insert(String str, DefinedEntity ent) throws SyntaxError {
		if (entities.containsKey(str)) throw new ReDefineError(ent.loc);
		entities.put(str, ent);
	}
	DefinedEntity check(String str) {
		return entities.get(str);
	}
}
class ClassScope extends GeneralScope {
	ClassScope(Scope pnt) {
		super(pnt);
	}
}
class LocalScope extends Scope {
	Map<String, DefinedVariable> variables;
	LocalScope(Scope pnt) {
		variables = new HashMap<String, DefinedVariable>();
		sonScopes = new LinkedList<Scope>();
		parent = pnt;
	}
	void insert(String str, DefinedVariable ent) throws SyntaxError {
		if (variables.containsKey(str)) throw new ReDefineError(ent.loc);
		variables.put(str, ent);
	}
	DefinedVariable check(String str) {
		return variables.get(str);
	}
}
