package GeneralDataStructure.OprandClass;

public class LocalMemAccess extends MemAccess {
	public LocalMemAccess(Oprand o) {
		super(null, o);
	}
	public LocalMemAccess(Oprand c, Oprand s) {
		super(null, c, s);
	}
}
