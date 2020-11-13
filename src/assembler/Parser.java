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
    // System.out.println("[eat]");
    if (type == null) {
      this.currentToken = this.getNextToken();
    } if (type instanceof TokenType) {
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
      // TODO: throw
    }
  }

  public Label label() {
    // System.out.println("[label]");
    Label node = new Label(this.currentToken);
    this.eat(TokenType.WORD);
    return node;
  }

  public Register register() {
    // System.out.println("[register]");
    Register node = new Register(this.currentToken);
    this.eat(TokenType.INT);
    return node;
  }

  public Unary unary() {
    // System.out.println("[unary]");
    Unary node = null;

    if (this.currentToken.type == TokenType.PLUS || this.currentToken.type == TokenType.MINUS) {
      node = new Unary(this.currentToken);
      this.eat(null);
    }

    return node;
  }

  public Number number() {
    // System.out.println("[number]");
    Number node = new Number(this.currentToken);
    this.eat(TokenType.INT);
    return node;
  }

  public Offset offset() {
    // System.out.println("[offset]");
    Unary unaryNode = this.unary();
    Number numberNode = this.number();
    Offset node = new Offset(unaryNode, numberNode);
    return node;
  }

  public Object field() {
    // System.out.println("[field]");
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
    // System.out.println("[opcode]");
    Command node = new Command(this.currentToken);
    this.eat(TokenType.MNEMONIC());
    return node;
  }

  // return address and move to next address
  private Integer address() {
    // System.out.println("[address]");
    Integer node = this.address;
    this.address += 1;
    return node;
  }

  public RType rType() {
    // System.out.println("[r type]");
    Integer addressNode = this.address();
    Command opcodeNode = this.opcode();
    Register field0Node = this.register();
    Register field1Node = this.register();
    Register field2Node = this.register();

    return new RType(addressNode, opcodeNode, field0Node, field1Node, field2Node);
  }

  public IType iType() {
    // System.out.println("[i type]");
    Integer addressNode = this.address();
    Command opcodeNode = this.opcode();
    Register field0Node = this.register();
    Register field1Node = this.register();
    Object field2Node = this.field();

    return new IType(addressNode, opcodeNode, field0Node, field1Node, field2Node);
  }

  public JType jType() {
    // System.out.println("[j type]");
    Integer addressNode = this.address();
    Command opcodeNode = this.opcode();
    Register field0Node = this.register();
    Register field1Node = this.register();

    return new JType(addressNode, opcodeNode, field0Node, field1Node);
  }

  public OType oType() {
    // System.out.println("[o type]");
    Integer addressNode = this.address();
    Command opcodeNode = this.opcode();

    return new OType(addressNode, opcodeNode);
  }

  public FillType fillType() {
    // System.out.println("[fill type]");
    Integer addressNode = this.address();
    Command opcodeNode = this.opcode();
    Object fieldNode = this.field();
    return new FillType(addressNode, opcodeNode, fieldNode);
  }

  public Instruction<?, ?, ?> instruction() {
    // System.out.println("[instruction]");
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

    // throw error
    return null;
  }

  private void skipComment() {
    while (
      this.currentToken.type != TokenType.EOL && 
      this.currentToken.type != TokenType.EOF
    ) {
      // System.out.println(this.currentToken.type + " " + this.currentToken.value);
      this.eat(null);
    }
  }

  public Instruction<?, ?, ?> statement() {
    // System.out.println("[statement]");
    Instruction<?, ?, ?> instructionNode = this.instruction();
    this.skipComment();

    if (this.currentToken.type == TokenType.EOL) {
      this.skipEmptyLine();
    }

    return instructionNode;
  }

  public ArrayList<Instruction<?, ?, ?>> statementList() {
    // System.out.println("[statement list]");
    ArrayList<Instruction<?, ?, ?>> statements = new ArrayList<Instruction<?, ?, ?>>();
    while (TokenType.MNEMONIC().contains(this.currentToken.type)) {
      statements.add(this.statement());
    }

    // throw statement list is empty

    return statements;
  }

  public Method method() {
    // System.out.println("[method]");
    Label labelNode = this.label();
    ArrayList<Instruction<?, ?, ?>> statementsNode = this.statementList();
    return new Method(labelNode, statementsNode);
  }

  public Initial initial() {
    // System.out.println("[initial]");
    return new Initial(this.statementList());
  }

  public ParsedTree program() {
    // System.out.println("[program]");
    Initial initialNode = null;

    // System.out.println(this.currentToken.type);
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

  public ParsedTree parse() {
    ParsedTree node = this.program();
    // throw not end of file error
    return node;
  }

  public void log() {
      while (this.currentToken.type != TokenType.EOL) { 
        System.out.println(this.currentToken.type + " " + this.currentToken.value);
        this.getNextToken(); 
      }
      System.out.println(this.currentToken.type + " " + this.currentToken.value);
  }
}
