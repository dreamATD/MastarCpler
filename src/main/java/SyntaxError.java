import javax.xml.ws.RequestWrapper;

public class SyntaxError extends Exception {
    Location loc;
    SyntaxError (Location l) {
        loc = new Location(l);
    }
    public String toString() {
        return "SyntaxError";
    }
}
class ReDefinedError extends SyntaxError {
    ReDefinedError(Location l) {
        super(l);
    }
    @Override public String toString() {
        return "ReDefinedError";
    }
}
class NoDefinedVarError extends SyntaxError {
    NoDefinedVarError(Location l) {
       super(l);
    }
    @Override public String toString() {
        return "NoDefinedVarError";
    }
}
class NoDefinedTypeError extends SyntaxError {
    NoDefinedTypeError(Location l) {
        super(l);
    }
    @Override public String toString() {
        return "NoDefinedTypeError";
    }
}
class WrongNameConsFunc extends SyntaxError {
    WrongNameConsFunc(Location l) {
        super(l);
    }
    @Override public String toString() {
        return "WrongNameConsFunc";
    }
}
class DisMatchedFormError extends SyntaxError {
    DisMatchedFormError(Location l) {
        super(l);
    }
    @Override public String toString() {
        return "DisMatchedFormError";
    }
}
class DisMatchedTypeError extends SyntaxError {
    DisMatchedTypeError(Location l) {
       super(l);
    }
    @Override public String toString() {
        return "DisMatchedTypeError";
    }
}
class DisMatchedFuncReturn extends SyntaxError {
    DisMatchedFuncReturn (Location l) {
        super(l);
    }
    @Override public String toString() {
        return "DisMatchedFuncReturn";
    }
}
class BrkOutIterStat extends SyntaxError {
    BrkOutIterStat (Location l) {
        super(l);
    }
    @Override public String toString() {
        return "BrkOutIterStat";
    }
}
class CntOutIterStat extends SyntaxError {
    CntOutIterStat (Location l) {
        super(l);
    }
    @Override public String toString() {
        return "CntOutIterStat";
    }
}
class NotConditionExpr extends SyntaxError {
    NotConditionExpr(Location l) {
        super(l);
    }
    @Override public String toString() {
        return "NotConditionExpr";
    }
}
class NoCastExpr extends SyntaxError {
    NoCastExpr(Location l) {
        super(l);
    }
    @Override public String toString() {
        return "NoCastExpr";
    }
}
class NotLeftValue extends SyntaxError {
    NotLeftValue(Location l) {
        super(l);
    }
    @Override public String toString() {
        return "NotLeftValue";
    }
}
class NullPointer extends SyntaxError {
    NullPointer(Location l) {
        super(l);
    }
    @Override public String toString() {
        return "NullPointer";
    }
}
class VoidDefVarError extends SyntaxError {
    VoidDefVarError (Location l) {
        super(l);
    }
    @Override public String toString() {
        return "VoidDefVarError";
    }
}
class NoMainFuncError extends SyntaxError {
	NoMainFuncError(Location l) {
		super(l);
	}
	@Override public String toString() {
		return "NoMainFuncError";
	}
}
class NoDefinedOpError extends SyntaxError {
	NoDefinedOpError(Location l) {
		super(l);
	}
	@Override public String toString() {
		return "NoDefinedOpError";
	}
}