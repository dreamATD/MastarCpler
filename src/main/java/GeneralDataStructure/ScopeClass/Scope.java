package GeneralDataStructure.ScopeClass;

import GeneralDataStructure.SymbolTable;
import javafx.util.Pair;

import java.util.*;

public class Scope<T> {
	public List<Scope<T> > sonScopes;
	public Scope<T> parent;
	public SymbolTable<T> table;
	String name;

	public String getName() {
		return name;
	}

	public Scope(Scope<T> pnt, String n) {
		sonScopes = new ArrayList<>();
		table = new SymbolTable<>();
		parent = pnt;
		name = n;
	}

	public SymbolTable<T> getTable() {
		return table;
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
		GeneralScope<L> sonScope = new GeneralScope<>(pnt, pnt.name + '.' + Integer.toString(pnt.sonScopes.size()));
		pnt.pushScope(sonScope);
		return sonScope;
	}

	public static <L> ClassScope<L> newClassScope(Scope<L> pnt) {
		ClassScope<L> sonScope = new ClassScope<>(pnt, pnt.name + '.' + Integer.toString(pnt.sonScopes.size()));
		pnt.pushScope(sonScope);
		return sonScope;
	}

	public static <L> LocalScope<L> newLocalScope(Scope<L> pnt) {
		LocalScope<L> sonScope = new LocalScope<>(pnt, pnt.name + '.' + Integer.toString(pnt.sonScopes.size()));
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

	public Pair<Scope<T>, T> matchVarName(String name) {
		Scope<T> p = this;
		while (p != null) {
			T ret = p.table.find(name);
			if (ret != null) return new Pair<>(p, ret);
			p = p.parent;
		}
		return null;
	}
}
