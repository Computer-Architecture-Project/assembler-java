package error;

public class LexerError extends Error {
    public LexerError(String message, Object... args) {
        super(message, args);
    }
}
