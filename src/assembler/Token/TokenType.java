package assembler.Token;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public enum TokenType {
  // R-TYPE
  ADD("add"),
  NAND("nand"),
  // I-TYPE
  LW("lw"),
  SW("sw"),
  BEQ("beq"),
  // J-TYPE
  JALR("jalr"),
  // O-TYPE
  HALT("halt"),
  NOOP("noop"),
  // SPECIAL TYPE
  FILL(".fill"),
  // misc
  PLUS("+"),
  MINUS("-"),
  INT("INTEGER"),
  WORD("WORD"),
  SPC("SPECIAL_CHAR"),
  EOL("END_OF_LINE"),
  EOF("END_OF_FILE");

  public final String value;
  private static Map<String, TokenType> valueEnumMap;

  private TokenType(String value) {
    this.value = value;
  }

  static {
    createValueEnumMap();
  }

  public static void createValueEnumMap() {
    valueEnumMap = new HashMap<String, TokenType>();
    for(TokenType type: TokenType.values()) {
      valueEnumMap.put(type.value, type);
    }
  }

  public static TokenType getEnum(String value) {
    // No value in token type
    if (valueEnumMap.get(value) == null) {
      throw new IllegalArgumentException();
    }

    return valueEnumMap.get(value);
  }

  public static Set<TokenType> R_TYPE() {
    Set<TokenType> types = new HashSet<TokenType>();
    types.add(TokenType.ADD);
    types.add(TokenType.NAND);
    return types;
  }

  public static Set<TokenType> I_TYPE() {
    Set<TokenType> types = new HashSet<TokenType>();
    types.add(TokenType.LW);
    types.add(TokenType.SW);
    types.add(TokenType.BEQ);
    return types;
  }

  public static Set<TokenType> J_TYPE() {
    Set<TokenType> types = new HashSet<TokenType>();
    types.add(TokenType.JALR);
    return types;
  }

  public static Set<TokenType> O_TYPE() {
    Set<TokenType> types = new HashSet<TokenType>();
    types.add(TokenType.HALT);
    types.add(TokenType.NOOP);
    return types;
  }

  public static Set<TokenType> SPACIAL_TYPE() {
    Set<TokenType> types = new HashSet<TokenType>();
    types.add(TokenType.FILL);
    return types;
  }

  public static Set<TokenType> MNEMONIC() {
    Set<TokenType> types = new HashSet<TokenType>();
    types.addAll(TokenType.R_TYPE());
    types.addAll(TokenType.I_TYPE());
    types.addAll(TokenType.J_TYPE());
    types.addAll(TokenType.O_TYPE());
    types.addAll(TokenType.SPACIAL_TYPE());
    return types;
  }
}
