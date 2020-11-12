package assembler;

import java.util.Set;

import assembler.AbstractSyntaxTree.Command;
import assembler.AbstractSyntaxTree.Label;
import assembler.AbstractSyntaxTree.Register;
import assembler.AbstractSyntaxTree.Unary;
import assembler.AbstractSyntaxTree.Number;
import assembler.AbstractSyntaxTree.Offset;
import assembler.Token.Token;
import assembler.Token.TokenType;

public class Parser {
  private Lexer lexer;
  private Integer address;
  private Token<?> currentToken;

  public Parser(Lexer lexer) {
    this.lexer = lexer;
    this.address = 0;
    this.skipEmptyLine();
  }

  private Token<?> getNextToken() {
    return this.lexer.getNextToken();
  }

  private void skipEmptyLine() {
    this.currentToken = this.getNextToken();
    while (this.currentToken.type == TokenType.EOL) {
      this.getNextToken();
    }
  }

  // Check Token Type Before Get Next Token
  private void eat(Object type) {
    if (type instanceof TokenType) {
      TokenType tokenType = (TokenType)type;
      if (tokenType == null || this.currentToken.type == tokenType) {
        this.getNextToken();
      }
    } else if(type instanceof Set) {
        Set<TokenType> types = (Set<TokenType>)type;
        if (types.contains(this.currentToken.type)) {
        this.getNextToken();
      }
    } else {
      // TODO: throw
    }
  }

  public Label label() {
    Label node = new Label(this.currentToken);
    this.eat(TokenType.WORD);
    return node;
  }

  public Register register() {
    Register node = new Register(this.currentToken);
    this.eat(TokenType.INT);
    return node;
  }

  public Unary unary() {
    Unary node = null;

    if (this.currentToken.type == TokenType.PLUS || this.currentToken.type == TokenType.MINUS) {
      node = new Unary(this.currentToken);
      this.eat(null);
    }

    return node;
  }

  public Number number() {
    Number node = new Number(this.currentToken);
    this.eat(TokenType.INT);
    return node;
  }

  public Offset offset() {
    Unary unaryNode = this.unary();
    Number numberNode = this.number();
    Offset node = new Offset(unaryNode, numberNode);
    return node;
  }

  public Object field() {
    Object node;

    if (
      this.currentToken.type == TokenType.INT || 
      this.currentToken.type == TokenType.PLUS ||
      this.currentToken.type == TokenType.MINUS
    ) {
      node = this.offset();
    } else {
      node = this.label();
    }

    return node;
  }

  public Command opcode() {
    Command node = new Command(this.currentToken);
    this.eat(TokenType.MNEMONIC());
    return node;
  }

  // return address and move to next address
  private Integer address() {
    Integer node = this.address;
    this.address += 1;
    return node;
  }
}
