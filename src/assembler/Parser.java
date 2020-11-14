package assembler;

import java.util.ArrayList;
import java.util.Set;

import assembler.AbstractSyntaxTree.Command;
import assembler.AbstractSyntaxTree.Label;
import assembler.AbstractSyntaxTree.Register;
import assembler.AbstractSyntaxTree.Unary;
import assembler.AbstractSyntaxTree.Instruction.FillType;
import assembler.AbstractSyntaxTree.Instruction.IType;
import assembler.AbstractSyntaxTree.Instruction.Instruction;
import assembler.AbstractSyntaxTree.Instruction.JType;
import assembler.AbstractSyntaxTree.Instruction.OType;
import assembler.AbstractSyntaxTree.Instruction.RType;
import assembler.AbstractSyntaxTree.ParsedTree.Initial;
import assembler.AbstractSyntaxTree.ParsedTree.Method;
import assembler.AbstractSyntaxTree.ParsedTree.ParsedTree;
import assembler.AbstractSyntaxTree.Number;
import assembler.AbstractSyntaxTree.Offset;
import assembler.Token.Token;
import assembler.Token.TokenType;
import error.ErrorCode;
import error.ParserError;

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
  private void eat(Object type) throws ParserError {
    if (type == null) {
      this.currentToken = this.getNextToken();
    } else if (type instanceof TokenType) {
      TokenType tokenType = (TokenType)type;
      if (tokenType == null || this.currentToken.type == tokenType) {
        this.currentToken = this.getNextToken();
      }
    } else if(type instanceof Set) {
        Set<TokenType> types = (Set<TokenType>)type;
        if (types.contains(this.currentToken.type)) {
          this.currentToken = this.getNextToken();
      }
    } else {
      this.error(ErrorCode.UNEXPECTED_TOKEN, this.currentToken);
    }
  }

  public Label label() throws ParserError {
    Label node = new Label(this.currentToken);
    this.eat(TokenType.WORD);
    return node;
  }

  public Register register() throws ParserError {
    Register node = new Register(this.currentToken);
    this.eat(TokenType.INT);
    return node;
  }

  public Unary unary() throws ParserError {
    Unary node = null;

    if (this.currentToken.type == TokenType.PLUS || this.currentToken.type == TokenType.MINUS) {
      node = new Unary(this.currentToken);
      this.eat(null);
    }

    return node;
  }

  public Number number() throws ParserError {
    Number node = new Number(this.currentToken);
    this.eat(TokenType.INT);
    return node;
  }

  public Offset offset() throws ParserError {
    Unary unaryNode = this.unary();
    Number numberNode = this.number();
    Offset node = new Offset(unaryNode, numberNode);
    return node;
  }

  public Object field() throws ParserError {
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

  public Command opcode() throws ParserError {
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

  public RType rType() throws ParserError {
    Integer addressNode = this.address();
    Command opcodeNode = this.opcode();
    Register field0Node = this.register();
    Register field1Node = this.register();
    Register field2Node = this.register();

    return new RType(addressNode, opcodeNode, field0Node, field1Node, field2Node);
  }

  public IType iType() throws ParserError {
    Integer addressNode = this.address();
    Command opcodeNode = this.opcode();
    Register field0Node = this.register();
    Register field1Node = this.register();
    Object field2Node = this.field();

    return new IType(addressNode, opcodeNode, field0Node, field1Node, field2Node);
  }

  public JType jType() throws ParserError {
    Integer addressNode = this.address();
    Command opcodeNode = this.opcode();
    Register field0Node = this.register();
    Register field1Node = this.register();

    return new JType(addressNode, opcodeNode, field0Node, field1Node);
  }

  public OType oType() throws ParserError {
    Integer addressNode = this.address();
    Command opcodeNode = this.opcode();

    return new OType(addressNode, opcodeNode);
  }

  public FillType fillType() throws ParserError {
    Integer addressNode = this.address();
    Command opcodeNode = this.opcode();
    Object fieldNode = this.field();
    return new FillType(addressNode, opcodeNode, fieldNode);
  }

  public Instruction<?, ?, ?> instruction() throws ParserError {
    if (TokenType.R_TYPE().contains(this.currentToken.type)) {
      return this.rType();
    }

    if (TokenType.I_TYPE().contains(this.currentToken.type)) {
      return this.iType();
    }

    if (TokenType.J_TYPE().contains(this.currentToken.type)) {
      return this.jType();
    }

    if (TokenType.O_TYPE().contains(this.currentToken.type)) {
      return this.oType();
    }

    if (this.currentToken.type == TokenType.FILL) {
      return this.fillType();
    }

    this.error(ErrorCode.UNEXPECTED_TOKEN, this.currentToken);
    return null;
  }

  private void skipComment() throws ParserError {
    while (
      this.currentToken.type != TokenType.EOL && 
      this.currentToken.type != TokenType.EOF
    ) {
      this.eat(null);
    }
  }

  public Instruction<?, ?, ?> statement() throws ParserError {
    Instruction<?, ?, ?> instructionNode = this.instruction();
    this.skipComment();

    if (this.currentToken.type == TokenType.EOL) {
      this.skipEmptyLine();
    }

    return instructionNode;
  }

  public ArrayList<Instruction<?, ?, ?>> statementList() throws ParserError {
    ArrayList<Instruction<?, ?, ?>> statements = new ArrayList<Instruction<?, ?, ?>>();
    while (TokenType.MNEMONIC().contains(this.currentToken.type)) {
      statements.add(this.statement());
    }

    // throw statement list is empty
    if(statements.size() == 0) {
      this.error(ErrorCode.UNEXPECTED_COMMAND, this.currentToken);
    }

    return statements;
  }

  public Method method() throws ParserError {
    Label labelNode = this.label();
    ArrayList<Instruction<?, ?, ?>> statementsNode = this.statementList();
    return new Method(labelNode, statementsNode);
  }

  public Initial initial() throws ParserError {
    return new Initial(this.statementList());
  }

  public ParsedTree program() throws ParserError {
    Initial initialNode = null;

    if (TokenType.MNEMONIC().contains(this.currentToken.type)) {
      initialNode = this.initial();
    }

    ArrayList<Method> methods = new ArrayList<Method>();
    if (this.currentToken.type == TokenType.WORD) {
      while (this.currentToken.type == TokenType.WORD) {
        methods.add(this.method());
      }
    }

    return new ParsedTree(initialNode, methods);
  }

  public ParsedTree parse() throws ParserError {
    ParsedTree node = this.program();
    if(this.currentToken.type != TokenType.EOF) {
      this.error(ErrorCode.UNEXPECTED_TOKEN, this.currentToken);
    }
    return node;
  }

  private void error(ErrorCode errorCode, Token token) throws ParserError {
    String message = errorCode.value + " -> " + token;
    throw new ParserError(message, errorCode, token);
  }
}
