package assembler.AbstractSyntaxTree;

import assembler.Token.Token;

public class Unary extends AST {
  public Token<?> token;
  public Object value;

  public Unary(Token<?> token) {
    this.token = token;
    this.value = token.value;
  }
}
