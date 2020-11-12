package assembler.AbstractSyntaxTree.ParsedTree;

import assembler.AbstractSyntaxTree.AST;
import assembler.AbstractSyntaxTree.Instruction.Instruction;

public class Initial extends AST {
  public Instruction[] statements;

  public Initial(Instruction[] statements) {
    this.statements = statements;
  }
}
