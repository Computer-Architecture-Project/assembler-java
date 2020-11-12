package assembler.AbstractSyntaxTree;

import assembler.Token.Token;

public class Register extends AST {
  public Token<?> token;
  public Object value;

  public Register(Token<?> token) {
    this.token = token;
    this.value = token.value;
  }
}
