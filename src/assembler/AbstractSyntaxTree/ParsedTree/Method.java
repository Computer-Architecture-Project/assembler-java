package assembler.AbstractSyntaxTree.ParsedTree;

import assembler.AbstractSyntaxTree.AST;
import assembler.AbstractSyntaxTree.Label;
import assembler.AbstractSyntaxTree.Instruction.Instruction;

public class Method extends AST {
  public Label label;
  public Instruction<?, ?, ?>[] statements;

  public Method(Label label, Instruction<?, ?, ?>[] statements) {
    this.label = label;
    this.statements = statements;
  }

  public Integer address() {
    return this.statements[0].address();
  }
}
