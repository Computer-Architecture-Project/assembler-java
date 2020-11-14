package error;

import assembler.Token.Token;

public abstract class Error extends Exception {
    public String message;
    public Token token;
    public ErrorCode errorCode;

    public Error(String message, Object... args) {
        super(message);

        this.message = message;
        
        for(Object arg : args) {
            if(arg instanceof Token) {
                this.token = (Token)arg;
            } else if(arg instanceof ErrorCode) {
                this.errorCode = (ErrorCode)arg;
            }
        }
    }
}
