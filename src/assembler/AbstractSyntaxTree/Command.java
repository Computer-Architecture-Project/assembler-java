package assembler.AbstractSyntaxTree;

import assembler.Token.Token;

public class Command extends AST {
  public Token token;
  public Object value;

  public Command(Token token) {
    this.token = token;
    this.value = token.value;
  }
}
