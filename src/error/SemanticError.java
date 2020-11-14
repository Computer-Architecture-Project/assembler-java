package error;

public class SemanticError extends Error {
    public SemanticError(String message, Object... args) {
        super(message, args);
    }
}
