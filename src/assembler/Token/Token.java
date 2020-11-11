package assembler.Token;

import java.util.HashMap;
import java.util.Map;

public class Token <V> {
  public TokenType type;
  public V value;
  public Integer line;
  public Integer column;

  public Token(TokenType type, V value) {
    this.type = type;
    this.value = value;
  }

  public Token(TokenType type, V value, Integer line, Integer column) {
    this.type = type;
    this.value = value;
    this.line = line;
    this.column = column;
  }

  public static Map<String, TokenType> RESERVED_KEYWORDS() {
    Map<String, TokenType> keywords = new HashMap<String, TokenType>();
    for(TokenType type: TokenType.MNEMONIC()) {
      keywords.put(type.value, type);
    }
    return keywords;
  }
}
