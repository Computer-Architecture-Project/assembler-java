package error;

public class ParserError extends Error {
    public ParserError(String message, Object... args) {
        super(message, args);
    }
    
}
