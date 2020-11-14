package error;

public enum ErrorCode {

    UNEXPECTED_COMMAND("Unexpected command"),
    UNEXPECTED_ARGUMENTS("Unexpected arguments"),
    DUPLICATE_LABEL("Label has already been declared"),
    UNEXPECTED_TOKEN("Unexpected token"),
    EMPTY_STATEMENT_LIST("Statement list is empty");

    public final String value;
    private ErrorCode(String value) {
        this.value = value;
    }
}
