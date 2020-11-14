package error;

public class InstructionError extends Error {
    public InstructionError(String message, Object... args) {
        super(message, args);
    }
}
