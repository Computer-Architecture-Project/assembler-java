package assembler;

import java.util.HashSet;
import java.util.Set;

import assembler.Token.Token;
import assembler.Token.TokenType;

public class Lexer {
  private String text;
  private Integer position;
  private Character currentCharacter;
  private Integer line;
  private Integer column;

  private final Set<Character> WHITESPACE = new HashSet<Character>(' ', '\t');

  public Lexer(String text) {
    if (text.length() > 0) {
      this.text = text;
    } else {
      this.text = " ";
    }

    this.position = 0;

    this.currentCharacter = this.text.charAt(this.position);

    this.line = 1;
    this.column = 1;
  }

  private void advance() {
    if (this.currentCharacter == '\n') {
      this.line += 1;
      this.column = 0;
    }

    this.position += 1;

    if (this.position > this.text.length() - 1) {
      this.currentCharacter = null;
    } else {
      this.currentCharacter = this.text.charAt(this.position);
      this.column += 1;
    }
  }

  private Token newLine() {
    Token token = new Token(TokenType.EOL, null, this.line, this.column);
    this.advance();
    return token;
  }

  private void skipWhiteSpace() {
    while (this.currentCharacter != null && WHITESPACE.contains(this.currentCharacter)) {
      this.advance();
    }
  }

  private Token<String> word() {
    Token<String> token = new Token<String>(null, null, this.line, this.column);

    String value = "" + this.currentCharacter;
    this.advance();

    while (this.currentCharacter != null && Character.isLetterOrDigit(this.currentCharacter)) {
      value += this.currentCharacter;
      this.advance();
    }

    TokenType tokenType = Token.RESERVED_KEYWORDS().get(value);
    if (tokenType == null) {
      token.type = TokenType.WORD;
      token.value = value;
    } else {
      token.type = tokenType;
      token.value = value;
    }

    return token;
  }

  private Token<Integer> number() {
    Token<Integer> token = new Token<Integer>(null, null, this.line, this.column);

    String value = "";
    while (this.currentCharacter != null && Character.isDigit(this.currentCharacter)) {
      value += this.currentCharacter;
      this.advance();
    }

    token.type = TokenType.INT;
    token.value = Integer.parseInt(value);

    return token;
  }

  private Token<Character> specialCharacter() {
    Token<Character> token = new Token<Character>(TokenType.SPC, this.currentCharacter, this.line, this.column);
    this.advance();
    return token;
  } 

  public Token getNextToken() {
    while (this.currentCharacter != null) {
      if (this.currentCharacter == '\n') {
        return this.newLine();
      }

      if (WHITESPACE.contains(this.currentCharacter)) {
        this.skipWhiteSpace();
        continue;
      }

      if (Character.isLetter(this.currentCharacter) || this.currentCharacter == '.') {
        return this.word();
      }

      if (Character.isDigit(this.currentCharacter)) {
        return this.number();
      }
  
      // TODO: try catch
      try {
        TokenType tokenType = TokenType.valueOf(this.currentCharacter.toString());
        this.advance();
        return new Token(tokenType, tokenType.value, this.line, this.column);
      } catch(Exception exception) {
        throw exception;
      }
      
    }
    return new Token(TokenType.EOF, null);
  }
}
