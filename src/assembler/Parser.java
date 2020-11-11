package assembler;

import assembler.Token.Token;
import assembler.Token.TokenType;

public class Parser {
  private Lexer lexer;
  private Integer address;
  private Token currentToken;

  public Parser(Lexer lexer) {
    this.lexer = lexer;
    this.address = 0;
    this.skipEmptyLine();
  }

  private Token getNextToken() {
    return this.lexer.getNextToken();
  }

  private void skipEmptyLine() {
    this.currentToken = this.getNextToken();
    while (this.currentToken.type == TokenType.EOL) {
      this.getNextToken();
    }
  }

  // public void parse()
}
