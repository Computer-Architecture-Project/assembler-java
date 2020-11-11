package assembler.AbstractSyntaxTree;

import assembler.Token.Token;

public class Label extends AST {
  public Token token;
  public Object value;

  public Label(Token token) {
    this.token = token;
    this.value = token.value;
  }
}
