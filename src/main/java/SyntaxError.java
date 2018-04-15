public class SyntaxError extends Exception {
    Location loc;
    SyntaxError (Location l) {
        loc = new Location(l);
    }
}
class ReDefineError extends SyntaxError {
    ReDefineError (Location l) {
        super(l);
    }
}
