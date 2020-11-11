package assembler.AbstractSyntaxTree;

import assembler.Token.Token;

public class Number extends AST {
  public Token token;
  public Object value;

  public Number(Token token) {
    this.token = token;
    this.value = token.value;
  }
}
