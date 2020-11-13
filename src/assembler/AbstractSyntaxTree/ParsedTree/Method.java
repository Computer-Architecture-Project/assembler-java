package assembler.AbstractSyntaxTree.ParsedTree;

import java.util.ArrayList;

import assembler.AbstractSyntaxTree.AST;
import assembler.AbstractSyntaxTree.Label;
import assembler.AbstractSyntaxTree.Instruction.Instruction;

public class Method extends AST {
  public Label label;
  public ArrayList<Instruction<?, ?, ?>> statements;

  public Method(Label label, ArrayList<Instruction<?, ?, ?>> statements) {
    this.label = label;
    this.statements = statements;
  }

  public Integer address() {
    return this.statements.get(0).address();
  }
}
