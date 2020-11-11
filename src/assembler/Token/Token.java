package assembler.Token;

import java.util.HashMap;
import java.util.Map;

public class Token {
  private TokenType type;
  private String value;
  private Integer line;
  private Integer column;

  public Token(TokenType type, String value, Integer line, Integer column) {
    this.type = type;
    // TODO: value store AnyType
    // value: Integer | Character | Null
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
