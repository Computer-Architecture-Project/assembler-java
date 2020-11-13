package assembler.AbstractSyntaxTree.ParsedTree;

import java.util.ArrayList;

import assembler.AbstractSyntaxTree.AST;
import assembler.AbstractSyntaxTree.Instruction.Instruction;

public class Initial extends AST {
  public ArrayList<Instruction<?, ?, ?>> statements;

  public Initial(ArrayList<Instruction<?, ?, ?>> statements) {
    this.statements = statements;
  }
}
