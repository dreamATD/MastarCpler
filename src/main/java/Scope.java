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
			for (Map.Entry<String, TypeRef> ent: cur.entities.entrySet()) {
				if (ent.getValue() instanceof FuncTypeRef) {
					System.out.print("<func " + ent.getKey() + "> ");
				} else if (ent.getValue() instanceof VarTypeRef) {
					System.out.print("<var " + ent.getKey() + "> ");
				}
			}
			System.out.println();
		} else if (curScope instanceof GeneralScope) {
			GeneralScope cur = (GeneralScope) curScope;
			System.out.print("GeneralScope: ");
			for (Map.Entry<String, TypeRef> ent: cur.entities.entrySet()) {
				if (ent.getValue() instanceof FuncTypeRef) {
					System.out.print("<func " + ent.getKey() + "> ");
				} else if (ent.getValue() instanceof VarTypeRef) {
					System.out.print("<var " + ent.getKey() + "> ");
				} else if (ent.getValue() instanceof ClassDefTypeRef) {
					System.out.print("<class " + ent.getKey() + "> ");
				} else System.out.print("exception!");
			}
			System.out.println();
		} else {
			LocalScope cur = (LocalScope) curScope;
			System.out.print("LocalScope: ");
			for (Map.Entry<String, VarTypeRef> ent: cur.variables.entrySet()) {
				System.out.print("<var " + ent.getKey() + "> ");
			}
			System.out.println();
		}
		for (int i = 0; i < curScope.sonScopes.size(); ++i) {
			Scope sonScope = curScope.sonScopes.get(i);
			dfs(sonScope, indentation + "    ");
		}
	}
	TypeRef matchVarName(String name, Location loc, GeneralScope genScope) throws SyntaxError {
		Scope p = this;
		while (true) {
			if (p instanceof GeneralScope) {
				if (((GeneralScope) p).entities.containsKey(name)) {
					return ((GeneralScope) p).entities.get(name);
				}
			} else if (p instanceof LocalScope){
				if (((LocalScope) p).variables.containsKey(name)) {
					return ((LocalScope) p).variables.get(name);
				}
			}
			if (p == genScope) break;
			p = p.parent;
		}
		throw new NoDefinedVarError(loc);
	}
}
class GeneralScope extends Scope {
	Map<String, TypeRef> entities;
	GeneralScope(Scope pnt) {
		entities = new HashMap<String, TypeRef>();
		sonScopes = new LinkedList<Scope>();
		parent = pnt;
	}
	boolean insert(String str, TypeRef ent) {
		if (entities.containsKey(str)) return false;
		entities.put(str, ent);
		return true;
	}
	TypeRef getEntity(String obj) {
		return entities.get(obj);
	}
}
class ClassScope extends GeneralScope {
	ClassScope(Scope pnt) {
		super(pnt);
	}
}
class LocalScope extends Scope {
	Map<String, VarTypeRef> variables;
	LocalScope(Scope pnt) {
		variables = new HashMap<String, VarTypeRef>();
		sonScopes = new LinkedList<Scope>();
		parent = pnt;
	}
	boolean insert(String str, VarTypeRef ent) {
		if (variables.containsKey(str)) return false;
		variables.put(str, ent);
		return true;
	}
	TypeRef getVar(String var) {
		return variables.get(var);
	}
}
