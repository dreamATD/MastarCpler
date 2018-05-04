package GeneralDataStructure.ScopeClass;

import GeneralDataStructure.SymbolTable;
import javafx.util.Pair;

import java.util.*;

public class Scope<T> {
	public List<Scope<T> > sonScopes;
	public Scope<T> parent;
	public SymbolTable<T> table;

	// mem start
	int base;

	public Scope(Scope<T> pnt) {
		sonScopes = new ArrayList<>();
		table = new SymbolTable<>();
		parent = pnt;
	}

	public void pushScope(Scope<T> ls) {
		sonScopes.add(ls);
	}

	public boolean addItem(String name, T info) {
		return table.insert(new Pair<>(name, info));
	}

	public T findItem(String name) {
		return table.find(name);
	}

	public static <L> GeneralScope<L> newGeneralScope(Scope<L> pnt) {
		GeneralScope<L> sonScope = new GeneralScope<>(pnt);
		pnt.pushScope(sonScope);
		return sonScope;
	}

	public static <L> ClassScope<L> newClassScope(Scope<L> pnt) {
		ClassScope<L> sonScope = new ClassScope<>(pnt);
		pnt.pushScope(sonScope);
		return sonScope;
	}

	public static <L> LocalScope<L> newLocalScope(Scope<L> pnt) {
		LocalScope<L> sonScope = new LocalScope<>(pnt);
		pnt.pushScope(sonScope);
		return sonScope;
	}

	public void dfs(String indentation) {
		System.out.print(indentation + this.toString() + ": ");
		table.println();
		for (int i = 0; i < sonScopes.size(); ++i) {
			Scope<T> sonScope = sonScopes.get(i);
			sonScope.dfs(indentation + "    ");
		}
	}

	public Pair<Scope<T>, T> matchVarName(String name, GeneralScope<T> genScope) {
		Scope<T> p = this;
		while (true) {
			T ret = p.table.find(name);
			if (ret != null) return new Pair<>(p, ret);
			if (p == genScope) break;
			p = p.parent;
		}
		return null;
	}
}
